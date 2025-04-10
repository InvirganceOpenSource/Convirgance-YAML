# Convirgance (YAML)

<a href="https://central.sonatype.com/artifact/com.invirgance/convirgance-yaml/versions">![Version](https://img.shields.io/badge/Version-1.0.0-blue)</a> <a href="https://github.com/InvirganceOpenSource/convirgance-yaml?tab=MIT-1-ov-file">![License](https://img.shields.io/badge/License-MIT-green)</a> <a href="#">![Repository](https://img.shields.io/badge/Platform-Java-gold)</a> <a href="https://central.sonatype.com/artifact/com.invirgance/convirgance-yaml">![Repository](https://img.shields.io/badge/Repository-Maven_Central-red)</a>

An optional package that adds YAML support to Convirgance. Currently provides YAML input and output. Uses [SnakeYAML](https://bitbucket.org/snakeyaml/snakeyaml/) for parsing.

## Quick Start

```java
FileSource source = new FileSource("data.yaml");
YamlInput input = new YamlInput();
Iterator<JSONObject> records = input.read(source);
```

## Installation

Add the following dependency to your Maven `pom.xml` file:

```xml
<dependency>
    <groupId>com.invirgance</groupId>
    <artifactId>convirgance-yaml</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Documentation

- TBD


## License

Convirgance is available under the MIT License. See [License](LICENSE.md) for more details.
