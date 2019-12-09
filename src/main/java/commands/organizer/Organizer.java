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
                "* 'delete' - удалить таск," +
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

    public static String checkDeadlines(Bot bot, String command) {
        String output = "";
        GregorianCalendar now = new GregorianCalendar();
        for (OrganizerElement e : bot.organizer) {
            String key = e.date.toString() + e.task;
            updateValues(key, bot);
            if (now.compareTo(e.date) == -1 && e.flag == Flag.DEADLINE_IS_COMING) {
                Integer timeToSend = checkIfSend(bot, key, e);
                if (timeToSend != -1) {
                    output += getDeadlineMessage(timeToSend) + e.task + '\n';
                }
            } else if (e.flag == Flag.COMPLETED || e.flag == Flag.FAILED) {
                bot.deadlines.remove(key);
            }
        }
        return output;
    }

    public static String changeTime(Bot bot, String command) {
        GregorianCalendar now = new GregorianCalendar();
        now.add(Calendar.MINUTE, 4);
        for (OrganizerElement e : bot.organizer) {
            if (e.flag == Flag.DEADLINE_IS_COMING) {
                e.date = now;
            }
        }
        return "Время изменено";
    }

    private static String getDeadlineMessage(Integer key) {
        HashMap<Integer, String> answers = new HashMap<>();
        answers.put(86400000, "До дедлайна 24 часа! Не забудь про задание ");
        answers.put(7200000, "Осталось 2 часа, чтобы выполнить ");
        answers.put(300000, "Всего 5 минут до дедлайна по задаче ");
        answers.put(0, "Упс! Задача просрочена :( ");
        return answers.get(key);
    }


    private static Integer checkIfSend(Bot bot, String key, OrganizerElement e) {
        GregorianCalendar now = new GregorianCalendar();
        Long curTimeMs;
        if (bot.test) {
            now = getSetTime(bot.time);
            curTimeMs = now.getTimeInMillis() + bot.count * 10000;
            bot.count += 1;
        }
        else {
            curTimeMs = now.getTimeInMillis();
        }

        Long taskTimeMs = e.date.getTimeInMillis();
        Integer eps = 10000;
        for (Integer time : bot.deadlines.get(key).keySet()) {
            Boolean shouldSend = Math.abs(taskTimeMs - curTimeMs - time) < eps;
            if (!bot.deadlines.get(key).get(time) && shouldSend) {
                bot.deadlines.get(key).put(time, true);
                return time;
            }
        }
        return -1;
    }


    private static void updateValues(String key, Bot bot) {
        if (!bot.deadlines.containsKey(key)) {
            HashMap<Integer, Boolean> times = new HashMap<>();
            times.put(86400000, false);
            times.put(7200000, false);
            times.put(300000, false);
            times.put(0, false);
            bot.deadlines.put(key, times);
        }
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

    public static String delete(Bot bot, String command) {
        try {
            bot.organizer.remove(Integer.parseInt(command.split(" ")[1]));
            return "Удалено";
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



    private static GregorianCalendar getSetTime(String time){
        GregorianCalendar now = new GregorianCalendar();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        int hour = Integer.parseInt(time.split("\\:")[0]);
        int minute = Integer.parseInt(time.split("\\:")[1]);
        int second = Integer.parseInt(time.split("\\:")[2]);
        return new GregorianCalendar(year, month, day, hour, minute, second);
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

    public static String startEdit(Bot bot, String command) {
        bot.statusActive = Status.ORGANIZER_EDIT;
        return "Введи номер задания, которое надо изменить";
    }

    public static String edit(Bot bot, String command) {
        if (bot.organizer.size() == 0) {
            bot.statusActive = Status.ORGANIZER;
            return "Нет заданий для редактирования";
        }

        if (!bot.editType.equals("")) {
            return editChoice(bot, command);
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

    public static String editQuestions(Bot bot, String command) {
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

    private static String editChoice(Bot bot, String command) {
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
                    String task = command.substring(11);
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
