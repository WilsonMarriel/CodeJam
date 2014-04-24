package _2013.Qualifiers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class TicTacToeTomek {

	public String readAndSolve(String filePath) {
		int cases = 0;
		int current_case = 1;
		StringBuilder output = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			cases = Integer.valueOf(br.readLine());
			int row = 0;
			int p = 0;
			int[][] winning_lines = new int[2][10];
			for (String line; (line = br.readLine()) != null;) {
				if (current_case > cases)
					break;

				for (int column = 0; column < 4; column++) {
					char c = line.charAt(column);
					if (c == 'X' || c == 'T') {
						winning_lines[0][row]++;
						winning_lines[0][4 + column]++;
						if (row == column)
							winning_lines[0][8]++;
						if (row + column == 3)
							winning_lines[0][9]++;
					}
					if (c == 'O' || c == 'T') {
						winning_lines[1][row]++;
						winning_lines[1][4 + column]++;
						if (row == column)
							winning_lines[1][8]++;
						if (row + column == 3)
							winning_lines[1][9]++;
					}
					if (c == '.')
						p++;
				}
				row++;

				if (row == 4) {
					output.append("Case #" + current_case + ": ");
					boolean has_winner = false;

					for (int i = 0; i < 2 && !has_winner; i++) {
						for (int j = 0; j < 10 && !has_winner; j++) {
							if (winning_lines[i][j] == 4) {
								if (i == 0)
									output.append("X won");
								else
									output.append("O won");

								has_winner = true;
							}
						}
					}
					if (!has_winner) {
						if (p > 0)
							output.append("Game has not completed");
						else
							output.append("Draw");
					}
					output.append(System.getProperty("line.separator"));

					current_case++;
					row = 0;
					p = 0;
					winning_lines = new int[2][10];
					if ((line = br.readLine()) != null)
						continue;
					else
						break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		print(output.toString(), filePath.replace(".in", "") + ".out");
		return output.toString();
	}

	public void print(String output, String filePath) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(filePath, "UTF-8");
			writer.println(output);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TicTacToeTomek a = new TicTacToeTomek();
		System.out.print(a.readAndSolve("C:\\Users\\Wilson\\Downloads\\A-large-practice.in"));

	}
}
