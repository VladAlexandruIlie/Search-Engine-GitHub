public class BM25Score implements Score {
    private static float BM25Score;
    private static float TFScore;
    private static float IDFScore;



    public float getScore(String a, Website w, Index index) {
        TFScore tf = new TFScore();
        IDFScore idf = new IDFScore();
        TFScore =  tf.getTFScore(a,w) ;
        IDFScore =  idf.getIDFScore(a, index);

        int nrOfWords = w.getWords().size(), sum = 0, nr = 0;
        double c, average, k = 1.75, b = 0.75;

        for (Website website :index.lookupAll()) {
            sum = sum + website.getWords().size();
            nr++;
        }

        average = (sum * 1.0) / nr;
        c = (TFScore * ((((k + 1) / k) * ((1 - b) + ((b * nrOfWords) / average))) + TFScore)) * IDFScore;

        BM25Score = (float) c;
        return BM25Score;
    }


    public float getTFScore(){
        return TFScore;
    }

    public static float getIDFScore() {
        return IDFScore;
    }
}
