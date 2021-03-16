FROM bellsoft/liberica-openjdk-centos:13.0.2
ENV PROJECT_ROOT C:\Users\User\Desktop\Otus_graduating_project
WORKDIR $PROJECT_ROOT
COPY ./graduationProject-0.0.1-SNAPSHOT.jar graduationProject-0.0.1-SNAPSHOT.jar
CMD java -XX:+UseContainerSupport -Xmx1g -Xms1g -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=1026 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -jar back-end-core-1.0-SNAPSHOT.jar
