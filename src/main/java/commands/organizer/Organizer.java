package commands.organizer;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import bot.Bot;
import bot.Status;

public class Organizer implements Serializable {
    private ArrayList<OrganizerElement> list = new ArrayList<>();

    public String start(Bot bot, String command) {
        bot.statusActive = Status.ORGANIZER;
        return "ОРГАНАЙЗЕР";
    }

    public String all(Bot bot, String command) {
        String result = "<pre>\n";
        int number = 0;
        for (OrganizerElement e : list) {
            if (e.flag != Flag.COMPLETED)
                e.updateFlag();
            if (e.flag == Flag.DEADLINE_IS_COMING)
                result = result + Integer.toString(number) + "\t" + e.flag + "\t" +
                        new SimpleDateFormat("dd MMMM y").format(e.date.getTime()) + "\t" + e.task+"\n";
            else
                result = result + Integer.toString(number) + "\t" + e.flag + "\t\t\t" +
                        new SimpleDateFormat("dd MMMM y").format(e.date.getTime()) + "\t" + e.task+"\n";
            number++;
        }
        result += "\n</pre>";
        return result;
    }

    public String add(Bot bot, String command) {
        bot.statusActive = Status.ORGANIZER_ADD;
        return "Введите новое задание: ДД.ММ.ГГГГ task";
    }

    public String completed(Bot bot, String command) {
        list.get(Integer.parseInt(command.split(" ")[1])).flag = Flag.COMPLETED;
        return "Выполнено";
    }

    public String push(Bot bot, String command) {
        int day = Integer.parseInt(command.split(" ")[0].split("\\.")[0]);
        int month = Integer.parseInt(command.split(" ")[0].split("\\.")[1]);
        int year = Integer.parseInt(command.split(" ")[0].split("\\.")[2]);
        String task = command.split("[0-9]{2}\\.[0-9]{2}\\.[0-9]{4} ")[1];
        list.add(new OrganizerElement(new GregorianCalendar(year, month - 1, day), task));
        //не робит
        Collections.sort(list, (x, y) -> x.date.after(y.date) ? 1 : 0);

        bot.statusActive = Status.ORGANIZER;
        return "Задание добавлено";
    }

    public String back(Bot bot, String command) {
        bot.statusActive = Status.ORGANIZER;
        return "Отмена записи задания";
    }

    public String quit(Bot bot, String command) {
        bot.statusActive = Status.MENU;
        return "Выход в главное меню";
    }
}
