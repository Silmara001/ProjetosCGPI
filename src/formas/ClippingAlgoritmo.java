package formas;

import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Line;
import matematica.Reta;

import com.sun.javafx.geom.Line2D;

import javafx.*;

/**
 * Classe baseada no algoritmo Cohen-Sutherland
 * 
 * @author alinemurakami
 *
 */
public class ClippingAlgoritmo {
	private double xMin;
	private double yMin;
	private double xMax;
	private double yMax;

	/**
	 * Area de recorte com área 0
	 */
	public ClippingAlgoritmo() {
	}

	/**
	 * Cria uma área de clipping com as dimensões do retangulo
	 * 
	 * @param clippingArea area de recorte a ser utilizada
	 */
	public ClippingAlgoritmo(Rectangle2D clippingArea) {
		setClip(clippingArea);
	}

	public ClippingAlgoritmo(ControleRetangulo r) {
		xMin = r.getVertice1().getX();
		xMax = r.getVertice2().getX();
		yMin = r.getVertice1().getY();
		yMax = r.getVertice2().getY();
	}

	/**
	 * Guarda os parâmetros do retangulo
	 * 
	 * @param clip área de recorte
	 */
	public void setClip(Rectangle2D clip) {
		xMin = clip.getMinX();
		xMax = clip.getMaxX();
		yMin = clip.getMinY();
		yMax = clip.getMaxY();
	}

	private static final int INSIDE = 0;
	private static final int LEFT = 1;
	private static final int RIGHT = 2;
	private static final int BOTTOM = 4;
	private static final int TOP = 8;

	private final int regionCode(double x, double y) {
		int code = x < xMin ? LEFT : x > xMax ? RIGHT : INSIDE;
		if (y < yMin)
			code |= BOTTOM;
		else if (y > yMax)
			code |= TOP;
		return code;
	}

	/**
	 * Clips a given line against the clip rectangle. The modification (if needed)
	 * is done in place.
	 * 
	 * @param line the line to clip
	 * @return true if line is clipped, false if line is totally outside the clip
	 *         rect.
	 */
	public boolean clip(Line2D line) {

		double p1x = line.x1;
		double p1y = line.y1;
		double p2x = line.x2;
		double p2y = line.y2;

		
		boolean vertical = p1x == p2x;

		double qx = 0d;
		double qy = 0d;

		double slope = vertical ? 0d : (p2y - p1y) / (p2x - p1x);

		int c1 = regionCode(p1x, p1y);
		int c2 = regionCode(p2x, p2y);

		while (c1 != INSIDE || c2 != INSIDE) {

			if ((c1 & c2) != INSIDE)
				return false;

			int c = c1 == INSIDE ? c2 : c1;

			if ((c & LEFT) != INSIDE) {
				qx = xMin;
				qy = (qx - p1x) * slope + p1y;
			} else if ((c & RIGHT) != INSIDE) {
				qx = xMax;
				qy = (qx - p1x) * slope + p1y;
			} else if ((c & BOTTOM) != INSIDE) {
				qy = yMin;
				qx = vertical ? p1x : (qy - p1y) / slope + p1x;
			} else if ((c & TOP) != INSIDE) {
				qy = yMax;
				qx = vertical ? p1x : (qy - p1y) / slope + p1x;
			}

			if (c == c1) {
				p1x = qx;
				p1y = qy;
				c1 = regionCode(p1x, p1y);
			} else {
				p2x = qx;
				p2y = qy;
				c2 = regionCode(p2x, p2y);
			}
		}
		line.setLine((float)p1x, (float)p1y, (float)p2x,(float) p2y);
		return true;
	}

}
