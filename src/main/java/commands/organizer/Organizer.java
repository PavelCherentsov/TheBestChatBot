package commands.organizer;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import bot.Bot;
import bot.Status;

public class Organizer implements Serializable {
    private ArrayList<OrganizerElement> list = new ArrayList<>();
    private OrganizerElement currentTask;
    private String editType = "";
    private int n;

    public String start(Bot bot, String command) {
        bot.statusActive = Status.ORGANIZER;
        return "ОРГАНАЙЗЕР";
    }

    public String all(Bot bot, String command) {
        String result = "<pre>\nВсе задания:\n";
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
        editType = "";
        return "Отмена записи задания";
    }

    public String quit(Bot bot, String command) {
        bot.statusActive = Status.MENU;
        return "Выход в главное меню";
    }

    public String start_edit(Bot bot, String command)
    {
        bot.statusActive = Status.ORGANIZER_EDIT;
        return "Введите номер задания, которое хотите изменить";
    }

    public String edit(Bot bot, String command)
    {
        if (list.size() == 0)
        {
            bot.statusActive = Status.ORGANIZER;
            return "Нет заданий для редактирования";
        }

        if (!editType.equals(""))
        {
            return edit_choice(bot, command);
        }
        try
        {
            OrganizerElement task = list.get(Integer.parseInt(command));
            n = Integer.parseInt(command);
            String result =  task.toString();
            currentTask = task;
            result += "\nЧто меняем? Дату - date, задание - task, дату и задание - all";
            return result;
        }
        catch (NumberFormatException e)
        {
            return "Неверный ввод. Введите номер задания или 'back', чтобы вернуться назад";
        }
        catch (IndexOutOfBoundsException e)
        {
            return "Неверный номер задания";
        }
    }

    public String edit_questions(Bot bot, String command)
    {
        String[] good_commands = new String[] {"date", "task", "all"};
        if (Arrays.asList(good_commands).contains(command))
            editType = command;
        HashMap<String, String> questions = new HashMap<>();
        questions.put("date", "Введи дату в формате ДД.ММ.ГГГГ");
        questions.put("task", "Введи таск");
        questions.put("all", "Введите задание: ДД.ММ.ГГГГ task");
        questions.put("default", "Неверный ввод");
        return questions.getOrDefault(command, questions.get("default"));
    }

    private String edit_choice(Bot bot, String command)
    {
        int day;
        int month;
        int year;
        String result = "";
        try {
            switch (editType) {
                case ("date"):
                    day = Integer.parseInt(command.split(" ")[0].split("\\.")[0]);
                    month = Integer.parseInt(command.split(" ")[0].split("\\.")[1]);
                    year = Integer.parseInt(command.split(" ")[0].split("\\.")[2]);
                    currentTask.changeDate(new GregorianCalendar(year, month - 1, day));
                    currentTask.updateFlag();
                    result += "Дата изменена";
                    break;
                case ("task"):
                    currentTask.changeTask(command);
                    result += "Задание изменено";
                    break;
                case ("all"):
                    day = Integer.parseInt(command.split(" ")[0].split("\\.")[0]);
                    month = Integer.parseInt(command.split(" ")[0].split("\\.")[1]);
                    year = Integer.parseInt(command.split(" ")[0].split("\\.")[2]);
                    String task = command.split("[0-9]{2}\\.[0-9]{2}\\.[0-9]{4} ")[1];
                    list.set(n, new OrganizerElement(new GregorianCalendar(year, month - 1, day), task));
                    n = -1;
                    result += "Задача изменена";
                    break;
            }
            editType = "";
            bot.statusActive = Status.ORGANIZER;
            return result;
        }
        catch (Exception e)
        {
            return "Неверный ввод. Напиши еще раз или пиши 'back', чтобы вернуться назад";
        }
    }
}
