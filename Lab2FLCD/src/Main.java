import java.util.Scanner;

public class Main {

    public static void main(String[] argv)
    {

        String path="/Users/mehimihai/Desktop/Faculta/FLCD/Lab1b/FLCD/Lab2FLCD/src/";

        MyScanner p3 = new MyScanner();

        MyScanner p2 = new MyScanner();

        MyScanner p1 = new MyScanner();

        Grammar g1 = new Grammar("/Users/mehimihai/Desktop/Faculta/FLCD/Lab1b/FLCD/Lab2FLCD/src/G1.txt");

        System.out.println(g1.getNonterminals());

        System.out.println(g1.getTerminals());

        System.out.println(g1.getProductions());

        System.out.println(g1.productionsForNonTerminal("S"));


        FiniteAutomata FA = new FiniteAutomata("/Users/mehimihai/Desktop/Faculta/FLCD/Lab1b/FLCD/Lab2FLCD/src/Fa.in");

        System.out.print("Choose from the following options:\n");
        System.out.println("1. Show the states of the FA");
        System.out.println("2. Show the alphabet of the FA");
        System.out.println("3. Show the set of final States");
        System.out.println("4. Show the Transitions of the FA");
        System.out.println("5. Check if FA is deterministic");
        System.out.println("6. Check if sequence is accepted");
        System.out.println("0. Exit the program");

        boolean select = true;

        Scanner scanner = new Scanner(System.in);

        while(select)
        {
            System.out.println("Input your option here: ");
            String writtenValue = scanner.nextLine();
            switch (writtenValue) {
                case "1" -> System.out.println(FA.getStates());
                case "2" -> System.out.println(FA.getAlphabet());
                case "3" -> System.out.println(FA.getFinalStates());
                case "4" -> System.out.println(FA.getTransition());
                case "5" -> System.out.println(FA.isDeterministic());
                case "6" -> {
                    System.out.println("Input sequence");
                    String sequence = scanner.nextLine();

                    System.out.println(FA.checkSequence(sequence));
                }
                case "0" -> select = false;
                default -> System.out.println("Choose one of the options from above");
            }
        }



        p1.readProgram(path + "p1");

        p1.writeToFile(path + "p1.out");
//
//        p2.readProgram(path + "p2");
//
//        p2.writeToFile(path + "p2.out");
//
//        p3.readProgram(path + "p3");
//
//        p3.writeToFile(path + "p3.out");
    }
}
