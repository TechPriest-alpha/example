---
name: java
description: Conventions and workflows for Java 25 + Spring Boot 4 work in this Maven project (Lombok, Spring Data JDBC, Spring Modulith, Liquibase, H2, WebMVC, RSocket). Use when writing, editing, reviewing, or debugging .java files, adding Maven dependencies, creating Spring endpoints or tests, or running Maven builds and tests here.
---

# Java 25 + Spring Boot 4 (this project)

## Stack facts

| Item | Value |
| --- | --- |
| JDK | Temurin 25.0.1 installed |
| Effective compiler release | 25 (derived from `<java.version>25</java.version>`; the Boot parent sets `maven.compiler.release`) |
| Spring Boot | 4.1.2 (parent POM) |
| Spring Modulith | 2.1.2 (BOM) |
| Build | Maven 3.9.6, **no wrapper** — use `mvn` |
| Base package | `com.example.spring.study` |
| DB | H2 file `./db/mydb.h2` (tests: in-memory via `test` profile), Liquibase, Spring Data JDBC (no JPA) |
| App entry | `MyStudyApp` (`@SpringBootApplication`) |
| Port | 8091 (`server.port` in `src/main/resources/application.yaml`) |

## Build & verify

Run from the project root:

```powershell
mvn -q -DskipTests compile
mvn -q test
mvn -q -Dtest=SomeTestAPITest test
mvn spring-boot:run
```

Or use the helper: `powershell -File .gigacode\skills\java\scripts\verify.ps1 -Clean -RunTests [-TestClass SomeTestAPITest]`. Prefer `-Clean`: incremental builds can keep stale resources in `target/classes` and hide configuration errors.

Always compile and run tests after changing Java code. Do not claim success without the command exit code. Tests run under the `test` profile against in-memory H2, and Liquibase applies `db/changelog/db.changelog-master.yaml` on every test context.

## Layout

- `src/main/java/com/example/spring/study/` — legacy sample controller (`SomeTestAPI`, `/test/hello`).
- `src/main/java/com/example/spring/modulith/study/` — application root `MyStudyApp` plus the `api`, `calculation`, `data` modules.
- `target/generated-sources/openapi/` — `StudyApi` and its models, generated at build time (never committed).
- `src/test/java/com/example/spring/study/` — tests mirror the production package; shared context tests extend `AbstractSpringBootTest`, which is the single place defining the `test` profile.
- `src/main/resources/application.properties` — Modulith module declarations.
- `src/main/resources/application.yaml` — application name, H2 file datasource, `server.port=8091`.
- `src/main/resources/db/changelog/` — Liquibase master changelog + `sql/` scripts (default Boot location).
- `src/test/resources/application-test.properties` — in-memory H2 overrides for tests.
- `src/main/resources/openapi/api-spec.yaml` — API contract for Modulith study endpoints (`/api/study/...`). It is documentation only; no generator is configured.

## Conventions (observed in this repo)

- 4 spaces, no tabs, UTF-8, one type per file.
- **Method arguments**: keep them on one line when the whole declaration/call is shorter than 130 characters. Otherwise put every argument on its own line, and move `(` and `)` to their own lines:

  ```java
  someCall(
      firstArgument,
      secondArgument
  );
  ```
- Logging via Lombok `@Slf4j` with parameterized messages: `log.info("testHello: {}", value)`.
- REST types use the `*API` suffix (`SomeTestAPI`); tests are `<Class>Test` in the same package.
- HTTP tests: extend `AbstractSpringBootTest`, add `@AutoConfigureMockMvc`, field-inject `MockMvc`; never repeat `@SpringBootTest`/`@ActiveProfiles` in subclasses (a subclass-level `@ActiveProfiles` would replace the inherited one).
- API boundary: implement the generated `StudyApi` interface; use generated model classes directly (no separate DTOs, no mapping layer).
- Persistence: Spring Data JDBC — use records for aggregates and `org.springframework.data.annotation.Id`, **not** `jakarta.persistence` annotations.
- Nullable inputs annotated with `jakarta.annotation.Nullable`.
- Prefer constructor injection for new production beans; existing tests use `@Autowired` fields, which is fine for tests.

## Spring Boot 4 specifics

Boot 4 splits the old monolithic starters; do not copy Boot 3 coordinates or import paths.

- Web: `spring-boot-starter-webmvc` (there is no `spring-boot-starter-web` here).
- H2 console: separate `spring-boot-h2console` module.
- Test support ships per technology: `spring-boot-starter-webmvc-test`, `spring-boot-starter-data-jdbc-test`, etc.
- Test annotations live in technology-specific packages, e.g. `org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc`. Let the IDE resolve imports; never guess Boot 3 locations.
- Modulith 2.1 split `spring-modulith-observability`: the artifact is now a pom-only aggregator. Use `spring-modulith-starter-insight` (actuator + observability api/core) instead of depending on `spring-modulith-actuator` and `spring-modulith-observability` separately.

See `references/spring-boot-4.md` for module mapping, Modulith config, and Liquibase details.

## Modulith modules (`com.example.spring.modulith.study`)

| Module | Contents | May depend on |
| --- | --- | --- |
| `api` | `StudyController implements StudyApi`; publishes `CalculationRequested` | `calculation` |
| `calculation` | `CalculationService` (`@EventListener` + `@Transactional`), `CalculationRequested`, `CalculationCompleted` | `data` |
| `data` | `CalculationRequestRecord`, `CalculationResultRecord`, two `CrudRepository` interfaces (`Type.CLOSED`) | — |

Flow: controller publishes a synchronous Spring event carrying a `Consumer<CalculationCompleted>` callback; the service stores a request row and a result row, computes the sum, then invokes the callback; the controller builds the generated response. GET reads through `CalculationService.find(id)`. `ModulithStructureTest` asserts `verify()` and exactly three modules.

OpenAPI-first workflow: edit `src/main/resources/openapi/api-spec.yaml` → `mvn generate-sources` → implement the new/changed method in `StudyController`.

## Pitfalls in this project

- **Modulith naming mismatch**: `application.properties` declares modules under `com.example.spring.modulith.study.*`, while production code is in `com.example.spring.study`. New modules must live under the package that matches these properties, or the properties must be updated.
- **Config split**: Modulith properties live in `application.properties`, everything else in `application.yaml`; `.properties` wins on conflicting keys. Keep new settings in the file where related ones already are.
- **Incremental builds hide resource problems**: Maven never deletes stale files from `target/classes`. If configuration looks missing, run `mvn clean test` before debugging.
- **Liquibase** runs on app startup and in every test context; changelog path is the Boot default `classpath:db/changelog/db.changelog-master.yaml`. A new changeset is picked up automatically, but never edit the already-applied changeset `1` (`runOnChange: false`) — add a new changeset instead.
- **Compiler release**: `maven.compiler.source/target` are ignored while `maven.compiler.release` is set (the Boot parent derives it from `${java.version}`). This POM sets only `<java.version>25</java.version>`; keep it that way instead of reintroducing `source`/`target`.
- **No preview features**: the compiler config has no `--enable-preview`, so preview APIs will not compile even after the release is raised.
- **Lombok** is `optional` and excluded from the Boot fat jar; annotation processing is configured for both `compile` and `test-compile`. Do not add it as a normal runtime dependency.
- **Generated code needs `jakarta.validation-api`**: the spring generator emits `@NotNull` on required model fields even with `useBeanValidation=false`, so the dependency must stay in the POM.

## Adding a feature (workflow)

1. Create/modify code under `src/main/java/com/example/spring/modulith/study/<module>/...`; keep module dependencies one-way (`api → calculation → data`).
2. Add a mirrored test under `src/test/java/...`; extend `AbstractSpringBootTest` (add `@AutoConfigureMockMvc` for HTTP).
3. For schema changes, add a changeset under `src/main/resources/db/changelog/` and reference it from the master changelog.
4. Add Maven dependencies with the Boot-managed versions (no `<version>` unless the artifact is outside the BOM).
5. Verify with `mvn -q -DskipTests compile`, then `mvn clean test`.

## Resources

- `references/java-25.md` — language features available at release 25, Lombok usage, preview limits.
- `references/spring-boot-4.md` — Boot 4 module names, moved test packages, Modulith, Liquibase, H2, RSocket.
- `scripts/verify.ps1` — compile / test runner with correct root resolution (`-Clean` for reproducible runs).
