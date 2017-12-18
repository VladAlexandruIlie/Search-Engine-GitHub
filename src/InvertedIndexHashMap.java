import java.util.HashMap;

/**
 * The InvertedIndexHashMap class extends the InvertedIndex class
 * Extends the inverted index with a constructor that uses a different type of Map
 */
public class InvertedIndexHashMap extends InvertedIndex{

    /**
     * The InvertedIndexHashMap constructor initializes the inverted index as a Hash Map
     */
    public InvertedIndexHashMap()
    {
        this.invertedIndex = new HashMap<>();
    }

}
