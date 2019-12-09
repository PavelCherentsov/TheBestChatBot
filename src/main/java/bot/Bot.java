package bot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
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
import commands.organizer.Flag;
import commands.organizer.Organizer;
import commands.organizer.OrganizerElement;


public class Bot {
    public Status statusActive = Status.START;
    private transient HashMap<Status, HashMap<String, BiFunction<Bot, String, String>>> dict = new HashMap<>();
    public int life;
    public String wordEncrypted;
    public String word;
    public ArrayList<Character> usateLettere;

    public CopyOnWriteArrayList<OrganizerElement> organizer = new CopyOnWriteArrayList<>();
    public ConcurrentHashMap<String, HashMap<Integer, Boolean>> deadlines = new ConcurrentHashMap<>();
    public OrganizerElement currentTask;
    public String editType = "";
    public int n;

    private Study study;

    public Boolean test = false;
    public String time;
    public Integer count = 0;

    public Bot() {
        study = new Study();
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
        dictionaryMenu.put("game", Hangman::startGame);
        dictionaryMenu.put("игра", Hangman::startGame);
        dictionaryMenu.put("виселица", Hangman::startGame);
        dictionaryMenu.put("default", NotUnderstand::notUnderstand);
        dictionaryMenu.put("study", study::mainMenu);
        dictionaryMenu.put("organizer", Organizer::start);
        dictionaryMenu.put("органайзер", Organizer::start);

        dict.put(Status.MENU, dictionaryMenu);

        HashMap<String, BiFunction<Bot, String, String>> dictionaryGame = new HashMap<>();
        dictionaryGame.put("help", Hangman::help);
        dictionaryGame.put("хелп", Hangman::help);
        dictionaryGame.put("помощь", Hangman::help);
        dictionaryGame.put("quit", Hangman::quit);
        dictionaryGame.put("выход", Hangman::quit);
        dictionaryGame.put("default", Hangman::game);

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
        dictOrganizer.put("default", Organizer::showDefault);
        dictOrganizer.put("add", Organizer::add);
        dictOrganizer.put("адд", Organizer::add);
        dictOrganizer.put("добавить", Organizer::add);
        dictOrganizer.put("all", Organizer::all);
        dictOrganizer.put("все", Organizer::all);
        dictOrganizer.put("список", Organizer::all);
        dictOrganizer.put("completed", Organizer::completed);
        dictOrganizer.put("выполнено", Organizer::completed);
        dictOrganizer.put("quit", Organizer::quit);
        dictOrganizer.put("выход", Organizer::quit);
        dictOrganizer.put("edit", Organizer::startEdit);
        dictOrganizer.put("изменить", Organizer::startEdit);
        dictOrganizer.put("show", Organizer::show);
        dictOrganizer.put("покажи", Organizer::show);
        dictOrganizer.put("показать", Organizer::show);
        dictOrganizer.put("help", Organizer::help);
        dictOrganizer.put("помощь", Organizer::help);
        dictOrganizer.put("хелп", Organizer::help);
        dictOrganizer.put("delete", Organizer::delete);
        dictOrganizer.put("удалить", Organizer::delete);
        dictOrganizer.put("change", Organizer::changeTime);

        dict.put(Status.ORGANIZER, dictOrganizer);

        HashMap<String, BiFunction<Bot, String, String>> dictOrganizerPush = new HashMap<>();
        dictOrganizerPush.put("default", Organizer::push);
        dictOrganizerPush.put("quit", Organizer::quit);
        dictOrganizerPush.put("выход", Organizer::quit);
        dictOrganizerPush.put("назад", Organizer::back);
        dictOrganizerPush.put("back", Organizer::back);
        dictOrganizerPush.put("help", Organizer::addHelp);
        dictOrganizerPush.put("хелп", Organizer::addHelp);
        dictOrganizerPush.put("помощь", Organizer::addHelp);

        dict.put(Status.ORGANIZER_ADD, dictOrganizerPush);

        HashMap<String, BiFunction<Bot, String, String>> dictEdit = new HashMap<>();
        dictEdit.put("default", Organizer::edit);
        dictEdit.put("back", Organizer::back);
        dictEdit.put("date", Organizer::editQuestions);
        dictEdit.put("task", Organizer::editQuestions);
        dictEdit.put("all", Organizer::editQuestions);
        dictEdit.put("назад", Organizer::back);
        dictEdit.put("quit", Organizer::quit);
        dictEdit.put("выход", Organizer::quit);

        dict.put(Status.ORGANIZER_EDIT, dictEdit);

        HashMap<String, BiFunction<Bot, String, String>> dictShow = new HashMap<>();
        dictShow.put("default", Organizer::showParse);
        dictShow.put("back", Organizer::back);
        dictShow.put("назад", Organizer::back);
        dictShow.put("quit", Organizer::quit);
        dictShow.put("выход", Organizer::quit);

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

    private String processing(String line) {
        line = line.replaceAll("<pre>", "");
        line = line.replaceAll("</pre>", "");
        for (Flag e : Flag.values()) {
            line = line.replaceAll(e.getEmoji(), e.getName());
        }
        return line;
    }
}
