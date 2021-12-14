import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ParserOutput {
    private final RecursiveDesc recursiveDescendent;
    private String derivationString;
    private String productionString;

    public ParserOutput(RecursiveDesc recursiveDescendent) {
        this.recursiveDescendent = recursiveDescendent;
        this.derivationString = "";
        this.productionString = "";
        buildDerivationString();
        buildProductionString();
        writeToFileDerivationString();
        writeToFileProductionString();
    }

    public void buildDerivationString() {
        List<String> workingStack = recursiveDescendent.config.workingStack;
        Grammar grammar = recursiveDescendent.grammar;
        String currentTerminals = "";
        Map<String, List<List<String>>> productions = grammar.getProductions();
        for (String production : workingStack) {
            if (grammar.getNonterminals().contains(String.valueOf(production.charAt(0)))) {
                String valueOfProduction = String.valueOf(production.charAt(0));
                int intValueOfProduction = Integer.parseInt(String.valueOf(production.charAt(1))) - 1;

                this.derivationString = this.derivationString + currentTerminals + valueOfProduction + " -> ";
                for (var p : productions.entrySet()) {
                    if (p.getKey().contains(valueOfProduction)) {
                        for (String productionValues : p.getValue().get(intValueOfProduction)) {
                            if (grammar.getTerminals().contains(productionValues)) {
                                currentTerminals += productionValues;
                            }
                        }
                    }
                }
            }
        }
        derivationString = derivationString + currentTerminals;
    }
    public void buildProductionString() {
        List<String> workingStack = recursiveDescendent.config.workingStack;
        Grammar grammar = recursiveDescendent.grammar;
        for (String production : workingStack) {
            if (grammar.getNonterminals().contains(String.valueOf(production.charAt(0)))) {
                this.productionString = this.productionString + production.charAt(0) +
                        ", " + production.charAt(0) + " -> ";
            } else if (grammar.getTerminals().contains(String.valueOf(production.charAt(0)))) {
                this.productionString = this.productionString + production.charAt(0);
            }
        }
        this.productionString = this.productionString.substring(3);
    }

    public void writeToFileDerivationString() {
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter("src/derivationString.txt");
            myWriter.write(derivationString);
            myWriter.close();
            System.out.println("Derivation String is: " + derivationString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFileProductionString() {
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter("src/productionString.txt");
            myWriter.write(productionString);
            myWriter.close();
            System.out.println("Production String is: " + productionString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
