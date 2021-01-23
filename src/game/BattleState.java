package game;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class BattleState extends game.BasicGameState implements KeyListener {
	static Image combatBorder;
	private StateBasedGame psbg;
	

	// ***********************************************************
	// ***********************************************************
	// INIT INIT INIT INIT INIT INIT INIT
	// ***********************************************************
	public void drawCombat(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(45, 45, 1190, 630);

		g.setColor(Color.white);
		g.setFont(StateGame.f_32);

		combatBorder.draw(35, 32);
		g.drawString("Blind Willie", 100, 70);
		StateGame.mike.healthBar.draw(g, 100, 120, Color.green);
		StateGame.mike.combat_portrait.draw(50, 200);

		StateGame.currentEnemy.draw(g, 965, 50);

		g.setColor(Color.white);
		g.fillRect(65, 500, 1150, 170);

	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		switch (arg0) {
		case KeyboardControls.left_bind:
			System.out.println("Left");
			break;
		case KeyboardControls.right_bind:
			System.out.println("right");
			break;
		case KeyboardControls.up_bind:
			System.out.println("up");
			break;
		case KeyboardControls.down_bind:
			System.out.println("down");
			break;
		case KeyboardControls.action_bind:
			psbg.enterState(0);
			break;
		}
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		combatBorder = new Image("gfx\\ropeborder_combat.png");
		psbg = arg1;
	}

	// ***********************************************************
	// ***********************************************************

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		drawCombat(g);

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {

	}

	@Override
	public int getID() {
		return 1;
	}
}