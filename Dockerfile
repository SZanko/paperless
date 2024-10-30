FROM caddy:latest

WORKDIR /srv

COPY ./frontend/paperless_frontend/build /srv

COPY ./Caddyfile /etc/caddy/Caddyfile
