FROM openjdk:17-jre-slim

WORKDIR /app

COPY --from=build /workspace/app/build/libs/*.jar app.jar

EXPOSE 8080 9000

ENTRYPOINT ["java","-jar","app.jar"]