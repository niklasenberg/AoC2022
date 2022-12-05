import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DayThree {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/niklasenberg/Desktop/input.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        partTwo(bufferedReader);
    }

    private static void partOne(BufferedReader bufferedReader) throws IOException {
        ArrayList<Character> commonCharacters = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            HashSet<Character> compartmentOne = new HashSet<>();
            HashSet<Character> compartmentTwo = new HashSet<>();
            int compartmentLength = line.length() / 2;

            for (int i = 0; i < compartmentLength; i++) {
                compartmentOne.add(line.charAt(i));
                compartmentTwo.add(line.charAt(i + compartmentLength));
            }

            compartmentOne.retainAll(compartmentTwo);
            commonCharacters.addAll(compartmentOne);
        }

        int sum = commonCharacters.stream().mapToInt(DayThree::getAlphabeticValue).sum();

        System.out.println(sum);
    }

    private static void partTwo(BufferedReader bufferedReader) throws IOException {
        ArrayList<Character> commonCharacters = new ArrayList<>();
        String line = bufferedReader.readLine();
        while (line != null) {
            HashMap<Integer, Set<Character>> compartments = new HashMap<>();
            for (int i = 0; i < 3; i++) {
                compartments.put(i, line.chars().mapToObj(ch -> (char) ch).collect(Collectors.toSet()));
                line = bufferedReader.readLine();
            }

            for (int i = 1; i < 3; i++) {
                compartments.get(0).retainAll(compartments.get(i));
            }
            commonCharacters.addAll(compartments.get(0));
        }

        int sum = commonCharacters.stream().mapToInt(DayThree::getAlphabeticValue).sum();

        System.out.println(sum);
    }

    private static int getAlphabeticValue(char c) {
        int sum = 0;
        if (Character.isUpperCase(c)) sum += 26;
        return sum + "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(Character.toUpperCase(c)) + 1;
    }
}
