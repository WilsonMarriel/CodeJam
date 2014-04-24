package _2013.Qualifiers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Treasure {
	static int cases;
	static StringBuilder output;

	public class Chest {
		int id;
		int lock;
		HashMap<Integer, Integer> keytype_count;
		boolean is_used;

		Chest(int id, int lock, HashMap<Integer, Integer> keytype_count) {
			this.id = id;
			this.lock = lock;
			this.keytype_count = keytype_count;
			is_used = false;
		}
	}

	public static class FieldState {

		HashMap<Integer, ArrayList<Integer>> type_chestsid; // type, chests that open with this type
		HashMap<Integer, Chest> id_chests;// chest_id, chest object
		HashMap<Integer, Integer> keytype_count;// type of key in my hand, quantity of that type of key in my hand (> 0)
		ArrayList<Integer> path = new ArrayList<Integer>();

		FieldState(HashMap<Integer, Integer> keytype_count, HashMap<Integer, Chest> id_chests,
				HashMap<Integer, ArrayList<Integer>> type_chestsid) {
			path = new ArrayList<Integer>();
			this.keytype_count = keytype_count;
			this.id_chests = id_chests;
			this.type_chestsid = type_chestsid;
		}

		boolean checkIfCountIsOk() {
			HashMap<Integer, Integer> type_count = new HashMap<Integer, Integer>(keytype_count);// in hand
			HashMap<Integer, Integer> chest_count = new HashMap<Integer, Integer>();// chest locks counter
			for (Chest c : id_chests.values()) {
				if (!c.is_used) {
					// put chest lock in chest_count
					if (chest_count.containsKey(c.lock))
						chest_count.put(c.lock, chest_count.get(c.lock) + 1);
					else
						chest_count.put(c.lock, 1);
					// sum chest keys with in hand keys
					for (Integer k : c.keytype_count.keySet()) {
						if (type_count.containsKey(k))
							type_count.put(k, type_count.get(k) + c.keytype_count.get(k));
						else
							type_count.put(k, c.keytype_count.get(k));
					}
				}
			}
			for (Integer t : chest_count.keySet()) {
				if (chest_count.get(t) > type_count.get(t))
					return false;
			}
			return true;
		}

		ArrayList<Integer> getOpenableAvaiableChests() {// get chests not used still, and that opens with a key i have
			ArrayList<Integer> openable = new ArrayList<Integer>();

			for (int key : keytype_count.keySet()) {
				if (!type_chestsid.containsKey(key))
					continue; // if there is no chest that opens with that type, go to next type
				for (Integer chest_id : type_chestsid.get(key))
					// each chest openable
					if (!id_chests.get(chest_id).is_used)
						openable.add(chest_id);// checks if chest was already used or not
			}
			return openable;
		}

		void resetAllChests() {
			for (Chest chest : id_chests.values())
				chest.is_used = false;
		}

		boolean checkForUnopenedChest() {
			for (Chest chest : id_chests.values())
				if (chest.is_used == false)
					return true;// if there is a chest not used, return true
			return false;// if all chests were used, return false
		}

		boolean buildPath() {
			if (!checkIfCountIsOk())
				return false;
			return buildPathLogic();
		}

		boolean buildPathLogic() {
			ArrayList<Integer> openable = getOpenableAvaiableChests();
			if (openable.isEmpty()) {// can't open any chest
				if (checkForUnopenedChest())
					return false;// path is stuck
				else
					return true;// path found
			}
			Collections.sort(openable);// sort the chests (so i get minimal path first)

			for (Integer chestid : openable) {// iterate through all possible paths
				Chest c = id_chests.get(chestid);
				c.is_used = true;

				for (Integer keytype : c.keytype_count.keySet()) {// add found keys to my hand
					if (keytype_count.containsKey(keytype))
						keytype_count.put(keytype, keytype_count.get(keytype) + c.keytype_count.get(keytype));
					else
						keytype_count.put(keytype, c.keytype_count.get(keytype));
				}

				// remove used keytype from my hand
				keytype_count.put(c.lock, keytype_count.get(c.lock) - 1);
				if (keytype_count.get(c.lock) == 0)
					keytype_count.remove(c.lock);

				if (buildPath()) {// DFS
					path.add(0, c.id);
					return true;
				} else {// get to the previous state
						// get back used key
					if (keytype_count.containsKey(c.lock))
						keytype_count.put(c.lock, keytype_count.get(c.lock) + 1);
					else
						keytype_count.put(c.lock, 1);

					for (Integer keytype : c.keytype_count.keySet()) {// give back keys found inside
						keytype_count.put(keytype, keytype_count.get(keytype) - c.keytype_count.get(keytype));
						if (keytype_count.get(keytype) == 0)
							keytype_count.remove(keytype);
					}
					c.is_used = false;// chest is not used anymore
				}
			}
			return false;// path not found
		}
	}

	void readAndWrite(String inputPath) {
		output = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(inputPath))) {
			cases = Integer.valueOf(br.readLine());

			for (int current_case = 1; current_case <= cases; current_case++) {
				HashMap<Integer, ArrayList<Integer>> type_chestsid = new HashMap<Integer, ArrayList<Integer>>();
				HashMap<Integer, Chest> id_chests = new HashMap<Integer, Chest>();
				HashMap<Integer, Integer> keytype_count = new HashMap<Integer, Integer>();
				int start_keys_count, chests_count;
				// getting number of chests and number of keys at beginning
				String[] begin = br.readLine().split(" ");
				start_keys_count = Integer.valueOf(begin[0]);
				chests_count = Integer.valueOf(begin[1]);

				// get starting key types
				String[] keytypes = br.readLine().split(" ");
				for (int current_key = 0; current_key < start_keys_count; current_key++) {
					Integer keytype = Integer.valueOf(keytypes[current_key]);
					if (keytype_count.containsKey(keytype))
						keytype_count.put(keytype, keytype_count.get(keytype) + 1);
					else
						keytype_count.put(keytype, 1);
				}

				// get chests
				for (int current_chest = 1; current_chest <= chests_count; current_chest++) {
					String[] chest_string = br.readLine().split(" ");

					// getting keys_inside map of the chest
					int keys_inside_count = Integer.valueOf(chest_string[1]);
					HashMap<Integer, Integer> chest_keytype_count = new HashMap<Integer, Integer>();
					for (int i = 2; i < keys_inside_count + 2; i++) {
						if (chest_keytype_count.containsKey(Integer.valueOf(chest_string[i])))
							chest_keytype_count.put(Integer.valueOf(chest_string[i]),
									chest_keytype_count.get(Integer.valueOf(chest_string[i])) + 1);
						else
							chest_keytype_count.put(Integer.valueOf(chest_string[i]), 1);
					}

					Chest chest = new Chest(current_chest, Integer.valueOf(chest_string[0]), chest_keytype_count);
					id_chests.put(chest.id, chest);// insert chest into chests map
					// insert into type_chestsid map of type, chests that open with this type
					if (type_chestsid.containsKey(chest.lock))
						type_chestsid.get(chest.lock).add(chest.id);
					else {
						ArrayList<Integer> chestsidlist = new ArrayList<Integer>();
						chestsidlist.add(chest.id);
						type_chestsid.put(chest.lock, chestsidlist);
					}
				}
				// finished populating case, starting process case
				FieldState fieldstate = new FieldState(keytype_count, id_chests, type_chestsid);
				output.append("Case #" + current_case + ":");
				if (fieldstate.buildPath()) {// path found
					for (Integer chestid : fieldstate.path) {
						output.append(" " + chestid);
					}
				} else {// path not found
					output.append(" IMPOSSIBLE");
				}
				output.append(System.getProperty("line.separator"));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		PrintWriter writer;
		try {
			writer = new PrintWriter(inputPath.replace(".in", "") + ".out", "UTF-8");
			writer.println(output);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Treasure rs = new Treasure();
		rs.readAndWrite("C:\\Users\\Wilson\\Desktop\\D-small-practice.in");
	}
}