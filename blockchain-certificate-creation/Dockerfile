FROM openjdk:19

WORKDIR /storage

ADD src /storage/src
ADD .mvn /storage/.mvn
ADD pom.xml /storage
ADD mvnw /storage
ADD mvnw.cmd /storage


ARG ORG_DOMAIN
ARG ORG_DOMAIN
ARG ORG_CONNECTION_FILE
ARG WALLET_USER_ID
ARG ORG_CA_MSP_ID
ARG ORG_CA_AFFILIATION
ARG NETWORK_CONFIG_PATH
ARG CA_CERT_PATH


RUN echo "The ARG variable value is $CA_CERT_PATH"

RUN ./mvnw clean install -DskipTests -T 1C

#RUN mv ./build/libs/*.jar ./storage.jar

RUN ./mvnw package -DskipTests

CMD ./mvnw test -Dtest="ProductionNetworkHyperledgerFabricAPI"