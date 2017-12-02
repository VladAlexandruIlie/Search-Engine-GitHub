class TFScore extends SimpleScore{
    private static float TFScore;

    static float getTFScore(String s, Website w) {
            double occurrences = 0, length;
            length = w.getWords().size();

            for (String word : w.getWords()) {
                if (word.equals(s)) occurrences = occurrences + 1;
            }
            //System.out.printf("TFScore: %f  / %f | on: %s \n", occurrences, length, w.getUrl());
            TFScore = (float) (occurrences / length);
            return TFScore;
    }

}
