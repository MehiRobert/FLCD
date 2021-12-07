import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class RecursiveDesc {

    Grammar grammar;
    public Config config;
    public String sequence;

    public RecursiveDesc(Grammar grammar, String sequence){
        this.grammar = grammar;
        this.sequence = sequence;
        config = new Config();
        config.state = StateValues.q;
        config.position = 1;
        config.inputStack = new ArrayList<>(Collections.singleton(grammar.getStartingSymbol()));
        config.workingStack = new ArrayList<>(Collections.singletonList("E"));
    }


    public void expand() {

            String currentElem = config.inputStack.get(0);
            if(config.workingStack.get(0).equals("E"))
            {
                config.workingStack.remove("E");
            }
            config.workingStack.add(currentElem + "1");
            config.inputStack.remove(currentElem);

            for(var key: grammar.getProductions().entrySet()){
                if(key.getKey().equals(currentElem)){
                    config.inputStack.addAll(key.getValue().get(0));
                }
            }


    }

    public void advance() {
            String currentElement = config.inputStack.get(0);
            if (grammar.getTerminals().contains(currentElement) &&
                currentElement.equals(Character.toString(sequence.charAt(config.position - 1)))) {
                config.position++;
                config.inputStack.remove(currentElement);
                config.workingStack.add(currentElement);
            }
            else {
                this.momentaryInsuccess();
            }

    }

    public void momentaryInsuccess() {

        config.state = StateValues.b;

    }

    public void back() {
            String currentElement = config.workingStack.get(config.workingStack.size() - 1);
            if(grammar.getTerminals().contains(currentElement)) {
                config.position--;
                config.workingStack.remove(currentElement);
                config.inputStack.add(0,currentElement);
            }

    }

    public void anotherTry(){
       String workingStackElem = config.workingStack.get(config.workingStack.size() - 1 );
       String stringValueOfHead = String.valueOf(workingStackElem.charAt(0));
       int intValueOfHead = Integer.parseInt(String.valueOf(workingStackElem.charAt(1))) + 1;
       if (grammar.getNonterminals().contains(stringValueOfHead)) {
           for(var prod: grammar.getProductions().entrySet()) {
               if (prod.getKey().equals(stringValueOfHead)){
                   if(prod.getValue().size() >= intValueOfHead){
                       config.workingStack.remove(config.workingStack.size() - 1 );
                       config.workingStack.add(stringValueOfHead + intValueOfHead);
                       config.inputStack.removeAll(prod.getValue().get(intValueOfHead - 2));
                       config.inputStack.addAll(prod.getValue().get(intValueOfHead - 1));
                       config.state = StateValues.q;

                   }
                   else {
                       if (config.position == 1 && stringValueOfHead.equals(grammar.getStartingSymbol())){
                           config.state = StateValues.e;
                       }
                       else{
                           config.inputStack.removeAll(prod.getValue().get(intValueOfHead - 2));
                           config.inputStack.add(stringValueOfHead);
                           config.workingStack.remove(workingStackElem);
                       }
                   }
               }
           }

       }
    }

    public void success(){
        config.state = StateValues.f;
    }
}
