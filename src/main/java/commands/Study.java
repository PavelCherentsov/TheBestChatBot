package commands;

import commands.schedulesrc.Schedule;

import java.util.ArrayList;
import java.util.List;

import bot.Bot;
import bot.Status;


public class Study {

    private static boolean classes = false;
    private static List<String> weekDays = new ArrayList<String>() {{
        add("m");
        add("t");
        add("w");
        add("th");
        add("f");

    }};

    public static String getSchedule(String day)
    {
        Schedule daySchedule = new Schedule(day);
        return daySchedule.getDaySchedule();
    }

    public static String mainMenu(Bot bot, String command)
    {
        bot.statusActive = Status.STUDY;
        return "Напиши 'classes' - получи расписание\nquit - выход в меню";
    }

    public static String startClasses (Bot bot, String command)
    {
        bot.statusActive = Status.CLASSES;
        return "Введи день недели (в формате: M, T, W, TH, F)";
    }

    public static String getClasses (Bot bot, String command)
    {
        if (weekDays.contains(command))
        {
            String word = getSchedule(command);
            return word + "Посмотри другой день или пиши 'quit', чтобы выйти";
        }
        else
            return def(bot, command);
    }

    public static String studyHelp (Bot bot, String command)
    {
        return "Напиши 'classes' - получи расписание\nquit - выход в меню";
    }

    public static String classesHelp (Bot bot, String command)
    {
        return "Чтобы посмотреть расписание на другой день, напиши день недели в формате 'Пн'\n" +
                "все - вернуться в меню study";
    }

    public static String quitToMenu (Bot bot, String command)
    {
        bot.statusActive = Status.MENU;
        return "Выход в главное меню";
    }

    public static String def (Bot bot, String command)
    {
        return "Напиши help";
    }
}
