package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateGame extends BasicGameState {
	int[][] cur_map;
	int[] collidables;
	static SpriteSheet island;
	static Animation character;
	OverworldEntity mike;
	// Calculated using real game
	float speed = 0.063f;

	float vp_x = 0;
	float vp_y = 0;
	int vp_w = Game.WIDTH;
	int vp_h = Game.HEIGHT;

	// sub-level file locations, could be expanded but 3 is plenty surely.
	String a_dest = "";
	String b_dest = "";
	String c_dest = "";

	int[] a_hop = { 0, 0 };
	int[] b_hop = { 0, 0 };
	int[] c_hop = { 0, 0 };

	int a_tile = 0;
	int b_tile = 0;
	int c_tile = 0;

	String area = "CISLAND";

	public boolean collision(int c) {
		for (int i = 0; i < collidables.length; i++) {
			if (collidables[i] == c) {
				return true;
			}
		}
		return false;
	}

	public int char_to_id(int c) {
		if (c == 88) {
			c = 999;
		} else {
			if (c > 57) {
				c = (char) (10 + (int) (c) - 97);
			} else {
				c = (char) (c - 48);
			}
		}
		return c;
	}

	public int[][] loadSheet(String fname, int def) throws SlickException, FileNotFoundException {

		// arbitrary size that will be changed later. (or maybe made dynamic) (probably not)
		boolean A_level = false, B_level = false, C_level = false;
		int[] aloc = { 0, 0 };
		int[] bloc = { 0, 0 };
		int[] cloc = { 0, 0 };

		int[][] retMap = new int[32][32];

		for (int i = 0, len = retMap.length; i < len; i++)
			Arrays.fill(retMap[i], def);

		String hor_line;
		int y = 0;
		try {
			Scanner scanner = new Scanner(new File(area + "\\" + fname + ".lvl"));
			hor_line = scanner.nextLine();
			while (hor_line.contains("*") == false) {
				for (int x = 0; x < hor_line.length(); x++) {
					int c = hor_line.charAt(x);
					if (c == 65) {
						c = 400;
						aloc[0] = x;
						aloc[1] = y;
						A_level = true;
					} else {
						c = char_to_id(c);
					}
					retMap[y][x] = c;
				}
				y++;
				hor_line = scanner.nextLine();
			}
			String[] cols = scanner.nextLine().split(" ");
			collidables = new int[cols.length];
			for (int i = 0; i < collidables.length; i++) {
				int a = (int) (cols[i].charAt(0));
				a = char_to_id(a);
				collidables[i] = a;
			}

			if (A_level) {
				a_dest = scanner.nextLine();
				String[] hop = scanner.nextLine().split(" ");
				a_hop[0] = Integer.parseInt(hop[0]);
				a_hop[1] = Integer.parseInt(hop[1]);
				a_tile = char_to_id(hop[2].charAt(0));
			}

			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner scanner = new Scanner(new File(area + "\\" + fname + ".lda"));
		island = new SpriteSheet(new Image(scanner.nextLine()), 16, 16);
		island.setFilter(Image.FILTER_NEAREST);
		return retMap;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		KeyboardControls kc = new KeyboardControls(this);
		arg0.getInput().addKeyListener(kc);
		mike = new OverworldEntity(new Image("overworld_sheet.png"), 20, 20, this);
		try {
			cur_map = loadSheet("cisland", 1);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		g.scale(4, 4);
		g.translate(vp_x, vp_y);

		// Only draw what we can see in the view

		for (int i = (int) (mike.y - 7); i < (int) mike.y + 8.5; i++) {
			for (int j = (int) (mike.x - 7.5); j < (int) mike.x + 9.5; j++) {
				if (i > 0 && i < cur_map.length && j > 0 && j < cur_map[0].length) {

					int tile = cur_map[i][j];
					int t = (tile == 400) ? a_tile : (tile == 500) ? b_tile : (tile == 600) ? c_tile : tile;
					if (t < 999) {
						island.getSprite(t, 1).draw(j * 16, i * 16, 16, 16);
					}
					// g.drawString(String.valueOf(cur_map[i][j]), j * 16, i * 16);
				}

			}
		}
		mike.draw(16, 16);

		g.translate(-vp_x, -vp_y);
		g.scale(0.4f, 0.4f);

		g.setColor(Color.black);
		// g.fillRect(2, 45, 1000, 100);
		g.setColor(Color.white);
		g.drawString(String.format("px: %f , py: %f  | tx: %f , ty: %f", mike.x, mike.y, mike.tx, mike.ty), 2, 50);

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		try {
			mike.update();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vp_x = 120 - (mike.x * 16);
		vp_y = 111 - (mike.y * 16);

	}

	@Override
	public int getID() {
		return 0;
	}
}
