#!/usr/bin/env sh
set -e
for name in log client_temp proxy_temp_path fastcgi_temp uwsgi_temp scgi_temp; do
    mkdir -vp /tmp/nginx/$name
done
ln -s -f /dev/stdout /tmp/nginx/log/access.log
ln -s -f /dev/stderr /tmp/nginx/log/error.log
chown -R nginx:nginx /tmp/nginx