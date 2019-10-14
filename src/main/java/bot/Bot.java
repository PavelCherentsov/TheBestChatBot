package bot;

import java.util.HashMap;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

import commands.Echo;
import commands.Hangman;
import commands.Help;
import commands.NotUnderstand;
import commands.Owners;
import commands.Quit;
import commands.Start;
import commands.Study;
import commands.organizer.Organizer;


public class Bot {
    public Status statusActive = Status.START;
    private HashMap<Status, HashMap<String, BiFunction<Bot, String, String>>> dict = new HashMap<>();

    private Hangman game = new Hangman();
    private Organizer organizer = new Organizer();

    public Bot() {
        initDict();
    }

    private void initDict() {
        HashMap<String, BiFunction<Bot, String, String>> dictionaryStart = new HashMap<>();

        dictionaryStart.put("help", Start::help);
        dictionaryStart.put("default", Start::start);

        dict.put(Status.START, dictionaryStart);

        HashMap<String, BiFunction<Bot, String, String>> dictionaryMenu = new HashMap<>();

        dictionaryMenu.put("help", Help::help);
        dictionaryMenu.put("хелп", Help::help);
        dictionaryMenu.put("помощь", Help::help);
        dictionaryMenu.put("авторы", Owners::owners);
        dictionaryMenu.put("echo", Echo::echo);
        dictionaryMenu.put("эхо", Echo::echo);
        dictionaryMenu.put("quit", Quit::quit);
        dictionaryMenu.put("game", game::startGame);
        dictionaryMenu.put("игра", game::startGame);
        dictionaryMenu.put("виселица", game::startGame);
        dictionaryMenu.put("default", NotUnderstand::notUnderstand);
        dictionaryMenu.put("study", Study::mainMenu);
        dictionaryMenu.put("organizer", organizer::start);

        dict.put(Status.MENU, dictionaryMenu);

        HashMap<String, BiFunction<Bot, String, String>> dictionaryGame = new HashMap<>();
        dictionaryGame.put("help", game::help);
        dictionaryGame.put("хелп", game::help);
        dictionaryGame.put("помощь", game::help);
        dictionaryGame.put("quit", game::quit);
        dictionaryGame.put("выход", game::quit);
        dictionaryGame.put("все", game::quit);
        dictionaryGame.put("всё", game::quit);
        dictionaryGame.put("default", game::game);

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

        HashMap<String, BiFunction<Bot, String, String>> dictOrganizer = new HashMap<>();
        dictOrganizer.put("default", organizer::all);
        dictOrganizer.put("add", organizer::add);
        dictOrganizer.put("all", organizer::all);
        dictOrganizer.put("quit", organizer::quit);

        dict.put(Status.ORGANIZER, dictOrganizer);
    }

    public String getAnswer(String line) {
        String command = line;
        if (!Pattern.matches(" +", line))
            command = line.split(" ")[0].toLowerCase();
        return dict.get(statusActive)
                .getOrDefault(command, dict.get(statusActive).get("default"))
                .apply(this, line);
    }

    public void run() {
        Scanner in = new Scanner(System.in, "Cp866");
        while (true) {
            String line = in.nextLine();
            String result = getAnswer(line);
            System.out.println(result);
        }
    }
}
