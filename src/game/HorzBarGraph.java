package game;

import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

//written by jdedmondt

public class HorzBarGraph extends Container {

	float percent;
	String label;
	Font f;

	public HorzBarGraph(int sizex, int sizey, float x, float y, float weight, Font f) {
		super(sizex, sizey, x, y, 0, 0, weight);
		this.percent = 0;
		this.f = f;
	}

	public void set_percent(int current, int max) {
		this.percent = (float) current / max;
	}

	public void set_label(String label) {
		this.label = label;
	}

	public void update(Input i, int mx, int my, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super.update(i, mx, my, delta);
	}

	public void draw(Graphics surface) {
		surface.setLineWidth(weight);
		surface.setColor(Color.red.darker(0.5f));
		surface.fillRect(x, y, sizex, sizey);
		surface.setColor(Color.red);
		surface.fillRect(x, y, (int) sizex * percent, sizey);
		surface.setColor(Color.white);
		f.drawString(this.x + 2, this.y + 3, "shuggy"); /* TODO: figure out how to color text properly */

	}

}
