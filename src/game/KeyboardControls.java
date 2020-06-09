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

	static final int KEY_W = 17;
	static final int KEY_A = 30;
	static final int KEY_S = 31;
	static final int KEY_D = 32;

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
			case LARROW:
			case KEY_A:
				s.setPlayerAnim(StateGame.playerLeft);
				s.move_target(-1,0);
				break;
			case RARROW:
			case KEY_D:
				s.setPlayerAnim(StateGame.playerRight);
				s.move_target(1,0);
				break;
			case UPARROW:
			case KEY_W:
				s.setPlayerAnim(StateGame.playerUp);
				s.move_target(0,-1);
				break;
			case DWNARROW:
			case KEY_S:
				s.setPlayerAnim(StateGame.playerDown);
				s.move_target(0,1);
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
