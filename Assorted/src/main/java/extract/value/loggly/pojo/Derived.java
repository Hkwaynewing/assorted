package extract.value.loggly.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Derived {

    @JsonProperty("CorrelationID")
    public String correlationID;
}
