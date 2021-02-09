package game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;

public class BattleMenu extends Menu {

	public boolean over;

	public BattleMenu(int x, int y) {
		super(x, y);
		this.items = new String[] {"Attack","Items","Special","Run"};
		this.sizey = 32 + items.length * 32;
		this.sizex = 200;
		this.over=false;
	}
	
	public void draw(Graphics g) {

		g.setColor(Color.white);
		int i = 0;
		for (String a : items) {
			g.setFont(StateGame.f_24);
			g.drawString(a, x + 64, y + 16 + i++ * 32);
		}
		StateGame.cursorSprite.draw(x + 20, y + 14 + selection * 32);

	}
	

	public void won() {
		this.items = new String[] {"Return"};
		this.over=true;
	}
	
}
