package extract.value.supplier.methods;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class Read2 {

    private final static String ERROR_MSG = "RolesAllowed\\((.*)\\)";

    /**
     * comes from https://bitbucket.org/rezdy/svc-auth-container/src/d7edd96db529bdb1e564fb685264516477f3b349/src/config/plans/CHANNELMANAGER.ts?at=master
     */
    private final static Set<String> CHANNEL_MANAGER = Set
            .of("Scopes.REZDYCONNECT_W", "Scopes.ACTIONLOGS_R", "Scopes.AGENT_INVOICE_W", "Scopes.AGENT_W",
                    "Scopes.API_R", "Scopes.API_W", "Scopes.BILLING_W", "Scopes.BOOKINGBUTTON_R",
                    "Scopes.BOOKINGFORMSETTINGS_W", "Scopes.BOOKINGFIELD_W", "Scopes.CATEGORY_W", "Scopes.COMPANY_W",
                    "Scopes.COMPANYDETAILS_W", "Scopes.CUSTOMER_W", "Scopes.CUSTOMERCOMMS_W", "Scopes.EMAILTEMPLATE_W",
                    "Scopes.EXTRA_W", "Scopes.LEGALSETTINGS_W", "Scopes.MARKETPLACEPAYMENTSETTINGS_W",
                    "Scopes.MESSAGING_W", "Scopes.ORDER_R", "Scopes.PICKUP_W", "Scopes.PRICEOPTION_W",
                    "Scopes.PRODUCT_W", "Scopes.PRODUCTTAG_W", "Scopes.RATE_W", "Scopes.REGIONALSETTINGS_W",
                    "Scopes.REPORT_R", "Scopes.REPORT_W", "Scopes.SUBSCRIPTION_W", "Scopes.USERPROFILE_W",
                    "Scopes.USERS_R", "Scopes.USERS_W", "Scopes.WEBHOOK_W", "Scopes.PAYMENTSETTINGS_R",
                    "Scopes.MARKETPLACEPAYMENTSETTINGS_R", "Scopes.WEBSITETRAVEL_W", "Scopes.ORDERPAYMENT_W",
                    "Scopes.PAYMENTSETTINGS_W");

    public static void main(String[] args) throws IOException {
        try (Stream<Path> paths = Files
                .walk(Paths.get("/Users/waynewen/workspace/api/api-public/src/main/java/com/rezdy/api/pub/v1/rest"))) {
            Pattern pattern = Pattern.compile(ERROR_MSG);
            paths.filter(Files::isRegularFile).forEach(path -> readit(path, pattern));
        }
    }

    private static void readit(Path path, Pattern pattern) {
        System.out.printf("\n----------------------------------\n");
        System.out.printf(path.getFileName().toString() + "\n");
        try {
            List<String> lines = Files.readAllLines(path);
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                processLine(pattern, lines, i, line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processLine(Pattern pattern, List<String> lines, int i, String line) {
        if (line.trim().startsWith("@RolesAllowed")) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String scope = matcher.group(1);
                if (CHANNEL_MANAGER.contains(scope)) {
                    System.out.printf(lines.get(i + 1) + "\n");
                }
            }
        }
    }
}