import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;



public class MyScanner {

    String path="/Users/mehimihai/Desktop/Faculta/FLCD/Lab1b/FLCD/Lab2FLCD/src/";

    private final List<String> tokens = new ArrayList<>();

    private final List<String> separators = Arrays.asList("[","]","(",")", ",","{","}",".", "\n");

    private final List<String> operators = Arrays.asList("+","-","*","/",":","=","%","<=",">=","!=",">","<","||","&&","and");

    private final List<String> reservedWords = Arrays.asList("integer","character","verify","otherwise","For","write","string","boolean","array","Text","length","stop","get");

    private final ProgramInternalForm PIF = new ProgramInternalForm();


    private final SymbolTable symbolTable ;

    public MyScanner()
    {


        this.symbolTable = new SymbolTable(100);
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void readProgram(String programFile)
    {
        try {
            File file = new File(programFile);
            Scanner reader = new Scanner(file);

            int counter = 1;

            while(reader.hasNextLine())
            {
                String line = reader.nextLine();

                List<String> tokens = tokenize(line);

                scan(tokens, counter);

                counter++;



            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public List<String> tokenize(String line) {
        List<String> tokens = new ArrayList<>();

        int i = 0;

        while(i < line.length())
        {
            if (line.charAt(i) == '"')
            {
                StringBuilder stringToken = new StringBuilder();
                i++;
                while(i < line.length() && line.charAt(i) != '"')
                {
                    stringToken.append(line.charAt(i));
                    i++;
                }
                i++;
                tokens.add(stringToken.toString());
            }
            else if(isSeparator(String.valueOf(line.charAt(i))))
            {
                if(line.charAt(i) != ' ' && line.charAt(i) != '\n' )
                {
                    tokens.add(String.valueOf(line.charAt(i)));
                }
                i++;
            }
            else if (isOperator(String.valueOf(line.charAt(i))))
            {
                tokens.add(String.valueOf(line.charAt(i)));

                i++;
            }
            else {
                StringBuilder token = new StringBuilder(String.valueOf(line.charAt(i)));
                if(token.toString().charAt(0) != ' ') {
                    i++;
                    while (i < line.length() && !isSeparator(String.valueOf(line.charAt(i))) && !isOperator(String.valueOf(line.charAt(i))) && line.charAt(i) != ' ') {
                        token.append(line.charAt(i));
                        i++;
                    }
                    tokens.add(token.toString());
                }
                else{
                    i++;
                }
            }
        }

        return tokens;
    }

    public void scan(List<String> tokensLine,Integer line)
    {
        tokensLine.forEach(token -> {
            if(this.reservedWords.contains(token))
            {
                this.PIF.add(token,2);
            }
            else if (this.operators.contains(token)){
                this.PIF.add(token,3);
            }
            else if (this.separators.contains(token)) {
                this.PIF.add(token,4);
            }
            else if(isIdentifier(token)){
                int position = this.symbolTable.addInSymbolTable(token);
                this.PIF.add(token,position);
            }
            else if(isConstant(token)) {
                int position = this.symbolTable.addInSymbolTable(token);
                this.PIF.add("const",position);
            }
            else
            {
               String error = "Lexical error on line " + line +  " -> " +  token + " <- ";


                writeError(path + "p3err.out",error);
            }


        });
    }

    public boolean isIdentifier(String token) {

//        String pattern = "^([a-zZ-Z])+([a-zA-Z]|[0-9])*$";

        FiniteAutomata fa = new FiniteAutomata("/Users/mehimihai/Desktop/Faculta/FLCD/Lab1b/FLCD/Lab2FLCD/src/identifierFa.in");
        return  fa.checkSequence(token);
    }

    public boolean isCharacter(String token) {
        String character = "^\'[a-zA-Z0-9]\'$";

        return token.matches(character);
    }

    public boolean isString(String token) {
        String pattern = "^[a-zA-Z0-9 ]*$";

        return token.matches(pattern);
    }

    public boolean isNumber(String token) {
//        String pattern = "^([+|-]?[1-9][0-9]*)|0$";

        FiniteAutomata fa = new FiniteAutomata("/Users/mehimihai/Desktop/Faculta/FLCD/Lab1b/FLCD/Lab2FLCD/src/integerFa.in");


          return fa.checkSequence(token);

//        return token.matches(pattern);
    }

    public boolean isConstant(String token) {
        return isNumber(token) || isString(token) || isCharacter(token) || token.equals("true") || token.equals("false");
    }

    public boolean isSeparator(String token) {
        return separators.contains(token);
    }

    public boolean isOperator(String token) {
        return operators.contains(token);
    }

    public void writeToFile(String filename)
    {
        try {
            FileWriter fw = new FileWriter(filename);
            fw.write("######################## SYMBOL TABLE #################");
            fw.write(this.symbolTable.toString());
            fw.write("\n\n\n");
            fw.write("####################### PIF ###########################");
            fw.write(this.PIF.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeError(String filename, String error) {
        try{
            FileWriter fw = new FileWriter(filename);
            fw.write(error);
            fw.write("\n\n");
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
