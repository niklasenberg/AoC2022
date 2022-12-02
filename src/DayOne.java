import java.io.*;
import java.util.Arrays;

public class DayOne {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/niklasenberg/Desktop/input.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        partTwo(bufferedReader);
    }

    private static void partOne(BufferedReader bufferedReader) throws IOException {
        String line;
        int maxCalories = 0;
        int currentCalories = 0;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.isEmpty()) {
                maxCalories = Math.max(currentCalories, maxCalories);
                currentCalories = 0;
            } else {
                currentCalories += Integer.parseInt(line);
            }
        }

        System.out.println(maxCalories);
    }

    private static void partTwo(BufferedReader bufferedReader) throws IOException {
        String line;
        int[] maxCalories = new int[3];
        int currentCalories = 0;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.isEmpty()) {
                maxCalories[0] = Math.max(currentCalories, maxCalories[0]);
                currentCalories = 0;
                Arrays.sort(maxCalories);
            } else {
                currentCalories += Integer.parseInt(line);
            }
        }

        System.out.println(Arrays.stream(maxCalories).sum());
    }
}
