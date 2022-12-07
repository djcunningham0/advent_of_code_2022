import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Advent03 {
    public static void main(String[] args) throws IOException {

        boolean example = ((args.length > 0) && (args[0].equals("example")));

        String file = (example) ? "data/example_03.txt" : "data/03.txt";
        String[] data = Shared.readFile(file);

        // Part 1:
        int total = 0;
        for (String line : data) {
            Rucksack rucksack = new Rucksack(line);
            Item item = rucksack.find_overlap();
            total += item.priority;
        }

        int solution_a = total;
        System.out.println("Solution 03a: " + solution_a);

        if (example) {
            int correct_a = 157;
            Shared.checkResult(solution_a, correct_a);
        }

        // Part 2:
        total = 0;
        for (int i = 0; i < data.length; i = i + 3) {
            String line_1 = data[i];
            String line_2 = data[i + 1];
            String line_3 = data[i + 2];
            ElfGroup elf_group = new ElfGroup(line_1, line_2, line_3);
            Item badge = elf_group.find_badge();
            total += badge.priority;
        }

        int solution_b = total;
        System.out.println("Solution 03b: " + solution_b);

        if (example) {
        int correct_b = 70;
            Shared.checkResult(solution_b, correct_b);
        }
    }
}

class Rucksack {
    String contents;
    String compartment_1;
    String compartment_2;

    public Rucksack(String contents) {
        this.contents = contents;
        compartment_1 = contents.substring(0, contents.length() / 2);
        compartment_2 = contents.substring(contents.length() / 2);
    }

    public Item find_overlap() {
        Set<String> set_1 = new HashSet<>(Arrays.asList(compartment_1.split("")));
        Set<String> set_2 = new HashSet<>(Arrays.asList(compartment_2.split("")));
        set_1.retainAll(set_2);
        char c = set_1.iterator().next().charAt(0);  // it's given that there will only be one item
        return new Item(c);
    }
}

class ElfGroup {
    String elf_1;
    String elf_2;
    String elf_3;

    public ElfGroup(String elf_1, String elf_2, String elf_3) {
        this.elf_1 = elf_1;
        this.elf_2 = elf_2;
        this.elf_3 = elf_3;
    }

    public Item find_badge() {
        // the badge is the item that is common among all three
        Set<String> set_1 = new HashSet<>(Arrays.asList(elf_1.split("")));
        Set<String> set_2 = new HashSet<>(Arrays.asList(elf_2.split("")));
        Set<String> set_3 = new HashSet<>(Arrays.asList(elf_3.split("")));
        set_1.retainAll(set_2);
        set_1.retainAll(set_3);
        char c = set_1.iterator().next().charAt(0);  // it's given that there will only be one item
        return new Item(c);
    }
}

class Item {
    char value;
    int priority;

    public Item(char value) {
        this.value = value;
        priority = value >= 'A' && value <= 'Z' ?
                value - 38 :
                value - 96;
    }
}