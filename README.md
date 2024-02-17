# Gradle Demo

Created a basic demo here to show how we can setup a management port inorder to handel our liveness probes even if the network is totally jammed up with traffic.

## Endpoints

**/greet** Writes back a greeting
**/sleep/<SECONDS>** Sleeps for x seconds

## Properties 

`server.tomcat.threads.max` set to 1 meaning only a single connection can be handled at a time.

## Run Code

```
./gradlew bootRun
```

## Useful Commands

Curl the Sleep Endpoint 4 times
```
for i in {1..4}; do
    curl -s localhost:8080/sleep/10 &
done
wait
```

## Create on Openshift

When creating in Openshift if using the new-app feature set `JAVA_APP_JAR` to `/deployments/build/libs/rest-service-0.0.1-SNAPSHOT.jar`