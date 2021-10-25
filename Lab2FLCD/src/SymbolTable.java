import org.junit.Test;


public class SymbolTable {

    private final String[] symbolTable;
    private final int capacity;

    public SymbolTable(int capacity) {
        this.capacity = capacity;
        this.symbolTable = new String[capacity];
    }

    //hash function that computes the sum of the ascii codes of a string, then it "%" to the size of the hash table
    private int hashFunction(String key) {
        int asciiSum = 0;
        for(int i = 0; i < key.length(); i++)
        {
            asciiSum += key.charAt(i);
        }

        return asciiSum % capacity;
    }

    public int addInSymbolTable(String token) {

        int positionInTable = 0;
        for (String entry: symbolTable)
        {

            // in case we find the element we return it's position
            if(entry != null && entry.equals(token))
            {
                System.out.println("Found at position " + positionInTable);
               return positionInTable;
            }
            positionInTable++;

        }

        //compute the hash
        int hashValue = hashFunction(token);

        //no collisions
        if(symbolTable[hashValue] == null)
        {
            symbolTable[hashValue] = token;
            System.out.println("Added at position" + hashValue);
            return hashValue;
        }

        //linear probing
        int collisionHash = hashValue;
        while(symbolTable[collisionHash] != null){
            collisionHash++;
        }
        // after we have found an empty position, we place the element on its position and then we return the position
        if(symbolTable[collisionHash] == null)
        {
            symbolTable[collisionHash] = token;
            System.out.println("Added at position" + collisionHash);
            return hashValue;
        }
        //not possible to end up here
        return -1;
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 0;
        for(String string: symbolTable)
        {

            if(string != null) {

                stringBuilder.append(string)
                        .append("->")
                        .append(counter)
                        .append("\n");
            }
            counter++;
        }
        return stringBuilder.toString();
    }




}

