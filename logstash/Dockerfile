FROM docker.elastic.co/logstash/logstash:7.17.3
EXPOSE 5005
COPY logstash.conf /etc/logstash/conf.d/logstash.conf
CMD logstash -f /etc/logstash/conf.d/logstash.conf