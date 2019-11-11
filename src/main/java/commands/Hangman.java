package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

import bot.Status;
import bot.Bot;

public class Hangman {

    public static transient ArrayList<String> words = new ArrayList<String>(){{
        add("коллаборация");
        add("когнитивный");
        add("трансцендентный");
        add("априори");
        add("конгруэнтность");
        add("уравновеситься");
        add("трензельный");
        add("автотележка");
        add("выпахивание");
        add("серпоклюв");
        add("возгреметь");
        add("солестойкость");
        add("слабоуздый");
    }};

    private static void initWords() {
        words.add("коллаборация");
        words.add("когнитивный");
        words.add("трансцендентный");
        words.add("априори");
        words.add("конгруэнтность");
        words.add("уравновеситься");
        words.add("трензельный");
        words.add("автотележка");
        words.add("выпахивание");
        words.add("серпоклюв");
        words.add("возгреметь");
        words.add("солестойкость");
        words.add("слабоуздый");
    }

    public static String startGame(Bot bot, String command) {
        bot.statusActive = Status.GAME;
        words.clear();
        initWords();
        bot.word = words.get(new Random().nextInt(words.size() - 1));
        bot.wordEncrypted = generateEncryptedWord(bot.word);
        bot.life = 6;
        bot.usateLettere = new ArrayList<Character>();
        return welcome() + "\n" + levels.get(bot.life) + "\n" + bot.wordEncrypted;
    }

    public static String game(Bot bot, String command) {
        if (Pattern.matches(" +", command))
            return help(bot, command);
        if (!checkIsLetter(command))
            return "Введите одну букву русского алфавита";
        String c = command.toLowerCase();
        if (checkLetterWas(command, bot))
            return "Ты уже называл эту букву";
        bot.usateLettere.add(c.charAt(0));
        openLetters(c, bot);
        if (!bot.wordEncrypted.contains("_")) {
            bot.statusActive = Status.MENU;
            return "Поздравляю! Ты выиграл! :)";
        }
        if (bot.life == 0) {
            bot.statusActive = Status.MENU;
            return levels.get(bot.life) + "\nТы проиграл(\nЯ загадал: " + bot.word;
        }
        return levels.get(bot.life) + "\n" + bot.wordEncrypted;

    }



    public static String help(Bot bot, String command) {
        return "Правила очень просты: я загадываю слово, а твоя задача не дать человечку свести \n" +
                "счеты с жизнью... ой, то есть тебе нужно по буквам слово угадать. У тебя есть \n" +
                "6 попыток ошибиться. Если тебе нужна помощь, введи 'help'." +
                " Для выхода из игры: введи 'quit'" +
                "\n" + levels.get(bot.life) + "\n" + bot.wordEncrypted;
    }

    public static String quit(Bot bot, String command) {
        bot.statusActive = Status.MENU;
        return levels.get(bot.life) + "\n" + "Правильный ответ: " + bot.word + "\n" + "Ты проиграл(";
    }

    private static String welcome() {
        return "Приветствую тебя в сногсшибательной, в прямом смысле этого слова, игре 'Виселица'.\n" +
                "Правила очень просты: я загадываю слово, а твоя задача не дать человечку свести \n" +
                "счеты с жизнью... ой, то есть тебе нужно по буквам слово угадать. У тебя есть \n" +
                "6 попыток ошибиться. Если тебе нужна помощь, введи 'help'. Удачи!";
    }

    private static Boolean checkIsLetter(String command){
        return Pattern.matches("[а-яА-Я]{1}", command);
    }

    private static Boolean checkLetterWas(String command, Bot bot) {
        String c = command.toLowerCase();
        for (Character e : bot.usateLettere) {
            if (e.equals(c.charAt(0)))
                return true;
        }
        return false;
    }

    private static String generateEncryptedWord(String word) {
        String result = "_";
        for (int i = 0; i < word.length() - 1; i++) {
            result += " _";
        }
        return result;
    }

    private static void openLetters(String c, Bot bot) {
        boolean result = false;
        for (int i = 0; i < bot.word.length(); i++) {
            if (bot.word.charAt(i) == c.charAt(0)) {
                bot.wordEncrypted = bot.wordEncrypted.substring(0, 2 * i) + c.charAt(0) + bot.wordEncrypted.substring(2 * i + 1);
                result = true;
            }
        }
        if (!result)
            bot.life--;
    }

    private static transient ArrayList<String> levels = new ArrayList<String>() {{
        add(0, "<pre>\n" +
                "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                " \\|/    |  \n" +
                "  |     |  \n" +
                " / \\    |  \n" +
                "      -----\n" +
                "</pre>");
        add(1, "<pre>\n" +
                "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                " \\|/    |  \n" +
                "  |     |  \n" +
                " /      |  \n" +
                "      -----\n" +
                "</pre>");
        add(2, "<pre>\n" +
                "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                " \\|/    |  \n" +
                "  |     |  \n" +
                "        |  \n" +
                "      -----\n" +
                "</pre>");
        add(3, "<pre>\n" +
                "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                " \\|     |  \n" +
                "  |     |  \n" +
                "        |  \n" +
                "      -----\n" +
                "</pre>");
        add(4, "<pre>\n" +
                "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                "  |     |  \n" +
                "  |     |  \n" +
                "        |  \n" +
                "      -----\n" +
                "</pre>");
        add(5, "<pre>\n" +
                "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                "        |  \n" +
                "        |  \n" +
                "        |  \n" +
                "      -----\n" +
                "</pre>");
        add(6, "<pre>\n" +
                "  _______  \n" +
                "  |    \\|  \n" +
                "        |  \n" +
                "        |  \n" +
                "        |  \n" +
                "        |  \n" +
                "      -----\n" +
                "</pre>");
    }};

}
