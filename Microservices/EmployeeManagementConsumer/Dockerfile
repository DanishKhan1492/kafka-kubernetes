FROM openjdk:17-alpine

WORKDIR /app/empconsumer/

RUN chmod -R 777 /app

ADD target/employeemanagementconsumer-1.0.0.jar /app/empconsumer/employeemanagementconsumer-1.0.0.jar

ENTRYPOINT [ "java", "-jar", "employeemanagementconsumer-1.0.0.jar" ]