package com.keyin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class SuggestionEngineTest {
    private final SuggestionEngine testEngine = new SuggestionEngine();

    @BeforeEach
    public void setUp() throws Exception {
        testEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").toURI()));
    }

    @Test
    public void testWrongLetterOrder() {
        Assertions.assertTrue(testEngine.generateSuggestions("kristne").contains("kristen"));
    }

    @Test
    public void testMultipleSuggestions() {
        String suggestions = testEngine.generateSuggestions("justn");
        Assertions.assertTrue(suggestions.contains("justin"), "Suggestions: " + suggestions);
    }

    @Test
    public void testLimit10() {
        Assertions.assertTrue(testEngine.generateSuggestions("zeus").split("\n").length <= 10);
    }

    @Test
    public void testCommonMisspelling() {
        String word = "malach";
        String expected = "malachi";
        String actual = testEngine.generateSuggestions(word);
        Assertions.assertTrue(actual.contains(expected), "Failed for word: " + word + ", suggestions: " + actual);
    }

    @Test
    public void testWordLookupKristen() {
        Assertions.assertTrue(testEngine.getWordSuggestionDB().containsKey("kristen"));
    }

    @Test
    public void testWordLookupHello() {
        Assertions.assertTrue(testEngine.getWordSuggestionDB().containsKey("hello"));
    }

    @Test
    public void testNoSuggestions() {
        String word = "qwertyuiop";
        String expected = "";
        String actual = testEngine.generateSuggestions(word);
        Assertions.assertEquals(expected, actual, "Failed for word: " + word + ", suggestions: " + actual);
    }

    @Test
    public void testGenerateSuggestionsForEmptyWord() {
        String word = "";
        String expected = "";
        String actual = testEngine.generateSuggestions(word);
        Assertions.assertEquals(expected, actual, "Failed for word: " + word + ", suggestions: " + actual);
    }

    @Test
    public void testSupercalafragelistic() {
        String word = "supercalafragelistic";
        String expected = "";
        String actual = testEngine.generateSuggestions(word);
        Assertions.assertEquals(expected, actual, "Failed for word: " + word + ", suggestions: " + actual);
    }
}
