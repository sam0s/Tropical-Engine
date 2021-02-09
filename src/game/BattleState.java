package game;

import java.util.Random;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class BattleState extends game.BasicGameState implements KeyListener {
	static Image combatBorder;
	Image backdrop;
	BattleMenu battMenu;

	int dmg_player = 0;
	int dmg_enemy = 0;

	float anim_offsetx = 0;
	float anim_offsety = 0;
	boolean fighting = false;
	int fightAnimPhase = 0;

	Animation slash;

	String[] log = { "", "", "", "", "" };

	private StateBasedGame psbg;
	private GameContainer pgc;
	private Graphics pg;

	// ***********************************************************
	// ***********************************************************
	// INIT INIT INIT INIT INIT INIT INIT
	// ***********************************************************

	public void addCombatText(String text) {
		for (int i = log.length - 1; i >= 0; i--) {
			if (i == log.length - 1) {
				log[i] = null;
			} else {
				log[i + 1] = log[i];
			}
		}
		log[0] = text;
	}

	public void drawCombat(Graphics g) {

		g.setColor(Color.white);
		g.setFont(StateGame.f_32);

		combatBorder.draw(35, 32);
		g.drawString("Blind Willie", 100, 70);
		StateGame.mike.healthBar.draw(g, 100, 120, Color.green);
		StateGame.mike.attack.draw(50, 200);

		if (!slash.isStopped()) {
			slash.draw(50, 200);
		}

		StateGame.currentEnemy.draw(g, 965 + (int) anim_offsetx, 50 + (int) anim_offsety);

	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		if (!fighting) {

			battMenu.keyPressed(arg0, this);
		}
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		SpriteSheet temp = new SpriteSheet(new Image("gfx\\anims\\slash.png"), 250, 300, 0);
		slash = new Animation(temp, 1);
		slash.setSpeed(0.01f);
		slash.setLooping(false);
		slash.stop();

		battMenu = new BattleMenu(100, 509);
		combatBorder = new Image("gfx\\ropeborder_combat.png");
		backdrop = new Image("gfx\\enviros\\dback.png");
		psbg = arg1;
		pgc = arg0;
		pg = pgc.getGraphics();
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
	public void update(GameContainer arg0, StateBasedGame arg1, int delta) throws SlickException {
		if (fighting) {
			if (fightAnimPhase == 0) {
				StateGame.mike.attack.start();

				fightAnimPhase++;
			}
			if (fightAnimPhase == 1) {
				if (StateGame.mike.attack.isStopped()) {
					fightAnimPhase++;
				}

			}
			if (fightAnimPhase == 2) {
				addCombatText("player does " + dmg_player);
				fightAnimPhase++;
				if (StateGame.currentEnemy.hit(dmg_player)) {
					battMenu.won();
					addCombatText("PLAYER WINS! XP GAIN!");
					fighting = false;
					fightAnimPhase = 0;
				}
			}

			// HIT
			if (fightAnimPhase == 3) {
				anim_offsetx += delta * 0.2;
				anim_offsety += delta * 0.02;

				if ((int) anim_offsetx > 4) {
					fightAnimPhase++;

				}

			}
			if (fightAnimPhase == 4) {
				anim_offsetx -= delta * 0.2;
				anim_offsety -= delta * 0.02;

				if ((int) anim_offsetx == 0) {
					fightAnimPhase++;
					slash.start();

				}

			}

			if (fightAnimPhase == 5) {
				if (slash.isStopped()) {
					slash.restart();
					slash.stop();
					fightAnimPhase++;
					addCombatText("enemy does " + dmg_enemy);

					StateGame.mike.hit(dmg_enemy);

					fighting = false;
					fightAnimPhase = 0;
				}
			}

		}

	}

	@Override
	public void actionPerformed(int action) {
		if (!fighting) {
			switch (action) {
			case 1:
				addCombatText("item used");
				break;
			case 0:
				if (battMenu.over == true) {
					psbg.enterState(0);
				}
				int a = combatTurn();
				if (a == 1) {
					battMenu.won();

				}
				break;

			}
		}

	}

	private int combatTurn() {
		fighting = true;
		Random r = new Random();
		int low_bound = ((StateGame.mike.STR + StateGame.mike.weaponDamage) - StateGame.currentEnemy.AGI / 2) / 4;
		int high_bound = ((StateGame.mike.STR + StateGame.mike.weaponDamage) - StateGame.currentEnemy.AGI / 2) / 2;

		dmg_player = 0;
		if (high_bound - low_bound >= 0) {

			// DAMAGE
			dmg_player = ((r.nextInt(high_bound - low_bound) + low_bound)) + 1;
		}
		System.out.println(dmg_player);

		low_bound = (StateGame.currentEnemy.STR - StateGame.mike.AGI / 2) / 4;
		high_bound = (StateGame.currentEnemy.STR - StateGame.mike.AGI / 2) / 2;

		dmg_enemy = 0;
		if (high_bound - low_bound >= 0) {
			dmg_enemy = ((r.nextInt(high_bound - low_bound) + low_bound)) + 1;
		}
		return 0;

	}

	@Override
	public int getID() {
		return 1;
	}
}
