package bot;

import java.util.HashMap;
import java.util.Scanner;
import java.util.function.BiFunction;

import commands.*;


public class Bot {
    public Status statusActive = Status.MENU;

    public Bot() {
        run();
    }

    private HashMap<Status, HashMap<String, BiFunction<Bot, String, String>>> dict = new HashMap<>();

    public void initDict() {
        HashMap<String, BiFunction<Bot, String, String>> dictionaryMenu = new HashMap<>();

        dictionaryMenu.put("help", Help::help);
        dictionaryMenu.put("авторы", Owners::owners);
        dictionaryMenu.put("echo", Echo::echo);
        dictionaryMenu.put("quit", Quit::quit);
        dictionaryMenu.put("game", Hangman::startGame);
        dictionaryMenu.put("default", Help::help);
        dictionaryMenu.put("study", Study::mainMenu);

        dict.put(Status.MENU, dictionaryMenu);

        HashMap<String, BiFunction<Bot, String, String>> dictionaryGame = new HashMap<>();
        dictionaryGame.put("help", Hangman::help);
        dictionaryGame.put("quit", Hangman::quit);
        dictionaryGame.put("default", Hangman::game);

        dict.put(Status.GAME, dictionaryGame);

        HashMap<String, BiFunction<Bot, String, String>> dictStudy = new HashMap<>();
        dictStudy.put("classes", Study::startClasses);
        dictStudy.put("help", Study::studyHelp);
        dictStudy.put("default", Study::def);
        dictStudy.put("quit", Study::quitToMenu);

        dict.put(Status.STUDY, dictStudy);

        HashMap<String, BiFunction<Bot, String, String>> dictClasses = new HashMap<>();
        dictClasses.put("default", Study::getClasses);
        dictClasses.put("quit", Study::mainMenu);
        dictClasses.put("help", Study::classesHelp);

        dict.put(Status.CLASSES, dictClasses);
    }

    public void run() {
        System.out.println("Привет, я бот! Напиши '/help', и я расскажу, что умею :)");
        initDict();
        Scanner in = new Scanner(System.in, "Cp866");
        while (true) {
            String input = in.nextLine();
            String command = input.split(" ")[0].toLowerCase();
            String result = dict.get(statusActive)
                            .getOrDefault(command, dict.get(statusActive).get("default"))
                            .apply(this, input);
            System.out.println(result);
        }
    }
}
