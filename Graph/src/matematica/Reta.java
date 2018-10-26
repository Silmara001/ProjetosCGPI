package matematica;

import javafx.scene.shape.Line;

/**
 * Classe auxiliar para armazenar a equação da reta
 *
 */
public class Reta extends Line {

	private double a;
	private double b;
	private double c;
	
	
	public Reta(Ponto p1, Ponto p2) {
		double m = (p2.getY() - p1.getY())/(p2.getX() - p1.getX());
		this.a = -1 * m;
		this.c = -1 * p1.getY() - m *(-1* p1.getX());
		this.b = 1;
	}
	
	public double getC() {
		return c;
	}
	public void setC(double c) {
		this.c = c;
	}
	public double getA() {
		return a;
	}
	public void setA(double a) {
		this.a = a;
	}
	public double getB() {
		return b;
	}
	public void setB(double b) {
		this.b = b;
	}
	
	
}
