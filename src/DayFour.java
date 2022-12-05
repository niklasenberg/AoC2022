import java.io.*;
import java.util.HashSet;
import java.util.stream.IntStream;

public class DayFour {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/niklasenberg/Desktop/input.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        String line;
        int rangeCount = 0;
        while ((line = bufferedReader.readLine()) != null) {
            String[] ranges = line.split(",");

            HashSet<Integer> rangeOne = establishRange(ranges[0]);
            HashSet<Integer> rangeTwo = establishRange(ranges[1]);

            //rangeCount += partOne(rangeOne, rangeTwo);
            rangeCount += partTwo(rangeOne, rangeTwo);
        }
        System.out.println(rangeCount);
    }

    private static int partOne(HashSet<Integer> rangeOne, HashSet<Integer> rangeTwo) {
        if (overlaps(rangeOne, rangeTwo)) return 1;
        return 0;
    }

    private static int partTwo(HashSet<Integer> rangeOne, HashSet<Integer> rangeTwo) {
        rangeOne.retainAll(rangeTwo);

        if (!rangeOne.isEmpty()) return 1;
        return 0;
    }

    private static boolean overlaps(HashSet<Integer> rangeOne, HashSet<Integer> rangeTwo) {
        return rangeOne.containsAll(rangeTwo) || rangeTwo.containsAll(rangeOne);
    }

    private static HashSet<Integer> establishRange(String range) {
        String[] numbers = range.split("-");
        int start = Integer.parseInt(numbers[0]);
        int end = Integer.parseInt(numbers[1]);

        return new HashSet<>(IntStream.rangeClosed(start, end).boxed().toList());
    }
}
