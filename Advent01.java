import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Advent01 {
    public static void main(String[] args) throws IOException {

        boolean example = ((args.length > 0) && (args[0].equals("example")));

        String file = (example) ? "data/example_01.txt" : "data/01.txt";
        String[] data = Shared.readFile(file);

        ArrayList<Integer> elf_list = new ArrayList<>();
        int sum = 0;
        for (String line : data) {
            if (line.equals("")) {
                elf_list.add(sum);
                sum = 0;
            } else {
                int value = Integer.parseInt(line);
                sum += value;
            }
        }
        elf_list.add(sum);  // last line won't get added in the foor loop

        // Part 1: find the elf carrying the most calories

        int solution_a = Collections.max(elf_list);
        System.out.println("Solution 01a: " + solution_a);

        if (example) {
            String check = Shared.checkResult(solution_a, 24000);
            System.out.println(check);
        }

        // Part 2: find the total calories carried by the top 3 elves
        elf_list.sort(Collections.reverseOrder());
        int solution_b = elf_list.get(0) + elf_list.get(1) + elf_list.get(2);
        System.out.println("Solution 01b: " + solution_b);

        if (example) {
            String check_b = Shared.checkResult(solution_b, 45000);
            System.out.println(check_b);
        }
    }
}
