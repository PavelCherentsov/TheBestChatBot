package Commands;

import Commands.schedulesrc.Schedule;

import java.util.Arrays;

public class Study {

    private static boolean classes = false;

    public static String study(String command) {
        String word = "";
        String[] weekDays = new String[]{"Пн", "Вт", "Ср", "Чт", "Пт"};
        if (command.equals("study") && !classes) {
            return "Напиши 'пары' - получи расписание\nquit - выход в меню";
        } else if (command.equals("пары") && !classes) {
            classes = true;
            return "Введи день недели (в формате: Пн, Вт, Ср, Чт, Пт)";
        } else if (Arrays.asList(weekDays).contains(command) && classes) {
            word = getSchedule(command);
            return word + "Посмотри другой день или пиши 'все', чтобы выйти";
        } else if (classes && command.equals("все")) {
            classes = false;
            return "Напиши 'пары' - получи расписание\nquit - выход в меню";
        } else if (classes && command.equals("help")) {
            return "Чтобы посмотреть расписание на другой день, напиши день недели в формате 'Пн'\n" +
                    "все - вернуться в меню study";
        } else if (command.equals("help")) {
            return "Напиши 'пары' - получи расписание\nquit - выход в меню";
        } else if (command.equals("quit") && !classes) {
            return "Выход в главное меню";
        } else {
            return "Напиши help - тебе помогут!";
        }

    }

    public static String getSchedule(String day)
    {
        Schedule daySchedule = new Schedule(day);
        String result = daySchedule.getDaySchedule();
        return result;
    }
}
