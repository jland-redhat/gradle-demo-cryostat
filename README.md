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
ENV JAVA_TOOL_OPTIONS="-javaagent:/deployments/app/cryostat-agent-shaded.jar"
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
            - name: CRYOSTAT_AGENT_BASEURI
              value: "http://<NAMESAPCE>.cryostat.svc.cluster.local:8181"
            - name: POD_IP
              valueFrom:
                fieldRef:
                  fieldPath: status.podIP
            - name: CRYOSTAT_AGENT_CALLBACK
              value: "http://$(POD_IP):9977" 
              # Replace "abcd1234" with a base64-encoded authentication token
              # See the note below for creating the Bear Token
            - name: CRYOSTAT_AGENT_AUTHORIZATION 
              value: "Bearer abcd1234"
            - name: CRYOSTAT_AGENT_API_WRITES_ENABLED 
              value: true
          ports:
            - containerPort: 9977
              protocol: TCP
          resources: {}
      restartPolicy: Always
status: {}
```


Use `oc create token cryostat-example` to create the bearer token (note I called my install cryostat-example so it created an SA with the same name)

## Service

Service with the following ports:

```yaml
kind: Service
apiVersion: v1
metadata:
  name: cryostat-demo-service
spec:
  ports:
    - name: cryostat-agent
      protocol: TCP
      port: 9977
      targetPort: 9977
    - name: jmx-agent
      protocol: TCP
      port: 9091
      targetPort: 9091
  selector:
    app: cryostat-demo
```
