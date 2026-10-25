# Java 25 reference

`java.version` resolves to `25.0.1` and the POM sets `<java.version>25</java.version>`, from which the Boot parent derives `maven.compiler.release=25`; the compiler runs with `--release 25`.

Watch out: the Boot parent also defines `maven.compiler.release` (from `${java.version}`), and `release` wins over `maven.compiler.source`/`maven.compiler.target`. If tests start failing with "release version 17 not supported"-style errors or 21+ syntax is rejected, check `mvn help:evaluate "-Dexpression=maven.compiler.release" -DforceStdout -q`.

Current properties block (keep it this way — do not add `maven.compiler.source`/`target`/`release` back):

```xml
<properties>
    <java.version>25</java.version>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```

## Feature availability at release 25

| Feature | Since | Available |
| --- | --- | --- |
| Records, sealed types, pattern matching for `instanceof`, text blocks, `var`, switch expressions | 14–17 | yes |
| Pattern matching for `switch`, record patterns | 21 | yes |
| Sequenced collections (`getFirst`, `getLast`, `reversed`) | 21 | yes |
| Virtual threads, scoped values | 21/25 | yes (scoped values final in 25) |
| Unnamed variables `_` | 22 | yes |
| Compact source files / instance `main` | 25 | yes (single-file programs) |
| Primitive patterns, module imports, structured concurrency | 25 preview | **no** — `--enable-preview` is not configured |

## Idioms for this codebase

Records as immutable DTO/aggregate types (with Spring Data JDBC):

```java
public record CalculationRequest(LocalDate date, List<Long> numbers) {}
```

Sealed hierarchy + exhaustive switch:

```java
sealed interface CalcResult permits Ok, Failed {}
record Ok(long id, String sum) implements CalcResult {}
record Failed(String reason) implements CalcResult {}

String describe(CalcResult result) {
    return switch (result) {
        case Ok(long id, String sum) -> "id=" + id + " sum=" + sum;
        case Failed(String reason) -> "failed: " + reason;
    };
}
```

Text blocks:

```java
String sql = """
    select id, number, text
    from number_string
    where id = ?
    """;
```

Virtual threads — enable app-wide rather than hand-rolling executors:

```properties
spring.threads.virtual.enabled=true
```

## Lombok

Configured as an annotation processor for both `default-compile` and `default-testCompile`, and excluded from the repackaged jar.

- `@Slf4j` — logging (`log.info("value: {}", v)`), as in `SomeTestAPI`.
- `@RequiredArgsConstructor` — constructor injection without boilerplate.
- `@Value` / `@Data` — only for simple mutable DTOs; records are preferred.
- Do not annotate Spring Data JDBC aggregates with JPA annotations; `@Data` on mutable entities also breaks identity semantics.

## Style

- 4-space indentation, K&R braces, one blank line between members.
- Class names PascalCase; the repo uses the `*API` suffix for REST controllers (`SomeTestAPI`).
- Use `jakarta.annotation.Nullable` for nullable parameters (matches `SomeTestAPI`), not JetBrains or Spring annotations.
- Prefer constructor injection for production beans; tests in this repo use `@Autowired` fields.
