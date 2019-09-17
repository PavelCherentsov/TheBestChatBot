import schedulesrc.Schedule;
import java.util.Scanner;

public class Commands {
    public static String help(String command){
        return "Я могу:\n" +
                "help - список возможностей\n" +
                "Авторы - те, кто создал меня\n" +
                "echo - повторю за тобой\n" +
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

    public static String study(String command)
    {
        System.out.println("Напиши 'пары' - получи расписание\n" +
                "somecommand - выход в главное меню");
        Scanner input = new Scanner(System.in);
        var word = "";
        while(true)
        {
            word = input.nextLine();
            System.out.println(word);
            if (word.equals("пары"))
            {
                System.out.println("Введи день недели (в формате: Пн, Вт, Ср, Чт, Пт)");
                var day = input.nextLine();
                getSchedule(day);
                System.out.println("Возврат в раздел учёбки (study)");
            }
            else if (word.equals("quit"))
            {
                System.out.println("I QUIT");
                break;
            }
            else if (word.equals("help"))
            {
                System.out.println("Напиши 'пары' - получи расписание\n" +
                        "somecommand - выход в главное меню");
            }
            else
            {
                System.out.println("Напиши 'help' - тебе помогут");
            }
        }
        input.close();
        System.out.println("end of study");
        return "...Возврат в главное меню...";
    }

    public static void getSchedule(String day)
    {
        var daySchedule = new Schedule(day);
        daySchedule.printDaySchedule();
    }
}
