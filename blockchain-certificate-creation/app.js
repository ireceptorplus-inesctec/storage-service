'use strict';

const { Gateway, Wallets } = require('fabric-network');
const FabricCAServices = require('fabric-ca-client');
const path = require('path');
const fs = require('fs');
const channel = process.env.CHANNEL_NAME;
const chaincode = process.env.CHAINCODE_NAME;

const admin_user = process.env.ORG_CA_ADMIN;
const admin_password = process.env.ORG_CA_ADMIN_PASSWORD;
const walletPath = process.env.WALLET_PATH;
const orgCADomain = process.env.ORG_CA_DOMAIN;



function prettyJSONString(inputString) {
    return JSON.stringify(JSON.parse(inputString), null, 2);
}

const loadCC = () => {
    const ccpPath = process.env.NETWORK_CONFIG_PATH;
    const ccp = JSON.parse(fs.readFileSync(ccpPath, 'utf8'));

    return ccp;
}

const loadContract = async (appUser) => {
    // load the network configuration
    const ccp = loadCC();

    const wallet = await Wallets.newFileSystemWallet(walletPath);

    // Check to see if we've already enrolled the user.
    const identity = await wallet.get(appUser);
    if (!identity) {
        console.log('An identity for the user ' + appUser + ' does not exist in the wallet');
        console.log('Run the registerUser.js application before retrying');
        return 1;
    }
    // Create a new gateway for connecting to our peer node.
    const gateway = new Gateway();

    await gateway.connect(ccp, { wallet, identity: appUser, discovery: { enabled: true, asLocalhost: false } });

    // Get the network (channel) our contract is deployed to.
    const network = await gateway.getNetwork(channel);

    // Get the contract from the network.
    const contract = network.getContract(chaincode);

    return { contract, gateway };
}

const enrollAdmin = async () => {
    try {
        // load the network configuration
        const ccp = loadCC();

        // Create a new CA client for interacting with the CA.
        const caInfo = ccp.certificateAuthorities[orgCADomain];
        const caTLSCACerts = caInfo.tlsCACerts.pem;
        const ca = new FabricCAServices(caInfo.url, { trustedRoots: caTLSCACerts, verify: false }, caInfo.caName);

        // Create a new file system based wallet for managing identities.
        const wallet = await Wallets.newFileSystemWallet(walletPath);
        console.log(`Wallet path: ${walletPath}`);

        // Check to see if we've already enrolled the admin user.
        const identity = await wallet.get(admin_user);
        if (identity) {
            console.log(`An identity for the admin user "${admin_user}" already exists in the wallet`);
            return;
        }

        // Enroll the admin user, and import the new identity into the wallet.
        const enrollment = await ca.enroll({ enrollmentID: admin_user, enrollmentSecret: admin_password });
        const x509Identity = {
            credentials: {
                certificate: enrollment.certificate,
                privateKey: enrollment.key.toBytes(),
            },
            mspId: 'Org1MSP',
            type: 'X.509',
        };
        await wallet.put(admin_user, x509Identity);
        console.log(`Successfully enrolled admin user "${admin_user}" and imported it into the wallet`);

    } catch (error) {
        console.error(`Failed to enroll admin user "${admin_user}": ${error}`);
        process.exit(1);
    }
}

const registerUser = async (appUser) => {
    try {
        // load the network configuration
        const ccp = loadCC();

        // Create a new CA client for interacting with the CA.
        const caURL = ccp.certificateAuthorities[orgCADomain].url;
        const ca = new FabricCAServices(caURL);

        // Create a new file system based wallet for managing identities.
        const wallet = await Wallets.newFileSystemWallet(walletPath);
        console.log(`Wallet path: ${walletPath}`);

        // Check to see if we've already enrolled the user.
        const userIdentity = await wallet.get(appUser);
        if (userIdentity) {
            console.log(`An identity for the user "${appUser}" already exists in the wallet`);
            return;
        }

        // Check to see if we've already enrolled the admin user.
        const adminIdentity = await wallet.get(admin_user);
        if (!adminIdentity) {
            console.log(`An identity for the admin user "${admin_user}" does not exist in the wallet`);
            console.log('Run "node certificateAuthorityActions.js admin" before retrying');
            return;
        }

        // build a user object for authenticating with the CA
        const provider = wallet.getProviderRegistry().getProvider(adminIdentity.type);
        const adminUser = await provider.getUserContext(adminIdentity, admin_user);

        // Register the user, enroll the user, and import the new identity into the wallet.
        const secret = await ca.register({
            affiliation: 'org1.department1',
            enrollmentID: appUser,
            role: 'client'
        }, adminUser);
        const enrollment = await ca.enroll({
            enrollmentID: appUser,
            enrollmentSecret: secret
        });
        const x509Identity = {
            credentials: {
                certificate: enrollment.certificate,
                privateKey: enrollment.key.toBytes(),
            },
            mspId: 'Org1MSP',
            type: 'X.509',
        };
        await wallet.put(appUser, x509Identity);
        console.log(`Successfully registered and enrolled user "${appUser}" with secret "${secret}" and imported it into the wallet`);

    } catch (error) {
        console.error(`Failed to register user "${appUser}": ${error}`);
        process.exit(1);
    }

    try
    {
        const { contract, gateway } = await loadContract(appUser);

        await contract.submitTransaction('enrollMyself');

        await gateway.disconnect();

    } catch (err)
    {
        console.error(`Failed to submit transaction: ${err}`);
        return {error: true, msg: String(err)};
    }
}

async function main() {
    let args = process.argv;

    if (args[2] === 'admin') {
        // node certificateAuthorityActions.js admin
        enrollAdmin(admin_password);
    } else if (args[2] === 'user') {
        // node certificateAuthorityActions.js user paulo
        let userId = args[3] || 'appUser';
        registerUser(userId);
    } else {
        console.error('Command not found');
    }
}

main();
