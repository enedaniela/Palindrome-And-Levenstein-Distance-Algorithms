import java.io.*;

public class P1 {
	public static String[] readData ( String filename ) {

		BufferedReader br = null;
		int n = 0;
		String pal[] = new String[1000];

		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(filename));

			/*read the number of words*/
			sCurrentLine = br.readLine();
			n = Integer.parseInt(sCurrentLine);

			/*read every word*/
			for (int i = 0; i < n; i++) {
				pal[i] = br.readLine();
			}
		} catch (Exception e) {		
			e.printStackTrace();
		} finally { 					
			try {
				if (br != null) 
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return pal;
	}

	public static boolean check(char s[]) {
		int contor = 0;
		int[] allChars = new int[26]; 

		/*count the number of appearances of each char*/
		for (int i = 0; i < s.length; i++) 
			allChars[s[i] - 'a']++;

		/*count how many of them are odd*/
		for (int i = 0; i < 26; ++ i) 
			if(allChars[i] % 2 == 1)
				contor ++;
		/*there is more than one char with odd number of appearances*/
		if(contor < 2)
			return true;
		return false;
	}

	/*move to get the last letter in place*/
	public static int move_last(char[] s, int first, int last, int poz) {
		for (int i = poz; i >= first + 1; i--)
			s[i] = s[i - 1];

		return poz - first;
	}

	/*move to get the first letter in place*/
	public static int move_first(char[] s, int first, int last, int poz) {
		for (int i = poz; i < last; i++)
			s[i] = s[i + 1];

		return last - poz;
	}

	/*how many letters should I swap to get on the first position
	 * a letter identical with the one on the last position
	 */
	public static int costForFirst(char[] s, int first, int last){
		String pal = new String(s);

		pal = pal.substring(first, last+1);
		int index = first + pal.indexOf(s[last]);

		if(index == last)
			return -1;

		return index;
	}

	/*how many letters should I swap to get on the last position
	 * a letter identical with the one on the first position
	 */
	public static int costForLast(char[] s, int first, int last){
		String pal = new String(s);

		pal = pal.substring(first, last + 1);
		String reverse = new StringBuffer(pal).reverse().toString();
		int index = last - reverse.indexOf(s[first]);

		if(index == first)
			return -1;

		return index;
	}

	public static int movesNumber(char[] s, int first, int last) {
		int mateOfBeg = -1;
		int mateOfLast = -1;

		/*find where the mate of the beginning is*/
		mateOfBeg = costForLast(s, first, last);
		
		/*find where the mate of the end is*/
		mateOfLast = costForFirst(s, first, last);

		/*first element has no match*/
		if (mateOfBeg == -1) {
			return move_last(s, first, last, mateOfLast);
		}
		
		/*last element has no match*/
		if (mateOfLast == -1) {
			return move_first(s, first, last, mateOfBeg);
		}

		int costLast = last - mateOfBeg;
		int costBeg = mateOfLast - last;

		if (costBeg < costLast) {
			return move_first(s, first, last, mateOfBeg);		   
		}
		return move_last(s, first, last, mateOfLast);
	}

	public static int solve(char[] s) {
		int rez = 0;

		/*check if the word can be a palindrome*/
		if (!check(s)) {
			return -1;
		}

		for (int i = 0; i < (s.length - 1) / 2; i++)
			/*while the beginning is different from the end*/
			if (s[i] != s[s.length - 1 - i])
				rez += movesNumber(s, i, s.length - 1 - i);
		return rez;
	}

	public static void main(String[] args) 
	{
		try {

			File statText = new File("joc.out");
			FileOutputStream is = new FileOutputStream(statText);
			OutputStreamWriter osw = new OutputStreamWriter(is);    
			Writer w = new BufferedWriter(osw);

			/*read data from the file*/
			String[] pal = readData("joc.in");
			char[] st = null;
			
			/*for every word compute the minimum number of swaps*/
			for(int i = 0; i < pal.length; i++){
				if(pal[i] != null){
					st = pal[i].toCharArray();
					/*write answer to the file*/
					w.write("" + solve(st) + "\n");
				}
			}
			w.close();
		} catch (IOException e) {
			System.err.println("Problem writing to the file");
		}
	}
}






















