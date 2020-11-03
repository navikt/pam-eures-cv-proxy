FROM nginx:1

COPY nginx.conf /etc/nginx/nginx.conf
COPY isAlive /www/data/internal/isAlive
COPY isReady /www/data/internal/isReady

RUN apt-get update && apt-get install -y curl
RUN apt-get update && apt-get install -y iputils-ping