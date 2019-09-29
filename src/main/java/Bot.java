import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Function;
import Commands.*;

public class Bot {
    private Status statusActive = Status.MENU;

    private HashMap<String, Function<String, String>> dictionary = new HashMap<String,Function<String, String>>();

    public Bot(){
        dictionary.put("help", Help::help);
        dictionary.put("Авторы", Owners::owners);
        dictionary.put("echo", Echo::echo);
        dictionary.put("quit", Quit::quit);
        dictionary.put("game", Hangman::game);
        dictionary.put("study", Study::study);
        cmdStart();
    }

    public void cmdStart(){
        System.out.println("Привет, я бот! Напиши 'help', и я расскажу, что умею :)");
        Scanner in = new Scanner(System.in);
        String command;
        String result = "";
        while (true){
            String line = in.nextLine();
            try {
                if (statusActive == Status.MENU){
                    command = line.split(" ")[0];
                    result = dictionary.get(command).apply(line);
                }
                else if (statusActive == Status.GAME){
                    result = Hangman.game(line);
                }

            }catch (NullPointerException e) {
                result = dictionary.get("help").apply(line);
            }
            if (line.split(" ")[0].equals("quit"))
                break;
            System.out.println(result);
        }
        in.close();
    }
}
