import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Advent05 {
    public static void main(String[] args) throws IOException {

        boolean example = ((args.length > 0) && (args[0].equals("example")));

        String file = (example) ? "data/example_05.txt" : "data/05.txt";
        String[] data = Shared.readFile(file);

        // Part 1:
        // parse the top section -- figure out how many stacks there are and store info in a buffer
        ArrayList<String> buffer = new ArrayList<>();
        int n_cols = 0;
        boolean done = false;
        int row = 0;
        while (!done) {
            String line = data[row];
            if (line.charAt(1) == '1') {
                done = true;
                n_cols = Integer.parseInt(line.substring(line.length()-2, line.length()-1));
            } else {
                buffer.add(line);
                row += 1;
            }
        }
        Grid grid = create_grid_from_buffer(buffer, n_cols);

        // now go through the instructions
        for (int i = row + 2; i < data.length; i++) {
            String instruction = data[i];
            grid.process_instruction(instruction);
        }

        StringBuilder solution_a = new StringBuilder();
        for (Stack<Character> s : grid.stacks) {
            solution_a.append(s.pop());
        }
        System.out.println("Solution 05a: " + solution_a);

        if (example) {
           String correct_a = "CMZ";
           Shared.checkResult(solution_a.toString(), correct_a);
        }

        // Part 2:
        Grid grid_2 = create_grid_from_buffer(buffer, n_cols);
        for (int i = row + 2; i < data.length; i++) {
            String instruction = data[i];
            grid_2.process_instruction_2(instruction);
        }

        StringBuilder solution_b = new StringBuilder();
        for (Stack<Character> s : grid_2.stacks) {
            solution_b.append(s.pop());
        }
        System.out.println("Solution 05b: " + solution_b);

        if (example) {
           String correct_b = "MCD";
           Shared.checkResult(solution_b.toString(), correct_b);
        }
    }

    private static Grid create_grid_from_buffer(ArrayList<String> buffer, int n_cols) {
        ArrayList<Stack<Character>> stacks = new ArrayList<>();
        for (int i = 0; i < n_cols; i++) {
            stacks.add(new Stack<>());
        }
        // loop through buffer in reverse order so we push to stacks correctly
        for (int i = buffer.size(); i > 0; i = i - 1) {
            String line = buffer.get(i - 1);
            for (int j = 0; j < n_cols; j++) {
                char crate = line.charAt(4 * j + 1);
                if (crate != ' ') {
                    stacks.get(j).push(crate);
                }
            }
        }
        return new Grid(stacks);
    }
}

class Grid {
    ArrayList<Stack<Character>> stacks;

    protected Grid(ArrayList<Stack<Character>> stacks) {
        this.stacks = stacks;
    }

    protected void process_instruction(String instruction) {
        int[] parsed = parse_instruction(instruction);
        int n = parsed[0];
        int from = parsed[1];
        int to = parsed[2];
        for (int i = 0; i < n; i++) {
            stacks.get(to).push(stacks.get(from).pop());
        }
    }

    protected void process_instruction_2(String instruction) {
        int[] parsed = parse_instruction(instruction);
        int n = parsed[0];
        int from = parsed[1];
        int to = parsed[2];
        ArrayList<Character> buffer = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            buffer.add(stacks.get(from).pop());
        }
        Collections.reverse(buffer);  // reverse it so we push in reverse order
        for (char x : buffer) {
            stacks.get(to).push(x);
        }
    }

    private int[] parse_instruction(String instruction) {
        int n = Integer.parseInt(instruction.split("move ")[1].split(" from")[0]);
        int from = Integer.parseInt(instruction.split("from ")[1].split(" to")[0]) - 1;
        int to = Integer.parseInt(instruction.split("to ")[1]) - 1;
        return new int[] {n, from, to};
    }
}
