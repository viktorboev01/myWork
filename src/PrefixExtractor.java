public class PrefixExtractor  {
    public static String getLongestCommonPrefix(String[] words){
        String result = "";
        if (words == null || words.length == 0){
            return result;
        }
        int minLength = words[0].length();
        for (String word : words) {
            if (word == null){
                return result;
            }
            if (word.length() < minLength){
                minLength = word.length();
            }
        }
        int i = 0;
        while (i < minLength) {
            for (String word : words){
                if (words[0].charAt(i) != word.charAt(i)) {
                    return result;
                }
            }
            result += words[0].charAt(i);
            i++;
        }
        return result;
    }
}
