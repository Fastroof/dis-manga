FROM openjdk:17-alpine
ARG WAR_FILE=target/*.war
COPY ${WAR_FILE} imgstrg.war
EXPOSE 8080
ENTRYPOINT ["java","-jar","/imgstrg.war"]