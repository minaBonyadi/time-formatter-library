# Duration Formatter Library

A small, production-oriented Java 21 library for converting durations expressed
in milliseconds into human-readable text.

The library provides a simple, stateless API with support for long and short
output formats.

## Features

- Convert milliseconds to seconds, minutes, and hours
- Truncate incomplete seconds
- Automatically omit zero-valued units
- Support long and short output formats
- Reject invalid negative durations
- Reject null formatting options
- Stateless and thread-safe implementation
- Comprehensive unit tests
- 100% line and branch coverage enforced by JaCoCo
- Automated CI validation

## Requirements

- Java 21+
- Maven 3.9+

## Build

Run the complete verification:

```bash
mvn clean verify
```

This compiles the project, runs the tests, generates the JaCoCo report, and
checks the configured coverage requirements.

## Usage

The main entry point is `DurationFormatter`.

### Long Format

The default format is `LONG`.

```java
DurationFormatter.format(10_145);
// "10 seconds"

DurationFormatter.format(214_000);
// "3 minutes 34 seconds"

DurationFormatter.format(7_439_000);
// "2 hours 3 minutes 59 seconds"
```

Zero-valued units are omitted:

```java
DurationFormatter.format(3_214_000);
// "53 minutes 34 seconds"
```

### Short Format

Use `DurationFormat.SHORT` for the compact representation:

```java
DurationFormatter.format(
        7_414_000,
        DurationFormat.SHORT
);
// "2h 3m 34s"
```

Supported formats:

| Format | Example |
|---|---|
| `LONG` | `2 hours 3 minutes 34 seconds` |
| `SHORT` | `2h 3m 34s` |

## Behaviour

Milliseconds that do not form a complete second are truncated.

```java
DurationFormatter.format(10_145);
// "10 seconds"
```

Zero duration:

```java
DurationFormatter.format(0);
// "0 seconds"
```

Negative durations throw `IllegalArgumentException`.

A null `DurationFormat` throws `NullPointerException`.

## Testing

The project uses JUnit 5, AssertJ, and JaCoCo.

Run tests only:

```bash
mvn test
```

Run the complete verification:

```bash
mvn clean verify
```

The project enforces:

- 100% line coverage
- 100% branch coverage

The JaCoCo report is generated at:

```text
target/site/jacoco/index.html
```

## Continuous Integration

GitHub Actions validates pull requests targeting `main`.

The CI pipeline uses Java 21 and runs:

```bash
mvn clean verify
```

This verifies compilation, unit tests, and code coverage before changes are
merged.

## Project Structure

```text
src/
├── main/java/io/github/formatter/
│   ├── DurationFormatter.java
│   └── DurationFormat.java
└── test/java/io/github/formatter/
    └── DurationFormatterTest.java
```

## Design

The formatter is implemented as a stateless utility class with an immutable
internal duration representation.

The duration conversion logic is shared between the supported output formats
to avoid duplication. `DurationFormat` is an enum to provide a type-safe API.

The library has no runtime framework dependencies and is designed to be
reusable across Java projects.

## Verification

Before submitting changes, run:

```bash
mvn clean verify
```

A successful build should finish with:

```text
BUILD SUCCESS
```