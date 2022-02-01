#!/usr/bin/env sh
set -e
for name in log client_temp proxy_temp_path fastcgi_temp uwsgi_temp scgi_temp; do
    mkdir -vp /tmp/nginx/$name
done

chown -R nginx:nginx /tmp/nginx