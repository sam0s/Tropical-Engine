package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

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

	public OverworldEntity(String identity, float x, float y, StateGame s) throws SlickException, FileNotFoundException {
		this.s = s;
		Scanner reader = new Scanner(new File("G_DATA\\" + identity + ".txt"));
		Image sheet = new Image(reader.nextLine() + ".png");
		reader.close();
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

	public void stop() throws SlickException, FileNotFoundException {
		this.moving = false;

		x = tx;
		y = ty;
	}
}
