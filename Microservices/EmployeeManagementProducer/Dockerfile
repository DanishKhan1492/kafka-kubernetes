FROM openjdk:17-alpine

WORKDIR /app/empproducer/

RUN chmod -R 777 /app

ADD empmanagproducer-1.0.0.jar /app/empproducer/empmanagproducer-1.0.0.jar

ENTRYPOINT [ "java", "-jar", "empmanagproducer-1.0.0.jar" ]
