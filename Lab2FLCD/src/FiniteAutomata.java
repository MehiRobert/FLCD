import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class FiniteAutomata {

    public List<String> states, alphabet, finalStates;

    public String initialState;

    public Map<Pair<String,String>,List<String>> transitions;

    public FiniteAutomata(String filaname)
    {
        this.states = new ArrayList<>();

        this.alphabet = new ArrayList<>();

        this.finalStates = new ArrayList<>();

        this.transitions = new HashMap<>();

        readElements(filaname);

    }

    public void readElements(String filename)  {

        try {
            File file = new File(filename);
            Scanner reader = new Scanner(file);

            String statesLine = reader.nextLine();
            this.states = Arrays.asList(statesLine.split(" "));

            String alphabetLine = reader.nextLine();
            this.alphabet = Arrays.asList(alphabetLine.split(" "));

            this.initialState = reader.nextLine();

            String finalStatesLine = reader.nextLine();

            this.finalStates = Arrays.asList(finalStatesLine.split(" "));
            int cnt = 0;
            while(reader.hasNextLine())
            {

                cnt++;
                System.out.println(cnt);
                System.out.println(this.finalStates);
                String[] transition = reader.nextLine().split(" ");
                Pair newPair = new Pair<>(transition[0],transition[1]);
                System.out.println(this.transitions.containsKey(newPair));
                if(this.transitions.containsKey(newPair))
                {
                    if(!this.transitions.get(newPair).contains(transition[2]))
                        this.transitions.get(newPair).add(transition[2]);
                }
                else {
                    this.transitions.put(new Pair<>(transition[0], transition[1]), new ArrayList<>(Collections.singletonList(transition[2])));
                };
            }


        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean isDeterministic()
    {
        for(Pair<String,String> key: this.transitions.keySet())
        {
            if(this.transitions.get(key).size() > 1)
            {
                return false;
            }
        }
        return true;
    }

    public boolean checkSequence(String sequence){
        if(!isDeterministic())
        {
            return false;
        }
        else{
            String currentState = this.initialState;
            for(char c: sequence.toCharArray())
            {
                Pair<String,String> stringPair = new Pair<>(currentState,String.valueOf(c));
                if(this.transitions.containsKey(stringPair))
                {
                    currentState = this.transitions.get(stringPair).get(0);
                }
                else {return false;}
            }

            return this.finalStates.contains(currentState);
        }
    }
    public String getStates() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("States: ");
        for(String s: this.states)
        {
            stringBuilder.append(s).append(" ");
        }
        return stringBuilder.toString();
    }

    public String getAlphabet() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Alphabet: ");
        for(String s: this.alphabet)
        {
            stringBuilder.append(s).append(" ");
        }
        return stringBuilder.toString();
    }

    public String getFinalStates() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Final States: ");
        for(String s: this.finalStates)
        {
            stringBuilder.append(s).append(" ");
        }
        return stringBuilder.toString();
    }

    public String getTransition() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Transitions: ");
        for(var s: this.transitions.entrySet())
        {
            stringBuilder.append(s.getKey()).append("->");
            stringBuilder.append(s.getValue());
        }
        return stringBuilder.toString();
    }
}
