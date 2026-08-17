# Duration Formatter Library

A small, production-oriented Java 21 library for converting durations expressed
in milliseconds into readable text.

The library supports:

- Seconds
- Minutes and seconds
- Hours, minutes and seconds
- Hiding zero-valued units
- Long and short output formats
- Millisecond truncation
- Validation of invalid negative durations

## Requirements

- Java 21+
- Maven 3.9+

## Build

Clone the repository and run:

```bash
mvn clean verify