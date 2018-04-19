import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by RomanG on 19.02.2018.
 */
public class DictionaryModelTest {
    DictionaryModel testmodel;


    @org.junit.Test
    public void addCharacter() {
        testmodel=new DictionaryModel();
        testmodel.addCharacter('2');
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("[","a","]",":","[a]","b","c"));
        List actual = testmodel.getMessage();
        assertTrue(actual.containsAll(expected));
        testmodel=new DictionaryModel();
        testmodel.addCharacter('3');
        expected = new ArrayList<>(Arrays.asList("[","d","]",":","[d]","e","f"));
        actual = testmodel.getMessage();
        assertTrue(actual.containsAll(expected));
        testmodel.addCharacter('2');
        actual = testmodel.getMessage();
        expected = new ArrayList<>(Arrays.asList("[","fa","]",":","[fa]","ea","fb", "da", "eb", "fc", "ec", "db", "dc"));
        assertTrue(actual.containsAll(expected));

    }

    @org.junit.Test
    public void removeLastCharacter() {
        testmodel = new DictionaryModel();
        testmodel.addCharacter('2');
        testmodel.acceptWord();
        testmodel.addCharacter('2');
        testmodel.acceptWord();
        assertEquals(new ArrayList<>(Arrays.asList("a","a")),testmodel.getMessage());
        testmodel.addCharacter('3');
        testmodel.removeLastCharacter();
        testmodel.removeLastCharacter();
        assertEquals(new ArrayList<>(Arrays.asList("a")),testmodel.getMessage());
    }

    @org.junit.Test
    public void nextlastMatch(){
        testmodel=new DictionaryModel();
        testmodel.addCharacter('2');

        ArrayList<String> expected = new ArrayList<>(Arrays.asList("[","a","]",":","[a]","b","c"));
        List actual = testmodel.getMessage();
        assertEquals(expected,actual);

        testmodel.nextMatch();
        expected = new ArrayList<>(Arrays.asList("[","b","]",":","a","[b]","c"));
        actual = testmodel.getMessage();
        assertEquals(expected,actual);

        testmodel.nextMatch();
        expected = new ArrayList<>(Arrays.asList("[","c","]",":","a","b","[c]"));
        actual = testmodel.getMessage();
        assertEquals(expected,actual);

        testmodel.nextMatch();
        expected = new ArrayList<>(Arrays.asList("[","a","]",":","[a]","b","c"));
        actual = testmodel.getMessage();
        assertEquals(expected,actual);

        testmodel.lastMatch();
        expected = new ArrayList<>(Arrays.asList("[","c","]",":","a","b","[c]"));
        actual = testmodel.getMessage();
        assertEquals(expected,actual);


    }

    @org.junit.Test
    public void acceptWord(){
        testmodel= new DictionaryModel();
        testmodel.acceptWord();
       assertEquals(testmodel.getMessage(),new ArrayList<>());
       testmodel.addCharacter('2');
       testmodel.acceptWord();
       ArrayList<String> expected = new ArrayList<>(Arrays.asList("a"));
       assertEquals(expected, testmodel.getMessage());
    }

}