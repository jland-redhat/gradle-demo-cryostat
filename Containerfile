FROM registry.access.redhat.com/ubi9/openjdk-17

WORKDIR /app

ADD build/libs/*.jar /app/app.jar


# add the Cryostat Agent JAR to the application image
ADD --chown=185 distribution/agent-libs/cryostat-agent*.jar /deployments/app/cryostat-agent.jar
ENV JAVA_TOOL_OPTIONS="-javaagent:/deployments/app/cryostat-agent.jar -Dcom.sun.management.jmxremote.port=9091 -Dcom.sun.management.jmxremote.rmi.port=9091 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"

EXPOSE 8080 9000

ENTRYPOINT ["java","-jar","app.jar"]