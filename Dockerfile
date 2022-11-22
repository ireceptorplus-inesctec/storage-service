# Docker instructions
#
# docker build -t $USERNAME/adc-storage:$VERSION .
# docker login --username $USERNAME
# docker push $USERNAME/adc-storage:$VERSION

FROM debian:stable

RUN apt-get update
RUN apt -y install default-jdk

RUN apt-get install -y \
    ca-certificates \
    curl \
    gnupg \
    lsb-release
RUN mkdir -p /etc/apt/keyrings
RUN curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /etc/apt/keyrings/docker.gpg
RUN echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/debian \
  $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null
RUN apt-get update
RUN chmod a+r /etc/apt/keyrings/docker.gpg
RUN apt-get update
RUN apt-get install -y docker-ce docker-ce-cli containerd.io docker-compose-plugin

RUN cat <<EOF | tee /etc/profile.d/jdk.sh
ENV JAVA_HOME /usr/lib/jvm/jdk-11
RUN export JAVA_HOME=/usr/lib/jvm/jdk-11
RUN export PATH=\$PATH:\$JAVA_HOME/bin

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

RUN apt-get install -y libcrypt1

#WORKDIR /storage

#COPY --from=0 /storage/storage.jar .
#CMD java -jar ./storage.jar --spring.config.location=classpath:/application.properties,$PROPERTIES_PATH --uma.clientSecret=$UMA_CLIENT_SECRET --spring.datasource.password=$DB_PASSWORD --spring.flyway.password=$DB_PASSWORD