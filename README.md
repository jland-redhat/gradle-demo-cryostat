# Gradle Cryostat Demo

Updated gradle demo to demo cryostat

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

## Deployment

Deployments must include the following fields

```yaml
apiVersion: apps/v1
kind: Deployment
...
spec:
  ...
  template:
    ...
    spec:
      containers:
        - name: sample-app
          env:
            - name: CRYOSTAT_AGENT_APP_NAME
              value: "myapp"
              # Replace this with the Kubernetes DNS record
              # for the Cryostat Service
            - name: CRYOSTAT_AGENT_BASEURI
              value: "http://cryostat.cryostat.mycluster.svc:8181"
            - name: POD_IP
              valueFrom:
                fieldRef:
                  fieldPath: status.podIP
            - name: CRYOSTAT_AGENT_CALLBACK
              value: "http://$(POD_IP):9977" 
              # Replace "abcd1234" with a base64-encoded authentication token
            - name: CRYOSTAT_AGENT_AUTHORIZATION 
              value: "Bearer abcd1234"
            - name: CRYOSTAT_AGENT_API_WRITES_ENABLED 
              value: true
            - name: JAVA_OPTS
              value: "javaagent:/deployments/app/cryostat-agent-shaded.jar"
          ports:
            - containerPort: 9977
              protocol: TCP
          resources: {}
      restartPolicy: Always
status: {}
```

Use `oc create token default` to create the bearer token