package game;

import java.io.FileNotFoundException;
import java.util.Arrays;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Player extends OverworldEntity {

	public Player(float x, float y, StateGame s) throws SlickException, FileNotFoundException {
		super("mike", x, y, s);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void stop() throws SlickException, FileNotFoundException {
		this.moving = false;
		System.out.println(Arrays.toString(s.dests));
		int t = s.cur_map[(int) ty][(int) tx];
		// Sub
		if (t >= 65 && t <= 68) {
			tx = s.hops[t - 65][0];
			ty = s.hops[t - 65][1];
			s.cur_map = s.loadSheet(s.dests[t - 65], 1);

		}

		x = tx;
		y = ty;
	}
}
