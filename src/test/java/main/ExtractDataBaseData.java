package main;

import constant_field.FoldName;
import other_column_extraction.ArticleIDAndOriginalAndCorrectColumnExtraction;
import read_write_file.WriteFileController;

/**
 * Extract DB DATA about Original Article and Correct Article(Data In OriginalAndCorrectData Fold).
 *
 * @version 1.0 2017年10月25日
 * @author Alex
 *
 */
public class ExtractDataBaseData {
    /**
     * Test
     */
    public static void main(final String[] args) {
        // From DB extract Original And Correct Article Content.
        ArticleIDAndOriginalAndCorrectColumnExtraction
                articleIDAndOriginalAndCorrectColumnExtraction =
                new ArticleIDAndOriginalAndCorrectColumnExtraction();
        articleIDAndOriginalAndCorrectColumnExtraction.setOriginalAndCorrectColumn();
        WriteFileController writeFileController;
        for (int i = 0; i < articleIDAndOriginalAndCorrectColumnExtraction.size(); i++) {
            try {
                writeFileController = new WriteFileController();
                writeFileController.writeDataIntoFile(FoldName.ORIGINAL_AND_CORRECT_DATA + FoldName.ORIGINAL_ARTICLE
                                + articleIDAndOriginalAndCorrectColumnExtraction.getArticleID().
                                get(i), articleIDAndOriginalAndCorrectColumnExtraction.
                                getOriginalList().get(i));
                writeFileController = new WriteFileController();
                writeFileController.writeDataIntoFile(FoldName.ORIGINAL_AND_CORRECT_DATA + FoldName.CORRECT_ARTICLE
                                + articleIDAndOriginalAndCorrectColumnExtraction.getArticleID().
                                get(i), articleIDAndOriginalAndCorrectColumnExtraction.
                                getCorrectList().get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
