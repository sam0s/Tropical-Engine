package game;
import java.awt.Dimension;
import java.awt.Toolkit;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {

	public Game(String name) {
		super(name);
	}

	static Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();

	// Add more of these when there are more states
	public static final int GAME = 0;
	public static final int GLOBE = 1;

	// don't take this personally
	public static final boolean MALE = true;
	public static final boolean FEMALE = false;

	// Application Properties

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	// public static final int WIDTH = (int) ss.getWidth();
	// public static final int HEIGHT = (int) ss.getHeight();

	public static final int MENU_BAR_HEIGHT = 68;
	public static final int FPS = 500;
	public static final Color win_inner = new Color(40, 40, 40, 225);
	public static final Color win_outer = Color.orange;
	public static final Color clear = new Color(0, 0, 0, 0);
	public static final int win_pad = 4;
	// major.minor(patch)
	public static final double VERSION = 0.01;

	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new StateGame());
		//this.addState(new StateGlobe());
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
