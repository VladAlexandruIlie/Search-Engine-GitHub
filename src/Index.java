import java.util.List;
/**
 * The index data structure provides a way to build an index from
 * a list of websites. It allows to lookup the websites that contain a query word.
 *
 * The index interface is implemented in two ways offering different functionality:
 *      1. Simple Index
 *      2. Inverted Index
 */
public interface Index {
    /**
     * The build method processes a list of websites into the index data structure.
     * @param sites The list of websites that should be indexed
     */
     void build(List<Website> sites);

    /**
     * Given a query string, returns a list of all websites that contain the query.
     * @param query The query
     * @return the list of websites that contains the query word.
     */
    List<Website> lookup(String query);

    List<Website> lookupAll();

}
