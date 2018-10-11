package main;

import constant_field.FoldName;
import read_write_file.ReadFileController;
import read_write_file.WriteFileController;
import treetagger.TreeTagger;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Split Sentence And Tagging(Lema or Stemming).
 * Note: Please Set Article ID Start and Article ID End and Set Treetagger Path(Revise FoldName Class Code).
 *
 * @version 1.0 2017年10月25日
 * @author Alex
 *
 */
public class SplitSentenceAndTagging {
    /**
     * Test.
     **/
    public static void main(final String[] args) throws Exception {
        ReadFileController readFileController;
        Scanner scanner = new Scanner(System.in);
        System.out.println("選擇 1 為 Original， 2 為 Correct");
        String inputFileName = FoldName.ORIGINAL_AND_CORRECT_DATA;
        String outputFileName = FoldName.ORIGINAL_AND_CORRECT_DATA;
        switch (scanner.nextInt()) {
            case 1:
                inputFileName += FoldName.ORIGINAL_ARTICLE;
                outputFileName += FoldName.ORIGINAL_ARTICLE_TAGGING_SPLIT_SENTENCE;
                break;
            case 2:
                inputFileName += FoldName.CORRECT_ARTICLE;
                outputFileName += FoldName.CORRECT_ARTICLE_TAGGING_SPLIT_SENTENCE;
                break;
            default:
                System.out.println("Error Input");
                break;
        }
        TreeTagger treeTagger = new TreeTagger();
        for (int articleID = FoldName.ARTICLE_ID_START; articleID <= FoldName.ARTICLE_ID_END; articleID++) {
            for (int systemType = 1; systemType <= 2; systemType++) {
                // 依據要求選擇 CORRECT 或 ORIGINAL
                try {
                    readFileController = new ReadFileController(inputFileName + articleID + "_" + systemType);
                } catch (Exception e) {
                    System.out.println(articleID + " Error Information: " + e.toString());
                    continue;
                }
                char[] charArray = readFileController.getLine().replace("$","").toCharArray();
                StringBuilder sentence = new StringBuilder();
                ArrayList<String> sentenceList = new ArrayList<String>();
                // flag 用來判斷...之類之符號(主要確認是否有進入判斷式過)
                boolean flag = false;
                for (int i = 0; i < charArray.length; i++) {
                    if (i == (charArray.length - 1)) {
                        sentence.append(charArray[i]);
                        sentenceList.add(sentence.toString());
                        sentence = new StringBuilder();
                    } else if (charArray[i] == '.' || charArray[i] == '?' || charArray[i] == '!') {
                        if (i + 1 < charArray.length) {
                            if (charArray[i + 1] == '.' || charArray[i + 1] == '?' || charArray[i + 1] == '!') {
                                if (!flag) {
                                    sentence.append(' ');
                                    sentence.append(charArray[i]);
                                } else {
                                    sentence.append(charArray[i]);
                                }
                                flag = true;
                            } else if (charArray[i + 1] == ' ') {
                                sentence.append(' ');
                                sentence.append(charArray[i]);
                                sentenceList.add(sentence.toString());
                                sentence = new StringBuilder();
                                flag = false;
                            } else if (charArray[i - 1] == '.' || charArray[i - 1] == '?' || charArray[i - 1] == '!') {
                                sentence.append(charArray[i]);
                                sentence.append(" ");
                                flag = false;
                            } else {
                                sentence.append(" ");
                                sentence.append(charArray[i]);
                                sentence.append(" ");
                                flag = false;
                            }
                        }
                    } else if (charArray[i] == ';' || charArray[i] == '(' || charArray[i] == ')' ||
                            charArray[i] == '—' || charArray[i] == '"' || charArray[i] == '；' ||
                            charArray[i] == '”' || charArray[i] == '¿' || charArray[i] == '¡' ||
                            charArray[i] == '“' || charArray[i] == ',' || charArray[i] == ':' ||
                            charArray[i] == '─' || charArray[i] == '「' || charArray[i] == '」' ||
                            charArray[i] == '）' || charArray[i] == '-' || charArray[i] == '¨' ||
                            charArray[i] == '=' || charArray[i] == '－' || charArray[i] == '–' ||
                            charArray[i] == '´') {
                        sentence.append(' ');
                        sentence.append(charArray[i]);
                        sentence.append(' ');
                    } else {
                        if (!isChinese(String.valueOf(charArray[i]))) {
                            sentence.append(charArray[i]);
                        } else {
                            //System.out.print(charArray[i]);
                        }
                    }
                }
                WriteFileController writeFileController = new WriteFileController();
                StringBuilder article = new StringBuilder();
                for (String s : sentenceList) {
                    // Tree tagger 會因為 \t 而導致 過濾一些重要詞彙
                    article.append(treeTagger.getPOSTag(s.replaceAll("\t", "")));
                    article.append(FoldName.SENTENCE_FLAG);
                }
                // 依據要求選擇 CORRECT 或 ORIGINAL
                writeFileController.writeDataIntoFile(outputFileName + articleID + "_" + systemType, article.toString());
            }
        }
    }

    /**
     * 只能判断部分 CJK 字符（CJK统一汉字）.
     * @param str 判斷中文字的字串
     * @return true or false
     */
    private static boolean isChinese(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
        return pattern.matcher(str.trim()).find();
    }
}