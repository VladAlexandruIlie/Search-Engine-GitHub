import java.util.ArrayList;
import java.util.List;

/**
 * A website is the basic entity of the search engine. It has a url, a title, and a list of words.
 *
 * @author Martin Aumüller
 */
public class Website {

    /**
     * title = the website's title
     * url = the website's url
     * words = a list of words storing the words on the website
     */
    private String title;
    private String url;
    private List<String> words;

    /**
     * Creates a {@code Website} object from a url, a title, and a list of words
     * that are contained on the website.
     * @param url the website's url
     * @param title the website's title
     * @param words the website's list of words
     */
    public Website(String url, String title, List<String> words) {
        this.url = url;
        this.title = title;
        this.words = words;
    }

    /**
     * Returns the website's title.
     * @return the website's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the website's url.
     * @return the website's url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Return the list of words.
     * @return the list of words.
     */
    public List<String> getWords() {
        return words;
    }

    /**
     * Checks whether a word is present on the website or not.
     *
     * @param word = the query word
     * @return True, if the word is present on the website.
     *         False, if it is not present on the website.
     */
    public Boolean containsWord(String word)
    {
        return words.contains(word);
    }

    /**
     * Returns a custom formatted string containing the title, url and all words.
     * @return a string containing the title, url and all words.
     */
    @Override
    public String toString() {
        return "Website{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", words=" + words +
                '}';
    }
}
