

public class RecursiveDescAlgorithm {

    private final RecursiveDesc recursiveDesc;

    public RecursiveDescAlgorithm(RecursiveDesc recursiveDesc){
        this.recursiveDesc = recursiveDesc;
    }

    public void parse() {
        while(!recursiveDesc.config.state.equals(StateValues.f) &&
        !recursiveDesc.config.state.equals(StateValues.e)) {
            if (recursiveDesc.config.state.equals(StateValues.q)){
                if(recursiveDesc.config.position == recursiveDesc.sequence.length() + 1 &&
                recursiveDesc.config.inputStack.isEmpty()){
                    recursiveDesc.success();
                }
                else {
                    String headOfInputStack = "E";
                    if (recursiveDesc.config.inputStack.size() > 0){
                        headOfInputStack = recursiveDesc.config.inputStack.get(0);
                    }
                    if(recursiveDesc.grammar.getNonterminals().contains(headOfInputStack)){
                        recursiveDesc.expand();
                    }
                    else{
                        if(recursiveDesc.config.position <= recursiveDesc.sequence.length() && headOfInputStack.equals(Character.toString(recursiveDesc.sequence.charAt(recursiveDesc.config.position - 1)))){
                            recursiveDesc.advance();
                        }
                        else{
                            recursiveDesc.momentaryInsuccess();
                        }
                    }
                }
            } else {
                if(recursiveDesc.config.state.equals(StateValues.b)){
                    String headOfWorkingStack = recursiveDesc.config.workingStack.get(recursiveDesc.config.workingStack.size() - 1);
                    if(recursiveDesc.grammar.getTerminals().contains(headOfWorkingStack)) {
                        recursiveDesc.back();
                    }
                    else {
                        recursiveDesc.anotherTry();
                    }
                }
            }
        }
        if(recursiveDesc.config.state.equals(StateValues.e)){
            System.out.println("Error");
        }
        else{
            System.out.println("Sequence Accepted");
            System.out.println("WorkingStack" + recursiveDesc.config.workingStack);
            System.out.println("InputStack" + recursiveDesc.config.inputStack);
        }
    }
}
