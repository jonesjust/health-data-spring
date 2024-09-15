package com.example.api;

import com.google.cloud.storage.transfermanager.ParallelUploadConfig;
import com.google.cloud.storage.transfermanager.TransferManager;
import com.google.cloud.storage.transfermanager.TransferManagerConfig;
import com.google.cloud.storage.transfermanager.UploadResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.mitre.synthea.engine.Generator;
import org.mitre.synthea.helpers.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}

@RestController
class ApiController {

    private static final String BUCKET_NAME = "raw-ehr-bucket";
    private static final String LOCAL_OUTPUT_DIR = "./data_output";

    @GetMapping("/")
    public String index() {
        return "Welcome, let's generate some data!";
    }

    @GetMapping("/generate-patients")
    public String generatePatients(
        @RequestParam(
            value = "population",
            defaultValue = "10"
        ) String population
    ) {
        System.out.println("starting generator.");

        Config.set("generate.default_population", population);
        Config.set("exporter.baseDirectory", LOCAL_OUTPUT_DIR);
        Generator generator = new Generator();
        generator.run();

        System.out.println("generator finished, uploading to Cloud Storage.");

        try {
            uploadDirectoryContents(BUCKET_NAME, Paths.get(LOCAL_OUTPUT_DIR));
            System.out.println("uploaded to cloud.");
            deleteLocalFiles(LOCAL_OUTPUT_DIR);
            System.out.println("deleted local files, process complete.");
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred while uploading files: " + e.getMessage();
        }
        return (
            "Generated " +
            population +
            " patients and uploaded to Cloud Storage!"
        );
    }

    private void uploadDirectoryContents(
        String bucketName,
        Path sourceDirectory
    ) throws IOException {
        TransferManager transferManager = TransferManagerConfig.newBuilder()
            .build()
            .getService();
        ParallelUploadConfig parallelUploadConfig =
            ParallelUploadConfig.newBuilder().setBucketName(bucketName).build();

        List<Path> filePaths = new ArrayList<>();
        try (Stream<Path> pathStream = Files.walk(sourceDirectory)) {
            pathStream.filter(Files::isRegularFile).forEach(filePaths::add);
        }

        List<UploadResult> results = transferManager
            .uploadFiles(filePaths, parallelUploadConfig)
            .getUploadResults();
        for (UploadResult result : results) {
            System.out.println(
                "Upload for " +
                result.getInput().getName() +
                " completed with status " +
                result.getStatus()
            );
        }
    }

    private void deleteLocalFiles(String dir) {
        try {
            Files.walk(Paths.get(dir))
                .sorted((a, b) -> b.compareTo(a))
                .map(Path::toFile)
                .forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
