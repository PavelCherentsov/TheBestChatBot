package commands;

import bot.Bot;

public class Help{
    public static String help(Bot bot, String command){
        return "Я могу:\n" +
                "help - список возможностей\n" +
                "авторы - те, кто создал меня\n" +
                "echo - повторю за тобой\n" +
                "study - информация для учебы\n" +
                "game - игра 'Виселица'\n" +
                "quit - попрощаться";
    }
}