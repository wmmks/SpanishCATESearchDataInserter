package other_column_extraction;

import constant_field.DatabaseConstants;
import database.MysqlDatabaseController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Article ID And Original And Correct Column Extraction.
 *
 * @version 1.0 2017年10月23日
 * @author Alex
 *
 */
public class ArticleIDAndOriginalAndCorrectColumnExtraction {
    /**
     * Connect DB.
     */
    private MysqlDatabaseController mysqlDatabaseController;

    /**
     * Article ID.
     */
    private ArrayList<String> articleID;

    /**
     * systemType List.
     */
    private ArrayList<String> systemType;

    /**
     * Original Content List.
     */
    private ArrayList<String> originalList;

    /**
     * Correct Content List.
     */
    private ArrayList<String> correctList;

    /**
     * Constructor.
     */
    public ArticleIDAndOriginalAndCorrectColumnExtraction() {
        mysqlDatabaseController = new MysqlDatabaseController();
        articleID = new ArrayList<String>();
        systemType = new ArrayList<String>();
        originalList = new ArrayList<String>();
        correctList = new ArrayList<String>();
    }

    /**
     * Extract Original And Correct Column Content.
     */
    public void setOriginalAndCorrectColumn() {
        ResultSet resultSet = mysqlDatabaseController.execSelect
                ( DatabaseConstants.ID + "," + DatabaseConstants.SYSTEM_TYPE + "," +
                                DatabaseConstants.ORIGINAL_ARTICLE_TEXT + "," +
                                DatabaseConstants.CORRECTED_ARTICLE_TEXT,
                        DatabaseConstants.ARTICLE_CONTENT, "1");
        try {
            while (resultSet.next()) {
                articleID.add(resultSet.getString(1));
                systemType.add(resultSet.getString(2));
                originalList.add(resultSet.getString(3));
                correctList.add(resultSet.getString(4));
            }
        } catch (SQLException e) {
            e.getErrorCode();
        }
    }

    /**
     * Get Article ID List.
     * @return article id
     */
    public ArrayList<String> getArticleID() {
        return articleID;
    }

    /**
     * Get System type List.
     * @return system type
     */
    public ArrayList<String> getSystemType() {
        return systemType;
    }

    /**
     * Get Original Content List.
     * @return original list
     */
    public ArrayList<String> getOriginalList() {
        return originalList;
    }

    /**
     * Get Correct Content List.
     * @return correct list
     */
    public ArrayList<String> getCorrectList() {
        return correctList;
    }

    /**
     * Get Array List Size.
     * @return article id size
     */
    public int size() {
        return articleID.size();
    }
}
