FROM nginx:alpine
COPY nginx.conf /etc/nginx/nginx.conf
RUN rm /etc/nginx/conf.d/default.conf
VOLUME /var/log/nginx/log
EXPOSE 80 443
ENTRYPOINT ["nginx","-g","daemon off;"]
