package commands.schedulesrc;

import java.io.Serializable;
import java.util.ArrayList;

public class Schedule implements Serializable {
    public String weekDay;
    public ArrayList<ScheduleElement> subjects;

    public Schedule(String d)
    {
        weekDay = d;
        subjects = makeFT201DaySchedule(d);
    }

    public ArrayList<ScheduleElement> makeFT201DaySchedule(String day)
    {
        ArrayList<ScheduleElement> daySchedule = new ArrayList<ScheduleElement>();
        String [] subjects = new String[] {"Объектно-ориентированное программирование",
                "Архитектура ЭВМ", "Иностранный язык", "Дискретная математика", "Языки сценариев",
                "Прикладная физическая культура", "Кратные интегралы и ряды", "Компьютерные сети",
                "Экономика"};
        switch (day.toLowerCase())
        {
            case "пн": {
                daySchedule.add(new ScheduleElement(3, "12:50", subjects[0], "632"));
                break;
            }
            case "вт": {
                daySchedule.add(new ScheduleElement(2, "10:50", subjects[1], "632"));
                daySchedule.add(new ScheduleElement(3, "12:50", subjects[2], "индив."));
                daySchedule.add(new ScheduleElement(4, "14:30", subjects[3], "532"));
                daySchedule.add(new ScheduleElement(5, "16:10", subjects[4], "150"));
                break;
            }
            case "ср": {
                daySchedule.add(new ScheduleElement(2, "10:15", subjects[5], "индив."));
                daySchedule.add(new ScheduleElement(3, "12:50", subjects[6], "509"));
                daySchedule.add(new ScheduleElement(4, "14:30", subjects[6], "605"));
                daySchedule.add(new ScheduleElement(5, "16:10", subjects[3], "612"));
                break;
            }
            case "чт": {
                daySchedule.add(new ScheduleElement(1, "09:00", subjects[2], "индив."));
                daySchedule.add(new ScheduleElement(2, "10:40", subjects[3], "532"));
                daySchedule.add(new ScheduleElement(3, "12:50", subjects[7], "150"));
                daySchedule.add(new ScheduleElement(4, "14:30", subjects[0], "526"));
                break;
            }
            case "пт": {
                daySchedule.add(new ScheduleElement(2, "10:40", subjects[6], "532"));
                daySchedule.add(new ScheduleElement(3, "12:50", subjects[6], "628"));
                daySchedule.add(new ScheduleElement(5, "16:00", subjects[5], "индив."));
            }
        }

        return daySchedule;
    }

    public String getDaySchedule()
    {
        String res = "";
        res += (weekDay + ":\n");
        for (ScheduleElement subject : subjects) {
            res += (subject.number.toString() + " " + subject.time + " " +
                    subject.subject + " " + subject.cabinet + "\n");
        }

        return res;
    }
}
