package main;

import treetagger.TreeTagger;

public class TreeTaggerTest {
    /**
     * Test.
     **/
    public static void main(final String[] args) throws Exception {
        TreeTagger treeTagger = new TreeTagger();
        // String s = "Javier es un poco nervioso por Estela siente con le .";
        String s1 = " He estado una vez en Isla Verde con unos compañera de clase en este verano .";
        // System.out.println(treeTagger.getPOSTag(s));
        System.out.println(treeTagger.getPOSTag(s1));
    }
}
