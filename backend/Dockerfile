FROM  openjdk:17-jdk-alpine


COPY ./target/backend-0.0.1-SNAPSHOT.jar /usr/app/

EXPOSE 5050

ENTRYPOINT ["java","-jar","/usr/app/backend-0.0.1-SNAPSHOT.jar","--spring.profiles.active=develop"]