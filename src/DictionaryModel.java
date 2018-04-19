
import java.util.*;

/**
 * Created by RomanG on 15.02.2018.
 */
public class DictionaryModel extends Observable implements DictionaryModelInterface {
    private TreeDictionary treeDic;
    private StringBuffer sb = new StringBuffer();
    private int curIndex;
    private ArrayList<String> message = new ArrayList();
    String[] matches;

    public DictionaryModel(String dictionaryFile) {
        treeDic = new TreeDictionary(dictionaryFile);

    }

    public DictionaryModel() {
        treeDic = new TreeDictionary(DictionaryModel.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/words.txt");
    }


    @Override
    public List<String> getMessage() {

        List<String> result = new ArrayList<>(message);
        if (matches!=null&&matches.length > 0) {
            result.add("[");
            result.add(matches[curIndex]);
            result.add("]");
            result.add(":");

            for (int i = 0; i < matches.length; i++) {
                if (i == curIndex) {
                    result.add("[" + matches[i] + "]");
                } else result.add(matches[i]);
            }
        }
        return result;
    }

    @Override
    public void addCharacter(char key) {
        sb.append(key);
        matches = treeDic.signatureToWords(sb.toString()).stream().toArray(String[]::new);
        if (matches.length > 0) curIndex = 0;
        setChanged();
        notifyObservers();
    }

    @Override
    public void removeLastCharacter() {
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            matches = treeDic.signatureToWords(sb.toString()).stream().toArray(String[]::new);
            curIndex = 0;

        } else if (message.size() > 0) message.remove(message.size() - 1);
        setChanged();
        notifyObservers();

    }

    @Override
    public void nextMatch() {
        if (matches != null && matches.length > 0) {
            int len = matches.length;
            curIndex = (++curIndex % len + len) % len;
            setChanged();
            notifyObservers();
        }
    }

    public void lastMatch() {
        if (matches != null && matches.length > 0) {
            int len = matches.length;
            curIndex = (--curIndex % len + len) % len;
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public void acceptWord() {
        if (matches != null && matches.length > 0) {
            message.add(matches[curIndex]);
            sb = new StringBuffer();
            matches = new String[0];
            setChanged();
            notifyObservers();
        }
    }
}
