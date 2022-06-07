package examples.postman;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PostmanConverter {
    public boolean convert(final String importFile, final String outputDir) {
        try {
            final Path pathTo = Paths.get(importFile);
            if (!Files.exists(pathTo)) {
                System.err.println("File at '" + importFile + "' does not exist; cannot import");
                return false;
            }
            final String content = new String(Files.readAllBytes(pathTo), StandardCharsets.UTF_8);
            final List<PostmanItem> items = PostmanUtils.readPostmanJson(content);
            final String collectionName = pathTo.getFileName().toString()
                    .replace(".postman_collection", "")
                    .replace(".json", "");
            final String converted = PostmanUtils.toKarateFeature(collectionName, items);
            final Path outputFilePath = Paths.get(outputDir, collectionName + ".feature");
            Files.write(outputFilePath, converted.getBytes());
            System.out.println("Converted feature file available at " + outputFilePath.toAbsolutePath().toString());
            return true;
        } catch (IOException e) {
            System.err.println("An error occurred with processing the file - the task could not be completed");
            return false;
        }
    }

}
