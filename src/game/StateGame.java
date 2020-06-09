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
	int[] collidables;
	static SpriteSheet island;
	static Animation character;

	static Image player_sheet;
	static Animation playerUp;
	static Animation playerDown;
	static Animation playerLeft;
	static Animation playerRight;
	float px;
	float py;
	float tx;
	float ty;
	boolean moving = false;

	// Calculated using real game
	float speed = 0.063f;

	float vp_x = 0;
	float vp_y = 0;
	int vp_w = Game.WIDTH;
	int vp_h = Game.HEIGHT;

	public void move_target(int movx, int movy) {
		if (moving == false) {
			if (collision(cur_map[(int) ty + movy][(int) tx + movx]) == false) {
				moving = true;
				tx += movx;
				ty += movy;

				System.out.println();
			}
		}
	}

	private boolean collision(int c) {
		for (int i = 0; i < collidables.length; i++) {
			if (collidables[i] == c) {
				return true;
			}
		}
		return false;
	}

	public int[][] loadSheet(String fname, int def) {
		// arbitrary size that will be changed later. (or maybe made dynamic) (probably not)
		int[][] retMap = new int[32][32];

		for (int i = 0, len = retMap.length; i < len; i++)
			Arrays.fill(retMap[i], def);

		String hor_line;
		int y = 0;
		try {
			Scanner scanner = new Scanner(new File(fname));
			hor_line = scanner.nextLine();
			while (hor_line.contains("*") == false) {
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
				hor_line = scanner.nextLine();
			}
			String[] cols = scanner.nextLine().split(" ");
			String[] start = scanner.nextLine().split(" ");
			collidables = new int[cols.length];
			for (int i = 0; i < collidables.length; i++) {
				collidables[i] = Integer.parseInt(cols[i]);
			}
			px = Integer.parseInt(start[0]);
			py = Integer.parseInt(start[1]);
			tx = px;
			ty = py;

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

		SpriteSheet p = new SpriteSheet(player_sheet.getSubImage(0, 0, 32, 16), 16, 16, 0);
		playerRight = new Animation(p, 1);
		playerRight.setSpeed((float) 0.003);

		p = new SpriteSheet(player_sheet.getSubImage(32, 0, 32, 16), 16, 16, 0);
		playerDown = new Animation(p, 1);
		playerDown.setSpeed((float) 0.003);

		p = new SpriteSheet(player_sheet.getSubImage(64, 0, 32, 16), 16, 16, 0);
		playerLeft = new Animation(p, 1);
		playerLeft.setSpeed((float) 0.003);

		p = new SpriteSheet(player_sheet.getSubImage(96, 0, 32, 16), 16, 16, 0);
		playerUp = new Animation(p, 1);
		playerUp.setSpeed((float) 0.003);

		island = new SpriteSheet(new Image("overworld_tiles.png"), 16, 16);
		island.setFilter(Image.FILTER_NEAREST);
		cur_map = loadSheet("cisland.txt", 1);

		character = playerUp;
		// character.setSpeed(0.05f);

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		g.scale(4, 4);
		g.translate(vp_x, vp_y);

		for (int i = 0; i < 32; i++) {
			for (int j = 0; j < 32; j++) {

				// g.drawString(String.valueOf(cur_map[i][j]), j * 32, i * 16);
				island.getSprite(cur_map[i][j], 1).draw(j * 16, i * 16, 16, 16);
				character.draw(px * 16, py * 16, 16, 16);
			}
		}

		// g.translate(-vp_x, -vp_y);
		// g.scale(0.4f, 0.4f);

		g.setColor(Color.black);
		g.fillRect(2, 45, 1000, 100);
		g.setColor(Color.white);
		g.drawString(String.format("px: %f , py: %f  | tx: %f , ty: %f", px, py, tx, ty), 2, 50);
		g.drawString(String.format("vpx: %f , vpy: %f", vp_x, vp_y), 2, 70);

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {

		if (px != tx || py != ty) {
			playerWalk();
		}
		vp_x = 120 - (px * 16);
		vp_y = 111 - (py * 16);

	}

	private void playerWalk() {
		if (px < tx) {
			px += speed;
			if (px >= tx) {
				px = tx;
				moving = false;
			}

		}

		if (px > tx) {
			px -= speed;
			if (px <= tx) {
				px = tx;
				moving = false;
			}

		}

		if (py < ty) {
			py += speed;
			if (py >= ty) {
				py = ty;
				moving = false;
			}

		}

		if (py > ty) {
			py -= speed;
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

	public void setPlayerAnim(Animation pa) {
		if (!moving) {
			character = pa;
		}

	}
}
