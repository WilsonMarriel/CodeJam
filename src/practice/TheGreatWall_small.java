package practice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TheGreatWall_small {
	final static int DEBUG_LEVEL = 5;
	final static int TIMER_LEVEL = 5;

	final static String FILE_NAME = "C-small-practice";// <<<--------

	final static String BASE = "C:/CodeJam/" + FILE_NAME;
	final static String SOURCE_FOLDER = "src";// <--- Eclipse standard
	final static String INPUT = BASE + ".in";
	final static String OUTPUT = BASE + ".out";
	final static String FILE_SEPARATOR = System.getProperty("file.separator");
	final static String LINE_SEPARATOR = System.getProperty("line.separator");
	static Scanner in;
	static PrintWriter out;
	static long startTime;
	final static int WALL_MIDDLE = 400;// doubled wall_mid to fit .5 numbers
	final static int MAX_DAYS = 676060;

	private static void caseSolver() {
		int N = in.nextInt();
		int[] wall = new int[2 * WALL_MIDDLE + 1];/* -wall_mid, 0, +wall_mid */// includes .5 spots
		HashMap<Integer, HashMap<Integer, Integer[]>> tribes = new HashMap<Integer, HashMap<Integer, Integer[]>>();
		HashMap<Integer, Integer[]> attack = new HashMap<Integer, Integer[]>();
		int first_day = MAX_DAYS;
		int last_day = 0;

		for (int i = 0; i < N; i++) {
			HashMap<Integer, Integer[]> tribe = new HashMap<Integer, Integer[]>();// new tribe
			int d = in.nextInt();
			int n = in.nextInt();
			int w = in.nextInt();
			int e = in.nextInt();
			int s = in.nextInt();
			int delta_d = in.nextInt();
			int delta_p = in.nextInt();
			int delta_s = in.nextInt();
			for (int j = 0; j < n; j++) {
				int ww = WALL_MIDDLE + 2 * (w + j * delta_p);
				int ee = WALL_MIDDLE + 2 * (e + j * delta_p);
				int dd = d + j * delta_d;
				int ss = s + j * delta_s;
				Integer[] empty_wall = new Integer[2 * WALL_MIDDLE + 1];
				Arrays.fill(empty_wall, 0);
				tribe.put(dd, empty_wall);// new day of attack
				for (int k = ww; k <= ee; k++) {
					Integer[] aux = tribe.get(dd);
					aux[k] = ss;
					tribe.put(dd, aux);
					if (!attack.containsKey(dd)) {
						empty_wall = new Integer[2 * WALL_MIDDLE + 1];
						Arrays.fill(empty_wall, 0);
						empty_wall[k] = ss;
						attack.put(dd, empty_wall);
					} else if (ss > attack.get(dd)[k]) {
						Integer[] aux2 = attack.get(dd);
						aux2[k] = ss;
						attack.put(dd, aux2);
					}
				}
			}
			tribes.put(i, tribe);
			if (d + (n - 1) * delta_d > last_day)
				last_day = d + (n - 1) * delta_d;
			if (d < first_day)
				first_day = d;
		}

		int counter = 0;
		for (int i = first_day; i <= last_day; i++) {
			// check attacks that succeeded
			for (int j = 0; j < N; j++) {
				if (tribes.containsKey(j) && tribes.get(j).containsKey(i)) {
					int ww = 0;
					int ee = 2 * WALL_MIDDLE;
					for (int k = ww; k <= ee; k++) {
						if (tribes.get(j).get(i)[k] > wall[k]) {
							counter++;
							break;
						}
					}
				}
			}
			// after battles the wall is build
			if (attack.containsKey(i)) {
				int ww = 0;
				int ee = 2 * WALL_MIDDLE;
				for (int k = ww; k <= ee; k++) {
					if (attack.get(i)[k] > wall[k])
						wall[k] = attack.get(i)[k];
				}
			}
		}
		out.print(counter);
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
