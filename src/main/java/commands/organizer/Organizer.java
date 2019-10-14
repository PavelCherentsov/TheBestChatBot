package commands.organizer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import bot.Bot;
import bot.Status;

public class Organizer {
    private ArrayList<OrganizerElement> list = new ArrayList<>();

    public String start(Bot bot, String command) {
        bot.statusActive = Status.ORGANIZER;
        return "+Organizer";
    }

    public String all(Bot bot, String command) {
        String result = "";
        for (OrganizerElement e : list) {
            result = result + e.toString() + "\n";
        }
        return result;
    }

    public String add(Bot bot, String command) {
        list.add(new OrganizerElement(new GregorianCalendar(2019,Calendar.OCTOBER,14),
                "ООП | Подготовиться к лекции", ""));
        return "Успешно";
    }

    public String quit(Bot bot, String command) {
        bot.statusActive = Status.MENU;
        return "-Organizer";
    }
}
