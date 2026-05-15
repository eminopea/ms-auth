FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/*.jar ms-auth.jar

EXPOSE 7081

ENTRYPOINT ["java","-jar","ms-auth.jar"]