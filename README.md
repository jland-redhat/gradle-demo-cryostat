# Gradle Demo

Created a basic demo here to show how we can setup a management port inorder to handel our liveness probes even if the network is totally jammed up with traffic.

## Endpoints

**/greet** Writes back a greeting
**/sleep/<SECONDS>** Sleeps for x seconds
**/memory/consume/{mbs}** Consumes X mbs of memory

## Properties

`server.tomcat.threads.max` set to 1 meaning only a single connection can be handled at a time.

This can be overwritten by setting the `SERVER_TOMCAT_THREADS_MAX` environment variable

## Run Code

```sh
./gradlew bootRun
```

### Build with Cryostat Agent locally

```sh
export JAVA_OPTS="-javaagent"
```

### Update Containerfile

```sh
export JAVA_OPTS="-javaagent:/deployments/app/cryostat-agent-shaded.jar"
```

## Useful Commands

Curl the Sleep Endpoint 4 times

```sh
for i in {1..4}; do
    curl -s localhost:8080/sleep/10 &
done
wait
```

Curl every 1 second
```sh
while true; do
    curl -s localhost:8080/sleep/10 &
    sleep 1
done
```

## Create on Openshift

When creating in Openshift if using the new-app feature set `JAVA_APP_JAR` to `/deployments/build/libs/rest-service-0.0.1-SNAPSHOT.jar`

## Run Container Locally

```sh
podman build . -t gradle-demo
podman run -p 8080:8080 -p 9000:9000 localhost/gradle-demo:latest
```

## Github Actions

Repos setup to push to quay using github actions

Does require `QUAY_USERNAME` and `QUAY_TOKEN` to be set in the github secrets.
