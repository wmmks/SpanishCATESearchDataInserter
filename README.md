# Spanish SearchPage Data Insert
主要將搜尋之程式邏輯資訊插入資料庫中。(Widows 10 Environment)</br>

## 必須下載 tree tagger。下載完後，必須加入至環境變數(TreeTagger\bin)

## Tree Tagger 資料集
https://drive.google.com/file/d/1mAZHkpeEEW7PpPdcdh8Cz4v5m512xJTv/view?usp=sharing</br>

## 執行順序
```
1. Extract DB DATA about Original Article and Correct Article(Data In OriginalAndCorrectData Fold).
2. Split Sentence And Tagging(Lema or Stemming) 
Note: Please Set Article ID Start and Article ID End and Set Treetagger Path(Revise FoldName Class Code).
3. Insert Data Into DB about (words_table) and (Original/Correct words_index_table and sentence_content).
```

## 主要插入的資料表
* words_table
* original_words_Index_table
* original_sentences_content
* corrected_words_Index_table
* corrected_sentences_content
