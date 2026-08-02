# Nidaco Warehouse instructions

## Build, test, and run

- Use Java 17 and Maven. The project is a Spring Boot 3.5.6 WAR (`pom.xml`).
- Run the complete test suite with `mvn test`.
- Run one JUnit class with `mvn -Dtest=PasswordTest test`; use the same `-Dtest=<ClassName>` form for other test classes.
- Produce the deployable WAR with `mvn package` (output: `target/nidaco.war`).
- Start the application locally with `mvn spring-boot:run`. It requires a MySQL database configured by `src/main/resources/application.properties`.
- No lint command is configured in `pom.xml`.
- `.vscode/mcp.json` defines the `nidaco-mysql` MCP server for read-only queries against the local `nidaco_warehouse` database. It prompts for the password; do not replace that prompt with a credential in version control.

## Architecture

- `com.amalfi.nidaco.Application` boots a Spring Boot application. It combines Spring Security, Spring Data JPA/Hibernate, MySQL, and the ZK Spring Boot integration.
- The web UI is server-rendered ZK: `.zul` pages and layout fragments live in `src/main/webapp`; Java composers live in `src/main/java/com/amalfi/nidaco/controller`. `index.zul` is the authenticated shell: it includes `layout/navbar.zul` and `layout/menu.zul`, and `IndexComposer` swaps the center `Include` between ZUL pages.
- A ZUL page binds its controller with `apply="fully.qualified.ComposerName"`. Components are connected to the composer by `@Wire` IDs, and user interactions by `@Listen` selectors. Keep ZUL component IDs, `@Wire` fields, and `@Listen` selectors synchronized, including IDs supplied by included fragments such as `components/toolbar.zul`.
- Feature flow is entity -> Spring Data repository -> service -> ZK composer -> ZUL view. Keep persistence operations in repositories/services rather than putting them directly in composers.
- JPA entities in `entity/` model warehouse data: `Prodotto` belongs to a `Categoria`; `Movimento` belongs to a product and uses `TipoMovimento`; users and roles are connected through `user_roles`. Entities use Lombok getters/setters/builders and JPA annotations.
- Authentication is database-backed. `SecurityConfig` uses `CustomUserDetailsService`, BCrypt passwords, and a custom `/login.zul` page whose composer calls `HttpServletRequest.login`. `DataInitializer` creates `ROLE_ADMIN` and the development admin account only when absent. When adding a protected/static route, update `SecurityConfig` deliberately so unauthenticated access remains limited to the intended assets and login endpoint.

## Repository conventions

- Follow the existing constructor-injection pattern: services and configuration classes use `final` dependencies with Lombok `@RequiredArgsConstructor`.
- Keep repository query behavior in the repository. For example, product filtering is implemented as JPQL in `ProdottoRepository.search(String filtro)`, where `null` and an empty string mean no filter.
- Use `BigDecimal` model properties for product monetary values (`prezzoAcquisto`, `prezzoVendita`, and `iva`); ZUL bindings and composer code must use the actual entity property names.
- ZK UI labels, dialog messages, and domain names are written in Italian; preserve that vocabulary in new UI work.
