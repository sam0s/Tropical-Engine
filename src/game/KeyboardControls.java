package game;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import java.util.Vector;

public class KeyboardControls implements KeyListener {

	StateGame s;

	int delta;
	Vector<Integer> keys;
	boolean shift;
	boolean ctrl;

	static final int LSHIFT = 42;
	static final int RSHIFT = 58;
	static final int LARROW = 203;
	static final int RARROW = 205;
	static final int UPARROW = 200;
	static final int DWNARROW = 208;
	static final int LCTRL = 29;
	static final int RCTRL = 157;
	
	static final int KEY_SPACE = 57;
	static final int KEY_ESC = 1;
	static final int KEY_W = 17;
	static final int KEY_A = 30;
	static final int KEY_S = 31;
	static final int KEY_D = 32;
	
	static final int up_bind = KEY_W;
	static final int down_bind = KEY_S;
	static final int left_bind = KEY_A;
	static final int right_bind = KEY_D;
	static final int action_bind = KEY_SPACE;
	static final int back_bind = KEY_ESC;

	public KeyboardControls(StateGame s) {
		this.s = s;
		this.delta = 0;
		this.keys = new Vector<Integer>();

		this.shift = false;
		this.ctrl = false;
	}

	public void set_delta(int delta) {
		this.delta = delta;
	}

	@Override
	public void inputEnded() {

	}

	@Override
	public void inputStarted() {
		// TODO Auto-generated method stub
		for (int k : keys) {
			switch (k) {
			case 1: /* escape */
				break;
			case left_bind:
				StateGame.mike.setPlayerAnim("left");
				StateGame.mike.move_target(-1, 0);
				break;
			case right_bind:
				StateGame.mike.setPlayerAnim("right");
				StateGame.mike.move_target(1, 0); 
				break;
			case up_bind:
				StateGame.mike.setPlayerAnim("up");
				StateGame.mike.move_target(0, -1);
				break;
			case down_bind:
				StateGame.mike.setPlayerAnim("down");
				StateGame.mike.move_target(0, 1);
				break;
			case action_bind:
				StateGame.mike.action();
				break;
			}
		}
	}

	@Override
	public boolean isAcceptingInput() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setInput(Input arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(int arg0, char arg1) {
		System.out.println(String.format("%d %c", arg0, arg1));
		switch (arg0) {
		case LSHIFT:
		case RSHIFT:
			shift = true;
			break;
		case LCTRL:
		case RCTRL:
			ctrl = true;
			break;
		}
		keys.addElement(arg0);
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		keys.removeIf(n -> ((Integer) n.intValue() == arg0));
		switch (arg0) {
		case LSHIFT:
		case RSHIFT:
			shift = false;
			break;
		case LCTRL:
		case RCTRL:
			ctrl = false;
			break;
		}
	}

}
