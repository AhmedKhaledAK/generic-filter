package GenericFilter;

public class GenericFilterFactory {
    public static GenericFilter getInstance() {
        return new GenericFilterImpl();
    }
}