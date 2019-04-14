#FROM docker.elastic.co/beats/heartbeat:7.0.0
#ARG HEARTBEAT_CONFIG_DIR=./config
#USER root
#COPY ${HEARTBEAT_CONFIG_DIR}/heartbeat.yml /usr/share/heartbeat/
#COPY ${HEARTBEAT_CONFIG_DIR}/monitors/*.yml /usr/share/heartbeat/monitors.d/
#RUN chown root:heartbeat /usr/share/heartbeat/heartbeat.yml
#RUN chown root:heartbeat /usr/share/heartbeat/monitors.d/*
#RUN chmod go-w /usr/share/heartbeat/heartbeat.yml
#RUN chmod go-w /usr/share/heartbeat/monitors.d/*
#USER heartbeat

FROM docker.elastic.co/beats/heartbeat:7.0.0
COPY heartbeat.yml /usr/share/heartbeat/heartbeat.yml

USER root
RUN chown root:heartbeat /usr/share/heartbeat/heartbeat.yml
RUN chmod go-w /usr/share/heartbeat/heartbeat.yml
USER heartbeat