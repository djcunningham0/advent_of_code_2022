# usage: `source make_template.sh 01` would create Advent01.java
if [[ $# -eq 0 ]]; then echo "must pass an argument"; return 1; fi
if [[ ${#1} != 2 ]]; then echo "argument must be two digits long"; return 1; fi
if [[ -f Advent$1.java ]]; then echo "Advent$1.java already exists!"; return 1; fi

cat -> Advent$1.java <<- EndOfString
import java.io.IOException;

public class Advent$1 {
    public static void main(String[] args) throws IOException {

        boolean example = ((args.length > 0) && (args[0].equals("example")));

        String file = (example) ? "data/example_$1.txt" : "data/$1.txt";
        String[] data = Shared.readFile(file);

        // Part 1:

        //int solution_a = ;
        //System.out.println("Solution $1a: " + solution_a);

        //if (example) {
        //   int correct_a = ;
        //   Shared.checkResult(solution_a, correct_a);
        //}

        // Part 2:
        //int solution_b = ;
        //System.out.println("Solution $1b: " + solution_b);

        //if (example) {
        //   int correct_b = ;
        //   Shared.checkResult(solution_b, correct_b);
        //}
    }
}
EndOfString

touch data/$1.txt
touch data/example_$1.txt
