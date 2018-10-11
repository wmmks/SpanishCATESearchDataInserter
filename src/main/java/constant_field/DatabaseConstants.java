package constant_field;

/**
 * 主要儲存資料庫中的資料表以及欄位名稱.
 */
public class DatabaseConstants {
    // 資料表名稱
    public static final String WORDS_TABLE = "words_table";
    public static final String ORIGINAL_WORDS_INDEX_TABLE = "original_words_Index_table";
    public static final String ORIGINAL_SENTENCES_CONTENT = "original_sentences_content";
    public static final String CORRECTED_WORDS_INDEX_TABLE = "corrected_words_Index_table";
    public static final String CORRECTED_SENTENCES_CONTENT = "corrected_sentences_content";
    public static final String ARTICLE_CONTENT = "articles_content";
    // 欄位名稱
    public static final String ID = "id";
    public static final String SENTENCE_ID = "sentence_id";
    public static final String WORD_ID = "word_id";
    public static final String POSITION = "position";
    public static final String PRIOR_SIGN = "prior_sign";
    public static final String POST_SIGN = "post_sign";
    public static final String TEXT = "text";
    public static final String POS = "pos";
    public static final String LEMMA = "lemma";
    public static final String ARTICLE_ID = "article_id";
    public static final String SYSTEM_TYPE = "system_type";
    public static final String ORIGINAL_ARTICLE_TEXT = "original_article_text";
    public static final String CORRECTED_ARTICLE_TEXT = "corrected_article_text";
}
