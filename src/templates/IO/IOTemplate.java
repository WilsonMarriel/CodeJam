package templates.IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;

public class IOTemplate {
	final static int DEBUG_LEVEL = 5;
	final static int TIMER_LEVEL = 5;

	final static String FILE_NAME = "";// <<<--------

	final static String BASE = "C:/CodeJam/";
	final static String SOURCE_FOLDER = "src";// <--- Eclipse standard
	final static String INPUT = BASE + FILE_NAME + ".in";
	final static String OUTPUT = BASE + FILE_NAME + ".out";
	final static String FILE_SEPARATOR = System.getProperty("file.separator");
	static Scanner in;
	static PrintWriter out;
	static long startTime;

	private static void caseSolver() {
		// TODO: implement caseSolver
	}

	// XXXXXXXXXXXXXXXXXX-----CODEJAM TEMPLATE-----XXXXXXXXXXXXXXXXXX
	public static void main(String[] args) {
		try {
			in = new Scanner(new FileReader(INPUT));
			in.useLocale(Locale.US);
			out = new PrintWriter(OUTPUT);

			int number_of_cases = in.nextInt();
			for (int c = 0; c < number_of_cases; c++) {
				out.print("Case #" + (c + 1) + ": ");
				log("Solving case " + (c + 1) + "...", 1);
				startTimer(1);
				caseSolver();
				endTimer(1);
				out.println();
			}
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
			String elementName = st.nextElement().toString();
			classPath += FILE_SEPARATOR + elementName;
			if (!st.hasMoreElements())
				className = elementName;
		}

		classPath += ".java";

		try {
			Files.copy(new File(classPath).toPath(), new File(BASE + className + ".java").toPath(),
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
	// XXXXXXXXXXXXXXXXXX-----CODEJAM TEMPLATE-----XXXXXXXXXXXXXXXXXX
}
