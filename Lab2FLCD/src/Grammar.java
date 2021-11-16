import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Grammar {

    private List<String> terminals;

    private List<String> nonterminals;

    public List<Pair<String,String>> productions;

    private String startingSymbol;

    public Grammar(String filename) {

        this.nonterminals = new ArrayList<>();

        this.productions = new ArrayList<>();

        this.terminals = new ArrayList<>();

        loadFromFile(filename);
    }


    private void loadFromFile(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath)))
        {
            this.nonterminals = new ArrayList<>(List.of(scanner.nextLine().split(",")));
            this.terminals = new ArrayList<>(List.of(scanner.nextLine().split(",")));
            this.startingSymbol = scanner.nextLine();

            while(scanner.hasNextLine())
            {

                String[] transition = scanner.nextLine().split(" -> ");
                Arrays.stream(transition[1].trim().split("\\|")).forEach(e ->
                    this.productions.add(new Pair<>(transition[0],e)
                ));


            }

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Pair<String,String>> productionsForNonTerminal(String nonTerminal){
        if(!this.nonterminals.contains(nonTerminal))
            return new ArrayList<>();
        List<Pair<String,String>> result = new ArrayList<>();
        for (var x : this.productions)
        {
            if(x.getFirst().contains(nonTerminal))
            {
                    result.add(x);
            }
        }
        return result;
    }


    public List<String> getTerminals() {
        return terminals;
    }

    public List<String> getNonterminals() {
        return nonterminals;
    }

    public List<Pair<String,String>> getProductions() {
        return productions;
    }

    public String getStartingSymbol() {
        return startingSymbol;
    }


}
