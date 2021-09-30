package extract.value.text.processing.methods;

import java.io.IOException;

class Read2 {

    private static String ss =
            "\"<p>It's not a partial update, <b>full booking object, as it was retrieved from the booking create or search services</b>, has to be send back to the request payload.\"\n"
                    + "                    + \"Otherwise, the properties or relations which <i>are currently supported (see below)</i> and they are not sent, will be deleted. </p>\"\n"
                    + "                    + \"<b>Order of the items in arrays have to be preserved for the following fields 'items', 'participants'.</b>\"\n"
                    + "                    + \"<p><b>Currently supported</b> update of properties:</p>\"\n"
                    + "                    + \"<ul>\"\n"
                    + "                    + \"<li>\"\n"
                    + "                    + \"Booking.status  which can be used to do a 2-step booking process - 1 step : create order with status PROCESSING, 2 step: update status to CONFIRMED/ABANDONED_CART. <br/>\"\n"
                    + "                    + \"Be aware that status update to CANCELLED, is not same as delete order, payments will not get refunded in this case.<br/>\"\n"
                    + "                    + \"</li>\"\n"
                    + "                    + \"<li>\"\n"
                    + "                    + \"Booking.customer - all customer data can be updated\"\n"
                    + "                    + \"</li>\"\n"
                    + "                    + \"<li>\"\n"
                    + "                    + \"Booking.field - all 'per booking' booking fields values\"\n"
                    + "                    + \"</li>\"\n"
                    + "                    + \"<li>\"\n"
                    + "                    + \"Booking.item.participant.field - all 'per participant' booking fields values\"\n"
                    + "                    + \"</li>\"\n"
                    + "                    + \"<li>\"\n"
                    + "                    + \"Booking.resellerComments - both the booking agent and the supplier can update the booking resellerComments\"\n"
                    + "                    + \"</li>\"\n"
                    + "                    + \"<li>\"\n"
                    + "                    + \"Booking.resellerReference - both the booking agent and the supplier can update the booking resellerReference\"\n"
                    + "                    + \"</li>\"\n"
                    + "                    + \"<li>\"\n"
                    + "                    + \"Booking.items.pickupLocation.locationName - both the booking agent and the supplier can update the booking pickup location\"\n"
                    + "                    + \"</li>\"\n"
                    + "                    + \"</ul>\"";

    public static void main(String[] args) throws IOException {
        String replace = ss.replace("\"", "").replace("+", "").replace("                    ", "");
        System.out.printf(replace);
    }

}