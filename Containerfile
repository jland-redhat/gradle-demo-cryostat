FROM registry.access.redhat.com/ubi9/openjdk-17

WORKDIR /app

ADD build/libs/*.jar /app/app.jar

EXPOSE 8080 9000

ENTRYPOINT ["java","-jar","app.jar"]