package constant_field;

/**
 * 主要儲存資料夾及檔案名稱.
 */
public class FoldName {
    public static final String ORIGINAL_AND_CORRECT_DATA = "OriginalAndCorrectData/";
    public static final String ORIGINAL_ARTICLE = "Original_Article_";
    public static final String CORRECT_ARTICLE = "Correct_Article_";
    public static final String ORIGINAL_ARTICLE_TAGGING_SPLIT_SENTENCE = "Original_Article_Tagging_SplitSentence_";
    public static final String CORRECT_ARTICLE_TAGGING_SPLIT_SENTENCE = "Correct_Article_Tagging_SplitSentence_";
    public static final String RESOURCE = "src/main/resources/";
    public static final String POS_TAGGING_CONVERTER = "pos_tagging_converter";
    public static final String N_EXCEPTION = "N_Exception";
    public static final String ADV_EXCEPTION = "ADV_Exception";
    public static final String VLINF_EXCEPTION = "VLINF_Exception";
    public static final String DATABASE_CONFIGURATION = "databaseConfiguration.properties";
    public static final String FILE_EXTENSION = ".txt";
    // 分割句子
    public static final String SENTENCE_FLAG = "$";
    // 分割 Mabel#N#Mabel(字串、詞性、字根)
    public static final String SPLIT = "#";
    // Set TreeTagger Path
    public static final String TREE_TAGGER_PATH = "D:/TreeTagger";
    public static final String LANGUAGE_MODEL = "/spanish-utf8.par:iso8859-1";
    // Set Article ID Start and Article ID End
    public static final int ARTICLE_ID_START = 1;//1518
    public static final int ARTICLE_ID_END = 2419;//2419
}
