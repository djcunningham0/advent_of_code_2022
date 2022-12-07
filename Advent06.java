import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Advent06 {
    public static void main(String[] args) throws IOException {

        boolean example = ((args.length > 0) && (args[0].equals("example")));

        String file = (example) ? "data/example_06.txt" : "data/06.txt";
        String[] data = Shared.readFile(file);
        String string = data[0];  // input is only one line in this puzzle

        // Part 1:
        Queue<Character> queue = new LinkedList<>();
        queue.add(string.charAt(0));
        queue.add(string.charAt(1));
        queue.add(string.charAt(2));

        int i = 3;
        boolean done = false;
        while (!done) {
            queue.add(string.charAt(i));
            Set<Character> noDups = new LinkedHashSet<>(queue);
            if (queue.size() == noDups.size()) {
                // all four characters are distinct
                done = true;
            } else {
                // there is a duplicate -- continue
                queue.remove();
                i += 1;
            }
        }

        int solution_a = i + 1;
        System.out.println("Solution 06a: " + solution_a);

        if (example) {
           int correct_a = 7;
           Shared.checkResult(solution_a, correct_a);
        }

        // Part 2:
        while (!queue.isEmpty()) {
            queue.remove();
        }
        for (int j = 0; j < 13; j++) {
            queue.add(string.charAt(j));
        }

        i = 13;
        done = false;
        while (!done) {
            queue.add(string.charAt(i));
            Set<Character> noDups = new LinkedHashSet<>(queue);
            if (queue.size() == noDups.size()) {
                // all characters are distinct
                done = true;
            } else {
                // there is a duplicate -- continue
                queue.remove();
                i += 1;
            }
        }

        int solution_b = i + 1;
        System.out.println("Solution 06b: " + solution_b);

        if (example) {
           int correct_b = 19;
           Shared.checkResult(solution_b, correct_b);
        }
    }
}
