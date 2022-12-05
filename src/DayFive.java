import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class DayFive {
    private static final HashMap<Integer, Stack<Character>> STACKS = new HashMap<>();
    private static final HashMap<Integer, ArrayList<Character>> LISTS = new HashMap<>();

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/niklasenberg/Desktop/input.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        establishStacks(bufferedReader);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] cmd = line.split(" ");
            int amount = Integer.parseInt(cmd[1]);
            int fromIndex = Integer.parseInt(cmd[3]);
            int toIndex = Integer.parseInt(cmd[5]);

            //partOne(amount, fromIndex, toIndex);
            partTwo(amount, fromIndex, toIndex);
        }


        String result = STACKS.values().stream().map(s -> {
            if (!s.isEmpty()) return s.pop().toString();
            return "";
        }).collect(Collectors.joining());

        System.out.println(result);
    }

    private static void partOne(int amount, int fromIndex, int toIndex) {
        for (int i = 0; i < amount; i++) {
            Character toBeMoved = STACKS.get(fromIndex).pop();
            STACKS.get(toIndex).push(toBeMoved);
        }
    }

    private static void partTwo(int amount, int fromIndex, int toIndex) {
        if (amount < 2) {
            partOne(amount, fromIndex, toIndex);
        } else {
            ArrayList<Character> listWithOrder = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                listWithOrder.add(STACKS.get(fromIndex).pop());
            }

            for (int i = 0; i < amount; i++) {
                STACKS.get(toIndex).push(listWithOrder.get(listWithOrder.size() - 1));
                listWithOrder.remove(listWithOrder.size() - 1);
            }
        }
    }

    private static void establishStacks(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        for (int i = 1; i <= getStackIndex(line.length() - 1); i++) {
            LISTS.put(i, new ArrayList<>());
        }

        while (!line.isEmpty()) {
            for (int i = 0; i < line.length(); i++) {
                if (Character.isAlphabetic(line.charAt(i))) LISTS.get(getStackIndex(i)).add(line.charAt(i));
            }
            line = bufferedReader.readLine();
        }

        for (Map.Entry<Integer, ArrayList<Character>> entry : LISTS.entrySet()) {
            ArrayList<Character> list = entry.getValue();
            STACKS.put(entry.getKey(), new Stack<>());
            while (!entry.getValue().isEmpty()) {
                STACKS.get(entry.getKey()).push(list.get(list.size() - 1));
                list.remove(list.size() - 1);
            }
        }
    }

    private static int getStackIndex(int stringIndex) {
        return (stringIndex + 3) / 4;
    }
}
