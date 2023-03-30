package com.ireceptorplus.storageService.BlockchainAPI;

import com.ireceptorplus.storageService.BlockchainAPI.BlockchainConfigProperties.HyperledgerCADetails;
import com.ireceptorplus.storageService.BlockchainAPI.BlockchainConfigProperties.HyperledgerNetworkConfig;
import com.ireceptorplus.storageService.BlockchainAPI.BlockchainConfigProperties.HyperledgerWalletDetails;
import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeoutException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class HyperledgerFabricAPITest extends HyperledgerFabricAPI
{
    protected HyperledgerCADetails hyperledgerCADetails;

    @Autowired
    public HyperledgerFabricAPITest(HyperledgerNetworkConfig hyperledgerNetworkConfig, HyperledgerWalletDetails hyperledgerWalletDetails, HyperledgerCADetails hyperledgerCADetails)
    {
        super(hyperledgerNetworkConfig, hyperledgerWalletDetails);
        this.hyperledgerCADetails = hyperledgerCADetails;
    }

    @Test
    public void initBlockchainTestAccounts()
    {
        try {
            enrollAdmin();
            registerUser();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ContractException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException e) {
            e.printStackTrace();
        } catch (EnrollmentException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (CryptoException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enrollAdmin() throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvalidArgumentException, CryptoException, CertificateException, org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException, EnrollmentException
    {
        // Create a CA client for interacting with the CA.
        Properties props = new Properties();
        props.put("pemFile", hyperledgerCADetails.getCertPath());
        props.put("allowAllHostNames", "true");
        HFCAClient caClient = HFCAClient.createNewInstance(hyperledgerCADetails.getBlockchainPeerUrl(), props);
        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
        caClient.setCryptoSuite(cryptoSuite);

        // Create a wallet for managing identities
        Wallet wallet = Wallets.newFileSystemWallet(Paths.get(hyperledgerWalletDetails.getPath()));

        // Check to see if we've already enrolled the admin user.
        if (wallet.get("admin") != null) {
            System.out.println("An identity for the admin user \"admin\" already exists in the wallet");
            return;
        }

        // Enroll the admin user, and import the new identity into the wallet.
        final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
        enrollmentRequestTLS.addHost("localhost");
        enrollmentRequestTLS.setProfile("tls");
        Enrollment enrollment = caClient.enroll(hyperledgerCADetails.getCaAdminUser(), hyperledgerCADetails.getCaAdminPassword(), enrollmentRequestTLS);
        Identity user = Identities.newX509Identity(hyperledgerCADetails.getMspId(), enrollment);
        wallet.put("admin", user);
        System.out.println("Successfully enrolled user \"admin\" and imported it into the wallet");
    }

    public void registerUser() throws Exception
    {
        String userId = hyperledgerWalletDetails.getUserId();
        // Create a CA client for interacting with the CA.
        Properties props = new Properties();
        props.put("pemFile", hyperledgerCADetails.getCertPath());
        props.put("allowAllHostNames", "true");
        HFCAClient caClient = HFCAClient.createNewInstance(hyperledgerCADetails.getBlockchainPeerUrl(), props);
        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
        caClient.setCryptoSuite(cryptoSuite);

        // Create a wallet for managing identities
        Wallet wallet = Wallets.newFileSystemWallet(Paths.get(hyperledgerWalletDetails.getPath()));

        // Check to see if we've already enrolled the user.
        if (wallet.get(userId) != null) {
            System.out.println("An identity for the user " + userId + " already exists in the wallet");
            return;
        }

        X509Identity adminIdentity = (X509Identity)wallet.get("admin");
        if (adminIdentity == null) {
            System.out.println("\"admin\" needs to be enrolled and added to the wallet first");
            return;
        }
        User admin = new User() {

            @Override
            public String getName() {
                return "admin";
            }

            @Override
            public Set<String> getRoles() {
                return null;
            }

            @Override
            public String getAccount() {
                return null;
            }

            @Override
            public String getAffiliation() {
                return hyperledgerCADetails.getAffiliation();
            }

            @Override
            public Enrollment getEnrollment() {
                return new Enrollment() {

                    @Override
                    public PrivateKey getKey() {
                        return adminIdentity.getPrivateKey();
                    }

                    @Override
                    public String getCert() {
                        return Identities.toPemString(adminIdentity.getCertificate());
                    }
                };
            }

            @Override
            public String getMspId() {
                return hyperledgerCADetails.getMspId();
            }

        };

        // Register the user, enroll the user, and import the new identity into the wallet.
        RegistrationRequest registrationRequest = new RegistrationRequest(userId);
        registrationRequest.setAffiliation(hyperledgerCADetails.getAffiliation());
        registrationRequest.setEnrollmentID(userId);
        String enrollmentSecret = caClient.register(registrationRequest, admin);
        Enrollment enrollment = caClient.enroll(userId, enrollmentSecret);
        Identity user = Identities.newX509Identity(hyperledgerCADetails.getMspId(), enrollment);
        wallet.put(userId, user);
        enrollMyself();
        System.out.println("Successfully enrolled user " + userId + " and imported it into the wallet");

    }
}
