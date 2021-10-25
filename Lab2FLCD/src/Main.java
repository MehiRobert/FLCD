import java.util.Scanner;

public class Main {

    public static void main(String[] argv)
    {

        MyScanner p3 = new MyScanner();

        MyScanner p2 = new MyScanner();

        MyScanner p1 = new MyScanner();


//        p1.readProgram("/Users/mehimihai/IdeaProjects/Lab2FLCD/src/p1");
//
//        p1.writeToFile("/Users/mehimihai/IdeaProjects/Lab2FLCD/src/p1.out");
//
//        p2.readProgram("/Users/mehimihai/IdeaProjects/Lab2FLCD/src/p2");
//
//        p2.writeToFile("/Users/mehimihai/IdeaProjects/Lab2FLCD/src/p2.out");

        p3.readProgram("/Users/mehimihai/IdeaProjects/Lab2FLCD/src/p3");

        p3.writeToFile("/Users/mehimihai/IdeaProjects/Lab2FLCD/src/p3.out");
    }
}
