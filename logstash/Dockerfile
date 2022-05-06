FROM docker.elastic.co/logstash/logstash:7.17.3
EXPOSE 5005
VOLUME .:/etc/logstash/conf.d
CMD logstash -f /etc/logstash/conf.d/logstash.conf