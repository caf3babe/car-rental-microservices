FROM docker.elastic.co/elasticsearch/elasticsearch:7.17.3
ENV node.name=elasticsearch
ENV discovery.type=single-node
ENV cluster.name=docker-cluster
ENV bootstrap.memory_lock=true
ENV ES_JAVA_OPTS="-Xms512m -Xmx512m"
EXPOSE 9200
EXPOSE 9300
