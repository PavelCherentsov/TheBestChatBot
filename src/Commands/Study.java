package Commands;

import Commands.schedulesrc.Schedule;

import java.util.Arrays;
import java.util.Scanner;

public class Study {

    public static String study(String c)
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Напиши 'пары' - получи расписание\n" +
                "все - выход в главное меню");
        var word = "";
        var weekDays = new String[] {"Пн", "Вт", "Ср", "Чт", "Пт"};
        while(true)
        {
            word = in.nextLine();
            if (word.equals("пары"))
            {
                System.out.println("Введи день недели (в формате: Пн, Вт, Ср, Чт, Пт)");
                while (true)
                {
                    var day = in.nextLine();
                    if (Arrays.asList(weekDays).contains(day))
                    {
                        System.out.println("Contains");
                        getSchedule(day);
                        System.out.println("Посмотри другой день или пиши 'все', чтобы выйти");
                    }
                    else if (day.equals("все"))
                    {
                        System.out.println("Возврат в раздел учёбки (study)");
                        break;
                    }
                    else if (day.equals("help"))
                    {
                        System.out.println("Чтобы посмотреть расписание на другой день, напиши день недели в формате 'Пн'\n" +
                                "все - вернуться в меню study");
                    }
                    else if (day.equals("хелп"))
                    {
                        System.out.println("Заело Shift-Alt?) Ладно, держи помощь:");
                        System.out.println("Чтобы посмотреть расписание на другой день, напиши день недели в формате 'Пн'\n" +
                                "все - вернуться в меню study");
                    }
                    else {
                        System.out.println("Некорректный ввод. Пиши 'help' для помощи");
                    }
                }
                //
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
            else if (word.equals("хелп"))
            {
                System.out.println("Белым по черному же написано - 'help'! Держи:");
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
