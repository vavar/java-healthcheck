# Springboot Application - Website Healthcheck

Demonstrate website healthcheck implementation in JAVA with Springboot.

```
check website and response time when given the CSV list,
calls the Healthcheck Report API to send the statistic of each website
```

## Configuration

### Config file
Before usage, application required following configs in `config.properties`.
```
healthcheck.report.url=<Healthcheck report API>
healthcheck.report.token=<LINE generated tokens>
```

### CSV file
Create simple CSV file to provided website list for testing website healthcheck.

#### Example file - test.txt
```
https://www.google.com
https://www.facebook.com
https://developers.line.biz/en/
https://should-not-found.com
https://twitter.com/
http://where-is-whis-domain.com
```


## Usages

### Run from source code
```
./mvnw spring-boot:run -Dspring-boot.run.arguments=test.txt
```

### Run from binary
```
java -jar healthcheck-0.0.1-SNAPSHOT.jar test.txt
```

## Development

### Prerequisites
- Java 8

### Build JAR package
```
./mvnw package
```




