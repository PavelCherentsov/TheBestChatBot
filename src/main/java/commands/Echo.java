package commands;

import bot.Bot;

public class Echo {
    public static String echo(Bot bot, String command){
        return command.substring(5);
    }
}
