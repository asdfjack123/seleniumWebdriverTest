package utils;

public class Strs {
	  public static boolean isEmpty(String s){
	        if("".equals(s) || s == null){
	            return true;
	        }
	        else{
	            return false;
	        }
	    }

	    public static String fixedSizeWithBlank(String s , int size){
//	        s = s.trim();
	        while (getWordCount(s)<size){
	            s = s + " ";
	        }
	        return s;
	    }

	    public static int getWordCount(String s)
	    {
	        int length = 0;
	        for(int i = 0; i < s.length(); i++)
	        {
	            int ascii = Character.codePointAt(s, i);
	            if(ascii >= 0 && ascii <=255)
	                length++;
	            else
	                length += 2;

	        }
	        return length;

	    }
}
