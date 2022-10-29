public class TourGuide {
    public static int getBestSightseeingPairScore(int[] places) {
        int result = 0;
        if (places == null){
            return result;
        }
        for (int i = 0; i < places.length - 1; i++){
            for (int j = i + 1; j < places.length; j++){
                if (places[i] + places[j] + i - j > result) {
                    result = places[i] + places[j] + i - j;
                }
            }
        }
        return result;
    }
}
