package bot;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

import com.google.inject.internal.cglib.core.$ClassEmitter;
import com.vdurmont.emoji.EmojiParser;
import commands.Echo;
import commands.Hangman;
import commands.Help;
import commands.NotUnderstand;
import commands.Owners;
import commands.Quit;
import commands.Start;
import commands.Study;
import commands.organizer.Flag;
import commands.organizer.Organizer;
import java.io.FileWriter;


public class Bot implements Serializable {
    public Status statusActive = Status.START;
    private HashMap<Status, HashMap<String, BiFunction<Bot, String, String>>> dict = new HashMap<>();

    private Hangman game = new Hangman();
    private Organizer organizer = new Organizer();
    private Study study = new Study();

    private String smile_emoji = EmojiParser.parseToUnicode("U+1F602");

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
        dictionaryMenu.put("органайзер", organizer::start);

        dict.put(Status.MENU, dictionaryMenu);

        HashMap<String, BiFunction<Bot, String, String>> dictionaryGame = new HashMap<>();
        dictionaryGame.put("help", game::help);
        dictionaryGame.put("хелп", game::help);
        dictionaryGame.put("помощь", game::help);
        dictionaryGame.put("quit", game::quit);
        dictionaryGame.put("выход", game::quit);
        dictionaryGame.put("default", game::game);

        dict.put(Status.GAME, dictionaryGame);

        HashMap<String, BiFunction<Bot, String, String>> dictStudy = new HashMap<>();
        dictStudy.put("classes", study::startClasses);
        dictStudy.put("пары", study::startClasses);
        dictStudy.put("help", study::studyHelp);
        dictStudy.put("default", study::def);
        dictStudy.put("quit", study::quitToMenu);
        dictStudy.put("выход", study::quitToMenu);

        dict.put(Status.STUDY, dictStudy);

        HashMap<String, BiFunction<Bot, String, String>> dictClasses = new HashMap<>();
        dictClasses.put("default", study::getClasses);
        dictClasses.put("quit", study::mainMenu);
        dictClasses.put("выход", study::mainMenu);
        dictClasses.put("help", study::classesHelp);
        dictClasses.put("помощь", study::classesHelp);
        dictClasses.put("хелп", study::classesHelp);

        dict.put(Status.CLASSES, dictClasses);

        HashMap<String, BiFunction<Bot, String, String>> dictOrganizer = new HashMap<>();
        dictOrganizer.put("default", organizer::showDefault);
        dictOrganizer.put("add", organizer::add);
        dictOrganizer.put("адд", organizer::add);
        dictOrganizer.put("добавить", organizer::add);
        dictOrganizer.put("all", organizer::all);
        dictOrganizer.put("все", organizer::all);
        dictOrganizer.put("список", organizer::all);
        dictOrganizer.put("completed", organizer::completed);
        dictOrganizer.put("выполнено", organizer::completed);
        dictOrganizer.put("quit", organizer::quit);
        dictOrganizer.put("выход", organizer::quit);
        dictOrganizer.put("edit", organizer::start_edit);
        dictOrganizer.put("изменить", organizer::start_edit);
        dictOrganizer.put("show", organizer::show);
        dictOrganizer.put("покажи", organizer::show);
        dictOrganizer.put("показать", organizer::show);
        dictOrganizer.put("help", organizer::help);
        dictOrganizer.put("помощь", organizer::help);
        dictOrganizer.put("хелп", organizer::help);

        dict.put(Status.ORGANIZER, dictOrganizer);

        HashMap<String, BiFunction<Bot, String, String>> dictOrganizerPush = new HashMap<>();
        dictOrganizerPush.put("default", organizer::push);
        dictOrganizerPush.put("quit", organizer::quit);
        dictOrganizerPush.put("выход", organizer::quit);
        dictOrganizerPush.put("назад", organizer::back);
        dictOrganizerPush.put("back", organizer::back);
        dictOrganizerPush.put("help", organizer::addHelp);
        dictOrganizerPush.put("хелп", organizer::addHelp);
        dictOrganizerPush.put("помощь", organizer::addHelp);

        dict.put(Status.ORGANIZER_ADD, dictOrganizerPush);

        HashMap<String, BiFunction<Bot, String, String>> dictEdit = new HashMap<>();
        dictEdit.put("default", organizer::edit);
        dictEdit.put("back", organizer::back);
        dictEdit.put("date", organizer::edit_questions);
        dictEdit.put("task", organizer::edit_questions);
        dictEdit.put("all", organizer::edit_questions);
        dictEdit.put("назад", organizer::back);
        dictEdit.put("quit", organizer::quit);
        dictEdit.put("выход", organizer::quit);

        dict.put(Status.ORGANIZER_EDIT, dictEdit);

        HashMap<String, BiFunction<Bot, String, String>> dictShow = new HashMap<>();
        dictShow.put("default", organizer::showParse);
        dictShow.put("back", organizer::back);
        dictShow.put("назад", organizer::back);
        dictShow.put("quit", organizer::quit);
        dictShow.put("выход", organizer::quit);

        dict.put(Status.ORGANIZER_SHOW, dictShow);
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
            result = processing(result);
            System.out.println(result);
        }
    }

    private String processing(String line){
        line = line.replaceAll("<pre>", "");
        line = line.replaceAll("</pre>", "");
        for (Flag e: Flag.values()) {
            line = line.replaceAll(e.getEmoji(), e.getName());
        }
        return line;
    }
}
