package extract.value.process.line.methods;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;

class Read2 {

    private final static String ERROR_MSG = "RolesAllowed\\((.*)\\)";

    public static void main(String[] args) throws IOException {
        try (Stream<Path> paths = Files
                .walk(Paths.get("/Users/waynewen/Documents/tmp"))) {
            Pattern pattern = Pattern.compile(ERROR_MSG);
            paths.filter(Files::isRegularFile).forEach(path -> readit(path, pattern));
        }
    }

    private static void readit(Path path, Pattern pattern) {
        System.out.printf("\n----------------------------------\n");
        System.out.printf(path.getFileName().toString() + "\n");

        File myObj = new File("filename.txt");

        try {
            myObj.createNewFile();
            FileWriter myWriter = new FileWriter("filename.txt");
            myWriter.write("Files in Java might be tricky, but it is fun enough!");
            List<String> lines = Files.readAllLines(path);
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                Optional.ofNullable(processLine(line)).ifPresent(s -> {
                    try {
                        myWriter.write(s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processLine(String line) {
        if (!StringUtils.isNumeric(line.trim()) && !line.trim().startsWith("![](data:image/svg+xml,%3csvg%20data-v-")) {
            return test(line) + "\n";
        }
        return null;
    }

    private static String test(String line) {
        if (line.startsWith("#") & line.contains("[#](https://www.it235.com/%E9%")) {
            int i = line.indexOf("[");
            int i1 = line.indexOf(")");
            return line.substring(0, i) + line.substring(i1 + 1);
        }
        return line;
    }
}