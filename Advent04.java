import java.io.IOException;

public class Advent04 {
    public static void main(String[] args) throws IOException {

        boolean example = ((args.length > 0) && (args[0].equals("example")));

        String file = (example) ? "data/example_04.txt" : "data/04.txt";
        String[] data = Shared.readFile(file);

        // Part 1:
        int total = 0;
        for (String line : data) {
            String[] split = line.split(",");
            Elf elf_1 = new Elf(split[0]);
            Elf elf_2 = new Elf(split[1]);
            ElfPair pair = new ElfPair(elf_1, elf_2);
            if (pair.has_complete_overlap()) {
                total += 1;
            }
        }

        int solution_a = total;
        System.out.println("Solution 04a: " + solution_a);

        if (example) {
           int correct_a = 2;
           Shared.checkResult(solution_a, correct_a);
        }

        // Part 2:
        total = 0;
        for (String line : data) {
            String[] split = line.split(",");
            Elf elf_1 = new Elf(split[0]);
            Elf elf_2 = new Elf(split[1]);
            ElfPair pair = new ElfPair(elf_1, elf_2);
            if (pair.has_partial_overlap()) {
                total += 1;
            }
        }
        int solution_b = total;
        System.out.println("Solution 04b: " + solution_b);
        
        if (example) {
           int correct_b = 4;
           Shared.checkResult(solution_b, correct_b);
        }
    }
}

class Elf {
    int min;
    int max;

    protected Elf(String range) {
        String[] split = range.split("-");
        min = Integer.parseInt(split[0]);
        max = Integer.parseInt(split[1]);
    }

    protected boolean contains_completely(Elf elf) {
        return elf.min >= min && elf.max <= max;
    }

    protected boolean contains_partial(Elf elf) {
        return (elf.min <= max && elf.max >= min);
    }
}

class ElfPair {
    Elf elf_1;
    Elf elf_2;

    protected ElfPair(Elf elf_1, Elf elf_2) {
        this.elf_1 = elf_1;
        this.elf_2 = elf_2;
    }

    protected boolean has_complete_overlap() {
        return elf_1.contains_completely(elf_2) || elf_2.contains_completely(elf_1);
    }

    protected boolean has_partial_overlap() {
        return elf_1.contains_partial(elf_2);
    }
}