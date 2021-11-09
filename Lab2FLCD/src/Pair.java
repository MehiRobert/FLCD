public class Pair<T,S> {
    private final T first;
    private final S second;

    public T getFirst() {
        return this.first;
    }

    public S getSecond() {
        return this.second;
    }

    public Pair(T first, S second)
    {

        this.first = first;
        this.second = second;
    }

    @Override
    public String toString()
    {
        return "(" + this.first.toString() + ", " + this.second.toString() + ")";
    }

    @Override
    public int hashCode() {
        return 31;
    }


    @Override
    public boolean equals(Object o)
    {
        if(! (o instanceof Pair))
            return false;
        Pair pair = (Pair) o;

        return (pair.getFirst().equals(this.first) && pair.getSecond().equals(this.second));
    }
}
