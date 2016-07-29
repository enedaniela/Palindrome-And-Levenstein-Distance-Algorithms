import java.io.*;

public class P2 {

	static int rows = 0, columns = 0;
	static int[][] a = new int[700][1300];
	static int n = 0;
	static int[] initWord = new int[1300];

	public static void readData(String filename) throws Exception {

		BufferedReader stream = new BufferedReader(new FileReader(filename));

		/*read the number of vars and the length of one var*/
		String[] vec = stream.readLine().split(" ");
		rows = Integer.parseInt(vec[0]);
		columns = Integer.parseInt(vec[1]);

		/*read the matix of vars*/
		for (int currentRow = 0; currentRow < rows; currentRow++) {
			String line = stream.readLine();
			String[] splitted = line.split(" ");
			for (int currentColumn = 0; currentColumn < columns; currentColumn++) {
				a[currentRow][currentColumn] = Integer.parseInt(splitted[currentColumn]);
			}
		}

		/*read the length of the initial word*/
		String lengthOfWord = stream.readLine();
		n = Integer.parseInt(lengthOfWord);

		/*read the initial word*/
		String word = stream.readLine();
		String[] splittedWord = word.split(" ");
		for (int j = 0; j < n; ++j) {
			initWord[j] = Integer.parseInt(splittedWord[j]);
		}

		stream.close();
	}

	/*determines if a certain elements exists in a certain column in the matrix*/
	public static boolean find(int elem, int where) {
		for (int i = 0; i < rows; i++)
			if (elem == a[i][where - 1])
				return true;
		return false;
	}

	public static int minimum(int a, int b, int c) {
		return Math.min(a, Math.min(b, c));
	}

	public static int compute() {
		int m = columns;
		int[][] d = new int[m + 1][n + 1];

		/*initialize the first row and first column in the matrix*/
		int p = 0, k = 0;
		do {
			d[p][0] = p;
			p++;
		} while (p <= m);

		do {
			d[0][k] = k;
			k++;
		} while (k <= n);

		/*building the matrix*/
		int j = 1;
		do {
			int i = 1;
			do {
				if (find(initWord[j - 1], i)) {
					/*if the element exists in its place, no operation is needed*/
					d[i][j] = d[i - 1][j - 1];
				} else {
					/*otherwise, we have to insert, delete or modify*/
					d[i][j] = minimum(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + 1);
				}
				i++;
			} while (i <= m);
			j++;
		} while (j <= n);

		/*return the last element of the matrix*/
		return d[m][n];
	}

	public static void main(String[] args) throws Exception {

		int dist = 0;
		try {
			File fileName = new File("evaluare.out");
			FileOutputStream is = new FileOutputStream(fileName);
			OutputStreamWriter osw = new OutputStreamWriter(is);
			Writer w = new BufferedWriter(osw);

			/*read data from the file*/
			readData("evaluare.in");

			/*determine the Levenshtein distance*/
			dist = compute();

			/*write the answer to the file*/
			w.write("" + dist);
			w.close();

		} catch (IOException e) {
			System.err.println("Problem writing to the file");
		}
	}
}
