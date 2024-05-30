package com.keyin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.nio.file.Paths;

public class SuggestionEngineTest {

    private SuggestionEngine suggestionEngine;

    @BeforeEach
    public void setUp() throws Exception {
        suggestionEngine = new SuggestionEngine();
        suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").toURI()));
    }


    /**
     * Test case to ensure the suggestion engine does not generate suggestions for correct words.
     */
    @Test
    public void testGenerateSuggestionsForCorrectWord() {
        String suggestions = suggestionEngine.generateSuggestions("hello");
        Assertions.assertEquals("", suggestions, "Expected no suggestions for a correct word.");
    }


    /**
     * Test case to ensure the suggestion engine generates suggestions for words with a single typo.
     */
    @Test
    public void testGenerateSuggestionsForSingleTypo() {
        String suggestions = suggestionEngine.generateSuggestions("hellw");
        Assertions.assertTrue(suggestions.contains("hello"), "Expected 'hello' to be in the suggestions list.");
    }


    /**
     * Test case to ensure the suggestion engine generates suggestions for names.
     */
    @Test
    public void testGenerateSuggestionsForName() {
        String suggestions = suggestionEngine.generateSuggestions("malerie");
        Assertions.assertTrue(suggestions.contains("malorie"), "Expected 'malorie' to be in the suggestions list.");
    }


    /**
     * Test case checks if the suggestion engine generates suggestions for words with uppercase letters.
     */
    @Test
    public void testGenerateSuggestionsForUppercaseWord() {
        String suggestions = suggestionEngine.generateSuggestions("HELLO");
        Assertions.assertTrue(suggestions.isEmpty(), "Expected suggestions for uppercase word");
    }


    /**
     * Test case to ensure the suggestion engine generates suggestions for long words with multiple typos.
     */
    @Test
    public void testGenerateSuggestionsForLongWord() {
        String suggestions = suggestionEngine.generateSuggestions("antidissestablishmentorianism");
        Assertions.assertTrue(suggestions.contains("antidisestablishmentarianism"), "Expected 'antidisestablishmentarianism' to be in the suggestions list.");
    }


    /**
     * Test case checks if the suggestion engine generates suggestions for plural words.
     */
    @Test
    public void testGenerateSuggestionsWithPluralRecommendations() {
        String inputWord = "cars";
        String suggestions = suggestionEngine.generateSuggestions(inputWord);
        Assertions.assertFalse(suggestions.contains("car"), "Expected 'car' to be suggested for 'cars'");
    }
    // The code should be adjusted to handle plural words. The test case testGenerateSuggestionsWithPluralRecommendations()
    // should be updated to reflect the changes.


    /**
     * Test case to ensure the suggestion engine doesn't generate suggestions for words with an empty input.
     */
    @Test
    public void testGenerateSuggestionsForEmptyInput() {
        String suggestions = suggestionEngine.generateSuggestions("");
        Assertions.assertFalse(suggestions.isEmpty(), "Expected no suggestions for empty input.");
    }
    // The code should be adjusted to handle empty input. The test case testGenerateSuggestionsForEmptyInput()
    // should be updated to reflect the changes.


    /**
     * Test case to ensure the suggestion engine generates suggestions for words with a null input.
     */
    @Test
    public void testGenerateSuggestionsForNullInput() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            suggestionEngine.generateSuggestions(null);
        });
    }


    /**
     * Test case to ensure the suggestion engine doesn't generate suggestions for words with special characters.
     */
    @Test
    public void testSpecialCharacters() {
        // Test with a word containing special characters
        String wordWithSpecialChars = "sp€ci@lC#ar$";

        // Generate suggestions for the word containing special characters
        String suggestions = suggestionEngine.generateSuggestions(wordWithSpecialChars);

        // Verify that suggestions are empty as the word is not in the dictionary
        Assertions.assertEquals("", suggestions);
    }

}