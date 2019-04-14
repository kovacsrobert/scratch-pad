FROM docker.elastic.co/beats/heartbeat:7.0.0
COPY config/heartbeat.yml /usr/share/heartbeat/heartbeat.yml
COPY config/monitors/monitor-*.yml /usr/share/heartbeat/monitors.d/

USER root
RUN chown root:heartbeat /usr/share/heartbeat/heartbeat.yml
RUN chown root:heartbeat /usr/share/heartbeat/monitors.d/monitor-*.yml
RUN chmod go-w /usr/share/heartbeat/heartbeat.yml
RUN chmod go-w /usr/share/heartbeat/monitors.d/monitor-*.yml
USER heartbeat