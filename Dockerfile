# Docker instructions
#
# docker build -t $USERNAME/adc-storage:$VERSION .
# docker login --username $USERNAME
# docker push $USERNAME/adc-storage:$VERSION


FROM openjdk:19

WORKDIR /storage

ADD src /storage/src
ADD .mvn /storage/.mvn
ADD pom.xml /storage
ADD mvnw /storage
ADD mvnw.cmd /storage

RUN ./mvnw clean install -DskipTests

#RUN mv ./build/libs/*.jar ./storage.jar


ENTRYPOINT ["./mvnw", "spring-boot:run", "-Dspring.config.location=classpath:/application.properties,$PROPERTIES_PATH"]

#WORKDIR /storage

#COPY --from=0 /storage/storage.jar .
#CMD java -jar ./storage.jar --spring.config.location=classpath:/application.properties,$PROPERTIES_PATH --uma.clientSecret=$UMA_CLIENT_SECRET --spring.datasource.password=$DB_PASSWORD --spring.flyway.password=$DB_PASSWORD