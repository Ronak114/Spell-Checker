import java.util.*;
public class SpellingChecker {

    // A dictionary of correctly spelled words
    private static Set<String> dictionary = new HashSet<>();
    
    public static void main(String[] args) {
        // Load the dictionary with words from a file or database
        // For this example, we'll just use a hardcoded set of words
        dictionary.add("Aryan");
        dictionary.add("Ronak");
        dictionary.add("Shraddha");
        dictionary.add("Sharvari");
        dictionary.add("ITPM");
        dictionary.add("Presentation");

        // Get user input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a word to check: ");
        String word = scanner.nextLine();
        
        // Check if the word is in the dictionary
        if (dictionary.contains(word)) {
            System.out.println("The word \"" + word + "\" is spelled correctly.");
        } else {
            // If not, suggest corrections
            System.out.println("The word \"" + word + "\" is spelled incorrectly.");
            List<String> suggestions = suggestCorrections(word);
            if (suggestions.isEmpty()) { 
                System.out.println("No suggestions found.");
            } else {
                System.out.println("Suggestions:");
                for (String suggestion : suggestions) {
                    System.out.println(suggestion);
                }
            }
        }
    }
    
    // Returns a list of suggested corrections for a misspelled word
    private static List<String> suggestCorrections(String word) {
        List<String> suggestions = new ArrayList<>();
        for (String candidate : dictionary) {
            if (distance(word, candidate) <= 2) {
                suggestions.add(candidate);
            }
        }
        Collections.sort(suggestions);
        return suggestions;
    }
    
    // Computes the Levenshtein distance between two strings
    private static int distance(String s1, String s2) {
        int[] prevRow = new int[s2.length() + 1];
        for (int j = 0; j < prevRow.length; j++) {
            prevRow[j] = j;
        }
        for (int i = 1; i <= s1.length(); i++) {
            int[] currRow = new int[s2.length() + 1];
            currRow[0] = i;
            for (int j = 1; j <= s2.length(); j++) {
                int insertion = prevRow[j] + 1;
                int deletion = currRow[j - 1] + 1;
                int substitution = prevRow[j - 1] + (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1);
                currRow[j] = Math.min(Math.min(insertion, deletion), substitution);
            }
            prevRow = currRow;
        }
        return prevRow[s2.length()];
    }
}