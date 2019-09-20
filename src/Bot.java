import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import Commands.*;

public class Bot {
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

    public static Consumer<String> print = System.out::println;

    public void cmdStart(){
        print.accept("Привет, я бот! Напиши 'help', и я расскажу, что умею :)");
        Scanner in = new Scanner(System.in);
        while (true){
            var command = in.nextLine();
            try {
                print.accept(dictionary.get(command.split(" ")[0]).apply(command));
            }catch (NullPointerException e) {
                print.accept(dictionary.get("help").apply(command));
            }
            if (command.split(" ")[0].equals("quit"))
                break;
        }
        in.close();
    }
}
