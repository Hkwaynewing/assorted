package extract.value.loggly.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class Json {

    @JsonProperty("LoggerName")
    public String loggerName;
    @JsonProperty("Version")
    public String version;
    @JsonProperty("ThreadID")
    public String threadID;
    @JsonProperty("LogMessage")
    public String logMessage;
    public String logStreamName;
    @JsonProperty("ThreadName")
    public String threadName;
    @JsonProperty("Level")
    public String level;
    @JsonProperty("TimeMillis")
    public String timeMillis;
    @JsonProperty("Timestamp")
    public Date timestamp;
    @JsonProperty("LevelValue")
    public String levelValue;
//    public Date timestamp;
}
