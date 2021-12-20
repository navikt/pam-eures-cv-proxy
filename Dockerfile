FROM nginx:1.18.0

COPY nginx.conf /etc/nginx/nginx.conf
COPY isAlive /www/data/internal/isAlive
COPY isReady /www/data/internal/isReady

VOLUME /var/cache/nginx
VOLUME /var/run
