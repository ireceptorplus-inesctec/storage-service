# Docker instructions
#
# docker build -t $USERNAME/adc-storage:$VERSION .
# docker login --username $USERNAME
# docker push $USERNAME/adc-storage:$VERSION

FROM fedora:35

RUN dnf install java-11-openjdk.x86_64 -y
RUN dnf -y install dnf-plugins-core
RUN dnf config-manager --add-repo https://download.docker.com/linux/fedora/docker-ce.repo
RUN dnf -y install docker-ce docker-ce-cli containerd.io docker-compose-plugin
RUN systemctl enable docker

RUN yum install -y \
	libxcrypt-compat \
	libnsl \
	procps \
	dash


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
RUN mv ./target/*.jar ./ireceptorchain-storage-service.jar
RUN ./mvnw test -Dtest="ProductionNetworkHyperledgerFabricAPI"

CMD java -jar ireceptorchain-storage-service.jar --spring.config.location=classpath:/application.properties,$PROPERTIES_PATH


#WORKDIR /storage

#COPY --from=0 /storage/storage.jar .
#CMD java -jar ./storage.jar --spring.config.location=classpath:/application.properties,$PROPERTIES_PATH --uma.clientSecret=$UMA_CLIENT_SECRET --spring.datasource.password=$DB_PASSWORD --spring.flyway.password=$DB_PASSWORD