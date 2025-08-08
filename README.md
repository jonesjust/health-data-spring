# Synthetic FHIR Generator

This Spring Boot application provides an API to generate synthetic patient data using [Synthea](https://github.com/synthetichealth/synthea).

### API Endpoints

- `GET /`: Displays a welcome message
- `GET /generate-patients`: Generate patients
  - Query parameter: `population` (default: 10)

Example usage:
```
http://localhost:8080/generate-patients?population=100
```

This will generate 100 synthetic patient records and save the FHIR data to the `LOCAL_OUTPUT_DIR`.

### Prerequisites

- Java Development Kit (JDK) 11 or higher
- No need to install Gradle — this project uses the included Gradle Wrapper.

### Installation & Setup

```bash
git clone https://github.com/jonesjust/health-data-spring.git
cd health-data-spring
./gradlew build
```

### Running the Application

`./gradlew bootRun`: Starts the application at http://localhost:8080

### Configuration

You can change the output directory by updating the `LOCAL_OUTPUT_DIR` variable