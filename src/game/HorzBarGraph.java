package game;

import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

//written by jdedmondt

public class HorzBarGraph {

	float percent;
	String label;
	Font f;
	private float weight;
	private int sizey;
	private int sizex;

	public HorzBarGraph(int sizex, int sizey, float weight, Font f) {
		this.weight = weight;
		this.sizex = sizex;
		this.sizey = sizey;
		this.percent = 0;
		this.f = f;
	}

	public void set_percent(int current, int max) {
		this.percent = (float) current / max;
	}

	public void set_label(String label) {
		this.label = label;
	}

	public void draw(Graphics surface, int xx, int yy, Color c) {
		surface.setLineWidth(weight);
		surface.setColor(c.darker(0.5f));
		surface.fillRect(xx, yy, sizex, sizey);
		surface.setColor(c);
		surface.fillRect(xx, yy, (int) sizex * percent, sizey);
		f.drawString(xx + 2, yy + 3, label, Color.black);

	}

}
