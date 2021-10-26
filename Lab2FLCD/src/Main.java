import java.util.Scanner;

public class Main {

    public static void main(String[] argv)
    {

        String path="/Users/mehimihai/Desktop/Faculta/FLCD/Lab1b/FLCD/Lab2FLCD/src/";

        MyScanner p3 = new MyScanner();

        MyScanner p2 = new MyScanner();

        MyScanner p1 = new MyScanner();


        p1.readProgram(path + "p1");

        p1.writeToFile(path + "p1.out");

        p2.readProgram(path + "p2");

        p2.writeToFile(path + "p2.out");

        p3.readProgram(path + "p3");

        p3.writeToFile(path + "p3.out");
    }
}
