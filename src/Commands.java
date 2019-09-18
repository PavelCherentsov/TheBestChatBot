import schedulesrc.Schedule;

import java.util.Arrays;
import java.util.Scanner;

public class Commands {
    public static String help(String command){
        return "Я могу:\n" +
                "help - список возможностей\n" +
                "авторы - те, кто создал меня\n" +
                "echo - повторю за тобой\n" +
                "study - информация для учебы\n" +
                "quit - попрощаться";
    }

    public static String owners(String command){
        return "Авторы: \n Черенцов Павел \n Аникина Инна";
    }

    public static String echo(String command){
        return command.substring(5);
    }

    public static String quit(String command){
        return "Пока-пока :_(";
    }

    public static String study(Scanner in)
    {
        System.out.println("Напиши 'пары' - получи расписание\n" +
                "quit - выход в главное меню");
        var word = "";
        var isInSchedule = false;
        var weekDays = new String[] {"Пн", "Вт", "Ср", "Чт", "Пт"};
        while(true)
        {
            word = in.nextLine();
            if (word.equals("пары"))
            {
                System.out.println("Введи день недели (в формате: Пн, Вт, Ср, Чт, Пт)");
                var day = in.nextLine();
                if (Arrays.asList(weekDays).contains(day))
                {
                    System.out.println("Contains");
                    getSchedule(day);
                    System.out.println("Чтобы посмотреть расписание на другой день, напиши день недели в формате 'Пн'\n" +
                            "quit - вернуться в меню study");
                    var command = in.nextLine();
                    System.out.println(command);
                }
                //System.out.println("Возврат в раздел учёбки (study)");
            }
            else if (word.equals("quit"))
            {
                break;
            }
            else if (word.equals("help"))
            {
                System.out.println("Напиши 'пары' - получи расписание\n" +
                        "quit - выход в главное меню");
            }
            else
            {
                System.out.println("Напиши 'help' - тебе помогут");
            }
        }
        return "...Выход в главное меню...";
    }

    public static void getSchedule(String day)
    {
        var daySchedule = new Schedule(day);
        daySchedule.printDaySchedule();
    }
}
