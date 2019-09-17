import java.awt.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

public class Bot {
    private HashMap<String, Function<String, String>> dictionary = new HashMap<String,Function<String, String>>();
    public Bot(){
        dictionary.put("help", Commands::help);
        dictionary.put("Авторы", Commands::owners);
        dictionary.put("echo", Commands::echo);
        dictionary.put("quit", Commands::quit);
        cmdStart(System.out::println);
    }

    public void cmdStart(Consumer<String> print){
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

    public void findCommand(){

    }
}
