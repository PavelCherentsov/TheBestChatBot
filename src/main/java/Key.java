import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Key {
    public static String get_token() throws IOException {
        String path = new File("").getAbsolutePath();
        String path_to_token = path.substring(0, path.lastIndexOf("\\")) + "\\";
        BufferedReader br = new BufferedReader(new FileReader(path_to_token+"\\"+"TOKEN"));
        String s = br.readLine();
        br.close();
        return s;
    }
}
