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
	int currentHP;

	int STR = 40;
	int AGI = 30;
	int HP = 30;
	int LK = 0;

	public Enemy(int id) throws SlickException {

		portrait = new Image("gfx\\enemysprites\\" + id + ".png");

		Scanner reader;
		try {
			reader = new Scanner(new File("G_DATA\\enemy\\" + id + ".txt"));
			name = reader.nextLine();
			STR = reader.nextInt();
			AGI = reader.nextInt();
			HP = reader.nextInt();

			System.out.println("I'm a  " + name + " i have " + STR + " strength, and " + AGI + " agiligty and " + HP + " health.");

			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		currentHP = HP;
		healthBar = new HorzBarGraph(200, 32, 2, StateGame.f_16);
		healthBar.set_label("HP: " + currentHP + " / " + HP);
		healthBar.set_percent(currentHP, HP);

	}

	public void draw(Graphics g, int x, int y) {
		g.setColor(Color.white);
		g.setFont(StateGame.f_32);

		portrait.draw(x, y);
		g.drawString(name, x, y + portrait.getHeight() + 40);
		healthBar.draw(g, x, y + portrait.getHeight() + 90, Color.red);

	}

	public void hit(int dmgTotal) {
		currentHP -= dmgTotal;
		if (currentHP >= 1) {
			healthBar.set_label("HP: " + currentHP + " / " + HP);
			healthBar.set_percent(currentHP, HP);
		} else {
			healthBar.set_label("HP: " + currentHP + " / " + HP);
			healthBar.set_percent(0, HP);
			System.out.println("ENEMY IS DEAD YOU WIN BOSSMAN");
		}

	}

}
