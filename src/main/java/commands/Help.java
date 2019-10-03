package commands;

public class Help{
    public static String help(String command){
        return "Я могу:\n" +
                "/help - список возможностей\n" +
                "Авторы - те, кто создал меня\n" +
                "echo - повторю за тобой\n" +
                "study - информация для учебы\n" +
                "game - игра 'Виселица'\n" +
                "quit - попрощаться";
    }
}
