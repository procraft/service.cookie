FROM openjdk:8-alpine3.7

LABEL maintainer="Procraft Team"

# bash is required by the sbt universal build start script
RUN apk --update add bash && \
    rm -rf /var/cache/apk/* && \
    rm -rf /tmp/* && \
    rm -rf /var/log/*

ENTRYPOINT ["/opt/libicraft-{{cookiecutter.service_folder}}/bin/{{cookiecutter.service_folder}}", "-Dpidfile.path=/dev/null"]

VOLUME /data

EXPOSE 9000

ADD libicraft-{{cookiecutter.service_folder}}.tgz /opt/
