package commands.organizer;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class OrganizerElement implements Comparable<OrganizerElement>, Serializable {
    public Flag flag;
    public GregorianCalendar date;
    public String task;

    public OrganizerElement(GregorianCalendar date, String task) {
        this.flag = Flag.DURING;
        this.date = date;
        this.task = task;
    }

    public void changeDate(GregorianCalendar date) {
        this.date = date;
    }

    public void changeTask(String task) {
        this.task = task;
    }

    public void updateFlag() {
        if (flag != Flag.COMPLETED) {
            boolean changed = false;
            GregorianCalendar d = new GregorianCalendar();
            d.add(Calendar.DAY_OF_MONTH, +3);
            if (date.before(d)) {
                flag = Flag.DEADLINE_IS_COMING;
                changed = true;
            }
            d.add(Calendar.DAY_OF_MONTH, -4);
            if (date.before(d)) {
                flag = Flag.FAILED;
                changed = true;
            }

            if (!changed) {
                flag = Flag.DURING;
            }
        }

    }

    @Override
    public int compareTo(OrganizerElement o) {
        return date.compareTo(o.date);
    }
}
