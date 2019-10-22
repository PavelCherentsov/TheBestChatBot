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

public class Hangman implements Serializable {
    private int life;
    private String word;
    private String wordEncrypted;
    private ArrayList<String> words = new ArrayList<String>();
    private ArrayList<Character> usateLettere = new ArrayList<>();

    private void initWords() {
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

    public String startGame(Bot bot, String command) {
        bot.statusActive = Status.GAME;
        initLevels();
        words.clear();
        initWords();
        word = words.get(new Random().nextInt(words.size() - 1));
        wordEncrypted = generateEncryptedWord(word);
        life = 6;
        usateLettere.clear();
        return welcome() + "\n" + levels.get(life) + "\n" + wordEncrypted;
    }

    public String game(Bot bot, String command) {
        if (Pattern.matches(" +", command))
            return help(bot, command);
        if (!checkIsLetter(command))
            return "Введите одну букву русского алфавита";
        String c = command.toLowerCase();
        if (checkLetterWas(command))
            return "Ты уже называл эту букву";
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

    private Boolean checkIsLetter(String command){
        return Pattern.matches("[а-яА-Я]{1}", command);
    }

    private Boolean checkLetterWas(String command) {
        String c = command.toLowerCase();
        for (Character e : usateLettere) {
            if (e.equals(c.charAt(0)))
                return true;
        }
        return false;
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
        levels.add(0, "<pre>\n" +
                "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                " \\|/    |  \n" +
                "  |     |  \n" +
                " / \\    |  \n" +
                "      -----\n" +
                "</pre>");
        levels.add(1, "<pre>\n" +
                "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                " \\|/    |  \n" +
                "  |     |  \n" +
                " /      |  \n" +
                "      -----\n" +
                "</pre>");
        levels.add(2, "<pre>\n" +
                "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                " \\|/    |  \n" +
                "  |     |  \n" +
                "        |  \n" +
                "      -----\n" +
                "</pre>");
        levels.add(3, "<pre>\n" +
                "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                " \\|     |  \n" +
                "  |     |  \n" +
                "        |  \n" +
                "      -----\n" +
                "</pre>");
        levels.add(4, "<pre>\n" +
                "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                "  |     |  \n" +
                "  |     |  \n" +
                "        |  \n" +
                "      -----\n" +
                "</pre>");
        levels.add(5, "<pre>\n" +
                "  _______  \n" +
                "  |    \\|  \n" +
                "  O     |  \n" +
                "        |  \n" +
                "        |  \n" +
                "        |  \n" +
                "      -----\n" +
                "</pre>");
        levels.add(6, "<pre>\n" +
                "  _______  \n" +
                "  |    \\|  \n" +
                "        |  \n" +
                "        |  \n" +
                "        |  \n" +
                "        |  \n" +
                "      -----\n" +
                "</pre>");
    }
}
