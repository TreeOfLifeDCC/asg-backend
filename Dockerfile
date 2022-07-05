
# the first stage of our build will use a maven 3.6.1 parent image
FROM maven:3.6.3-adoptopenjdk-14 AS MAVEN_BUILD

# copy the pom and src code to the container
COPY ./ ./

# package our application code
RUN mvn clean package

# the second stage of our build will use open jdk 8 on alpine 3.9
FROM openjdk:14-jdk-alpine

# copy only the artifacts we need from the first stage and discard the rest
COPY --from=MAVEN_BUILD /target/platform.jar /platform.jar

# set the startup command to execute the jar
CMD ["java", "-jar", "/platform.jar"]