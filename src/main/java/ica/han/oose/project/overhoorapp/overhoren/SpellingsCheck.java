package ica.han.oose.project.overhoorapp.overhoren;

/**
 * check if the spelling is correct
 *
 * @author Roy van Keijsteren
 * @version 1.0
 * @since 4/28/2015
 */
public class SpellingsCheck {

    private SpellingsCheck() {

    }

    public static boolean checkAnswer(String wordOne, String wordTwo, int mistakesAllowed) {

        if (mistakesAllowed > 2)
            mistakesAllowed = 2;
        else if (mistakesAllowed < 0)
            mistakesAllowed = 0;

        if (wordOne.length() == wordTwo.length()) { // if the words are the same length
            return calculateDifferences(wordOne, wordTwo) <= mistakesAllowed;

        } else {
            if (wordOne.length() < wordTwo.length()) { // if first word is not long enough
                if ((wordTwo.length() - wordOne.length()) > mistakesAllowed) {
                    return false;
                } else {
                    if (wordTwo.length() - wordOne.length() == 1) {
                        if (checkValidationWhileRemovingOneCharacter(wordTwo, wordOne, mistakesAllowed))
                            return true;
                    } else {
                        if (checkValidationWhileRemovingTwoCharacters(wordTwo, wordOne))
                            return true;
                    }
                }
            } else { // if first word is too long.
                if ((wordOne.length() - wordTwo.length()) > mistakesAllowed) {
                    return false;
                } else {
                    if (wordOne.length() - wordTwo.length() == 1) {
                        if (checkValidationWhileRemovingOneCharacter(wordOne, wordTwo, mistakesAllowed))
                            return true;
                    } else {
                        if (checkValidationWhileRemovingTwoCharacters(wordOne, wordTwo))
                            return true;
                    }
                }
            }
            return false;
        }
    }

    private static boolean checkValidationWhileRemovingOneCharacter(String wordOne, String wordTwo, int mistakesAllowed) {
        for (int i = 0; i < wordOne.length(); i++) {
            StringBuilder letterRemover = new StringBuilder(wordOne);
            letterRemover.deleteCharAt(i);
            if (calculateDifferences(letterRemover.toString(), wordTwo) <= mistakesAllowed) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkValidationWhileRemovingTwoCharacters(String wordOne, String wordTwo) {
        for (int i = 0; i < wordOne.length(); i++) {
            StringBuilder letterRemoverOne = new StringBuilder(wordOne);
            letterRemoverOne.deleteCharAt(i);
            for (int j = 0; j < wordOne.length() - 1; j++) {
                StringBuilder letterRemoverTwo = letterRemoverOne;
                letterRemoverTwo.deleteCharAt(j);
                if (letterRemoverOne.toString().equals(wordTwo)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks the number of differences between two strings.
     *
     * @param word1 The first string
     * @param word2 The second string
     * @return The number of differences between two strings
     */
    public static int calculateDifferences(String word1, String word2) {
        final String longerWord = word1.length() >= word2.length() ? word1 : word2;
        final String shorterWord = longerWord.equals(word1) ? word2 : word1;
        final char[] word1Chars = longerWord.toCharArray();
        final char[] word2Chars = shorterWord.toCharArray();
        int tempCounter = 0;
        for (int i = 0; i < longerWord.length(); i++) {
            if (i >= word2Chars.length) {
                tempCounter++;
            } else {
                if (word1Chars[i] != word2Chars[i] && !isSwappedButSame(i, longerWord, shorterWord)) {
                    tempCounter++;
                }
            }
        }
        return tempCounter;
    }

    /**
     * Makes it so that it doesn't count swapped characters as 2 mistakes.
     * Example:
     * word1 is Hello
     * word2 is Hlelo
     * result: 1
     *
     * @param idx   The index to check swapped characters.
     * @param word1 The first string
     * @param word2 The second string
     * @return <tt>true</tt> if the characters are swapped, <tt>false</tt> if they are not.
     */
    private static boolean isSwappedButSame(int idx, String word1, String word2) {
        try {
            return idx <= word2.length() - 1 && word1.charAt(idx) == word2.charAt(idx + 1) && word1.charAt(idx + 1) == word2.charAt(idx);
        } catch (StringIndexOutOfBoundsException e) {
            return false;
        }
    }
}
