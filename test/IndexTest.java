import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IndexTest {
    Index simpleIndex = null;
    Index invertedIndex = null;
    List<Website> sites = new ArrayList<Website>();

    @BeforeEach
    void setUp() {
        sites.add(new Website("example1.com", "example1", Arrays.asList("word1", "word2", "word1")));
        sites.add(new Website("example2.com", "example2", Arrays.asList("word2", "word3")));
        simpleIndex = new SimpleIndex();
        simpleIndex.build(sites);
        invertedIndex = new InvertedIndex();
        //invertedIndex.build(sites);
    }

    @AfterEach
    void tearDown() {
        simpleIndex = null;
        invertedIndex = null;
    }

    @Test
    void buildSimpleIndex() {
        assertEquals("SimpleIndex{sites=[Website{title='example1', url='example1.com', words=[word1, word2, word1]}, Website{title='example2', url='example2.com', words=[word2, word3]}]}", simpleIndex.toString());
    }

    @Test
    void buildInvertedIndex(){
        assertEquals("SimpleIndex{sites=[Website{title='example1', url='example1.com', words=[word1, word2, word1]}, Website{title='example2', url='example2.com', words=[word2, word3]}]}", invertedIndex.toString());
    }

    @Test
    void lookupSimpleIndex() {
        lookup(simpleIndex);
    }

    private void lookup(Index index) {
        assertEquals(1, index.lookup("word1").size());
        assertEquals(2, index.lookup("word2").size());
        assertEquals(0, index.lookup("word4").size());
    }

/*    private void checkInvertedIndex(){
        assertEquals();
    }*/



}