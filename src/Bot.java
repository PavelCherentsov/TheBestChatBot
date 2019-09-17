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
        dictionary.put("study", Commands::study);
        //cmdStart(System.out::println);
        newCmdStart(System.out::println);
    }

    public void newCmdStart(Consumer<String> print)
    {
        System.out.println("Welcome! Use 'help' to get help");
        Scanner in = new Scanner(System.in);
        var line = "";
        while (!line.equals("quit"))
        {
            line = in.nextLine();
            System.out.println(line);
            try
            {
                print.accept(dictionary.get(line.split(" ")[0]).apply(line));
            }
            catch (NullPointerException e)
            {
                print.accept(dictionary.get("help").apply(line));
            }
        }

        System.out.println("I am out");
        in.close();
        System.out.println("in is closed");
    }

    public void cmdStart(Consumer<String> print){
        Scanner in = new Scanner(System.in);
        var command = "";
        while (true){
            command = in.nextLine();
            try
            {
                print.accept(dictionary.get(command.split(" ")[0]).apply(command));
            }
            catch (NullPointerException e)
            {
                print.accept(dictionary.get("help").apply(command));
            }
            if (command.equals("study")) {
                continue;
            }
            if (command.split(" ")[0].equals("quit"))
                break;
        }
        in.close();
        System.out.println("EXIT");
    }

    public void findCommand(){

    }
}
