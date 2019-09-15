public class Commands {
    public static String help(String command){
        return "Я могу:\n" +
                "help - список возможностей\n" +
                "Авторы - те, кто создал меня\n" +
                "echo - повторю за тобой\n";
    }

    public static String owners(String command){
        return "Авторы: \n Черенцов Павел \n Аникина Инна";
    }

    public static String echo(String command){
        var array = command.split(" ");
        var res = "";
        for (int i=1; i< array.length;i++){
            res = res + array[i] + " ";
        }
        return res;
    }
}
