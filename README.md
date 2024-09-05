# Synthetic EHR Cloud Uploader

This Spring Boot application provides an API to generate synthetic patient data using Synthea and automatically uploads it to Google Cloud Storage.

## Features

- Generate synthetic patient data with customizable population size
- Automatically upload generated data to Google Cloud Storage
- Clean up local files after successful upload

## How to Use

1. Fork this repository
2. Clone your forked repository to your local machine.
3. Set up your Google Cloud credentials.
4. Configure the `BUCKET_NAME` in the `ApiController` class.
5. Adjust the `LOCAL_OUTPUT_DIR` if needed.
6. Build the application using `./gradlew build`
7. Run the application using `./gradlew bootRun`

### API Endpoints

- `GET /`: Welcome message
- `GET /generate-patients`: Generate patients and upload to GCS
  - Query parameter: `population` (default: 10)

Example usage:
```
http://localhost:8080/generate-patients?population=100
```

This will generate 100 synthetic patients and upload the data to your specified GCS bucket.

If you find this project useful, consider buying me a coffee.

<a href='https://ko-fi.com/Q5Q811RI0C' target='_blank'><img height='36' style='border:0px;height:36px;' src='https://storage.ko-fi.com/cdn/kofi1.png?v=3' border='0' alt='Buy Me a Coffee at ko-fi.com' /></a>
