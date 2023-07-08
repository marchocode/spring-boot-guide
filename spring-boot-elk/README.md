
## Command

```
docker exec -it search /usr/share/elasticsearch/bin/elasticsearch-create-enrollment-token -s kibana
```

```
docker exec -it search /usr/share/elasticsearch/bin/elasticsearch-reset-password -u elastic
```

## Errors

```
filebeat  | {"log.level":"error","@timestamp":"2023-06-27T07:56:13.488Z","log.logger":"esclientleg","log.origin":{"file.name":"transport/logging.go","file.line":38},"message":"Error dialing x509: certificate signed by unknown authority","service.name":"filebeat","network":"tcp","address":"search:9200","ecs.version":"1.6.0"}
```


```
handshake... ERROR x509: certificate signed by unknown authority
```