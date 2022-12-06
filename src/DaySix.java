import java.io.*;
import java.util.LinkedList;

public class DaySix {
    private static final int PART_ONE = 4;
    private static final int PART_TWO = 14;

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/niklasenberg/Desktop/input.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = bufferedReader.readLine();

        LinkedList<Character> previousCharacters = new LinkedList<>();
        int processed = 0;
        for (; processed < line.length(); processed++) {
            previousCharacters.add(line.charAt(processed));
            if (previousCharacters.size() > PART_TWO)
                previousCharacters.remove();

            if (previousCharacters.stream().distinct().count() == PART_TWO)
                break;
        }

        System.out.println(processed+1);
    }
}
