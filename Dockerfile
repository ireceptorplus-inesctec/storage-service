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

RUN ./mvnw clean install -DskipTests -T4

#RUN mv ./build/libs/*.jar ./storage.jar

RUN ./mvnw package -DskipTests
RUN mv ./target/*.jar ./ireceptorchain-storage-service.jar

CMD java -jar ireceptorchain-storage-service.jar --spring.config.location=classpath:/application.properties,$PROPERTIES_PATH


#WORKDIR /storage

#COPY --from=0 /storage/storage.jar .
#CMD java -jar ./storage.jar --spring.config.location=classpath:/application.properties,$PROPERTIES_PATH --uma.clientSecret=$UMA_CLIENT_SECRET --spring.datasource.password=$DB_PASSWORD --spring.flyway.password=$DB_PASSWORD