import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

public class Advent02 {
    public static void main(String[] args) throws IOException {

        boolean example = ((args.length > 0) && (args[0].equals("example")));

        String file = (example) ? "data/example_02.txt" : "data/02.txt";
        String[] data = Shared.readFile(file);

        // Part 1: A/X = rock, B/Y = paper, C/Z = scissors
        Map<Character, Integer> shape_scores = new HashMap<>();
        shape_scores.put('X', 1);
        shape_scores.put('Y', 2);
        shape_scores.put('Z', 3);

        Map<String, Integer> outcome_scores = new HashMap<>();
        outcome_scores.put("A X", 3);
        outcome_scores.put("A Y", 6);
        outcome_scores.put("A Z", 0);
        outcome_scores.put("B X", 0);
        outcome_scores.put("B Y", 3);
        outcome_scores.put("B Z", 6);
        outcome_scores.put("C X", 6);
        outcome_scores.put("C Y", 0);
        outcome_scores.put("C Z", 3);

        int total_score = 0;
        for (String line : data) {
            char shape = line.charAt(2);
            int shape_score = shape_scores.get(shape);
            int outcome_score = outcome_scores.get(line);
            total_score += shape_score + outcome_score;
        }

        int solution_a = total_score;
        System.out.println("Solution 02a: " + solution_a);

        if (example) {
            Shared.checkResult(solution_a, 15);
        }

        // Part 2: X = lose, Y = draw, Z = win
        Map<String, Character> map = new HashMap<>();
        map.put("A X", 'Z');
        map.put("A Y", 'X');
        map.put("A Z", 'Y');
        map.put("B X", 'X');
        map.put("B Y", 'Y');
        map.put("B Z", 'Z');
        map.put("C X", 'Y');
        map.put("C Y", 'Z');
        map.put("C Z", 'X');

        Map<Character, Integer> simple_outcome_map = new HashMap<>();
        simple_outcome_map.put('X', 0);
        simple_outcome_map.put('Y', 3);
        simple_outcome_map.put('Z', 6);

        total_score = 0;
        for (String line : data) {
            char shape = map.get(line);
            int shape_score = shape_scores.get(shape);
            char outcome = line.charAt(2);
            int outcome_score = simple_outcome_map.get(outcome);
            total_score += shape_score + outcome_score;
        }

        int solution_b = total_score;
        System.out.println("Solution 02a: " + solution_b);

        if (example) {
            Shared.checkResult(solution_b, 12);
        }

    }
}
