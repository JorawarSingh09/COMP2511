package scrabble;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;

public class Scrabble {

    private String word;

    private List<String> dictionary = new ArrayList<String>(Arrays.asList("ab", "abe", "able", "ad", "ae", "ae",
            "ah", "al", "ale", "at", "ate", "ba", "bad", "be", "be", "bead", "bed", "bra", "brad", "bread", "bred",
            "cabble", "cable", "ea", "ea", "eat", "eater", "ed", "ha", "hah", "hat", "hate", "hater", "hath", "he",
            "heat", "heater", "heath", "heather", "heathery", "het", "in", "io", "ion", "li", "lin", "lion", "on",
            "program", "ra", "rad", "re", "rea", "read", "red", "sa", "sat", "scabble", "scrabble", "se", "sea", "seat",
            "seathe", "set", "seth", "sh", "sha", "shat", "she", "shea", "sheat", "sheath", "sheathe", "sheather",
            "sheathery", "sheth", "st", "te")); 

    public Scrabble(String word) {
        this.word = word;
    }

    public int score() {
        if (!dictionary.contains(word)) return 0;

        ArrayList<String> matched = new ArrayList<String>();

        Set<Character> wordLetters = new HashSet<>();
        for (char c : word.toCharArray()) {
            wordLetters.add(c);
        }

        int score = 0;
        for (int i = 0; i < dictionary.size(); i++) {
            String subWord = dictionary.get(i);
            Set<Character> subWordLetters = new HashSet<>();
            for (char c : subWord.toCharArray()) {
                subWordLetters.add(c);
            }
            if (wordLetters.containsAll(subWordLetters) && !matched.contains(dictionary.get(i))) {
                matched.add(subWord);
                score++;
            }
        }
        return score;
    }
}
