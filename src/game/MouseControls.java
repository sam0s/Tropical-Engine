package game;

import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

public class MouseControls implements MouseListener {
	StateGame s;

	int delta;

	public MouseControls(StateGame s) {
		this.s = s;
		this.delta = 0;
	}

	public void set_delta(int delta) {
		this.delta = delta;
	}

	@Override
	public void inputEnded() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputStarted() {
		// TODO Auto-generated method stub

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
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		// if (s.dragging) {
		// if s.mo
		// s.vp_x += newx - oldx;
		// s.vp_y += newy - oldy;
		// }

	}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {
		System.out.println(arg0);
		/* check if mouse is over entity */
		if (arg0 == 0) {
		}
	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheelMoved(int arg0) {
		// TODO Auto-generated method stub
		
	}

}
