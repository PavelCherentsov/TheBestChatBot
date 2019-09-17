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
}
