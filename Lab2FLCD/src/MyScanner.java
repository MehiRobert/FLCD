import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MyScanner {

    private List<String> tokens = new ArrayList<>();

    private final List<String> separators = Arrays.asList("[","]","(",")", ",", "\n");

    private final List<String> operators = Arrays.asList("+","-","*","/",":","=","%","<=",">=","!=",">","<","||","&&","and","write.Text");

    private final List<String> reservedWords = Arrays.asList("integer","character","verify","otherwise","For","write","string","boolean");

    private final List<String> types = Arrays.asList("integer","boolean","string","character");

    private final ProgramInternalForm PIF = new ProgramInternalForm();

    private Map<String, Integer> ST = new HashMap<>();


    private int capacity = 100;

    private SymbolTable symbolTable = new SymbolTable(100);

    public MyScanner()
    {

        readTokens();

        this.symbolTable = new SymbolTable(100);
    }

    public void readTokens() {
        try {

            File file = new File("/Users/mehimihai/IdeaProjects/Lab2FLCD/src/token");

            Scanner reader = new Scanner(file);

            while(reader.hasNextLine())
            {
                String token = reader.nextLine();
                tokens.add(token);
            }
            reader.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public List<String> getTokens() {
        return tokens;
    }

    public void readProgram(String programFile)
    {
        try {
            File file = new File(programFile);
            Scanner reader = new Scanner(file);

            while(reader.hasNextLine())
            {
                String line = reader.nextLine();

                //getting rid of the white spaces

                List<String> tokens = tokenize(line);

                scan(tokens);




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

    public void scan(List<String> tokensLine)
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
            else if(isArray(token)) {
                this.PIF.add(token,5);
            }
            else if(isIdentifier(token)){
                int position = this.symbolTable.addInSymbolTable(token);
                this.PIF.add(token,position);
            }
            else if(isConstant(token)) {
                int position = this.symbolTable.addInSymbolTable(token);
                this.PIF.add("const",position);
            }


        });
    }

    public boolean isIdentifier(String token) {

        String pattern = "^([a-zZ-Z])+([a-zA-Z]|[0-9])*$";

        return  token.matches(pattern);
    }

    public boolean isCharacter(String token) {
        String character = "^\'[a-zA-Z0-9_]\'$";

        return token.matches(character);
    }

    public boolean isString(String token) {
        String pattern = "^[a-zA-Z0-9_ ]*$";

        return token.matches(pattern);
    }

    public boolean isNumber(String token) {
        String pattern = "^([+|-]?[1-9][0-9]*)|0$";

        return token.matches(pattern);
    }

    public boolean isArray(String token) {
       String[] possibleArray = token.split("\\.");
       if(possibleArray.length != 2)
           return false;
       return this.types.contains(possibleArray[0]) && possibleArray[1].equals("array");
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
}
