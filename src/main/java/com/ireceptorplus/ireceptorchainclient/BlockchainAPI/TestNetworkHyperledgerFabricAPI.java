package com.ireceptorplus.ireceptorchainclient.BlockchainAPI;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeInputDataTypes.TraceabilityDataToBeSubmitted;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeReturnDataTypes.TraceabilityDataReturnType;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ProcessingDetails;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.ReproducibilityData;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.ReproducibleScript;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.Exceptions.BlockchainAPIException;
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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeoutException;

public class TestNetworkHyperledgerFabricAPI extends HyperledgerFabricAPI
{

    String blockchainDirectoryPath = "../ireceptorchain/";

    public TestNetworkHyperledgerFabricAPI(HyperledgerWalletDetails walletDetails)
    {
        super(new HyperledgerNetworkDetails("../ireceptorchain/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml",
                "mychannel", "ireceptorchain"),
                walletDetails);
    }

    public static void main(String[] args) throws Exception {
        HyperledgerWalletDetails walletDetailsCreator = new HyperledgerWalletDetails("wallet-creator", "creator");
        TestNetworkHyperledgerFabricAPI api = new TestNetworkHyperledgerFabricAPI(walletDetailsCreator);
        api.initBlockchainTestAccounts();
        createTestTraceabilityDataEntry(api);
        List<TraceabilityDataReturnType> dataReturnTypeList = api.getTraceabilityDataAwaitingValidation();
        api.submitVote(dataReturnTypeList.get(0), VoteType.YES);

        System.out.println("stuff");
    }

    private void initBlockchainTestAccounts()
    {
        try {
            enrollAdmin();
            registerUser();
            clientApp();
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

    private static void createTestTraceabilityDataEntry(TestNetworkHyperledgerFabricAPI api) throws BlockchainAPIException
    {
        ReproducibilityData reproducibilityData = new ReproducibilityData(new ArrayList<>(),
                new ReproducibleScript("https://repository.com/script.sh"),
                new ArrayList<>());
        TraceabilityDataToBeSubmitted data = new TraceabilityDataToBeSubmitted(
                "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad",
                "32443b8c5b4116cccf12c517a095e39c050ce7b53858efeff48f7597c3659487",
                new ProcessingDetails("igblast", "1.0",
                        "52da1b1b9e707c38e7e158422aa1ffb5bd8b71d29e66e6a1b8bf6da541cb0f3d",
                        "--config a",
                        reproducibilityData));
        api.createTraceabilityDataEntry(data);
    }

    private String resolveBlockchainCertsDirPath(String relativePath)
    {
        return blockchainDirectoryPath + relativePath;
    }

    private void enrollAdmin() throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvalidArgumentException, CryptoException, CertificateException, org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException, EnrollmentException
    {
        // Create a CA client for interacting with the CA.
        Properties props = new Properties();
        props.put("pemFile",
                resolveBlockchainCertsDirPath("test-network/organizations/peerOrganizations/org1.example.com/ca/ca.org1.example.com-cert.pem"));
        props.put("allowAllHostNames", "true");
        HFCAClient caClient = HFCAClient.createNewInstance("https://localhost:7054", props);
        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
        caClient.setCryptoSuite(cryptoSuite);

        // Create a wallet for managing identities
        Wallet wallet = Wallets.newFileSystemWallet(Paths.get("wallet"));

        // Check to see if we've already enrolled the admin user.
        if (wallet.get("admin") != null) {
            System.out.println("An identity for the admin user \"admin\" already exists in the wallet");
            return;
        }

        // Enroll the admin user, and import the new identity into the wallet.
        final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
        enrollmentRequestTLS.addHost("localhost");
        enrollmentRequestTLS.setProfile("tls");
        Enrollment enrollment = caClient.enroll("admin", "adminpw", enrollmentRequestTLS);
        Identity user = Identities.newX509Identity("Org1MSP", enrollment);
        wallet.put("admin", user);
        System.out.println("Successfully enrolled user \"admin\" and imported it into the wallet");
    }

    public void registerUser() throws Exception
    {
        String userId = hyperledgerWalletDetails.userId;
        // Create a CA client for interacting with the CA.
        Properties props = new Properties();
        props.put("pemFile",
                resolveBlockchainCertsDirPath("test-network/organizations/peerOrganizations/org1.example.com/ca/ca.org1.example.com-cert.pem"));
        props.put("allowAllHostNames", "true");
        HFCAClient caClient = HFCAClient.createNewInstance("https://localhost:7054", props);
        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
        caClient.setCryptoSuite(cryptoSuite);

        // Create a wallet for managing identities
        Wallet wallet = Wallets.newFileSystemWallet(Paths.get("wallet"));

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
                return "org1.department1";
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
                return "Org1MSP";
            }

        };

        // Register the user, enroll the user, and import the new identity into the wallet.
        RegistrationRequest registrationRequest = new RegistrationRequest(userId);
        registrationRequest.setAffiliation("org1.department1");
        registrationRequest.setEnrollmentID(userId);
        String enrollmentSecret = caClient.register(registrationRequest, admin);
        Enrollment enrollment = caClient.enroll(userId, enrollmentSecret);
        Identity user = Identities.newX509Identity("Org1MSP", enrollment);
        wallet.put(userId, user);
        System.out.println("Successfully enrolled user " + userId + " and imported it into the wallet");

    }

    public void clientApp() throws IOException, ContractException, InterruptedException, TimeoutException
    {
        String userId = hyperledgerWalletDetails.userId;
        // Load a file system based wallet for managing identities.
        Path walletPath = Paths.get("wallet");
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);
        // load a CCP
        Path networkConfigPath = Paths.get(resolveBlockchainCertsDirPath(""), "test-network", "organizations", "peerOrganizations", "org1.example.com", "connection-org1.yaml");

        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, userId).networkConfig(networkConfigPath).discovery(true);

        // create a gateway connection
        try (Gateway gateway = builder.connect()) {

            // get the network and contract
            Network network = gateway.getNetwork("mychannel");
            Contract contract = network.getContract("ireceptorchain");

            byte[] result;

            result = contract.evaluateTransaction("queryAllCars");
            System.out.println(new String(result));

            contract.submitTransaction("createCar", "CAR10", "VW", "Polo", "Grey", "Mary");

            result = contract.evaluateTransaction("queryCar", "CAR10");
            System.out.println(new String(result));

            contract.submitTransaction("changeCarOwner", "CAR10", "Archie");

            result = contract.evaluateTransaction("queryCar", "CAR10");
            System.out.println(new String(result));
        }
    }
}
