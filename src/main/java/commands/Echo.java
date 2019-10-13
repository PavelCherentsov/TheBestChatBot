package commands;

import bot.Bot;

public class Echo {
    public static String echo(Bot bot, String command){
        if (command.substring(4).equals("") || command.substring(4).equals(" "))
            return "Все молчат, а ты купи слона.";
        return "Все говорят: \"" + command.substring(5) + "\", а ты купи слона.";
    }
}
