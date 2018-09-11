package main;

import treetagger.TreeTagger;

public class TreeTaggerTest {
    /**
     * Test.
     **/
    public static void main(final String[] args) throws Exception {
        TreeTagger treeTagger = new TreeTagger();
        // String s = "Javier es un poco nervioso por Estela siente con le .";
        String s1 = "Caperucita roja fue llamado a atención porlos flores, mariosa maravillosas y los pájaros que cantabanbien.";
        // System.out.println(treeTagger.getPOSTag(s));
        System.out.println(treeTagger.getPOSTag(s1));
    }
}
