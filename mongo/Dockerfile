FROM alpine:edge

RUN apk update
RUN apk upgrade
RUN apk add bash --no-cache
RUN apk add mongodb --no-cache
RUN rm -rf /var/cache/apk/*

RUN mkdir -p /data/db
RUN chown -R mongodb /data/db

EXPOSE 27017 28018

CMD [ "mongod", "--bind_ip", "0.0.0.0" ]