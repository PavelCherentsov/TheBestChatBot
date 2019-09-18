import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import schedulesrc.Schedule;

public class Commands {
    public static String help(String command){
        return "Я могу:\n" +
                "help - список возможностей\n" +
                "Авторы - те, кто создал меня\n" +
                "echo - повторю за тобой\n" +
                "study - информация для учебы\n" +
                "game - игра 'Виселица'\n" +
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
        Scanner in = new Scanner(System.in);
        System.out.println("Напиши 'пары' - получи расписание\n" +
                "quit - выход в главное меню");
        var word = "";
        while(true)
        {
            word = in.nextLine();
            System.out.println(word);
            if (word.equals("пары"))
            {
                System.out.println("Введи день недели (в формате: Пн, Вт, Ср, Чт, Пт)");
                var day = in.nextLine();
                getSchedule(day);
                System.out.println("Возврат в раздел учёбки (study)");
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

    public static String game(String command) {
        initLevels();
        initWords();
        var word = words.get(new Random().nextInt(words.size()-1));
        var _word = "_";
        for (int i =0; i< word.length()-1; i++){
            _word += " _";
        }
        Scanner in = new Scanner(System.in);
        var life = 6;
        while (life != 0){
            System.out.println(levels.get(life));
            System.out.println(_word);
            var c = in.nextLine().charAt(0);
            var yes = false;
            for (int i=0; i<word.length(); i++){
                if (word.charAt(i) == c){
                    _word = _word.substring(0, 2*i) + c + _word.substring(2*i+1);
                    yes = true;
                }
            }
            if (!yes){
                life--;
            }
            if (!_word.contains("_")){
                System.out.println(_word);
                return "Поздравляю! Вы выиграли! :)";
            }

        }
        if (_word.contains("_")){
            System.out.println(levels.get(life));
            return "Вы проиграли(";
        }

        return "Поздравляю! Вы выиграли! :)";

    }

    static ArrayList<String> words = new ArrayList<String>();

    private static void initWords(){
        words.add("коллаборация");
        words.add("когнитивный");
        words.add("трансцендентный");
        words.add("априори");
        words.add("конгруэнтность");

    }

    static ArrayList<String> levels = new ArrayList<String>();

    private static void initLevels(){
        levels.add(0, "  _______  \n" +
                    "  |    \\|  \n" +
                    "  O     |  \n" +
                    " \\|/    |  \n" +
                    "  |     |  \n" +
                    " /\\     |  \n" +
                    "      -----");
        levels.add(1, "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                " \\|/    |  \n" +
                "  |     |  \n" +
                " /      |  \n" +
                "      -----");
        levels.add(2, "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                " \\|/    |  \n" +
                "  |     |  \n" +
                "        |  \n" +
                "      -----");
        levels.add(3, "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                " \\|     |  \n" +
                "  |     |  \n" +
                "        |  \n" +
                "      -----");
        levels.add(4, "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                "  |     |  \n" +
                "  |     |  \n" +
                "        |  \n" +
                "      -----");
        levels.add(5, "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                "        |  \n" +
                "        |  \n" +
                "        |  \n" +
                "      -----");
        levels.add(6, "  _______  \n" +
                "  |    \\|  \n" +
                "        |  \n" +
                "        |  \n" +
                "        |  \n" +
                "        |  \n" +
                "      -----");

    }
}
