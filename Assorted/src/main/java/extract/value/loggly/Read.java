package extract.value.loggly;

import com.google.gson.Gson;
import extract.value.loggly.pojo.Root;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class Read {

    private final static String PRODUCT_CODE = "productCode:(.*),startTime:";
    private final static String SUPPLIER_NAME = "supplierName:(.*),sup";
    private final static String ORDER_NUMBER = "orderNumber:(.*),status";
    private final static String TIMESTAMP = "Timestamp:(.*),Level:";
    private final static String HTTP_CODE = "Response (.*), call";
    private final static String ERROR_MSG = "ERRORMESSAGE:(.*)}}";


    public static void main(String[] args) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get("/Users/waynewen/Documents/log"))) {
            String toCompile = PRODUCT_CODE;

            Pattern pattern = Pattern.compile(toCompile);
            int eventIndex = ERROR_MSG.equals(toCompile) || HTTP_CODE.equals(toCompile) ? 1 : 2;
            paths.filter(Files::isRegularFile)
                    .forEach(path -> readit(path, pattern, eventIndex));
        }
    }

    private static void readit(Path pathh, Pattern pattern, int eventIndex) {
        try {
            Root roots = new Gson().fromJson(Files.readString(pathh), Root.class);
            roots.events.stream().map(event -> event.logmsg);
            if (roots.events.size() > 3) {
                String content = roots.events.get(eventIndex).logmsg.replace("\\\"", "").replace("\"", "");
//                String content = roots.events.get(eventIndex).logmsg.replace("\\\"", "").replace("\"", "")
//                        .toUpperCase();
                Matcher matcher = pattern.matcher(content);
                if (matcher.find()) {
                    String group = matcher.group(1);
                    System.out.printf(group + "\n");
                } else {
                    System.out.printf("*********** not found\n");
                }
            } else {
//                System.out.printf("*********** some other way\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
