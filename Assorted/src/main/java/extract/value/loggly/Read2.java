package extract.value.loggly;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import extract.value.loggly.pojo.Root;

class Read2 {

    private final static String PRODUCT_ID = "productId:(.*),dateTime:";
    private final static String TIMESTAMP = "Timestamp:(.*),Level:";
    private final static String ERROR_MSG = "errorMessage:(.*)},";


    public static void main(String[] args) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get("/Users/waynewen/Documents/log"))) {
            String toCompile = ERROR_MSG;

            Pattern pattern = Pattern.compile(toCompile);
            int eventIndex = ERROR_MSG.equals(toCompile) ? 0 : 1;
            paths.filter(Files::isRegularFile)
                    .forEach(path -> readit(path, pattern, eventIndex));

        }
    }

    private static void readit(Path path, Pattern pattern, int eventIndex) {
        try {
            Root roots = new Gson().fromJson(Files.readString(path), Root.class);
            roots.events.stream().map(event -> event.logmsg);
            String content = roots.events.get(eventIndex).logmsg.replace("\\\"", "").replace("\"", "");
            Matcher matcher = pattern.matcher(content);
            if (matcher.find()) {
                String group = matcher.group(1);
                System.out.printf(group + "\n");
            } else {
                System.out.printf("*********** not found\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
