class IDFScore extends SimpleScore{
    private static float IDFScore;

    static float getIDFScore(String query, Index idx) {
        double allWebsites = 0, matchedWebsites = 0;
        allWebsites= idx.lookupAll().size();
        for (Website website : idx.lookupAll()){
            if (website.getWords().contains(query)) matchedWebsites ++;
        }

        IDFScore = (float) Math.log(allWebsites/matchedWebsites);
        return IDFScore;
    }
}
