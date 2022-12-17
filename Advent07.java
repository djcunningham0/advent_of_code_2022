import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

public class Advent07 {
    public static void main(String[] args) throws IOException {

        boolean example = ((args.length > 0) && (args[0].equals("example")));

        String file = (example) ? "data/example_07.txt" : "data/07.txt";
        String[] data = Shared.readFile(file);

        // Part 1:
        Stack<String> location = new Stack<>();
        HashMap<String, Integer> directories = new HashMap<>();
        for (String line : data) {
            if (line.startsWith("$ cd")) {
                if (line.charAt(5) == '.' && line.charAt(6) == '.') {
                    location.pop();
                } else if (line.charAt(5) == '/') {
                    location.clear();
                    location.push("/");
                    if (!directories.containsKey("/")) {
                        directories.put("/", 0);
                    }
                } else {
                    String dir = line.substring(5);
                    location.push(dir);
                    // directory names aren't distinct -- store full path in the hashmap
                    String fullDir = getFullPath(location);
                    if (!directories.containsKey(fullDir)) {
                        directories.put(fullDir, 0);
                    }
                }
            } else if (!line.startsWith("dir") && !line.startsWith("$ ls")) {
                int size = Integer.parseInt(line.split(" ")[0]);
                StringBuilder strBuilder = new StringBuilder();
                for (int i = 0; i < location.size(); i++) {
                    String d = location.elementAt(i);
                    strBuilder.append(d);
                    if (!d.equals("/")) {
                        strBuilder.append("/");
                    }
                    String fullPath = strBuilder.toString();
                    directories.put(fullPath, directories.get(fullPath) + size);
                }
            }
            // don't need to do anything for the "ls" or "dir" lines
        }

        // find directories with size at most 100000
        int sum = 0;
        for (int val : directories.values()) {
            if (val <= 100000) {
                sum += val;
            }
        }

        int solution_a = sum;
        System.out.println("Solution 07a: " + solution_a);

        if (example) {
           int correct_a = 95437;
           Shared.checkResult(solution_a, correct_a);
        }

        // Part 2:
        int targetUsedSpace = 70000000 - 30000000;
        int usedSpace = directories.get("/");
        int minVal = 999999999;
        for (int v : directories.values()) {
            if (v < minVal && usedSpace - v <= targetUsedSpace) {
                minVal = v;
            }
        }
        int solution_b = minVal;
        System.out.println("Solution 07b: " + solution_b);

        if (example) {
           int correct_b = 24933642;
           Shared.checkResult(solution_b, correct_b);
        }
    }

    public static String getFullPath(Stack<String> locationStack) {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < locationStack.size(); i++) {
            strBuilder.append(locationStack.elementAt(i));
            if (!locationStack.elementAt(i).equals("/")) {
                strBuilder.append("/");
            }
        }
        return strBuilder.toString();
    }
}
