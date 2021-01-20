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
	int hp = 5;

	public Enemy(int id) throws SlickException {
		portrait = new Image("gfx\\enemysprites\\" + id + ".png");
		Scanner reader;
		try {
			reader = new Scanner(new File("G_DATA\\enemy\\" + id + ".txt"));
			name = reader.nextLine();

			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.setFont(StateGame.f_32);
		g.drawString(name, 150, 60);
		portrait.draw(900, 45);

	}

}
