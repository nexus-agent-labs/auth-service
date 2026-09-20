# auth-service

## Run

Copy `.env.example` to `.env`, fill in the empty secret values, then load it
before starting. `DB_URL` and `DB_USERNAME` already include local defaults.

```bash
cp .env.example .env
# Edit .env, then:
set -a; source .env; set +a
./gradlew bootRun
```

Health check: `GET http://localhost:8080/actuator/health`

## Test

```bash
./gradlew test
```

Tests use an isolated in-memory H2 database through the `test` profile.
