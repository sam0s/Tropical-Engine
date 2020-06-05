package game;
import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class HorzBarGraph extends Container {

	float percent;
	String label;
	Font f;
	
	public HorzBarGraph(int sizex, int sizey, float x, float y, float weight, Font f) {
		super(sizex, sizey, x, y, 0, 0, weight);
		
		this.percent = 0;
		this.f = f;
	}
	
	public void set_percent(float percent) {
		this.percent = percent;
	}
	
	public void set_label(String label) {
		this.label = label;
	}
	
	public void update(Input i, int mx, int my, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super.update(i, mx, my, delta);
	}
	
	public void draw(Graphics surface) {
		surface.setLineWidth(weight);
		surface.setColor(outer.darker(0.5f));
		surface.fillRect(x, y, sizex, sizey);
		surface.setColor(outer);
		surface.fillRect(x,  y, (int) sizex * percent, sizey);
		surface.drawRect(x, y+1, sizex, sizey-(int)weight);
		if (label != null) {
			f.drawString(this.x + 2, this.y + 3, label, Color.cyan); /* TODO: figure out how to color text properly */
		}
	}

}
