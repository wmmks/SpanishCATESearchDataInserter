package main;

import constant_field.DatabaseConstants;
import constant_field.FoldName;
import database.MysqlDatabaseController;
import database.SqlObject;
import read_write_file.ReadFileController;

import java.util.*;

/**
 * Insert Data Into DB about (words_table) and (Original/Correct words_index_table and sentence_content).
 *
 * @version 1.0 2017年10月25日
 * @author Alex
 *
 */
public class InsertDataIntoDB {
    /**
     * Test
     */
    public static void main(String[] argv) {
        MysqlDatabaseController mysqlDatabaseController = new MysqlDatabaseController();
        SqlObject wordsTableSqlObject;
        SqlObject wordsIndexTableSqlObject;
        SqlObject sentenceContentSqlObject;
        // 將資料庫資料初始化
        mysqlDatabaseController.execDelete(DatabaseConstants.WORDS_TABLE, "1");
        mysqlDatabaseController.execDelete(DatabaseConstants.ORIGINAL_WORDS_INDEX_TABLE, "1");
        mysqlDatabaseController.execDelete(DatabaseConstants.ORIGINAL_SENTENCES_CONTENT, "1");
        mysqlDatabaseController.execDelete(DatabaseConstants.CORRECTED_WORDS_INDEX_TABLE, "1");
        mysqlDatabaseController.execDelete(DatabaseConstants.CORRECTED_SENTENCES_CONTENT, "1");
        try {
            Thread.sleep(40000);
        } catch (InterruptedException e) {
            e.getCause();
        }
        System.out.println("Clear Finished...");
        // words_table 資訊，進入迴圈後可以不用重新產生
        int wordID = 1; // word_table word id
        Map<String, Integer> wordsTable = new HashMap<String, Integer>();
        // EX: corrected_words_Index_table
        List<Map<String, String>> wordsIndexTable;
        Map<String, String> wordsIndex;
        int id;
        int sentenceID;
        int position;
        String priorSign;
        String postSign;
        // EX: corrected_sentences_content
        Map<Integer, List<String>> sentenceContent;
        List<String> list;
        List<String> dataInsertList =
                Arrays.asList( FoldName.ORIGINAL_ARTICLE_TAGGING_SPLIT_SENTENCE,
                        FoldName.CORRECT_ARTICLE_TAGGING_SPLIT_SENTENCE);
        for (String dataInsert : dataInsertList) {
            String indexTable = "Words Index Table";
            String content = "Sentence Content";
            if (dataInsert.equals(FoldName.ORIGINAL_ARTICLE_TAGGING_SPLIT_SENTENCE)) {
                indexTable = DatabaseConstants.ORIGINAL_WORDS_INDEX_TABLE;
                content = DatabaseConstants.ORIGINAL_SENTENCES_CONTENT;
            } if (dataInsert.equals(FoldName.CORRECT_ARTICLE_TAGGING_SPLIT_SENTENCE)) {
                indexTable = DatabaseConstants.CORRECTED_WORDS_INDEX_TABLE;
                content = DatabaseConstants.CORRECTED_SENTENCES_CONTENT;
            }
            // 由於資料類型不同(Original or Correct)，故必須重新初始化
            wordsIndexTable = new ArrayList<Map<String, String>>();
            wordsIndex = new HashMap<String, String>();
            id = 1;
            sentenceID = 1;
            position = 0;
            priorSign = "";
            postSign = "";
            sentenceContent = new HashMap<Integer, List<String>>();
            for (int articleID = FoldName.ARTICLE_ID_START; articleID <= FoldName.ARTICLE_ID_END ; articleID++) {
                ReadFileController readFileController;
                try {
                    readFileController = new ReadFileController(FoldName.ORIGINAL_AND_CORRECT_DATA + dataInsert + articleID);
                    //readFileControllerSpace = new ReadFileController(FoldName.ORIGINAL_AND_CORRECT_DATA + dataInsert + articleID + FoldName.SAVE_SPACE);
                } catch (Exception e) {
                    System.out.println(articleID + " Error Information: " + e.toString());
                    continue;
                }
                String[] segSentenceList = readFileController.getLine().split("\\$");
                for (String segSentence : segSentenceList) {
                    StringBuilder sentence = new StringBuilder();
                    String[] seg = segSentence.split(" ");
                    // 存原本句子的內容(顯示在搜尋後的頁面)
                    for (int i = 0; i < seg.length; i++) {
                        String[] word = seg[i].split(FoldName.SPLIT);
                        sentence.append(word[0]);
                        Boolean b = false;
                        if (i != (seg.length - 1)) {
                            String[] nextWord = seg[i + 1].split(FoldName.SPLIT);
                            String[] mark = new String[]{".","?","¿","!","¡","“","”",",",":"};
                            for (String m : mark) {
                                if (nextWord[0].contains(m) || (word[0].contains(m))) {
                                    b = true;
                                }
                            }
                            if (!b || (word[0].equals(",")) || (word[0].equals(":"))) {
                                sentence.append(" ");
                            }
                        }
                    }
                    list = new ArrayList<String>();
                    list.add(Integer.toString(articleID));
                    list.add(sentence.toString());
                    sentenceContent.put(sentenceID, list);
                    for (int i = 0; i < seg.length - 1 ; i++) {
                        if (i == 0) {
                            String[] s = seg[i].split(FoldName.SPLIT);
                            if (s[0].equals("¿") || s[0].equals("¡")) {
                                priorSign = s[0];
                                continue;
                            }
                        } if (i == seg.length - 2) {
                            String[] s = seg[seg.length - 1].split(FoldName.SPLIT);
                            if (s[0].equals(".") || s[0].equals("?") || s[0].equals("!")) {
                                postSign = s[0];
                            }
                        }
                        if (wordsTable.get(seg[i]) == null) {
                            wordsTable.put(seg[i], wordID);
                            wordID ++;
                        }
                        wordsIndex.put(DatabaseConstants.ID, Integer.toString(id));
                        wordsIndex.put(DatabaseConstants.SENTENCE_ID, Integer.toString(sentenceID));
                        wordsIndex.put(DatabaseConstants.WORD_ID, Integer.toString(wordsTable.get(seg[i])));
                        wordsIndex.put(DatabaseConstants.POSITION, Integer.toString(position));
                        wordsIndex.put(DatabaseConstants.PRIOR_SIGN, priorSign);
                        wordsIndex.put(DatabaseConstants.POST_SIGN, postSign);
                        wordsIndexTable.add(wordsIndex);
                        id++;
                        position++;
                        priorSign = "";
                        postSign = "";
                        wordsIndex = new HashMap<String, String>();
                    }
                    position = 0;
                    sentenceID++;
                }
            }
            // Data Insert To Words Index Table
            for (Map<String, String> wit : wordsIndexTable) {
                wordsIndexTableSqlObject = new SqlObject();
                wordsIndexTableSqlObject.addSqlObject(DatabaseConstants.ID, wit.get(DatabaseConstants.ID));
                wordsIndexTableSqlObject.addSqlObject(DatabaseConstants.SENTENCE_ID, wit.get(DatabaseConstants.SENTENCE_ID));
                wordsIndexTableSqlObject.addSqlObject(DatabaseConstants.WORD_ID, wit.get(DatabaseConstants.WORD_ID));
                wordsIndexTableSqlObject.addSqlObject(DatabaseConstants.POSITION, wit.get(DatabaseConstants.POSITION));
                wordsIndexTableSqlObject.addSqlObject(DatabaseConstants.PRIOR_SIGN, wit.get(DatabaseConstants.PRIOR_SIGN));
                wordsIndexTableSqlObject.addSqlObject(DatabaseConstants.POST_SIGN, wit.get(DatabaseConstants.POST_SIGN));
                mysqlDatabaseController.execInsert(indexTable, wordsIndexTableSqlObject);
            }
            System.out.println(indexTable + " is finished");
            // Data Insert To Sentence Content Table
            for (Integer sc : sentenceContent.keySet()) {
                sentenceContentSqlObject = new SqlObject();
                sentenceContentSqlObject.addSqlObject(DatabaseConstants.ID, sc);
                sentenceContentSqlObject.addSqlObject(
                        DatabaseConstants.ARTICLE_ID, sentenceContent.get(sc).get(0));
                sentenceContentSqlObject.addSqlObject(
                        DatabaseConstants.TEXT, sentenceContent.get(sc).get(1));
                mysqlDatabaseController.execInsert(content, sentenceContentSqlObject);
            }
            System.out.println(content + " is finished");
        }
        // Data Insert To Words Table
        for (String word : wordsTable.keySet()) {
            wordsTableSqlObject = new SqlObject();
            String[] seg = word.split(FoldName.SPLIT);
            wordsTableSqlObject.addSqlObject(DatabaseConstants.ID, wordsTable.get(word));
            System.out.println("---------");
            System.out.println(seg[0]);
            System.out.println(seg[1]);
            System.out.println(seg[2]);
            System.out.println("---------");
            wordsTableSqlObject.addSqlObject(DatabaseConstants.TEXT, seg[0]);
            wordsTableSqlObject.addSqlObject(DatabaseConstants.POS, seg[1]);
            wordsTableSqlObject.addSqlObject(DatabaseConstants.LEMMA, seg[2]);
            mysqlDatabaseController.execInsert(DatabaseConstants.WORDS_TABLE, wordsTableSqlObject);
        }
        System.out.println(DatabaseConstants.WORDS_TABLE + " is Finished");
    }
}
