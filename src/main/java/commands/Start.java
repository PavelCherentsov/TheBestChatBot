package commands;

import bot.Bot;
import bot.Status;

public class Start {
    public static String start(Bot bot, String command) {
        return "Привет, я бот! Напиши 'help', и я расскажу, что умею :)";
    }

    public static String help(Bot bot, String command) {
        bot.statusActive = Status.MENU;
        return Help.help(bot, command);
    }
}
