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
}
