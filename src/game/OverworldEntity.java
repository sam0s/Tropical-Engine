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
	Image portrait;
	boolean moving = false;
	float x;
	float y;
	float tx;
	float ty;
	int directionx;
	int directiony;
	String[] textList = new String[20];
	StateGame s;
	// Calculated using real game
	float speed = 0.063f;

	public OverworldEntity(String identity, float x, float y, StateGame s) throws SlickException, FileNotFoundException {
		this.s = s;

		// LOAD NPC DATA
		Scanner reader = new Scanner(new File("G_DATA\\" + identity + ".txt"));
		portrait = new Image("gfx\\por_" + identity + ".png");
		Image sheet = new Image(reader.nextLine() + ".png");
		int index = 0;
		String line = "";
		while (line.contains("*") == false) {
			line = reader.nextLine();
			textList[index++] = line;
		}
		reader.close();
		sheet.setFilter(Image.FILTER_NEAREST);

		SpriteSheet p = new SpriteSheet(sheet.getSubImage(0,0,64,32), 32, 32, 0);
		anRight = new Animation(p, 1);
		anRight.setSpeed((float) 0.003);

		p = new SpriteSheet(sheet.getSubImage(64, 0, 64, 32), 32, 32, 0);
		anDown = new Animation(p, 1);
		anDown.setSpeed((float) 0.003);

		p = new SpriteSheet(sheet.getSubImage(128, 0, 64, 32), 32, 32, 0);
		anLeft = new Animation(p, 1);
		anLeft.setSpeed((float) 0.003);

		p = new SpriteSheet(sheet.getSubImage(192, 0, 64, 32), 32, 32, 0);
		anUp = new Animation(p, 1);
		anUp.setSpeed((float) 0.003);

		current = anDown;
		this.x = x;
		this.y = y;
		this.tx = x;
		this.ty = y;
	}

	public void draw(int sx, int sy) {
		current.draw(x * 32, y * 32, sx, sy);
	}

	public void move_target(int movx, int movy) {
		if (StateGame.currentText.length() == 0) {
			if (moving == false) {
				current = (movx == 0 && movy == -1) ? anUp : (movx == 0 && movy == 1) ? anDown : (movx == -1 && movy == 0) ? anLeft : anRight;
			}
			this.directionx = movx;
			this.directiony = movy;
			if (this.moving == false) {
				boolean whack = false;
				if (tx >= 0 && ty >= 0) {
					for (OverworldEntity d : StateGame.npcs) {
						if (d != null) {
							int[] d1 = new int[] { (int) d.x, (int) d.y };
							System.out.println("(" + d1[0] + ", " + d1[1] + "),");
							if (d1[0] == (int) tx + movx && d1[1] == (int) ty + movy) {
								whack = true;
							}
						}
					}

					if (s.collision(s.cur_map[(int) ty + movy][(int) tx + movx]) == false && !whack) {
						this.moving = true;
						tx += movx;
						ty += movy;
						System.out.println();
					}
				}
			}
		}
	}

	public void updateMovement() throws FileNotFoundException, SlickException {
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

	public void update() throws SlickException, FileNotFoundException {
		// later change this to a variable that locks movement for other cases such as animations or "cutscenes"
		if (StateGame.currentText.length() == 0) {
			updateMovement();
		}
	}

	public void stop() throws SlickException, FileNotFoundException {
		this.moving = false;

		x = tx;
		y = ty;
	}
}
