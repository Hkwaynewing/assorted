package extract.value.loggly.pojo;

import java.util.List;

public class Event {

    public String id;
    public Object timestamp;
    public String logmsg;
    public String raw;
    public Event event;
    public List<String> logtypes;
    public Object unparsed;
    public List<String> tags;
}
