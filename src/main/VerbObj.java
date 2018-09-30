package src.main;


public class VerbObj {


    static String Spanish;
    static String Deutsch;
    static int DeutschVerbs = 0;
    static int SpansichVerbs = 0;

    public static int getSpansichVerbs() {
        return SpansichVerbs;
    }

    public static void setSpansichVerbs(int spansichVerbs) {
        SpansichVerbs = spansichVerbs;
    }



    public static int getDeutschVerbs() {
        return DeutschVerbs;
    }

    public static void setDeutschVerbs(int deutschVerbs) {
        DeutschVerbs = deutschVerbs;
    }

    public static String getSpanish() {
        return Spanish;
    }

    public static void setSpanish(String spanish) {
        Spanish = spanish;
    }

    public static String getDeutsch() {
        return Deutsch;
    }

    public static void setDeutsch(String deutsch) {
        Deutsch = deutsch;
    }
}
