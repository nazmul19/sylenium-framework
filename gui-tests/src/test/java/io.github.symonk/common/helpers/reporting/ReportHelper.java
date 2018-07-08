package io.github.symonk.common.helpers.reporting;

import io.github.symonk.configurations.properties.ManagesFrameworkProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class ReportHelper implements ReportInteractable {


    private final static String FILE_EXISTS = "environment.properties file already exists, deleting it";
    private final static String DUPLICATE_FILE_ERROR = "io error occurred when checking for duplicate files";
    private final static String DEFAULT_DIRECTORY = "target\\allure-results\\environment.properties";
    private final static String PROPERTIES_HEADER = "Generated runtime properties";
    private final static String STREAM_CLOSE_ERROR = "Failed to close output stream";
    private final static String INVALID_ARGS = "provided arguments do not meet a valid test run, aborting the run";
    private final static String IO_EXCEPTION = "IO error occurred when generating or pushing the environment file";

    private final ManagesFrameworkProperties properties;

    public ReportHelper(final ManagesFrameworkProperties properties) {
        this.properties = properties;
    }

    @Override
    public void pushDynamicTestRunPropertiesToReport() {
        generateEnvironmentPropertiesFile();
    }

    private void generateEnvironmentPropertiesFile() {

        final Properties environmentProperties = new Properties();
        properties.getPropertiesAsMap().forEach(environmentProperties::setProperty);


        FileOutputStream fos = null;
        try {
            Path pathToFile = Paths.get(DEFAULT_DIRECTORY);
            if (!removeFileIfExists(pathToFile)) {
                Files.createDirectories(pathToFile.getParent());
            }
            Files.createFile(pathToFile);
            fos = new FileOutputStream(pathToFile.toString());
            environmentProperties.store(fos, PROPERTIES_HEADER);
        } catch (IOException exception) {
            abortTheTestRun(IO_EXCEPTION);
        } finally {
            try {
                Objects.requireNonNull(fos).close();
            } catch (Exception exception) {
                log.error(STREAM_CLOSE_ERROR, exception);
            }
        }
    }

    private boolean removeFileIfExists(Path filePath) {
        try {
            if (Files.exists(filePath)) {
                log.info(FILE_EXISTS);
                Files.delete(filePath);
                return true;
            }
        } catch (IOException exception) {
            log.error(DUPLICATE_FILE_ERROR, exception);
        }
        return false;
    }

    private void abortTheTestRun(final String message) {
        log.info(INVALID_ARGS);
        throw new IllegalArgumentException(message);
    }


}