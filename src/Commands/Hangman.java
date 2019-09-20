package Commands;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Consumer;


public class Hangman {
    private static Consumer<String> print = System.out::println;

    private static int life;
    private static String word;
    private static String wordEncrypted;
    private static ArrayList<String> words = new ArrayList<String>();

    public static String game(String command) {
        Welcome();
        initLevels();
        initWords();
        word = words.get(new Random().nextInt(words.size()-1));
        wordEncrypted = GenerateEncryptedWord(word);
        Scanner in = new Scanner(System.in);
        life = 6;
        while (life != 0){
            print.accept(levels.get(life));
            print.accept(wordEncrypted);
            var c = in.nextLine().toLowerCase();
            if (c.equals("help")){
                Help();
            }else if (c.equals("quit")){
                break;
            } else {
                var guessed = OpenLetters(c);
                if (!guessed)
                    life--;
                if (!wordEncrypted.contains("_"))
                    break;
            }

        }
        if (wordEncrypted.contains("_")){
            print.accept(levels.get(life));
            print.accept("Правильный ответ: " + word);
            return "Ты проиграл(";
        }

        return "Поздравляю! Ты выиграл! :)";

    }

    private static String GenerateEncryptedWord(String word){
        var result = "_";
        for (int i =0; i< word.length()-1; i++){
            result += " _";
        }
        return result;
    }

    private static boolean OpenLetters(String c){
        var result = false;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == c.charAt(0)) {
                wordEncrypted = wordEncrypted.substring(0, 2 * i) + c.charAt(0) + wordEncrypted.substring(2 * i + 1);
                result = true;
            }
        }
        return result;
    }

    private static void initWords(){
        words.add("коллаборация");
        words.add("когнитивный");
        words.add("трансцендентный");
        words.add("априори");
        words.add("конгруэнтность");

    }

    private static ArrayList<String> levels = new ArrayList<String>();

    private static void initLevels(){
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

    private static void Welcome(){
        print.accept("Приветствую тебя в сногсшибательной, в прямом смысле этого слова, игре 'Виселица'.\n" +
                "Правила очень просты: я загадываю слово, а твоя задача не дать человечку свести \n" +
                "счеты с жизнью... ой, то есть тебе нужно по буквам слово угадать. У тебя есть \n" +
                "6 попыток ошибиться. Если тебе нужна помощь, введи 'help'. Удачи!");
    }

    private static void Help(){
        print.accept("Правила очень просты: я загадываю слово, а твоя задача не дать человечку свести \n" +
                "счеты с жизнью... ой, то есть тебе нужно по буквам слово угадать. У тебя есть \n" +
                "6 попыток ошибиться. Если тебе нужна помощь, введи 'help'." +
                " Для выхода из игры: введи 'quit'");
    }
}
