import org.junit.Test;

public class TestSymbol {


        @Test
        public void testAdd() {
            SymbolTable symbolTable = new SymbolTable(5);
            assert(symbolTable.addInSymbolTable("a") == 2);
            assert(symbolTable.addInSymbolTable("a") == 2);
            assert(symbolTable.addInSymbolTable("b") == 3);
        }
    }

