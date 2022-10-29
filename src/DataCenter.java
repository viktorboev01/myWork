public class DataCenter {
    public static int getCommunicatingServersCount(int[][] map){
        int result = 0;
        int sizeRows = map.length;
        int sizeCols = map[0].length;
        int[] visitedRows = new int[sizeRows];
        int[] visitedCols = new int[sizeCols];
        for (int i = 0; i < sizeRows; i++){
            for (int j = 0; j < sizeCols; j++){
                if (map[i][j] == 1){
                    visitedRows[i]++;
                    visitedCols[j]++;
                }
            }
        }
        for (int i = 0; i < sizeRows; i++){
            for (int j = 0; j < sizeCols; j++){
                if (map[i][j] == 1 && (visitedRows[i] > 1 || visitedCols[j] > 1)){
                    result++;
                }
            }
        }
        return result;
    }
}
