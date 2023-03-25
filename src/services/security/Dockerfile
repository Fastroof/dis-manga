FROM openjdk:8-alpine
ARG WAR_FILE=target/*.war
COPY ${WAR_FILE} security.war
EXPOSE 8080
ENTRYPOINT ["java","-jar","/security.war"]