FROM adoptopenjdk:11-jre-hotspot
ADD build/libs/alfa-app-test-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]