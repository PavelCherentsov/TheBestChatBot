package Commands;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Consumer;


public class Hangman {
    public static Consumer<String> print = System.out::println;

    public static String game(String command) {
        print.accept("Приветствую тебя в сногсшибательной, в прямом смысле этого слова, игре 'Виселица'.\n" +
                        "Правила очень просты: я загадываю слово, а твоя задача не дать человечку свести \n" +
                        "счеты с жизнью... ой, то есть тебе нужно по буквам слово угадать. У тебя есть \n" +
                        "6 попыток ошибиться. Если тебе нужна помощь, введи 'help'. Удачи!");
        initLevels();
        initWords();
        var word = words.get(new Random().nextInt(words.size()-1));
        var _word = "_";
        for (int i =0; i< word.length()-1; i++){
            _word += " _";
        }
        Scanner in = new Scanner(System.in);
        var life = 6;
        while (life != 0){
            print.accept(levels.get(life));
            print.accept(_word);
            var c = in.nextLine().toLowerCase();
            if (c.equals("help")){
                print.accept("Правила очень просты: я загадываю слово, а твоя задача не дать человечку свести \n" +
                        "счеты с жизнью... ой, то есть тебе нужно по буквам слово угадать. У тебя есть \n" +
                        "6 попыток ошибиться. Если тебе нужна помощь, введи 'help'." +
                        " Для выхода из игры: введи 'quit'");
            }else if (c.equals("quit")){
                break;
            } else {
                var yes = false;
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == c.charAt(0)) {
                        _word = _word.substring(0, 2 * i) + c.charAt(0) + _word.substring(2 * i + 1);
                        yes = true;
                    }
                }
                if (!yes) {
                    life--;
                }
                if (!_word.contains("_")) {
                    print.accept(_word);
                    return "Поздравляю! Ты выиграл! :)";
                }
            }

        }
        if (_word.contains("_")){
            print.accept(levels.get(life));
            print.accept("Правильный ответ: " + word);
            return "Ты проиграл(";
        }

        return "Поздравляю! Ты выиграл! :)";

    }

    static ArrayList<String> words = new ArrayList<String>();

    private static void initWords(){
        words.add("коллаборация");
        words.add("когнитивный");
        words.add("трансцендентный");
        words.add("априори");
        words.add("конгруэнтность");

    }

    static ArrayList<String> levels = new ArrayList<String>();

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
}
