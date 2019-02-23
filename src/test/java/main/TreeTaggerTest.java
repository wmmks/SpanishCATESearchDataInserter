package main;

import treetagger.TreeTagger;

public class TreeTaggerTest {
    /**
     * Test.
     **/
    public static void main(final String[] args) throws Exception {
        TreeTagger treeTagger = new TreeTagger();
        // String s = "Javier es un poco nervioso por Estela siente con le .";
        if ("pájaros".toLowerCase().equals("pájaros")) {
            System.out.println("hi");
        }
        System.out.println("Pájaros".toLowerCase());
        String s1 = "Ellas allí arlos decirla estaban muy contentas, y para darle las gracias al cazador, la abuelita lo invitó a comer pan dulce y beber chocolate caliente.";
        // System.out.println(treeTagger.getPOSTag(s));
        System.out.println(treeTagger.getPOSTag(s1));
    }
}
