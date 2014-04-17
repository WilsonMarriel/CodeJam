package templates.IO;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class IOOperations {
	// ${:import(java.io.FileReader,
	// java.io.IOException,
	// java.util.Locale,
	// java.util.Scanner)}
	@SuppressWarnings({ "unused", "resource" })
	private static void readFile(String filePath) {
		try {
			Scanner reader = new Scanner(new FileReader(filePath));
			reader.useLocale(Locale.US);

			while (reader.hasNext()) {
				String line = reader.nextLine();
				// ${cursor}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ${:import(java.io.PrintWriter,
	// java.io.IOException,
	// java.util.ArrayList)}
	@SuppressWarnings({ "unused", "resource" })
	private static void writeFile(ArrayList<String> lines, String filePath) {
		try {
			PrintWriter writer = new PrintWriter(filePath);

			for (String line : lines) {
				// ${cursor}
				writer.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
