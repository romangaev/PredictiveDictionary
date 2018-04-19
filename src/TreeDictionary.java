

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Roman Gaev on 15.02.2018.
 * This class represents a dictionary with a Tree structure.
 * It's able to return not only the whole words for signature but also the prefixes of probable words starting with this signature.
 */
public class TreeDictionary implements Dictionary {
    //Using array to store branches of the tree and HashSet to collect corresponding words
    private TreeDictionary[] branches;
    private HashSet<String> nodePref;


    /**
     * Constructor for a root TreeDictionary
     *
     * @param path - filepath
     */
    public TreeDictionary(String path) {
        branches = new TreeDictionary[8];
        nodePref = new HashSet<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String contentLine;
            while ((contentLine = br.readLine()) != null) {
                if (PredictivePrototype.isValidWord(contentLine)) {

                    String lowCase = contentLine.toLowerCase();

                    String sign = PredictivePrototype.wordToSignature(lowCase);

                    this.addWord(lowCase, sign, 0);


                }

            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Constructor for a branch TreeDictionary
     */
    public TreeDictionary() {
        branches = new TreeDictionary[8];
        nodePref = new HashSet<>();
    }

    /**
     * addWord method for recursive word addition to a dictionary
     *
     * @param word      - whole word
     * @param signature - signature of the word
     * @param order     - level of tree(starting from 0)
     */
    private void addWord(String word, String signature, int order) {
        int address = Character.getNumericValue(signature.charAt(order)) - 2;
        if (this.branches[address] == null) this.branches[address] = new TreeDictionary();

        TreeDictionary nextBranch = this.branches[address];
        nextBranch.getNodePref().add(word);

        if ((order + 1) != word.length()) {
            nextBranch.addWord(word, signature, ++order);
        }


    }

    /**
     * findAndGet method for recursive search of word Set corresponding to the signature
     *
     * @param signature - signature of the word
     * @param order     - level of tree(starting from 0)
     * @return Set of corresponding words
     */
    private Set<String> findAndGet(String signature, int order) {
        int address = Character.getNumericValue(signature.charAt(order)) - 2;
        TreeDictionary nextBranch = this.branches[address];
        if (nextBranch == null) return new HashSet<String>();
        if ((order + 1) == signature.length()) {
            return nextBranch.getNodePref().parallelStream().map(x -> x.substring(0, signature.length())).collect(Collectors.toSet());
        } else {
            return nextBranch.findAndGet(signature, ++order);
        }
    }

    /**
     * getNodePref - getter method for Set of whole words fully or partially corresponding to node signature
     *
     * @return Set of words stored in the node
     */
    public Set<String> getNodePref() {
        return nodePref;
    }

    /**
     * signatureToWords - returns set of probable words or prefixes for given signature
     *
     * @param signature - given signature
     * @return Set of words and prefixes
     */
    @Override
    public Set<String> signatureToWords(String signature) {
        if (signature.length()==0) return new HashSet<>();
        return this.findAndGet(signature, 0);
    }
}
