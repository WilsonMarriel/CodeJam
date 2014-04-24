package _2013.Round1B;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Osmos {
	final static int DEBUG_LEVEL = 5;
	final static int TIMER_LEVEL = 5;

	final static String FILE_NAME = "A-large-practice";// <<<--------

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
		int A = in.nextInt();
		int N = in.nextInt();
		ArrayList<Integer> motes = new ArrayList<Integer>(N);
		for (int i = 0; i < N; i++) {
			motes.add(in.nextInt());
		}
		if (A == 1) {
			out.print(N);
			return;
		}
		Collections.sort(motes);

		int minCount = motes.size();
		int removed = 0;
		while (!motes.isEmpty()) {
			int fakeA = A;
			ArrayList<Integer> fakeMotes = new ArrayList<>(motes);
			int count = removed;
			while (!fakeMotes.isEmpty()) {
				while (!fakeMotes.isEmpty() && fakeA > fakeMotes.get(0)) {
					fakeA += fakeMotes.get(0);
					fakeMotes.remove(0);
				}
				if (fakeMotes.isEmpty())
					break;
				fakeA = 2 * fakeA - 1;
				count++;
				if (count > minCount)
					break;
			}
			if (minCount > count)
				minCount = count;
			if (minCount - removed <= 0)
				break;
			motes.remove(motes.size() - 1);
			removed++;
		}
		out.print(minCount);
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
