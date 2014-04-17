package practice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;

public class GoodLuck_small {
	final static int DEBUG_LEVEL = 5;
	final static int TIMER_LEVEL = 5;

	final static String FILE_NAME = "C-small-practice-1";// <<<--------

	final static String BASE = "C:/CodeJam/" + FILE_NAME;
	final static String SOURCE_FOLDER = "src";// <--- Eclipse standard
	final static String INPUT = BASE + ".in";
	final static String OUTPUT = BASE + ".out";
	final static String FILE_SEPARATOR = System.getProperty("file.separator");
	final static String LINE_SEPARATOR = System.getProperty("line.separator");
	static Scanner in;
	static PrintWriter out;
	static long startTime;
	@SuppressWarnings("serial")
	final static HashSet<Integer> primes = new HashSet<Integer>() {
		{
			add(2);
			add(3);
			add(5);
		}
	};

	private static void caseSolver() {
		out.println();
		int R = in.nextInt();
		int N = in.nextInt();
		int M = in.nextInt();
		int K = in.nextInt();

		for (int i = 0; i < R; i++) {
			int[] quant = new int[M + 1];
			int[] max = new int[M + 1];

			for (int index = 0; index < K; index++) {
				int product = in.nextInt();
				int[] count = new int[M + 1];
				int[] pot = new int[M + 1];
				for (Integer p : primes) {
					if (p > M)
						continue;
					pot[p] = p;
					while (product % pot[p] == 0) {
						pot[p] *= p;
						count[p]++;
					}
					if (count[p] > max[p])
						max[p] = count[p];
				}
			}

			// logic start
			int total = 0;
			for (Integer p : primes) {
				if (p > M)
					continue;
				if (p != 2) {
					if (!(max[p] == 0)) {
						quant[p] = max[p];
						total += quant[p];
					}
				}
			}
			if ((N - total) != 0) {
				if ((N - total) == 1) {
					if (max[2] == 1)
						quant[2]++;
					else
						quant[4]++;
				} else if ((N - total) == 2) {
					if (max[2] <= 2)
						quant[2] += 2;
					else if (max[2] == 3)
						quant[2] = quant[4] = 1;
					else
						quant[4] += 2;
				} else if ((N - total) == 3) {
					if (max[2] <= 3)
						quant[2] += 3;
					else if (max[2] == 4) {
						quant[2] = 2;
						quant[4] = 1;
					} else if (max[2] == 5) {
						quant[2] = 1;
						quant[4] = 2;
					} else
						quant[4] += 3;
				}
			}
			// logic end

			// print start
			for (int j = 2; j <= M; j++) {
				for (int k = 0; k < quant[j]; k++) {
					out.print(j);
				}
			}
			out.println();
			// print end
		}
	}

	/*
	 * XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	 * XXXXXXXXXXXXXXXX-----CODEJAM IO TEMPLATE-----XXXXXXXXXXXXXXXXX
	 * XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	 */
	public static void main(String[] args) {
		try {
			in = new Scanner(new FileReader(INPUT));
			in.useLocale(Locale.US);
			out = new PrintWriter(OUTPUT);

			int number_of_cases = in.nextInt();
			in.nextLine();
			for (int c = 0; c < number_of_cases; c++) {
				out.print("Case #" + (c + 1) + ": ");
				log("Solving case " + (c + 1) + "...", 1);
				startTimer(1);
				caseSolver();
				endTimer(1);
				out.println();
			}
			out.println();
			out.flush();
			out.close();
			in.close();
			copySourceCode();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void copySourceCode() {
		String classPath = System.getProperty("user.dir") + FILE_SEPARATOR + SOURCE_FOLDER;

		String className = new Object() {
		}.getClass().getEnclosingClass().getCanonicalName();

		StringTokenizer st = new StringTokenizer(className, ".");

		while (st.hasMoreElements()) {
			classPath += FILE_SEPARATOR + st.nextElement().toString();
		}

		classPath += ".java";

		try {
			Files.copy(new File(classPath).toPath(), new File(BASE + ".java").toPath(),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void log(String message, int d_level) {
		if (DEBUG_LEVEL >= d_level) {
			System.out.println(message);
		}
	}

	private static void startTimer(int t_level) {
		if (TIMER_LEVEL >= t_level) {
			startTime = System.currentTimeMillis();
		}
	}

	private static void endTimer(int t_level) {
		if (TIMER_LEVEL >= t_level) {
			long endTime = System.currentTimeMillis();
			System.out.println((endTime - startTime) + " ms");
		}
	}
	// XXXXXXXXXXXXXXXX-----CODEJAM IO TEMPLATE-----XXXXXXXXXXXXXXXXX
}
