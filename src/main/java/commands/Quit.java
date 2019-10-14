package commands;

import bot.Bot;
import bot.Status;

public class Quit {
    public static String quit(Bot bot, String command){
        bot.statusActive = Status.START;
        return "Пока-пока. Заглядывай ещё :3";
    }
}
