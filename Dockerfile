FROM maven:3.8.6-eclipse-temurin-17
RUN useradd --create-home --shell /bin/bash mmclient
USER mmclient
WORKDIR /home/mmclient
ENV MAVEN_CONFIG=/home/mmclient/.m2
COPY ./target/money-management-client-*.jar /home/mmclient/money-management-client.jar
CMD ["java", "-jar", "/home/mmclient/money-management-client.jar"]