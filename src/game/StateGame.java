package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateGame extends BasicGameState {
	private int[][] cur_map;
	static SpriteSheet island;

	public int[][] loadSheet(String fname, int def) {
		int[][] retMap = new int[32][32];

		String hor_line;
		int y = 0;
		try {
			Scanner scanner = new Scanner(new File(fname));
			while (scanner.hasNextLine()) {
				Arrays.fill(retMap[y], new Integer(def));
				hor_line = scanner.nextLine();
				for (int x = 0; x < hor_line.length(); x++) {
					char c = hor_line.charAt(x);
					if (c > 57) {
						c = (char) (10 + (int) (c) - 97);
					} else {
						c = (char) (c - 48);
					}
					retMap[y][x] = c;
				}
				y++;
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return retMap;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		// TODO Auto-generated method stub
		island = new SpriteSheet(new Image("overworld_tiles.png"), 16, 16);
		island.setFilter(Image.FILTER_NEAREST);
		cur_map = loadSheet("cisland.txt", 1);
		int f = (int) ('0');
		System.out.println(f);

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		for (int i = 0; i < 32; i++) {
			for (int j = 0; j < 32; j++) {

				// g.drawString(String.valueOf(cur_map[i][j]), j * 32, i * 16);
				island.getSprite(cur_map[i][j], 1).draw(j * 32, i * 32, 32, 32);
			}
		}

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
}
