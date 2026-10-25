# Spring Boot 4 reference (this project)

Boot `4.1.2` (parent), Spring Modulith `2.1.2` (BOM import).

## Dependency coordinates used here

| Capability | Artifact |
| --- | --- |
| Web MVC | `spring-boot-starter-webmvc` |
| Data JDBC | `spring-boot-starter-data-jdbc` |
| Liquibase | `spring-boot-starter-liquibase` |
| RSocket | `spring-boot-starter-rsocket` |
| Actuator | `spring-boot-starter-actuator` |
| H2 console | `spring-boot-h2console` (separate module, not part of `starter-webmvc`) |
| Modulith core/JDBC/insight | `spring-modulith-starter-core`, `spring-modulith-starter-jdbc`, `spring-modulith-starter-insight` |
| Test slices | `spring-boot-starter-{actuator,data-jdbc,liquibase,rsocket,webmvc}-test`, `spring-modulith-starter-test` |

Runtime versions observed in the build: Spring Framework 7.0.10, Liquibase 5.0.4, H2 2.4.240, JUnit Jupiter 6.0.3.

Modulith 2.1 split observability: `spring-modulith-observability` is now a **pom-only aggregator** (no jar), so a dependency on it fails to resolve. `spring-modulith-starter-insight` replaces the old `spring-modulith-actuator` + `spring-modulith-observability` pair — it pulls actuator, `spring-modulith-observability-api` (compile) and `spring-modulith-observability-core` (runtime).

Boot 4 has no aggregated `spring-boot-starter-test` equivalent in this POM — add the per-technology `*-test` starter that matches the code under test.

## Test annotation packages

Boot 4 moved auto-configuration test annotations into technology-specific packages:

```java
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;                 // unchanged
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
```

Shared context bootstrap lives in one base class:

```java
// AbstractSpringBootTest — single source of truth for the test profile
@SpringBootTest
@ActiveProfiles(AbstractSpringBootTest.TEST_PROFILE)
public abstract class AbstractSpringBootTest {
    public static final String TEST_PROFILE = "test";
}

// SomeTestAPITest
@AutoConfigureMockMvc
class SomeTestAPITest extends AbstractSpringBootTest {
    @Autowired
    MockMvc mockMvc;
}
```

Do not repeat `@SpringBootTest`/`@ActiveProfiles` on subclasses: a subclass-level `@ActiveProfiles` replaces the inherited list. Keep `@AutoConfigureMockMvc` on the subclasses that need HTTP support.

When unsure about an import, let the IDE resolve it; do not paste Boot 3 (`org.springframework.boot.test.autoconfigure.web.servlet.*`) paths.

## Configuration

Two active config files under `src/main/resources/`; on conflicting keys `.properties` wins over `.yaml`.

`application.properties` — Spring Modulith module declarations:

```properties
spring.modulith.modules.com.example.spring.modulith.study.api=api
spring.modulith.modules.com.example.spring.modulith.study.calculation=calculation
spring.modulith.modules.com.example.spring.modulith.study.data=data
spring.modulith.modules.com.example.spring.modulith.study.audit=audit
```

`application.yaml` — application name, file-based H2 datasource, port:

```yaml
spring:
  application:
    name: spring-boot-study
  datasource:
    url: jdbc:h2:file:./db/mydb.h2
    driver-class-name: org.h2.Driver
    username:
    password:
    hikari:
      maximum-pool-size: 10
server:
  port: 8091
```

Tests activate profile `test` (`@ActiveProfiles("test")`) and load `src/test/resources/application-test.properties`, which replaces the datasource URL with `jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1` and sets user `sa` with an empty password. Keep the file-based DB out of tests so they never lock `./db/mydb.h2`.

## Spring Modulith

- Modules are declared by naming their Java package: `spring.modulith.modules.<package>=<module-name>`. Packages are `com.example.spring.modulith.study.{api,calculation,data,audit}` — code placed under `com.example.spring.study` will not be treated as one of these modules.
- Annotate module packages/classes with `@ApplicationModule` and expose packages with `@NamedInterface`.
- Verify boundaries in a test: `ApplicationModules.of(MyStudyApp.class).verify()`.
- Actuator exposes module info under `/actuator/modulith`.

## Liquibase

- Changelog lives at the Boot default `classpath:db/changelog/db.changelog-master.yaml` (`src/main/resources/db/changelog/`), so no `spring.liquibase.*` configuration is needed.
- It runs on application startup and in every `@SpringBootTest` context; the changelog table is `PUBLIC.DATABASECHANGELOG`.
- Changeset `1` (`label: test-init`, `runOnChange: false`) executes `sql/init.sql`. Never edit an applied changeset — add a new one.
- `mvn clean test` is the reliable verification; incremental builds can retain stale resources in `target/classes` and mask a misplaced changelog.
- Add a changeset per schema change:

```yaml
databaseChangeLog:
  - changeSet:
      id: 2
      author: me
      changes:
        - sqlFile:
            path: sql/my-change.sql
            relativeToChangelogFile: true
```

## H2

- File database `./db/mydb.h2` (existing `db/mydb.h2.mv.db`) for the running app.
- H2 console is a separate module (`spring-boot-h2console`); enable it with `spring.h2.console.enabled=true` and open `/h2-console`.
- Tests already override the URL to in-memory H2 through the `test` profile (`src/test/resources/application-test.properties`), so they never lock the file DB. Keep this when adding new tests: annotate them with `@ActiveProfiles("test")`.

## RSocket

- `spring-boot-starter-rsocket` is on the classpath, so an RSocket server may start alongside MVC.
- Handlers use `@MessageMapping` / `@ConnectMapping` on `@Controller` beans.
- Server transport is not configured yet; if enabling it, set `spring.rsocket.server.port` and keep MVC on `server.port`.

## Actuator

Endpoints are on `/actuator`; Modulith and observability integrations are already present. When adding endpoints or health indicators, use Boot 4's `@Endpoint`/`HealthIndicator` APIs from the packages the IDE resolves.

## Boot 4 migration reminders

- Starters are split by technology — prefer the narrowest one.
- Auto-configuration classes moved under `org.springframework.boot.<technology>.autoconfigure`; do not hand-write these imports.
- Test slices ship with the corresponding `*-test` starter; adding `spring-boot-starter-test` from a Boot 3 snippet is wrong here.
