package commands;

import bot.Bot;

public class Help{
    public static String help(Bot bot, String command){
        return "Давай посмотрим, что я умею:\n" +
                " * help - список возможностей\n" +
                " * авторы - те, кто создал меня\n" +
                " * echo - повторю за тобой\n" +
                " * study - информация для учебы\n" +
                " * game - игра 'Виселица'\n" +
                " * organizer - личный time-manager\n" +
                " * quit - попрощаться";
    }
}
