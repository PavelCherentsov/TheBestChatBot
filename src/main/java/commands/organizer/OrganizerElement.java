package commands.organizer;

import java.util.GregorianCalendar;

public class OrganizerElement {
    public Flag flag;
    public GregorianCalendar date;
    public String task;
    public String comment;

    public OrganizerElement(GregorianCalendar date, String task, String comment) {
        this.flag = Flag.DURING;
        this.date = date;
        this.task = task;
        this.comment = comment;
    }

    @Override
    public String toString(){
        return flag + "\t" + date.getTime() + "\t" + task + "\t" + comment;
    }
}
