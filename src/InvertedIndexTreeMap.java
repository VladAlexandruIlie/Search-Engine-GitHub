import java.util.TreeMap;

/**
 * The InvertedIndexTreeMap class extends the InvertedIndex class
 * Extends the inverted index with a constructor that uses a different type of Map
 */
public class InvertedIndexTreeMap extends InvertedIndex{

    /**
     * The InvertedIndexTreeMap constructor initializes the inverted index as a Tree Map
     */
    public InvertedIndexTreeMap()
    {
        this.invertedIndex = new TreeMap<>();
    }

}
