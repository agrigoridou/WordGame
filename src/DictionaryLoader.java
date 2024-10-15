import java.io.*;
import java.util.*;

public class DictionaryLoader {
    public static Set<String> loadDictionary(String filename) throws IOException {
        Set<String> dictionary = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                dictionary.add(line.trim().toUpperCase()); // Αποθηκεύουμε τις λέξεις σε κεφαλαία
            }
        }
        return dictionary;
    }
}
