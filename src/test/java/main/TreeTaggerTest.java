package main;

import treetagger.TreeTagger;

public class TreeTaggerTest {
    /**
     * Test.
     **/
    public static void main(final String[] args) throws Exception {
        TreeTagger treeTagger = new TreeTagger();
        // String s = "Javier es un poco nervioso por Estela siente con le .";
        String s1 = "Después comerse   el lobo las conció, llegó la casa de abuela para un camino corto. ";
        // System.out.println(treeTagger.getPOSTag(s));
        System.out.println(treeTagger.getPOSTag(s1));
    }
}
