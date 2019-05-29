package formas;

import javafx.geometry.Rectangle2D;
import matematica.Ponto;

import java.util.ArrayList;
import java.util.List;



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

	private boolean isCompletelyOutside(int c1, int c2) {
		boolean isOutside = false;
		if(c1 != INSIDE && c2 != INSIDE && 
				c1 != LEFT && c2 != LEFT &&
				c1 != RIGHT && c2 != RIGHT &&
				c1 != BOTTOM && c2 != BOTTOM &&
				c1 != TOP && c2 != TOP 
				) {
			isOutside = true;			
		}

		return isOutside;

	}

	/**
	 * Clips a given line against the clip rectangle. The modification (if needed)
	 * is done in place.
	 * 
	 * @param line the line to clip
	 * @return true if line is clipped, false if line is totally outside the clip
	 *         rect.
	 */
	public boolean clipLinha(ControleLinha line) {

		List<Ponto> pontos = line.getPontos();

		double p1x = pontos.get(0).getX();
		double p1y = pontos.get(0).getY();
		double p2x =pontos.get(1).getX();
		double p2y =pontos.get(1).getY();

		boolean vertical = p1x == p2x;

		double qx = 0d;
		double qy = 0d;

		double slope = vertical ? 0d : (p2y - p1y) / (p2x - p1x);

		int c1 = regionCode(p1x, p1y);
		int c2 = regionCode(p2x, p2y);

		if(isCompletelyOutside(c1,c2) ) {
			line = null;
		}else {

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

			List<Ponto> novosPontos = new ArrayList();
			novosPontos.add(new Ponto(p1x, p1y));
			novosPontos.add(new Ponto(p2x, p2y));

			line.setPontos(novosPontos);;
		}
		return true;
	}


	public boolean clipReta(ControleReta line) {

		double p1x = line.p1.getX();
		double p1y = line.p1.getY();
		double p2x =line.p2.getX();
		double p2y =line.p2.getY();

		boolean vertical = p1x == p2x;

		double qx = 0d;
		double qy = 0d;

		double slope = vertical ? 0d : (p2y - p1y) / (p2x - p1x);

		int c1 = regionCode(p1x, p1y);
		int c2 = regionCode(p2x, p2y);

		if(isCompletelyOutside(c1,c2) ) {
			return false;
		}
		else {

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


			line.setP1(new Ponto(p1x, p1y));
			line.setP2(new Ponto(p2x, p2y));

		}
		return true;
	}

	public boolean clipForma(Forma forma) {
		if(forma.getClass().equals(ControleReta.class)) {	
			ControleReta reta = (ControleReta) forma;
			if(!clipReta(reta)) {
				return false;
			};	
		}

		if(forma.getClass().equals(ControleLinha.class)) {
			ControleLinha poligono = (ControleLinha) forma;

			boolean shouldDeleteForm = true;

			for (ControleReta reta : poligono.calcularRetas()) {
				boolean wasClipped = clipReta(poligono, reta);
				shouldDeleteForm = shouldDeleteForm && !wasClipped;
				
			}
			if(shouldDeleteForm) {
				return false;
			}
		}

		
		return true;
	}

	private boolean clipReta(ControleLinha poligono, ControleReta line) {
		
		double p1x = line.p1.getX();
		double p1y = line.p1.getY();
		double p2x =line.p2.getX();
		double p2y =line.p2.getY();
		
		Ponto ponto1 = line.p1;
		Ponto ponto2 = line.p2;

		boolean vertical = p1x == p2x;

		double qx = 0d;
		double qy = 0d;

		double slope = vertical ? 0d : (p2y - p1y) / (p2x - p1x);

		int c1 = regionCode(p1x, p1y);
		int c2 = regionCode(p2x, p2y);

		if(isCompletelyOutside(c1,c2) ) {
			return false;
		}
		else {

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
	
			line.setP1(new Ponto(p1x, p1y));
			line.setP2(new Ponto(p2x, p2y));

		}
		return true;
	}

	

}
