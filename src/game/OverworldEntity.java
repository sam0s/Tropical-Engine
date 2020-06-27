package game;

import java.io.FileNotFoundException;
import java.util.Arrays;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class OverworldEntity {
	Animation anUp;
	Animation anDown;
	Animation anLeft;
	Animation anRight;
	Animation current;
	boolean moving = false;
	float x;
	float y;
	float tx;
	float ty;
	StateGame s;
	// Calculated using real game
	float speed = 0.063f;

	public void setPlayerAnim(String pa) {
		if (!moving) {
			current = (pa.equals("up")) ? anUp : (pa.equals("down")) ? anDown : (pa.equals("left")) ? anLeft : anRight;
		}

	}

	public OverworldEntity(Image sheet, float x, float y, StateGame s) {
		this.s = s;
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
		this.x = x;
		this.y = y;
		this.tx = x;
		this.ty = y;
	}

	public void draw(int sx, int sy) {
		current.draw(x * 16, y * 16, sx, sy);

	}

	public void move_target(int movx, int movy) {
		if (this.moving == false) {
			if (tx >= 0 && ty >= 0) {
				if (s.collision(s.cur_map[(int) ty + movy][(int) tx + movx]) == false) {
					this.moving = true;
					tx += movx;
					ty += movy;

					System.out.println();
				}
			}
		}
	}

	public void update() throws SlickException, FileNotFoundException {
		if (x != tx || y != ty) {
			if (x < tx) {
				x += speed;
				if (x >= tx) {
					stop();
				}

			}

			if (x > tx) {
				x -= speed;
				if (x <= tx) {
					stop();
				}

			}

			if (y < ty) {
				y += speed;
				if (y >= ty) {
					stop();
				}

			}

			if (y > ty) {
				y -= speed;
				if (y <= ty) {
					stop();
				}

			}
		}

	}

	private void stop() throws SlickException, FileNotFoundException {
		this.moving = false;
		System.out.println(Arrays.toString(s.dests));
		int t = s.cur_map[(int) ty][(int) tx];
		// Sub
		if (t >= 65 && t <= 68) {
			tx = s.hops[t - 65][0];
			ty = s.hops[t - 65][1];
			s.cur_map = s.loadSheet(s.dests[t - 65], 1);

		}

		x = tx;
		y = ty;
	}
}
