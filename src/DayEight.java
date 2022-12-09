import java.io.*;

public class DayEight {
    private static int[][] forest;

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/niklasenberg/Desktop/input.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        String line = bufferedReader.readLine();
        forest = new int[line.length()][line.length()];

        int y = line.length() - 1;
        while (line != null) {
            for (int i = 0; i < line.length(); i++) {
                forest[y][i] = Character.getNumericValue(line.charAt(i));
            }
            y--;
            line = bufferedReader.readLine();
        }

        //partOne();
        partTwo();
    }

    private static void partOne() {
        int visibleSum = 0;
        for (int i = forest.length - 1; i >= 0; i--) {
            for (int j = 0; j < forest.length; j++) {
                if (isVisible(i, j)) {
                    visibleSum++;
                }
            }
        }

        System.out.println(visibleSum);
    }

    private static void partTwo() {
        int highestScenicScore = 0;
        for (int i = forest.length - 1; i >= 0; i--) {
            for (int j = 0; j < forest.length; j++) {
                highestScenicScore = Math.max(highestScenicScore, getScenicScore(i, j));
            }
        }

        System.out.println(highestScenicScore);
    }

    private static boolean isVisible(int y, int x) {
        if (y == 0 || x == 0 || y == forest.length - 1 || x == forest.length - 1) //Edge trees
            return true;

        return traverse(y + 1, x, forest[y][x], "up") || traverse(y - 1, x, forest[y][x], "down") || traverse(y, x - 1, forest[y][x], "left") || traverse(y, x + 1, forest[y][x], "right");
    }

    private static boolean traverse(int y, int x, int originalTree, String direction) {
        int thisTree = forest[y][x];

        if (thisTree >= originalTree) return false;

        if (x == 0 || x == forest.length - 1 || y == 0 || y == forest.length - 1) {
            return true;
        }

        return switch (direction) {
            case "up" -> traverse(y + 1, x, originalTree, direction);
            case "down" -> traverse(y - 1, x, originalTree, direction);
            case "left" -> traverse(y, x - 1, originalTree, direction);
            case "right" -> traverse(y, x + 1, originalTree, direction);
            default -> false;
        };
    }

    private static int getScenicScore(int y, int x) {
        return traverseCount(y + 1, x, forest[y][x], "up") * traverseCount(y - 1, x, forest[y][x], "down") * traverseCount(y, x - 1, forest[y][x], "left") * traverseCount(y, x + 1, forest[y][x], "right");
    }

    private static int traverseCount(int y, int x, int originalTree, String direction) {
        if (x <= 0 || x >= forest.length - 1 || y <= 0 || y >= forest.length - 1) {
            return 1;
        }

        int thisTree = forest[y][x];

        if (thisTree >= originalTree) return 1;

        return switch (direction) {
            case "up" -> traverseCount(y + 1, x, originalTree, direction) + 1;
            case "down" -> traverseCount(y - 1, x, originalTree, direction) + 1;
            case "left" -> traverseCount(y, x - 1, originalTree, direction) + 1;
            case "right" -> traverseCount(y, x + 1, originalTree, direction) + 1;
            default -> 1;
        };
    }
}
