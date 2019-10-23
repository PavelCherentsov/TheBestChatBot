package commands.organizer;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import bot.Bot;
import bot.Status;

public class Organizer implements Serializable {
    private ArrayList<OrganizerElement> list = new ArrayList<>();

    public String showDefault(Bot bot, String command) {
        return "Введи 'help', чтобы узнать все возможности";
    }

    public String help(Bot bot, String command) {
        return "help)";
    }

    public String start(Bot bot, String command) {
        bot.statusActive = Status.ORGANIZER;
        return "Приветствую, я твой личный Time-Manager.";
    }

    public String all(Bot bot, String command) {
        String result = "<pre>\nВсе задания:\n";
        int number = 0;
        for (OrganizerElement e : list) {
            if (e.flag != Flag.COMPLETED)
                e.updateFlag();
            result = result + Integer.toString(number++) + "\t" + e.flag + "\t\t\t" +
                    getDateFormat(e.date.getTime()) + "\t" + e.task + "\n";
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
        String date = command.split(" ")[0];
        String task = command.split("[0-9]{2}\\.[0-9]{2}\\.[0-9]{4} ")[1];
        list.add(new OrganizerElement(getDate(date), task));
        Collections.sort(list);
        bot.statusActive = Status.ORGANIZER;
        return "Задание добавлено";
    }

    private GregorianCalendar getDate(String date) {
        int day = Integer.parseInt(date.split("\\.")[0]);
        int month = Integer.parseInt(date.split("\\.")[1]) - 1;
        int year = Integer.parseInt(date.split("\\.")[2]);
        return new GregorianCalendar(year, month, day);
    }

    public String show(Bot bot, String command) {
        bot.statusActive = Status.ORGANIZER_SHOW;
        return "По какому критерию показывать список? Дата или Флаг? (вводи сразу)";
    }

    public String showParse(Bot bot, String command) {
        bot.statusActive = Status.ORGANIZER;
        if (Pattern.matches("([0-9]{2}\\.){2}[0-9]{4}", command))
            return showByDate(command);
        if (Pattern.matches("comp.*", command.toLowerCase()))
            return showByFlag(Flag.COMPLETED);
        if (Pattern.matches("dead.*", command.toLowerCase()))
            return showByFlag(Flag.DEADLINE_IS_COMING);
        if (Pattern.matches("dur.*", command.toLowerCase()))
            return showByFlag(Flag.DURING);
        if (Pattern.matches("fail.*", command.toLowerCase()))
            return showByFlag(Flag.FAILED);
        return "Я тебя не понял, напиши 'help' для помощи";
    }

    public String showByDate(String command) {
        String result = "<pre>\nНа " + command + " нужно:\n";
        for (OrganizerElement e : list) {
            if (e.flag != Flag.COMPLETED && e.date.compareTo(getDate(command)) == 0)
                result = result + e.task + "\n";
        }
        result += "\n</pre>";
        return result;
    }

    public String showByFlag(Flag flag) {
        String result = "<pre>\n" + flag + ":\n";
        for (OrganizerElement e : list) {
            if (e.flag != Flag.COMPLETED && e.flag == flag)
                result = result + getDateFormat(e.date.getTime())
                        + "\t\t" + e.task + "\n";
        }
        result += "\n</pre>";
        return result;
    }

    public String back(Bot bot, String command) {
        bot.statusActive = Status.ORGANIZER;
        return "Отмена записи";
    }

    public String quit(Bot bot, String command) {
        bot.statusActive = Status.MENU;
        return "Выход в главное меню";
    }

    private String getDateFormat(Date date) {
        return new SimpleDateFormat("dd.MM.y").format(date);
    }

    public String start_edit(Bot bot, String command) {
        bot.statusActive = Status.ORGANIZER_EDIT;
        return "Введите номер задания, которое хотите изменить";
    }

    public String edit(Bot bot, String command) {
        try {
            OrganizerElement task = list.get(Integer.parseInt(command));
            //list.set(task, value);
            String result = task.toString();
            result += "\nЧто меняем? Дату - 1, задание - 2, дату и задание - 3";
            return result;
        } catch (NumberFormatException e) {
            return "Неверный ввод. Введите номер задания или 'back', чтобы вернуться назад";
        } catch (IndexOutOfBoundsException e) {
            return "Неверный номер задания";
        }
    }
}
