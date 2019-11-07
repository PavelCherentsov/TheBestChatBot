package commands.schedulesrc;

import java.io.Serializable;

public class ScheduleElement implements Serializable {
    public Integer number;
    public String time;
    public String subject;
    public String cabinet;
    public ScheduleElement(Integer num, String t, String sub, String cab)
    {
        number = num;
        time = t;
        subject = sub;
        cabinet = cab;
    }
}
