package treetagger;

import constant_field.FoldName;
import org.annolab.tt4j.TokenHandler;
import org.annolab.tt4j.TreeTaggerWrapper;
import read_write_file.ReadFileController;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Tree Tagger.
 *
 * @version 1.0 2017年10月24日
 * @author Alex
 *
 */
public class TreeTagger {
    /**
     * Tree Tagger.
     */
    private TreeTaggerWrapper<String> ttw;

    /**
     * Tag Term.
     */
    private String tagging = "";

    /**
     * Save Pos Convert Result.
     */
    private HashMap<String, String> posConverter = new HashMap<String, String>();

    /**
     * System Configure，Tree Tagger Path(Depending On User).
     */
    public TreeTagger() {
        ttw = new TreeTaggerWrapper<String>();
        // 必須下載 tree tagger ， 下載完後必須加入至環境變數，以下為
        System.setProperty("treetagger.home", FoldName.TREE_TAGGER_PATH);
        // Tree Tagger POS tagging 所對應到的詞性類型先儲存起來
        try {
            ReadFileController readFileController =
                    new ReadFileController(FoldName.POS_TAGGING_CONVERTER + FoldName.FILE_EXTENSION);
            for (String pos : readFileController.getLineList()) {
                posConverter.put(pos.split(":")[0], pos.split(":")[1] +
                        "=" + pos.split(":")[0]);
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    /**
     * Tree Tagger.
     * @param sentence sentence
     * @throws Exception if anything goes wrong when tree tagger
     * @return tag result
     */
    private String treeTagger(String sentence) throws Exception {
        tagging = "";
        List<String> input = Arrays.asList(sentence.split(" "));
        try {
            ttw.setModel(FoldName.LANGUAGE_MODEL);
            ttw.setHandler(new TokenHandler<String>() {
                public void token(String token, String pos, String lemma) {
                    tagging += token + FoldName.SPLIT + posConverter.get(pos) + FoldName.SPLIT + lemma + " ";
                }
            });
            ttw.process(input);
        } finally {
            ttw.destroy();
        }
        return tagging.trim();
    }

    /**
     * Part Of Speech Tagging Result.
     * @param sentence sentence
     * @throws Exception if anything goes wrong when covert
     * @return tree tag result
     */
    public String getPOSTag(String sentence) throws Exception {
        return treeTagger(sentence);
    }
}
