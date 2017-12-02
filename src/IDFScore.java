class IDFScore extends SimpleScore{
    private static float IDFScore;

    static float getIDFScore(String term, Index idx) {
        double allWebsites = 0, matchedWebsites = 0;
        allWebsites= idx.lookup("*").size();
        for (Website website : idx.lookup("*")){
            if (website.getWords().contains(term)) matchedWebsites ++;
        }

        IDFScore = (float) Math.log(allWebsites/matchedWebsites);
        //System.out.printf("IDFScore: %f  / %f |\n", allWebsites, matchedWebsites);
        return IDFScore;
    }
}
