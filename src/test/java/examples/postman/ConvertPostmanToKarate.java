package examples.postman;

import com.intuit.karate.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConvertPostmanToKarate {
    @Test
    public void ConvertUtil() throws IOException {
        File tempSource = File.createTempFile("karate-postman-input", ".postman_collection.json");
        tempSource.deleteOnExit();
        Path tempOutput = Files.createTempDirectory("karate-postman-output");
        tempOutput.toFile().deleteOnExit();

        // populate the temp source file with the Postman export data
        InputStream is = getClass().getResourceAsStream("postman-echo-single.postman_collection");
        String postman = FileUtils.toString(is);
        Files.write(Paths.get(tempSource.toURI()), postman.getBytes());

        // perform the conversion
        boolean successful = new PostmanConverter().convert(tempSource.toString(), tempOutput.toString());
        assertTrue(successful);

        // load the expected output from the resources
        is = getClass().getResourceAsStream("expected-converted.txt");
        String expectedConverted = FileUtils.toString(is);

        // load the actual output form the disk
        Path actualOutputPath = Paths.get(tempOutput.toString(),
                tempSource.getName().replace(".postman_collection.json", "") + ".feature");
        String converted = new String(Files.readAllBytes(actualOutputPath), StandardCharsets.UTF_8);

        // the first line is dynamic, as it contains the temp dir characters
        assertTrue(converted.startsWith("Feature: karate-postman-input"));

        // trim the file so it doesn't contain the line starting with 'Feature':
        String convertedTrimmed = Arrays.stream(converted.split(System.lineSeparator()))
                .filter(line -> !line.startsWith("Feature:"))
                .collect(Collectors.joining(System.lineSeparator()));

        // assert that the trimmed actual output equals the trimmed expected output
        assertEquals(convertedTrimmed.trim(), expectedConverted.trim());
    }
}
