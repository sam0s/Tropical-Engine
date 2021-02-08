package game;

import java.util.Random;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class BattleState extends game.BasicGameState implements KeyListener {
	static Image combatBorder;
	Image backdrop;
	BattleMenu battMenu;

	String[] log = { "", "", "", "", "" };

	private StateBasedGame psbg;

	// ***********************************************************
	// ***********************************************************
	// INIT INIT INIT INIT INIT INIT INIT
	// ***********************************************************

	public void addCombatText(String text) {
		for (int i = log.length - 1; i >= 0; i--) {
			System.out.println(i);
			if (i == log.length - 1) {
				log[i] = null;
			} else {
				log[i + 1] = log[i];
			}
		}

		log[0] = text;

		for (int i = 0; i < log.length; i++) {
			System.out.println("[" + log[i] + "]");
		}
		System.out.println("\n");
	}

	public void drawCombat(Graphics g) {

		g.setColor(Color.white);
		g.setFont(StateGame.f_32);

		combatBorder.draw(35, 32);
		g.drawString("Blind Willie", 100, 70);
		StateGame.mike.healthBar.draw(g, 100, 120, Color.green);
		StateGame.mike.combat_portrait.draw(50, 200);

		StateGame.currentEnemy.draw(g, 965, 50);

	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		battMenu.keyPressed(arg0, this);
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		battMenu = new BattleMenu(100, 509);
		combatBorder = new Image("gfx\\ropeborder_combat.png");
		backdrop = new Image("gfx\\enviros\\dback.png");
		psbg = arg1;
	}

	// ***********************************************************
	// ***********************************************************

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		backdrop.draw(36, 36);
		drawCombat(g);
		battMenu.draw(g);
		g.setColor(Color.green);
		for (int i = log.length - 1; i >= 0; i--) {
			g.drawString(log[i], 670, 640 - i * 32);

		}

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {

	}

	@Override
	public void actionPerformed(int action) {
		switch (action) {
		case 1:
			addCombatText("item used");
			break;
		case 0:
			Random r = new Random();
			int low_bound = ((StateGame.mike.STR + StateGame.mike.weaponDamage) - StateGame.currentEnemy.AGI / 2) / 4;
			int high_bound = ((StateGame.mike.STR + StateGame.mike.weaponDamage) - StateGame.currentEnemy.AGI / 2) / 2;

			int dmgTotal = 0;
			if (high_bound - low_bound >= 0) {

				// DAMAGE
				dmgTotal = ((r.nextInt(high_bound - low_bound) + low_bound)) + 1;
			}

			System.out.println("Damage roll: ");
			addCombatText("Player deals " + dmgTotal + " (low: " + low_bound + " | high: " + high_bound);
			StateGame.currentEnemy.hit(dmgTotal);

			low_bound = (StateGame.currentEnemy.STR - StateGame.mike.AGI / 2) / 4;
			high_bound = (StateGame.currentEnemy.STR - StateGame.mike.AGI / 2) / 2;

			int dmgTotal2 = 0;
			if (high_bound - low_bound >= 0) {
				dmgTotal2 = ((r.nextInt(high_bound - low_bound) + low_bound)) + 1;
			}

			addCombatText("Enemy deals " + dmgTotal2 + " (low: " + low_bound + " | high: " + high_bound);

			StateGame.mike.hit(dmgTotal2);

			break;

		}

	}

	@Override
	public int getID() {
		return 1;
	}
}
