import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

public class DaySeven {
    private static final Directory ROOT = new Directory("/", null);
    private static Directory currentLocation = ROOT;
    private static final HashSet<Directory> DIRECTORIES = new HashSet<>();

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/niklasenberg/Desktop/input.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        DIRECTORIES.add(ROOT);

        String line = bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            String[] command = line.split(" ");
            switch (command[0]) {
                case "$" -> executeCommand(command);
                case "dir" -> declareDirectory(command);
                default -> declareFile(command);
            }
        }

        System.out.println(partOne());
        System.out.println(partTwo());
    }

    private static long partOne() {
        return DIRECTORIES.stream().filter(d -> d.getSize() < 100000).mapToLong(SystemFile::getSize).sum();
    }

    private static long partTwo() {
        long occupiedSpace = 70000000 - ROOT.getSize();
        long neededSpace = 30000000 - occupiedSpace;
        return DIRECTORIES.stream().filter(d -> d.getSize() > neededSpace).min(Comparator.comparing(Directory::getSize)).get().getSize();
    }

    private static void declareFile(String[] command) {
        if (!currentLocation.containsSystemFile(command[1]))
            currentLocation.files.add(new MyFile(command[1], Long.parseLong(command[0])));
    }

    private static void declareDirectory(String[] command) {
        if (!currentLocation.containsSystemFile(command[1])) {
            Directory newDirectory = new Directory(command[1], currentLocation);
            currentLocation.files.add(newDirectory);
            DIRECTORIES.add(newDirectory);
        }
    }

    private static void executeCommand(String[] command) {
        if ("cd".equals(command[1])) {
            if (command[2].equals("..")) {
                currentLocation = currentLocation.parent == null ? currentLocation : currentLocation.parent;
            } else {
                currentLocation = (Directory) currentLocation.files.stream().filter(d -> d.getName().equals(command[2])).toList().get(0);
            }
        }
    }

    interface SystemFile {

        long getSize();

        String getName();
    }

    static class Directory implements SystemFile {
        private final String name;
        private final Directory parent;
        private final ArrayList<SystemFile> files = new ArrayList<>();

        public Directory(String name, Directory parent) {
            this.name = name;
            this.parent = parent;
        }

        @Override
        public long getSize() {
            return files.stream().mapToLong(SystemFile::getSize).sum();
        }

        @Override
        public String getName() {
            return name;
        }

        public boolean containsSystemFile(String s) {
            return files.stream().anyMatch(sf -> sf.getName().equals(s));
        }
    }

    record MyFile(String name, long size) implements SystemFile {

        @Override
        public long getSize() {
            return size;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
