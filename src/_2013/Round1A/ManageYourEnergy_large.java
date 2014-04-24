package _2013.Round1A;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

public class ManageYourEnergy_large {
	final static int DEBUG_LEVEL = 5;
	final static int TIMER_LEVEL = 5;

	final static String FILE_NAME = "B-large-practice";// <<<--------

	final static String BASE = "C:/CodeJam/" + FILE_NAME;
	final static String SOURCE_FOLDER = "src";// <--- Eclipse standard
	final static String INPUT = BASE + ".in";
	final static String OUTPUT = BASE + ".out";
	final static String FILE_SEPARATOR = System.getProperty("file.separator");
	final static String LINE_SEPARATOR = System.getProperty("line.separator");
	static Scanner in;
	static PrintWriter out;
	static long startTime;

	private static void caseSolver() {
		long E = in.nextLong();
		long R = in.nextLong();
		int N = in.nextInt();
		long[] v = new long[N];
		for (int i = 0; i < N; i++)
			v[i] = in.nextLong();
		int[] nextBigger = new int[N];
		long gain = 0;
		long energy = E;
		Stack<Integer> big = new Stack<Integer>();

		for (int i = N - 1; i >= 0; i--) {
			while (!big.isEmpty()) {
				if (v[i] >= v[big.peek()])
					big.pop();
				else
					break;
			}
			int h = 0;
			if (big.isEmpty())
				h = -1;
			else
				h = big.peek();
			nextBigger[i] = h;
			big.push(i);
		}

		for (int i = 0; i < N; i++) {
			if (nextBigger[i] == -1) {
				gain += energy * v[i];
				energy = 0;
			} else {
				long diff = nextBigger[i] - i;
				long left = energy - E + diff * R;
				if (left > E)
					left = E;
				if (left > 0) {
					gain += left * v[i];
					energy -= left;
				}
			}
			energy += R;
			if (energy > E)
				energy = E;
		}

		out.print(gain);
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
