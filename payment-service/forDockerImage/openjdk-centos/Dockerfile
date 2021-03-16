FROM bellsoft/liberica-openjdk-centos:13.0.2
ENV PROJECT_ROOT /User/Desktop/payment_service
WORKDIR $PROJECT_ROOT
COPY ./payment-service-1.0-SNAPSHOT.jar payment-service-1.0-SNAPSHOT.jar
CMD java -XX:+UseContainerSupport -Xmx1g -Xms1g -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=1026 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -jar payment-service-1.0-SNAPSHOT.jar