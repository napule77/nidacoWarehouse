# nidacoWarehouse

Progetto base per gestione magazzino con:
- Spring Boot 3
- ZK 10
- MySQL

## Avvio rapido

1. Assicurati di avere MySQL attivo in locale.
2. Configura credenziali in `src/main/resources/application.properties`.
3. Avvia l'app:

```bash
mvn spring-boot:run
```

UI ZK disponibile su:
- `http://localhost:8080/index.zul`

API base disponibili su:
- `GET /api/warehouse/products`
- `POST /api/warehouse/products`
- `POST /api/warehouse/movements`
