package game;

import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateGame extends game.BasicGameState implements KeyListener {
	public static Enemy currentEnemy = null;
	int[][] cur_map;
	Vector<Integer> keys;
	static int[] collidables;
	static SpriteSheet island;
	static Animation character;
	static Player mike;

	Menu currentMenu;
	Menu overworldMenu;

	static String currentText = "";
	static float textCursor = 0;
	static String nextText = "";
	static double textSpeed = 0.5;

	java.awt.Font fontRaw = null;
	public static Font f_32, f_18, f_24, f_16, f_14;
	static Image textBorder;
	static Image alert;

	// Calculated using real game
	float speed = 0.063f;

	float vp_x = 0;
	float vp_y = 0;
	int vp_w = Game.WIDTH;
	int vp_h = Game.HEIGHT;

	// lets try to make this work :)
	String[] dests = new String[10];
	int[][] hops = new int[10][2];
	int[] s_tiles = new int[10];

	static NPC[] npcs = new NPC[10];
	public static Image currentPortrait;
	public static Image currentPortraitBack;
	static Image cursorSprite;

	StateBasedGame psbg;
	GameContainer gc;

	String area = "CISLAND";

	public void startBattle() {
		psbg.enterState(1);
	}

	public void init_fonts() {
		try {
			fontRaw = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new java.io.File("TerminusTTF-Bold-4.47.0.ttf"));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		if (fontRaw == null) {
			fontRaw = new java.awt.Font("Default", 0, 28);
		}

		f_32 = new TrueTypeFont(fontRaw.deriveFont(32f), true);
		f_24 = new TrueTypeFont(fontRaw.deriveFont(24f), true);
		f_18 = new TrueTypeFont(fontRaw.deriveFont(18f), true);
		f_16 = new TrueTypeFont(fontRaw.deriveFont(16f), true);
		f_14 = new TrueTypeFont(fontRaw.deriveFont(14f), true);
	}

	public boolean collision(int c) {
		for (int i = 0; i < collidables.length; i++) {
			if (collidables[i] == c) {
				return true;
			}
		}
		return false;
	}

	public void drawDialog(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(20, 440, 1240, 240);
		g.setColor(Color.white);
		if (textCursor < currentText.length()) {

			if (currentText.charAt((int) textCursor) == '&') {
				nextText = currentText.substring((int) textCursor + 1);
				currentText = currentText.substring(0, (int) textCursor);
				System.out.println(currentText);
				System.out.println(nextText);
				textCursor = currentText.length();
			}
			textCursor += textSpeed;
		}
		currentPortraitBack.draw(76, 473);
		if (currentText.charAt(0) == ' ') {
			mike.portrait.draw(76, 473);
		} else {
			currentPortrait.draw(76, 473);
		}
		textBorder.draw(7, 430);

		f_32.drawString(246, 470, currentText.substring(0, (int) textCursor));
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

	public int[][] loadSheet(String fname) throws SlickException, FileNotFoundException {

		// arbitrary size that will be changed later. (or maybe made dynamic) (probably not)
		int[][] retMap = new int[32][32];
		npcs = new NPC[10];
		for (int i = 0, len = retMap.length; i < len; i++) {
			Arrays.fill(retMap[i], 1);
		}

		String hor_line;
		int y = 0;
		try {
			Scanner scanner = new Scanner(new File(area + "\\" + fname + ".lvl"));
			hor_line = scanner.nextLine();
			while (hor_line.contains("*") == false) {
				for (int x = 0; x < hor_line.length(); x++) {
					int c = hor_line.charAt(x);
					if ((c >= 65 && c <= 68) == false) {
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

			int tn = 0;
			while (scanner.hasNextLine()) {
				dests[tn] = scanner.nextLine();
				String[] hop = scanner.nextLine().split(" ");
				hops[tn][0] = Integer.parseInt(hop[0]);
				hops[tn][1] = Integer.parseInt(hop[1]);
				s_tiles[tn] = char_to_id(hop[2].charAt(0));
				tn++;
			}

			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner scanner = new Scanner(new File(area + "\\" + fname + ".lda"));
		island = new SpriteSheet(new Image(scanner.nextLine()), 32, 32);
		int npn = Integer.parseInt(scanner.nextLine());
		island.setFilter(Image.FILTER_NEAREST);
		if (npn > 0) {
			String np[] = scanner.nextLine().split(" ");
			npcs[0] = new NPC(np[0], Integer.parseInt(np[1]), Integer.parseInt(np[2]), this);
		}
		scanner.close();
		return retMap;
	}

	// ***********************************************************
	// ***********************************************************
	// INIT INIT INIT INIT INIT INIT INIT
	// ***********************************************************
	@Override
	public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
		init_fonts();
		psbg = arg1;
		this.gc = gc;
		alert = new Image("noti.png");
		currentPortrait = new Image("gfx\\por_mike.png");
		textBorder = new Image("gfx\\ropeborder.png");
		cursorSprite = new Image("gfx\\cursor.png");
		currentPortraitBack = new Image("gfx\\porback_desert.png");
		currentEnemy = new Enemy(1);
		overworldMenu = new Menu(32, 32);
		currentMenu = overworldMenu;

		this.keys = new Vector<Integer>();

		try {
			mike = new Player(20, 18, this);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			cur_map = loadSheet("testarea");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// ***********************************************************
	// ***********************************************************

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g) throws SlickException {
		g.scale(Game.SCALE, Game.SCALE);
		g.translate(vp_x, vp_y);

		// Only draw what we can see in the view
		for (int i = (int) (mike.y - 6); i < (int) mike.y + 7.5; i++) {
			for (int j = (int) (mike.x - 10.4); j < (int) mike.x + 11.5; j++) {
				if (i > 0 && i < cur_map.length && j > 0 && j < cur_map[0].length) {

					int tile = cur_map[i][j];
					int t = tile;
					if (tile >= 65 && tile <= 68) {
						t = s_tiles[tile - 65];

					}
					if (t < 999) {
						island.getSprite(t, 1).draw(j * 32, i * 32, 32, 32);
					}
					// g.drawString(String.valueOf(cur_map[i][j]), j * 16, i * 16);
				}

			}
		}

		for (OverworldEntity ent : npcs) {
			if (ent != null) {
				ent.draw(32, 32);
			}
		}

		mike.draw(32, 32);

		g.translate(-vp_x, -vp_y);
		g.scale((float) 1 / (Game.SCALE), (float) 1 / (Game.SCALE));

		if (currentMenu != null) {
			currentMenu.draw(g);
		}

		if (currentText.length() > 0) {
			drawDialog(g);
		}

		// g.drawString(String.format("px: %f , py: %f  | tx: %f , ty: %f", mike.x, mike.y, mike.tx, mike.ty), 2, 50);

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {

		// could be a better way to do this? (move this to level load will work but do it later)
		for (int k : keys) {

			switch (k) {
			case 1: /* escape */
				break;
			case KeyboardControls.left_bind:
				StateGame.mike.move_target(-1, 0);
				break;
			case KeyboardControls.right_bind:
				StateGame.mike.move_target(1, 0);
				break;
			case KeyboardControls.up_bind:
				StateGame.mike.move_target(0, -1);
				break;
			case KeyboardControls.down_bind:
				StateGame.mike.move_target(0, 1);
				break;
			}
		}
		for (OverworldEntity ent : npcs) {
			if (ent != null) {
				try {
					ent.update();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		try {
			mike.update();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		vp_x = 190 + 120 - (mike.x * 32);
		vp_y = 50 + 111 - (mike.y * 32);

	}

	@Override
	public void keyPressed(int arg0, char arg1) {
		if (currentMenu != null) {
			return;
		}
		System.out.println(String.format("%d %c", arg0, arg1));

		keys.addElement(arg0);
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		keys.removeIf(n -> ((Integer) n.intValue() == arg0));

		if (currentMenu != null) {
			overworldMenu.keyPressed(arg0, this);
			return;
		}
		switch (arg0) {
		case KeyboardControls.action_bind:
			if (StateGame.mike.action()) {
				currentMenu = overworldMenu;
			}
			// startBattle();
			break;
		}
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public void actionPerformed(int action) {
		System.out.println(action);
		switch (action) {
		case 0:
			currentMenu = null;
			break;
		case 3:
			this.gc.exit();
			break;

		}

	}
}
