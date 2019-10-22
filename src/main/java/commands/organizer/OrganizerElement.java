package commands.organizer;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class OrganizerElement implements Serializable {
    public Flag flag;
    public GregorianCalendar date;
    public String task;
    public String comment;

    public OrganizerElement(GregorianCalendar date, String task) {
        this.flag = Flag.DURING;
        this.date = date;
        this.task = task;
    }

    public void updateFlag(){
        GregorianCalendar d = new GregorianCalendar();
        d.roll(Calendar.DAY_OF_MONTH, +3);
        if (date.before(d)){
            flag = Flag.DEADLINE_IS_COMING;
        }
        d.roll(Calendar.DAY_OF_MONTH, -4);
        if (date.before(d)){
            flag = Flag.FAILED;
        }

    }

    @Override
    public String toString(){
        updateFlag();
        return flag + "\t" + new SimpleDateFormat("dd MMMM y").format(date.getTime()) + "\t" + task;
    }
}
