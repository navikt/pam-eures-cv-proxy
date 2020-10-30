FROM navikt/java:14

RUN apt-get update && apt-get install -y curl
RUN apt-get update && apt-get install -y iputils-ping

COPY build/libs/proxy-*-all.jar ./app.jar
