import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Function;

import Commands.*;

public class Bot {
    private Status statusActive;

    private HashMap<String, Function<String, String>> dictionary = new HashMap<String, Function<String, String>>();

    public Bot() {
        dictionary.put("/help", Help::help);
        dictionary.put("Авторы", Owners::owners);
        dictionary.put("echo", Echo::echo);
        dictionary.put("quit", Quit::quit);
        dictionary.put("game", Hangman::game);
        dictionary.put("study", Study::study);
        cmdStart();
    }

    public void cmdStart() {
        Scanner in = new Scanner(System.in, "Cp866");
        String command;
        String result = "";
        while (true) {
            String line = in.nextLine();
            try {
                if (line.equals("/start")) {
                    result = "Привет, я бот! Напиши '/help', и я расскажу, что умею :)";
                    statusActive = Status.MENU;
                } else if (statusActive == Status.MENU) {
                    command = line.split(" ")[0];
                    result = dictionary.get(command).apply(line);
                    if (command.equals("game")) {
                        statusActive = Status.GAME;
                    }
                    if (command.equals("quit"))
                        break;
                } else if (statusActive == Status.GAME) {
                    result = Hangman.game(line);
                    if (line.equals("quit") || result.equals("Поздравляю! Ты выиграл! :)")
                            || result.equals("Ты проиграл(")) {
                        statusActive = Status.MENU;
                    }
                } else {
                    result = "Для запуска напишите '/start'";
                }
            } catch (NullPointerException e) {
                result = dictionary.get("/help").apply(line);
            }
            System.out.println(result);
        }
        in.close();
    }
}
