import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProgramInternalForm {

        private final List<Pair<String,Integer>> PIF = new ArrayList<>();


        public void add(String token, int type)
        {
            PIF.add(new Pair<>(token,type));
        }
        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            for(Pair<String, Integer> pair: PIF)
            {
                stringBuilder.append(pair.toString())
                             .append("\n");
            }
            return stringBuilder.toString();
        }
}
