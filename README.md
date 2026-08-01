# nidacoWarehouse

Progetto base per gestione magazzino con:
- Spring Boot 3.3 (Java 17)
- ZK 10 — UI server-side *(vedi nota compatibilità)*
- MySQL 8

## Prerequisiti

| Strumento | Versione minima |
|-----------|----------------|
| Java      | 17             |
| Maven     | 3.9            |
| MySQL     | 8.0            |

## Configurazione

Edita `src/main/resources/application.properties` con le credenziali MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nidaco_warehouse?createDatabaseIfNotExist=true&serverTimezone=UTC
spring.datasource.username=<utente>
spring.datasource.******
```

## Avvio

```bash
mvn spring-boot:run
```

Oppure come JAR standalone:

```bash
mvn package
java -jar target/nidacoWarehouse-0.0.1-SNAPSHOT.jar
```

## Endpoint REST disponibili

| Metodo | URL | Descrizione |
|--------|-----|-------------|
| `GET`  | `/api/warehouse/products` | Lista prodotti |
| `POST` | `/api/warehouse/products` | Crea prodotto |
| `POST` | `/api/warehouse/movements` | Registra movimento |

### Payload di esempio

**Crea prodotto**
```json
{"sku":"SKU-001","name":"Bulloneria M8","quantity":120,"location":"A1-03"}
```

**Registra movimento**
```json
{"productId":1,"type":"INBOUND","quantity":20,"note":"arrivo fornitore"}
```

Tipi di movimento supportati: `INBOUND`, `OUTBOUND`, `ADJUSTMENT`.

## Gestione errori

Tutti gli endpoint REST restituiscono una risposta JSON strutturata in caso di errore:

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Risorsa non trovata: index.zul",
  "timestamp": "2024-01-15T10:30:00Z"
}
```

Nessuna informazione interna viene esposta nelle risposte di errore.

## Test

```bash
mvn test
```

I test utilizzano H2 in memoria; MySQL non è necessario per l'esecuzione dei test.

---

## Nota: compatibilità ZK 10 e Spring Boot 3

**`GET /index.zul` restituisce 404 — questo è il comportamento atteso** nella versione corrente.

### Causa

ZK 10.0.0 (disponibile su Maven Central) è compilato contro `javax.servlet-api 3.1.0`
(namespace Java EE). Spring Boot 3 usa embedded Tomcat 10 che fornisce solo le classi
`jakarta.servlet.*` (namespace Jakarta EE 9+). I due namespace sono incompatibili a runtime:
Tomcat 10 non può istanziare servlet che implementano `javax.servlet.Servlet`.

### Stato

| Componente | Versione attuale | Richiesto |
|------------|-----------------|-----------|
| Spring Boot | 3.3.2 | ≥ 3.x (jakarta) |
| ZK CE | 10.0.0 | serve build jakarta-compatible |
| Tomcat (embedded) | 10.x | fornisce jakarta.servlet |

Le pagine `.zul` sono presenti in `src/main/webapp/` nella struttura corretta, pronte
per essere servite da `DHtmlLayoutServlet` non appena ZK rilasci un artifact
Jakarta EE-compatible.

### Path verso la risoluzione

**Opzione A — Spring Boot 2.7 + ZK 10** *(solo ambienti legacy)*
Downgrade a Spring Boot 2.7.x (Tomcat 9, `javax.servlet`), compatibile con ZK 10.0.0.
Richiede il revert di tutti gli import `jakarta.*` → `javax.*`.

**Opzione B — ZK con supporto Jakarta** *(consigliata)*
Verificare il rilascio di una versione ZK CE/EE compilata contro `jakarta.servlet-api`.
Aggiornare la dipendenza in `pom.xml`; le pagine `.zul` in `src/main/webapp/`
diventano accessibili senza ulteriori modifiche.
