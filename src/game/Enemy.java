package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Enemy {
	Image portrait;
	String name;
	HorzBarGraph healthBar;
	int hp = 5;
	int maxhp = 5;

	public Enemy(int id) throws SlickException {
		portrait = new Image("gfx\\enemysprites\\" + id + ".png");
		healthBar = new HorzBarGraph(200, 32, 2, StateGame.f_16);
		healthBar.set_label("HP: " + hp + " / " + maxhp);
		healthBar.set_percent(5, 5);

		Scanner reader;
		try {
			reader = new Scanner(new File("G_DATA\\enemy\\" + id + ".txt"));
			name = reader.nextLine();

			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics g, int x, int y) {
		g.setColor(Color.white);
		g.setFont(StateGame.f_32);

		portrait.draw(x, y);
		g.drawString(name, x, y + portrait.getHeight() + 40);
		healthBar.draw(g, x, y + portrait.getHeight() + 90, Color.red);

	}

}
