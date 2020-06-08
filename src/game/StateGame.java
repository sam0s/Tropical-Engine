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
	static Animation character;

	static Image player_sheet;
	static Animation playerUp;
	static Animation playerDown;
	static Animation playerLeft;
	static Animation playerRight;
	float px = 20;
	float py = 20;
	float tx = 20;
	float ty = 20;
	boolean moving = false;

	public void move_target(int movx, int movy) {
		if (moving == false) {
			moving = true;
			tx += movx;
			ty += movy;
		}
	}

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
		KeyboardControls kc = new KeyboardControls(this);
		arg0.getInput().addKeyListener(kc);
		// TODO Auto-generated method stub
		player_sheet = new Image("overworld_sheet.png");
		player_sheet.setFilter(Image.FILTER_NEAREST);

		SpriteSheet p = new SpriteSheet(player_sheet.getSubImage(0, 0, 32, 16), 13, 16, 3);
		playerRight = new Animation(p, 1);
		playerRight.setSpeed((float) 0.005);

		p = new SpriteSheet(player_sheet.getSubImage(0, 19, 32, 16), 13, 16, 3);
		playerLeft = new Animation(p, 1);
		playerLeft.setSpeed((float) 0.005);

		p = new SpriteSheet(player_sheet.getSubImage(33, 0, 32, 16), 13, 16, 5);
		playerDown = new Animation(p, 1);
		playerDown.setSpeed((float) 0.005);

		p = new SpriteSheet(player_sheet.getSubImage(33, 19, 32, 16), 13, 16, 5);
		playerUp = new Animation(p, 1);
		playerUp.setSpeed((float) 0.005);

		island = new SpriteSheet(new Image("overworld_tiles.png"), 16, 16);
		island.setFilter(Image.FILTER_NEAREST);
		cur_map = loadSheet("cisland.txt", 1);

		character = playerUp;
		// character.setSpeed(0.05f);

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {

		for (int i = 0; i < 32; i++) {
			for (int j = 0; j < 32; j++) {

				// g.drawString(String.valueOf(cur_map[i][j]), j * 32, i * 16);
				island.getSprite(cur_map[i][j], 1).draw(j * 32, i * 32, 32, 32);
				character.draw(px * 32, py * 32, 22, 32);
			}
		}
		g.setColor(Color.black);
		g.fillRect(2, 45, 1000, 32);
		g.setColor(Color.white);
		g.drawString(String.format("px: %f , py: %f  | tx: %f , ty: %f", px, py, tx, ty), 2, 50);

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		if (px != tx || py != ty) {
			playerWalk();
		}

	}

	private void playerWalk() {
		if (px < tx) {
			px += 0.05;
			if (px >= tx) {
				px = tx;
				moving = false;
			}

		}

		if (px > tx) {
			px -= 0.05;
			if (px <= tx) {
				px = tx;
				moving = false;
			}

		}

		if (py < ty) {
			py += 0.05;
			if (py >= ty) {
				py = ty;
				moving = false;
			}

		}

		if (py > ty) {
			py -= 0.05;
			if (py <= ty) {
				py = ty;
				moving = false;
			}

		}

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
}
