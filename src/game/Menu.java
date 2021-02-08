package game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;

public class Menu {
	int sizex;
	int sizey;
	int x;
	int y;
	String[] items;
	int selection = 0;

	public Menu(int x, int y) {
		this.x = x;
		this.y = y;

		items = new String[] { "Resume", "Inventory", "Options", "Quit"};
		this.sizey = 32 + items.length * 32;
		this.sizex = 200;

	}

	public void keyPressed(int key, game.BasicGameState sg) {
		switch (key) {
		case KeyboardControls.down_bind:
			selection += 1;
			break;
		case KeyboardControls.up_bind:
			selection -= 1;
			break;
		case KeyboardControls.action_bind:
			sg.actionPerformed(selection);
			break;
		}

		if (selection < 0) {
			selection = items.length - 1;
		}
		if (selection > items.length - 1) {
			selection = 0;
		}

	}

	public void draw(Graphics g) {

		g.setColor(Color.black);
		g.fillRect(x, y, sizex, sizey);
		g.setColor(Color.white);
		g.setLineWidth(4);
		g.drawRect(x, y, sizex, sizey);
		
		int i = 0;
		for (String a : items) {
			g.setFont(StateGame.f_24);
			g.drawString(a, x + 64, y + 16 + i++ * 32);
		}

		StateGame.cursorSprite.draw(x + 20, y + 14 + selection * 32);

	}

}
