import java.util.ArrayList;
import java.util.List;

public class SimpleIndex implements Index {
    /**
     * The list of websites stored in the index.
     */
    private List<Website> simpleIndexed;
    /**
     * The build method processes a list of websites into the index data structure.
     * @param sites The list of websites that should be indexed
     */
    public void build(List<Website> sites)
    {
        this.simpleIndexed = sites;
    }

    /**
     * Given a query string, returns a list of all websites that contain the query.
     * @param query The query
     * @return the list of websites that contains the query word.
     */
    public List<Website> lookup(String query)
    {
        //long startTime = System.nanoTime();

        List<Website> simpleInd = new ArrayList<Website>();

        for (Website w: simpleIndexed) {
            if (w.containsWord(query)) {
                simpleInd.add(w);
                //System.out.println("query found on " + w.getUrl());
                }
            }
        //long endTime = System.nanoTime();

        if (!simpleInd.isEmpty()) {
            //System.out.println("Simple Index execution time: " + (endTime - startTime) + " nano - seconds!");
        }
    return simpleInd;
    }
}
