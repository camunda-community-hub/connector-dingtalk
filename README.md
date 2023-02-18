# Camunda DingTalk Connector

## Build

```bash
mvn clean package
```

## API

### Input

```json
{
  "authentication":{
    "accessToken":"secrets.ACCESS_TOKEN",
    "secretToken":"secrets.SECRET_TOKEN"
  },
  "messageType": "TEXT OR MARKDOWN",
  "title": "only_for_markdown",
  "content": "content"
}
```

### Output

```json
{
  "errcode": "error code",
  "errmsg": ""
}
```

## Test locally

Run unit tests

```bash
mvn clean verify
```

### Test as local Job Worker

Use
the [Camunda Connector Runtime](https://github.com/camunda-community-hub/spring-zeebe/tree/master/connector-runtime#building-connector-runtime-bundles)
to run your function as a local Job Worker.

## Element Template

The element templates can be found in
the [element-templates/dingtalk-connector.json](element-templates/dingtalk-connector.json) file.

## Build a release

Trigger the [release action](./.github/workflows/RELEASE.yml) manually with the version `x.y.z` you want to release and the next SNAPSHOT version.
