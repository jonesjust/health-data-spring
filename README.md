# Synthetic FHIR Generator

This Spring Boot application provides an API to generate synthetic patient data using [Synthea](https://github.com/synthetichealth/synthea).

### API Endpoints

- `GET /`: Welcome message
- `GET /generate-patients`: Generate patients
  - Query parameter: `population` (default: 10)

Example usage:
```
http://localhost:8080/generate-patients?population=100
```

This will generate 100 synthetic patient records and save the FHIR data to the `LOCAL_OUTPUT_DIR`.
