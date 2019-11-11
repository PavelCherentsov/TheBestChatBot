package commands.organizer;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import bot.Bot;
import bot.Status;


public class Organizer implements Serializable {

    public static String showDefault(Bot bot, String command) {
        return "Введи 'help', чтобы узнать все возможности";
    }

    public static String help(Bot bot, String command) {
        return "Справка по пользованию Time-Manager'ом:\n" +
                "\nФлаги:\n" +
                Flag.COMPLETED.getEmoji() + " - Выполнено\n" +
                Flag.DURING.getEmoji() + " - В процессе\n" +
                Flag.DEADLINE_IS_COMING.getEmoji() + " - Скоро дедлайн\n" +
                Flag.FAILED.getEmoji() + " - Потрачено\n" +
                "\nКоманды:\n" +
                "* 'add' - добавить задачу, \n" +
                "* 'all' - показать все задачи, \n" +
                "* 'edit' - редактировать,\n" +
                "* 'show' - показать задачи по приоритетам или дате,\n" +
                "* 'completed' - отметить задачу как выполненную,\n" +
                "* 'quit' - выход в меню";
    }

    public static String addHelp(Bot bot, String command) {
        return "Введи новое задание: ДД.ММ.ГГГГ task \n'back' - вернуться назад";
    }

    public static String start(Bot bot, String command) {
        bot.statusActive = Status.ORGANIZER;
        return "Приветствую, я твой личный Time-Manager.";
    }

    public static String all(Bot bot, String command) {
        String result = "Все задания:\n<pre>";
        int number = 0;
        for (OrganizerElement e : bot.organizer) {
            if (e.flag != Flag.COMPLETED)
                e.updateFlag();
            result = result + "\n" + Integer.toString(number++) + "\t" + e.flag.getEmoji() + "\t\t" +
                    getDateFormat(e.date.getTime()) + "\t" + e.task;
        }
        result += "</pre>";
        if (bot.organizer.isEmpty())
            return "Заданий пока нет";
        return result;
    }

    public static String add(Bot bot, String command) {
        bot.statusActive = Status.ORGANIZER_ADD;
        return "Введи новое задание: ДД.ММ.ГГГГ ЗАДАНИЕ";
    }

    public static String completed(Bot bot, String command) {
        try {
            bot.organizer.get(Integer.parseInt(command.split(" ")[1])).flag = Flag.COMPLETED;
            return "Выполнено";
        } catch (IndexOutOfBoundsException e) {
            return "Неверный номер задания";
        } catch (Exception e) {
            return "Неправильный ввод :( \nВведи 'completed [N задачи]'";
        }
    }

    public static String push(Bot bot, String command) {
        if (!Pattern.matches("(\\d+\\.){2}\\d+ .+", command))
            return notCorrect();
        String date = command.split(" ")[0];
        String task = command.split("(\\d+\\.){2}\\d+ ")[1];
        bot.organizer.add(new OrganizerElement(getDate(date), task));
        Collections.sort(bot.organizer);
        bot.statusActive = Status.ORGANIZER;
        return "Задание добавлено";
    }

    private static GregorianCalendar getDate(String date) {
        int day = Integer.parseInt(date.split("\\.")[0]);
        int month = Integer.parseInt(date.split("\\.")[1]) - 1;
        int year = Integer.parseInt(date.split("\\.")[2]);
        return new GregorianCalendar(year, month, day);
    }

    public static String show(Bot bot, String command) {
        bot.statusActive = Status.ORGANIZER_SHOW;
        return "По какому критерию показывать список? Дата или Флаг? (вводи сразу)";
    }

    public static String showParse(Bot bot, String command) {
        bot.statusActive = Status.ORGANIZER;
        if (bot.organizer.isEmpty())
            return "Заданий пока нет";
        if (Pattern.matches("([0-9]{2}\\.){2}[0-9]{4}", command))
            return showByDate(command, bot);
        Flag flag = getFlag(command);
        if (flag != null)
            return showByFlag(flag, bot);
        return notCorrect();
    }

    private static Flag getFlag(String command) {
        for (Flag flag : Flag.values()) {
            if (Pattern.matches(flag.getPattern(), command.toLowerCase()))
                return flag;
        }
        return null;
    }

    public static String notCorrect() {
        return "Я тебя не понял, напиши 'help' для помощи";
    }

    public static String showByDate(String command, Bot bot) {
        String result = "На " + command + " нужно:\n<pre>";
        for (OrganizerElement e : bot.organizer) {
            if (e.flag != Flag.COMPLETED && e.date.compareTo(getDate(command)) == 0)
                result = result + "\n" + e.task;
        }
        result += "</pre>";
        return result;
    }

    public static String showByFlag(Flag flag, Bot bot) {
        String result = "<pre>" + flag.getName() + ":\n";
        for (OrganizerElement e : bot.organizer) {
            if (e.flag == flag)
                result = result + "\n" + getDateFormat(e.date.getTime()) + "\t\t" + e.task;
        }
        result += "</pre>";
        return result;
    }

    public static String back(Bot bot, String command) {
        bot.statusActive = Status.ORGANIZER;
        bot.editType = "";
        return "Отмена записи";
    }

    public static String quit(Bot bot, String command) {
        bot.statusActive = Status.MENU;
        return "Выход в главное меню";
    }

    private static String getDateFormat(Date date) {
        return new SimpleDateFormat("dd.MM.y").format(date);
    }

    public static String start_edit(Bot bot, String command) {
        bot.statusActive = Status.ORGANIZER_EDIT;
        return "Введи номер задания, которое надо изменить";
    }

    public static String edit(Bot bot, String command) {
        if (bot.organizer.size() == 0) {
            bot.statusActive = Status.ORGANIZER;
            return "Нет заданий для редактирования";
        }

        if (!bot.editType.equals("")) {
            return edit_choice(bot, command);
        }
        try {
            OrganizerElement task = bot.organizer.get(Integer.parseInt(command));
            bot.n = Integer.parseInt(command);
            bot.currentTask = task;
            return "Что меняем? Дату - date, задание - task, дату и задание - all";
        } catch (NumberFormatException e) {
            return "Неверный ввод. Введи номер задания или 'back', чтобы вернуться назад";
        } catch (IndexOutOfBoundsException e) {
            return "Неверный номер задания";
        }
    }

    public static String edit_questions(Bot bot, String command) {
        String[] goodCommands = new String[]{"date", "task", "all"};
        if (Arrays.asList(goodCommands).contains(command))
            bot.editType = command;
        HashMap<String, String> questions = new HashMap<>();
        questions.put("date", "Введи дату в формате ДД.ММ.ГГГГ");
        questions.put("task", "Введи таск");
        questions.put("all", "Введи задание: ДД.ММ.ГГГГ task");
        questions.put("default", "Неверный ввод");
        return questions.getOrDefault(command, questions.get("default"));
    }

    private static String edit_choice(Bot bot, String command) {
        String result = "";
        try {
            switch (bot.editType) {
                case ("date"):
                    bot.currentTask.changeDate(getDate(command.split(" ")[0]));
                    bot.currentTask.updateFlag();
                    result += "Дата изменена";
                    break;
                case ("task"):
                    bot.currentTask.changeTask(command);
                    result += "Задание изменено";
                    break;
                case ("all"):
                    String task = command.split("([0-9]{2}\\.){2}\\.[0-9]{4} ")[1];
                    bot.organizer.set(bot.n, new OrganizerElement(getDate(command.split(" ")[0]), task));
                    bot.n = -1;
                    result += "Задача изменена";
                    break;
            }
            bot.editType = "";
            bot.statusActive = Status.ORGANIZER;
            return result;
        } catch (Exception e) {
            return "Неверный ввод. Напиши еще раз или пиши 'back', чтобы вернуться назад";
        }
    }
}
