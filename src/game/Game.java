package game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Random;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {

	static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	static Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();

	// Add more of these when there are more states
	public static final int GAME = 0;

	// Application Properties
	public static final int SCALE = 2;
	public static final int WIDTH = 1280; // * SCALE;
	public static final int HEIGHT = 720; // * SCALE;

	public static final int FPS = 61;
	// public static final int FPS = 100;
	public static final Color win_inner = new Color(40, 40, 40, 225);
	public static final Color win_outer = Color.orange;
	public static final Color clear = new Color(0, 0, 0, 0);
	public static final int win_pad = 4;
	// major.minor(patch)
	public static final double VERSION = 1.06;

	public static String addChar(String str, char ch, int position) {
		return str.substring(0, position) + ch + str.substring(position);
	}

	public static String wordWrap(String input, int linemax, int cutoff) {
		String line = input;
		String returnLine = input;
		int y = 0;
		int cursor = 0;
		int offset = 0;
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == '&') {
				cursor = 0;
			}
			if (cursor > linemax) {
				if (line.charAt(i) == ' ' || cursor > linemax + cutoff) {

					returnLine = addChar(returnLine, '\n', (i + offset) + 1);
					offset += 1;
					y += 1;
					cursor = 0;
				}
			}
			cursor++;
		}
		return returnLine;
	}

	public Game(String name) {
		super(name);

	}

	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new StateGame());
		this.addState(new BattleState());
	}

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new Game("Tropical Engine v" + VERSION));

			app.setDisplayMode(WIDTH, HEIGHT, false);
			app.setFullscreen(false);
			// app.setFullscreen(true);
			app.setTargetFrameRate(FPS);
			app.setShowFPS(true);
			app.setUpdateOnlyWhenVisible(false);
			app.start();

		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
