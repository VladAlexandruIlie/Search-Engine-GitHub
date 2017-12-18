import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHelper {
    /**
     *  parseFile is the only method in the FileHelper class and has the role of parsing / reading and interpreting
     *      the 3 text files "enwiki-tiny", "enwiki-small" and "enwiki-medium" which represent the data sample sets.
     *  The method reads the given document line by line analyses each one for semantic components.
     *  While reading the document, the method checks whether or not it has all the information needed to store a
     *      website as a valid entry in the list of websites "sites" - which will be returned in the end
     *  The program considers that it has all the information needed for a websites, when it reaches a new line starting
     *      with the url of a website and checks if the method variables "url", "title" and "listOfWords" have been
     *      initialized. Unless they are null, a new Website object is created and stored into "sites"
     *
     * @param filename the name of the file which will be
     * @return a list of all websites found in in the file
     */
    public static List<Website> parseFile(String filename) {
        List<Website> sites = new ArrayList<Website>();
        String url = null, title = null;
        List<String> listOfWords = null;

        try {
            Scanner sc = new Scanner(new File(filename), "UTF-8");
            while (sc.hasNext()) {
                String line = sc.nextLine();
                if (line.startsWith("*PAGE:")) {
                    /*
                     * Assignment 2: Modifying the basic Search Engine
                     * A website is created only when all 3 of it's components "url", "title" and "listOfWords"
                     * hold data.
                     */
                    if (url != null && title != null && listOfWords!=null)
                    {
                        sites.add(new Website(url, title, listOfWords));
                    }
                    // new website starts
                    url = line.substring(6);
                    title = null;
                    listOfWords = null;
                    }
                else if (title == null) {
                    title = line;
                    }
                else {
                    // and that's a word!
                    if (listOfWords==null) {
                        listOfWords = new ArrayList<String>();
                    }
                    listOfWords.add(line);
                }
            }
            // checks if the last potential website has all required components
            if (url != null && title != null && listOfWords!=null) {
                sites.add(new Website(url, title, listOfWords));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't load the given file");
           e.printStackTrace();
        }

        return sites;
    }
}
