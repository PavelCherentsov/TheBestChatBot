package commands;

import commands.schedulesrc.Schedule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import bot.Bot;
import bot.Status;


public class Study{

    private transient List<String> weekDays = new ArrayList<String>() {{
        add("пн");
        add("вт");
        add("ср");
        add("чт");
        add("пт");

    }};

    public String getSchedule(String day) {
        Schedule daySchedule = new Schedule(day);
        return daySchedule.getDaySchedule();
    }

    public String mainMenu(Bot bot, String command) {
        bot.statusActive = Status.STUDY;
        return "Напиши 'classes' или 'пары' - получи расписание\n'quit' - выход в меню";
    }

    public String startClasses(Bot bot, String command) {
        bot.statusActive = Status.CLASSES;
        return "Введи день недели (в формате: пн, вт, ср, чт, пт)";
    }

    public String getClasses(Bot bot, String command) {
        if (weekDays.contains(command.toLowerCase())) {
            String word = getSchedule(command.toLowerCase());
            return word + "Посмотри другой день или пиши 'quit', чтобы выйти";
        } else
            return def(bot, command);
    }

    public String studyHelp(Bot bot, String command) {
        return "Напиши 'classes' или 'пары' - получи расписание\n'quit' - выход в меню";
    }

    public String classesHelp(Bot bot, String command) {
        return "Чтобы посмотреть расписание на другой день, напиши день недели в формате 'пн'\n" +
                "все - вернуться в меню study";
    }

    public String quitToMenu(Bot bot, String command) {
        bot.statusActive = Status.MENU;
        return "Выход в главное меню";
    }

    public String def(Bot bot, String command) {
        return "Напиши 'help' - тебе помогут ;)";
    }
}
