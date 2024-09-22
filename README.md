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

If you find this project useful, consider buying me a coffee.

<a href='https://ko-fi.com/Q5Q811RI0C' target='_blank'><img height='36' style='border:0px;height:36px;' src='https://storage.ko-fi.com/cdn/kofi1.png?v=3' border='0' alt='Buy Me a Coffee at ko-fi.com' /></a>
