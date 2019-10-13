package commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import bot.Status;
import bot.Bot;

public class Hangman {
    private int life;
    private String word;
    private String wordEncrypted;
    private ArrayList<String> words = new ArrayList<String>();
    private HashMap<String, Function<String, String>> dictionary = new HashMap<String, Function<String, String>>();
    private ArrayList<Character> usateLettere = new ArrayList<>();

    private void initWords() {
        words.add("коллаборация");
        words.add("когнитивный");
        words.add("трансцендентный");
        words.add("априори");
        words.add("конгруэнтность");
        words.add("уравновеситься");
        words.add("трензельный");
        words.add("ихневмон");
        words.add("автотележка");
        words.add("выпахивание");
        words.add("серпоклюв");
        words.add("орясина");
        words.add("возгреметь");
        words.add("солестойкость");
        words.add("слабоуздый");

    }

    public String startGame(Bot bot, String command) {
        bot.statusActive = Status.GAME;
        initLevels();
        initWords();
        word = words.get(new Random().nextInt(words.size() - 1));
        wordEncrypted = generateEncryptedWord(word);
        life = 6;
        return welcome() + "\n" + levels.get(life) + "\n" + wordEncrypted;
    }

    public String game(Bot bot, String command) {
        if (command.equals(""))
            return help(bot, "");
        String c = command.toLowerCase();
        for (Character e : usateLettere) {
            if (e.equals(c.charAt(0)))
                return "Ты уже называл эту букву";
        }
        usateLettere.add(c.charAt(0));
        openLetters(c);
        if (!wordEncrypted.contains("_")) {
            bot.statusActive = Status.MENU;
            return "Поздравляю! Ты выиграл! :)";
        }
        if (life == 0) {
            bot.statusActive = Status.MENU;
            return levels.get(life) + "\nТы проиграл(\nЯ загадал: " + word;
        }
        return levels.get(life) + "\n" + wordEncrypted;

    }

    public String help(Bot bot, String command) {
        return "Правила очень просты: я загадываю слово, а твоя задача не дать человечку свести \n" +
                "счеты с жизнью... ой, то есть тебе нужно по буквам слово угадать. У тебя есть \n" +
                "6 попыток ошибиться. Если тебе нужна помощь, введи 'help'." +
                " Для выхода из игры: введи 'quit'" +
                "\n" + levels.get(life) + "\n" + wordEncrypted;
    }

    public String quit(Bot bot, String command) {
        bot.statusActive = Status.MENU;
        return levels.get(life) + "\n" + "Правильный ответ: " + word + "\n" + "Ты проиграл(";
    }

    private String welcome() {
        return "Приветствую тебя в сногсшибательной, в прямом смысле этого слова, игре 'Виселица'.\n" +
                "Правила очень просты: я загадываю слово, а твоя задача не дать человечку свести \n" +
                "счеты с жизнью... ой, то есть тебе нужно по буквам слово угадать. У тебя есть \n" +
                "6 попыток ошибиться. Если тебе нужна помощь, введи 'help'. Удачи!";
    }

    private String generateEncryptedWord(String word) {
        String result = "_";
        for (int i = 0; i < word.length() - 1; i++) {
            result += " _";
        }
        return result;
    }

    private void openLetters(String c) {
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

    private ArrayList<String> levels = new ArrayList<String>();

    private void initLevels() {
        levels.add(0, "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                " \\|/    |  \n" +
                "  |     |  \n" +
                " / \\    |  \n" +
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
