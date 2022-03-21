package assorted;

public final class IngestionTopics {

    public enum Enum {

        VALUE_ONE(1);  //1


        public final int version;
        public final String name;

        Enum(final int weight) {
            this.version = weight; //2
            this.name = toTopicNamePart(this); //3
        }


    }

    private static String toTopicNamePart(final java.lang.Enum<?> e) {
        return e.name().toLowerCase().replace("_", "-");
    }

    private static final int val1 = Enum.VALUE_ONE.version; //4

    //Cannot read field "version" because "assorted.IngestionTopics$Enum.VALUE_ONE" is null
    public static int getVal1(final Enum anEnum) {
        return val1;
    }

}
