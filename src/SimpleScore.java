public class SimpleScore implements Score {

    public float getScore(String s, Website w, Index idx) {
        new TFScore();
        new IDFScore();

        float tfScore = TFScore.getTFScore(s, w);
        float idfScore = IDFScore.getIDFScore(s, idx);
        return tfScore * idfScore;

    }
}
