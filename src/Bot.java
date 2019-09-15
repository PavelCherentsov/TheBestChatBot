import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Function;

public class Bot {
    private HashMap<String, Function<String, String>> dictionary = new HashMap<String,Function<String, String>>();
    public Bot(){
        dictionary.put("help", Commands::help);
        dictionary.put("Авторы", Commands::owners);
        dictionary.put("echo", Commands::echo);
        cmdStart();
    }

    public void cmdStart(){
        Scanner in = new Scanner(System.in);
        var i = 1;
        while (true){
            var command = in.nextLine();
            System.out.println(dictionary.get(command.split(" ")[0]).apply(command));
            if (i == 0) break;
        }
        in.close();
    }

    public void findCommand(){

    }
}
