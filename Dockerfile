FROM maven:3.8.6-eclipse-temurin-17
RUN useradd --create-home --shell /bin/bash mmclient
USER mmclient
WORKDIR /home/mmclient
COPY ./target/mm-client-*.jar /home/mmclient/mm-client.jar
CMD ["java", "-jar", "/home/mmclient/mm-client.jar"]