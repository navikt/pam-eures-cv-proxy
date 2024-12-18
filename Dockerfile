FROM nginx:latest

RUN sed -i 's/^nginx:x:[0-9]*:/nginx:x:1069:/' /etc/group
RUN sed -i 's|^nginx:x:[0-9]*:[0-9]*:\([^:]*\):/nonexistent|nginx:x:1069:1069:\1:/tmp/nginx|' /etc/passwd

COPY nginx.conf /etc/nginx/nginx.conf

COPY initenv.sh /docker-entrypoint.d/00-initenv.sh
RUN chmod +x /docker-entrypoint.d/00-initenv.sh

RUN apt-get update && \
    apt-get upgrade curl

EXPOSE 8080
