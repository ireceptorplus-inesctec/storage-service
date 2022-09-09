# Docker instructions
#
# docker build -t $USERNAME/adc-storage:$VERSION .
# docker login --username $USERNAME
# docker push $USERNAME/adc-storage:$VERSION

FROM alpine:3.16.2

RUN apk add openjdk11
RUN apk add --update docker openrc
RUN rc-update add docker boot


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