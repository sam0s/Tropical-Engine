package game;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ImageButton extends Button {

	public Image sprite;

	public ImageButton(int sizex, int sizey, float x, float y, Image sprite, Method func, Object gc) {
		super((sizex == 0) ? sprite.getWidth() : sizex, (sizey == 0) ? sprite.getHeight() : sizey, x, y, 0, "", null, func, gc);
		this.sprite = sprite;

	}

	@Override
	public void draw(Graphics surface) {
		if (!hidden) {
			if (hi) {
				sprite.draw(x, y, sizex, sizey, Color.gray);
			} else {
				sprite.draw(x, y, sizex, sizey);
			}

		}

	}

}