import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Grammar {

    private List<String> terminals;

    private List<String> nonterminals;

    private Map<String, List<List<String>>> productions;

    private String startingSymbol;

    public Grammar(String filename) {

        this.nonterminals = new ArrayList<>();

        this.productions = new LinkedHashMap<>();

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

                List<String> transition = Arrays.asList(scanner.nextLine().split(" -> "));
                List<List<String>> allStates = new ArrayList<>();
                Arrays.stream(transition.get(1).split(" \\| ")).forEach(e ->
                    allStates.add(Arrays.asList(e.split(" "))
                ));

                if(this.productions.containsKey(transition.get(0))){
                    this.productions.get(transition.get(0)).addAll(allStates);
                }
                else {
                    this.productions.put(transition.get(0),allStates);
                }

            }

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<List<String>> productionsForNonTerminal(String nonTerminal){
        if(!this.nonterminals.contains(nonTerminal))
            return new ArrayList<>();
        List<List<String>> result = new ArrayList<>();
        for (var x : this.productions.entrySet())
        {
            List<List<String>> listOfProd = x.getValue();
            if(x.getKey().equals(nonTerminal))
            {
                result.addAll(listOfProd);
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

    public Map<String,List<List<String>>> getProductions() {
        return productions;
    }

    public String getStartingSymbol() {
        return startingSymbol;
    }

    public boolean isCFG() {
        Set<String> keys = this.productions.keySet();


        for(String key: keys){
            if(key.split(" ").length != 1 || this.terminals.contains(key) || !this.nonterminals.contains(key))
            {
                System.out.println(key);
                System.out.println(this.nonterminals);
                return false;
            }
        }
        return true;

    }


}
