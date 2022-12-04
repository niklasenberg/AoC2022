import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class DayTwo {
    private static final ArrayList<Move> MOVES = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/niklasenberg/Desktop/input.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        setUpMoves();

        partTwo(bufferedReader);

        MOVES.clear();
    }

    private static void setUpMoves() {
        Move rock = new Move("A", "X", 1);
        Move paper = new Move("B", "Y", 2, rock);
        Move scissors = new Move("C", "Z", 3, paper);
        rock.setWinsAgainst(scissors);
        Collections.addAll(MOVES, rock, paper, scissors);
    }

    private static void partOne(BufferedReader bufferedReader) throws IOException {
        String line;
        int sum = 0;
        while ((line = bufferedReader.readLine()) != null) {
            String[] choices = line.split(" ");
            Move firstMove = findMove(choices[0]);
            Move playerMove = findMove(choices[1]);

            if (firstMove.equals(playerMove)) { //Draw
                sum += 3;
            } else if (playerMove.winsAgainst.equals(firstMove)) {
                sum += 6;
            }
            sum += playerMove.value;
        }
        System.out.println(sum);
    }

    private static void partTwo(BufferedReader bufferedReader) throws IOException {
        String line;
        int sum = 0;
        while ((line = bufferedReader.readLine()) != null) {
            String[] choices = line.split(" ");
            Move firstMove = findMove(choices[0]);
            Move playerMove;
            String command = choices[1];

            switch (command) {
                case "X" -> playerMove = findMove(firstMove.winsAgainst.primaryCharacter);
                case "Y" -> {
                    playerMove = findMove(firstMove.primaryCharacter);
                    sum += 3;
                }
                default -> {
                    playerMove = findSuperiorMove(firstMove);
                    sum += 6;
                }
            }
            sum += playerMove.value;
        }
        System.out.println(sum);
    }

    private static Move findMove(String choice) {
        return MOVES.stream().filter(m -> m.primaryCharacter.equals(choice) || m.secondaryCharacter.equals(choice)).toList().get(0);
    }

    private static Move findSuperiorMove(Move move) {
        return MOVES.stream().filter(m -> m.winsAgainst.equals(move)).toList().get(0);
    }

    static class Move {
        private final String primaryCharacter;
        private final String secondaryCharacter;
        private final int value;
        private Move winsAgainst;

        public Move(String primaryCharacter, String secondaryCharacter, int value) {
            this.primaryCharacter = primaryCharacter;
            this.secondaryCharacter = secondaryCharacter;
            this.value = value;
        }

        public Move(String primaryCharacter, String secondaryCharacter, int value, Move winsAgainst) {
            this.primaryCharacter = primaryCharacter;
            this.secondaryCharacter = secondaryCharacter;
            this.value = value;
            this.winsAgainst = winsAgainst;
        }

        public void setWinsAgainst(Move winsAgainst) {
            this.winsAgainst = winsAgainst;
        }

        public boolean equals(Move move) {
            return this.primaryCharacter.equals(move.primaryCharacter);
        }
    }
}
