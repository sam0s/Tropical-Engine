package game;

import java.io.FileNotFoundException;
import java.util.Arrays;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.Transition;

public class Player extends OverworldEntity {

	Image combat_portrait;
	HorzBarGraph healthBar;

	int LEVEL = 10;
	int STR = 35;
	int AGI = 31;
	int HP = 54;
	int currentHP = HP;
	int LK = 0;

	int weaponDamage = 2;
	Animation attack;

	public Player(float x, float y, StateGame s) throws SlickException, FileNotFoundException {
		super("mike", x, y, s);
		this.combat_portrait = new Image("gfx\\enemysprites\\mike_fire.png");
		healthBar = new HorzBarGraph(200, 32, 2, StateGame.f_16);
		healthBar.set_label("HP: " + currentHP + " / " + HP);
		healthBar.set_percent(currentHP, HP);

		SpriteSheet p = new SpriteSheet(this.combat_portrait, 250, 300, 0);
		attack = new Animation(p, 1);
		attack.setSpeed((float) 0.006);
		attack.setPingPong(true);
		attack.setLooping(false);
		attack.stop();

	}

	public void setPlayerAnim(String pa) {
		if (!moving) {
			current = (pa.equals("up")) ? anUp : (pa.equals("down")) ? anDown : (pa.equals("left")) ? anLeft : anRight;
		}

	}

	public void stop() throws SlickException, FileNotFoundException {
		this.moving = false;

		// System.out.println(Arrays.toString(s.dests));
		int t = s.cur_map[(int) ty][(int) tx];

		// Sub
		if (t >= 65 && t <= 68) {

			tx = s.hops[t - 65][0];
			ty = s.hops[t - 65][1];
			s.cur_map = s.loadSheet(s.dests[t - 65]);

		}

		x = tx;
		y = ty;

		if (Game.getRandomNumberInRange(0, 47) == 0) {
			StateGame.currentEnemy = new Enemy(Game.getRandomNumberInRange(0, 2));
		}

	}

	public boolean hit(int dmgTotal) {
		currentHP -= dmgTotal;
		if (currentHP >= 1) {
			healthBar.set_label("HP: " + currentHP + " / " + HP);
			healthBar.set_percent(currentHP, HP);
		} else {
			healthBar.set_label("HP: " + currentHP + " / " + HP);
			healthBar.set_percent(0, HP);
			System.out.println("YOU IS DEAD ENEMY WIN BOSSMAN");
			return true;
		}

		return false;

	}

	// returns true if space is clear
	public boolean action() {
		if (StateGame.currentText.length() == 0) {
			for (NPC d : StateGame.npcs) {
				if (d != null) {
					if (d.alert == 1) {
						StateGame.currentPortrait = d.portrait;
						StateGame.currentText = d.textList[d.state];
						return false;
					}

				}
			}
			System.out.println("none");
			return true;
		} else {

			if (StateGame.textCursor < StateGame.currentText.length()) {

				// fast forward text
				for (int i = (int) StateGame.textCursor; i < StateGame.currentText.length(); i++) {
					if (StateGame.currentText.charAt(i) == '&') {
						StateGame.textCursor = i - 1;
						return false;
					}
				}
				StateGame.textCursor = StateGame.currentText.length();
				return false;
			}

			// reset cursor
			StateGame.textCursor = 0;

			// close text box
			if (StateGame.currentText.equals(StateGame.nextText)) {
				StateGame.nextText = "";
			}

			// move to next text segment!
			StateGame.currentText = StateGame.nextText;
		}
		return false;

	}
}
