package _2013.Round1B;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;

public class FallingDiamonds {
	final static int DEBUG_LEVEL = 5;
	final static int TIMER_LEVEL = 5;

	final static String FILE_NAME = "B-large-practice";// <<<--------

	final static String BASE = "C:/CodeJam/" + FILE_NAME;
	final static String SOURCE_FOLDER = "src";// <--- Eclipse standard
	final static String INPUT = BASE + ".in";
	final static String OUTPUT = BASE + ".out";
	final static String FILE_SEPARATOR = System.getProperty("file.separator");
	final static String LINE_SEPARATOR = System.getProperty("line.separator");
	final static int DOUBLE_PRECISION = 6;
	static Scanner in;
	static PrintWriter out;
	static long startTime;

	private static void caseSolver() {
		int N = in.nextInt();
		int X = Math.abs(in.nextInt());
		int Y = in.nextInt();

		int line = (X + Y) / 2;
		int lower_triangle = f(line - 1);
		if (N >= f(line)) {
			out.print(1.0);
		} else if (X == 0) {
			out.print(0.0);
		} else if (lower_triangle + Y >= N) {
			out.print(0.0);
		} else {
			N -= lower_triangle;
			if (2 * line + Y < N)
				out.print(1.0);
			else {
				double p = calculate(N, Y + 1);

				out.print(p);
			}
		}
	}

	static double calculate(int N, int K) {
		BigDecimal ret = choose(N, K);
		K = N - K;
		BigDecimal result = ret.divide(BigDecimal.valueOf(2).pow(N));
		for (int i = K - 1; i >= 0; i--) {
			ret = ret.multiply(BigDecimal.valueOf(i + 1)).divide(BigDecimal.valueOf(N - i));
			result = result.add(ret.divide(BigDecimal.valueOf(2).pow(N)));
		}
		result.setScale(DOUBLE_PRECISION + 1, RoundingMode.DOWN);
		return result.doubleValue();
	}

	static BigDecimal choose(int N, int K) {
		if (K > N / 2)
			K = N - K;
		BigDecimal ret = BigDecimal.ONE;
		for (int k = 0; k < K; k++) {
			ret = ret.multiply(BigDecimal.valueOf(N - k)).divide(BigDecimal.valueOf(k + 1));
		}
		return ret;
	}

	static int f(int x) {
		return 2 * x * x + 3 * x + 1;
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
