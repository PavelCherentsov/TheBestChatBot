package commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Function;

import bot.Status;
import bot.Bot;

public class Hangman {
    private static int life;
    private static String word;
    private static String wordEncrypted;
    private static ArrayList<String> words = new ArrayList<String>();
    private static HashMap<String, Function<String, String>> dictionary = new HashMap<String, Function<String, String>>();

    private static void initWords() {
        words.add("коллаборация");
        words.add("когнитивный");
        words.add("трансцендентный");
        words.add("априори");
        words.add("конгруэнтность");

    }

    public static String startGame(Bot bot, String command) {
        bot.statusActive = Status.GAME;
        initLevels();
        initWords();
        word = words.get(new Random().nextInt(words.size() - 1));
        wordEncrypted = generateEncryptedWord(word);
        life = 6;
        return welcome() + "\n" + levels.get(life) + "\n" + wordEncrypted;
    }

    public static String game(Bot bot, String command) {
        String c = command.toLowerCase();
        openLetters(c);
        if (!wordEncrypted.contains("_"))
            return "Поздравляю! Ты выиграл! :)";
        if (life == 0) {
            return "Ты проиграл(";
        }
        return levels.get(life) + "\n" + wordEncrypted;
    }

    public static String help(Bot bot, String command) {
        return "Правила очень просты: я загадываю слово, а твоя задача не дать человечку свести \n" +
                "счеты с жизнью... ой, то есть тебе нужно по буквам слово угадать. У тебя есть \n" +
                "6 попыток ошибиться. Если тебе нужна помощь, введи 'help'." +
                " Для выхода из игры: введи 'quit'" +
                "\n" + levels.get(life) + "\n" + wordEncrypted;
    }

    public static String quit(Bot bot, String command) {
        bot.statusActive = Status.MENU;
        return levels.get(life) + "\n" + "Правильный ответ: " + word + "\n" + "Ты проиграл(";
    }

    private static String welcome() {
        return "Приветствую тебя в сногсшибательной, в прямом смысле этого слова, игре 'Виселица'.\n" +
                "Правила очень просты: я загадываю слово, а твоя задача не дать человечку свести \n" +
                "счеты с жизнью... ой, то есть тебе нужно по буквам слово угадать. У тебя есть \n" +
                "6 попыток ошибиться. Если тебе нужна помощь, введи 'help'. Удачи!";
    }

    private static String generateEncryptedWord(String word) {
        String result = "_";
        for (int i = 0; i < word.length() - 1; i++) {
            result += " _";
        }
        return result;
    }

    private static void openLetters(String c) {
        boolean result = false;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == c.charAt(0)) {
                wordEncrypted = wordEncrypted.substring(0, 2 * i) + c.charAt(0) + wordEncrypted.substring(2 * i + 1);
                result = true;
            }
        }
        if (!result)
            life--;
    }

    private static ArrayList<String> levels = new ArrayList<String>();

    private static void initLevels() {
        levels.add(0, "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                " \\|/    |  \n" +
                "  |     |  \n" +
                " /\\     |  \n" +
                "      -----");
        levels.add(1, "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                " \\|/    |  \n" +
                "  |     |  \n" +
                " /      |  \n" +
                "      -----");
        levels.add(2, "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                " \\|/    |  \n" +
                "  |     |  \n" +
                "        |  \n" +
                "      -----");
        levels.add(3, "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                " \\|     |  \n" +
                "  |     |  \n" +
                "        |  \n" +
                "      -----");
        levels.add(4, "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                "  |     |  \n" +
                "  |     |  \n" +
                "        |  \n" +
                "      -----");
        levels.add(5, "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                "        |  \n" +
                "        |  \n" +
                "        |  \n" +
                "      -----");
        levels.add(6, "  _______  \n" +
                "  |    \\|  \n" +
                "        |  \n" +
                "        |  \n" +
                "        |  \n" +
                "        |  \n" +
                "      -----");
    }
}
