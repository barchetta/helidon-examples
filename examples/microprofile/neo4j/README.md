# Helidon Neo4J MP Example

This example implements a simple Neo4j REST service using MicroProfile.

## Build and run

Bring up a Neo4j instance via Docker

```shell
docker run --publish=7474:7474 --publish=7687:7687 -e 'NEO4J_AUTH=neo4j/secret'  neo4j:5
```
Goto the Neo4j browser and play the first step of the movies graph: [`:play movies`](http://localhost:7474/browser/?cmd=play&arg=movies).

Then build
```shell
mvn package
java -jar target/helidon-examples-integration-neo4j-mp.jar
```

## Exercise the application

```
curl -X GET http://localhost:8080/movies
```

## Try health and metrics

```
curl -s -X GET http://localhost:8080/health
{"outcome":"UP",...
. . .

# JSON Format
curl -H 'Accept: application/json' -X GET http://localhost:8080/metrics
{"base":...
. . .
```

