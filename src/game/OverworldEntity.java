package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class OverworldEntity {
	Animation anUp;
	Animation anDown;
	Animation anLeft;
	Animation anRight;
	Animation current;
	boolean moving = false;

	public void setPlayerAnim(String pa) {
		if (!moving) {
			current = (pa.equals("up")) ? anUp : (pa.equals("down")) ? anDown : (pa.equals("left")) ? anLeft : anRight;
		}

	}

	public OverworldEntity(Image sheet) {
		sheet.setFilter(Image.FILTER_NEAREST);

		SpriteSheet p = new SpriteSheet(sheet.getSubImage(0, 0, 32, 16), 16, 16, 0);
		anRight = new Animation(p, 1);
		anRight.setSpeed((float) 0.003);

		p = new SpriteSheet(sheet.getSubImage(32, 0, 32, 16), 16, 16, 0);
		anDown = new Animation(p, 1);
		anDown.setSpeed((float) 0.003);

		p = new SpriteSheet(sheet.getSubImage(64, 0, 32, 16), 16, 16, 0);
		anLeft = new Animation(p, 1);
		anLeft.setSpeed((float) 0.003);

		p = new SpriteSheet(sheet.getSubImage(96, 0, 32, 16), 16, 16, 0);
		anUp = new Animation(p, 1);
		anUp.setSpeed((float) 0.003);

		current = anDown;
	}

	public void draw(float x, float y, int sx, int sy) {
		current.draw(x, y, sx, sy);

	}
}
