package game;

import java.io.FileNotFoundException;

import org.newdawn.slick.SlickException;

public class NPC extends OverworldEntity {
	String text;
	int state;
	int alert = 0;

	public NPC(String identity, float x, float y, StateGame s) throws SlickException, FileNotFoundException {
		super(identity, x, y, s);
		this.text="Hi there I am deborah.";
	}

	@Override
	public void draw(int sx, int sy) {
		current.draw(x * 32, y * 32, sx, sy);
		if (alert == 1) {
			StateGame.alert.draw(x * 32, y * 32 - sy, sx, sy);
		}

	}

	@Override
	public void update() throws SlickException, FileNotFoundException {
		super.update();
		alert = 0;
		if (StateGame.mike.x + StateGame.mike.directionx == this.x && StateGame.mike.y + StateGame.mike.directiony == this.y) {
			alert = 1;
		}

	}

}
