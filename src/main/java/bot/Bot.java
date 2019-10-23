package bot;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

import com.google.inject.internal.cglib.core.$ClassEmitter;
import commands.Echo;
import commands.Hangman;
import commands.Help;
import commands.NotUnderstand;
import commands.Owners;
import commands.Quit;
import commands.Start;
import commands.Study;
import commands.organizer.Organizer;
import java.io.FileWriter;


public class Bot implements Serializable {
    public Status statusActive = Status.START;
    private HashMap<Status, HashMap<String, BiFunction<Bot, String, String>>> dict = new HashMap<>();

    private Hangman game = new Hangman();
    private Organizer organizer = new Organizer();
    private Study study = new Study();

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
        dictionaryMenu.put("выход", Quit::quit);
        dictionaryMenu.put("все", Quit::quit);
        dictionaryMenu.put("всё", Quit::quit);
        dictionaryMenu.put("game", game::startGame);
        dictionaryMenu.put("игра", game::startGame);
        dictionaryMenu.put("виселица", game::startGame);
        dictionaryMenu.put("default", NotUnderstand::notUnderstand);
        dictionaryMenu.put("study", study::mainMenu);
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
        dictStudy.put("classes", study::startClasses);
        dictStudy.put("пары", study::startClasses);
        dictStudy.put("help", study::studyHelp);
        dictStudy.put("default", study::def);
        dictStudy.put("quit", study::quitToMenu);
        dictStudy.put("выход", study::quitToMenu);
        dictStudy.put("все", study::quitToMenu);
        dictStudy.put("всё", study::quitToMenu);

        dict.put(Status.STUDY, dictStudy);

        HashMap<String, BiFunction<Bot, String, String>> dictClasses = new HashMap<>();
        dictClasses.put("default", study::getClasses);
        dictClasses.put("quit", study::mainMenu);
        dictClasses.put("выход", study::mainMenu);
        dictClasses.put("все", study::mainMenu);
        dictClasses.put("всё", study::mainMenu);
        dictClasses.put("help", study::classesHelp);

        dict.put(Status.CLASSES, dictClasses);

        HashMap<String, BiFunction<Bot, String, String>> dictOrganizer = new HashMap<>();
        dictOrganizer.put("default", organizer::all);
        dictOrganizer.put("add", organizer::add);
        dictOrganizer.put("all", organizer::all);
        dictOrganizer.put("completed", organizer::completed);
        dictOrganizer.put("quit", organizer::quit);
        dictOrganizer.put("выход", organizer::quit);
        dictOrganizer.put("все", organizer::quit);
        dictOrganizer.put("всё", organizer::quit);
        dictOrganizer.put("edit", organizer::start_edit);

        dict.put(Status.ORGANIZER, dictOrganizer);

        HashMap<String, BiFunction<Bot, String, String>> dictOrganizerPush = new HashMap<>();
        dictOrganizerPush.put("default", organizer::push);
        dictOrganizerPush.put("quit", organizer::back);
        dictOrganizerPush.put("выход", organizer::back);
        dictOrganizerPush.put("все", organizer::back);
        dictOrganizerPush.put("всё", organizer::back);

        dict.put(Status.ORGANIZER_ADD, dictOrganizerPush);

        HashMap<String, BiFunction<Bot, String, String>> dictEdit = new HashMap<>();
        dictEdit.put("default", organizer::edit);
        dictEdit.put("back", organizer::back);
        dictEdit.put("date", organizer::edit_questions);
        dictEdit.put("task", organizer::edit_questions);
        dictEdit.put("all", organizer::edit_questions);

        dict.put(Status.ORGANIZER_EDIT, dictEdit);
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
            if (result.contains("<pre>" )){
                result = result.substring(0, result.indexOf("<pre>")) + result.substring(result.indexOf("<pre>") + 5);
            }
            if (result.contains("</pre>")){
                result = result.substring(0, result.indexOf("</pre>")) + result.substring(result.indexOf("</pre>") + 6);
            }
            System.out.println(result);
        }
    }
}
