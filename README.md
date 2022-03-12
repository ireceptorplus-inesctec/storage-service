# iReceptorChain Client

This repository contains the back-end software of the client to interact with [iReceptorChain](https://gitlab.inesctec.pt/ireceptorplus/blockchain/ireceptorchain) prototype. The iReceptorChain client also consists of a front-end that is available at [this repository](https://gitlab.inesctec.pt/ireceptorplus/prototypes/storage-service-ionic-vue).

This repository contains the code for a java spring application that interacts with the front-end and the blockchain. Since it interacts with the blockchain, it is required to run the [iReceptorChain prototype](https://gitlab.inesctec.pt/ireceptorplus/blockchain/ireceptorchain) first, using the instruction available at the repository README.md.  It it is a GUI-based client that allows the user to create traceability data entries and validate other users' traceability data entries, submitting a vote accordingly to the blockchain.

## Test network
Since this is a client of the iReceptorChain blockchain prototype, it is required to run the iReceptorChain blockchain prototype first. This section will explain how to run both the iReceptorChain Storage Service and the iReceptorChain, using the test network provided by Hyperledger Fabric.

### Directory setup
It is also required that the iReceptorChain repository directory is at the same level as the ireceptorchain-client directory, since the java application will access the iReceptorChain directory to read the certificates to connect to the test network.

### Running

This repository currently supports two different running modes for the iReceptorChain Storage Service: development and deployment.

#### Development
The development mode assumes you will run the postgresql database inside a docker container and the java spring-boot application in your host machine. To run in deployment mode, on the repository root, run:
```bash
docker-compose up
```
That will bring up the postgresql database.

Then run
```bash
./mvnw spring-boot:run
```
to start the java spring-boot application.

#### Deployment
The deployment mode runs both the postegresql and the java spring-boot application inside docker containers. To run the iReceptorChain Storage Service, navigate to the `deployed` folder and run
```bash
docker-compose up
```