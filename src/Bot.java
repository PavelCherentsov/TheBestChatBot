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
        print.accept("Привет, я бот! Напиши 'help', и я расскажу, что умею :)");
        Scanner in = new Scanner(System.in);
        var command = "";
        while (!command.equals("quit") && in.hasNextLine()){
            command = in.nextLine();
            if (command.equals("study"))
            {
                Commands.study(in);
            }
            else{
                try
                {
                    print.accept(dictionary.get(command.split(" ")[0]).apply(command));
                }
                catch (NullPointerException e)
                {
                    print.accept(dictionary.get("help").apply(command));
                }
            }

        }
        in.close();
    }

    public void findCommand(){

    }
}
