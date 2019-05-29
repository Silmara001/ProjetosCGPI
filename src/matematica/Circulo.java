package matematica;

import formas.Forma;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import formas.ControlePonto;

public class Circulo extends Forma {
	Ponto  _centro;
	double _raio;

	//Construtores
	Circulo(int x, int y, double raio){
		setCentro(new Ponto(x, y));
		setRaio(raio);

	}
	Circulo(Ponto c, double raio){
		setCentro(new Ponto(c));
		setRaio(raio);

	}

	public Circulo(int x, int y, double raio, Color cor){
		setCentro(new Ponto(x, y));
		setRaio(raio);
		this._cor = cor;
	}

	public Circulo(Ponto c, double raio, Color cor){
		setCentro(new Ponto(c));
		setRaio(raio);
		this._cor = cor;
	}

	//Getters and Setters
	void setRaio(double raio){
		_raio = raio;
	}

	void setCentro(Ponto c){
		_centro = c;
	}

	public Ponto getCentro(){
		return _centro;
	}
	public double getRaio(){
		return _raio;
	}

	@Override
	public void desenhar(GraphicsContext g) {
		int cx, cy, raio;
		cx = (int)getCentro().getX();
		cy = (int)getCentro().getY();
		raio = (int)getRaio();
		if (raio != 0) {
			int x = 0;
			int y = raio;
			ControlePonto p = new ControlePonto(x, y, get_cor());
			for (double alfa=0; alfa <= 45; alfa=alfa+0.2) {
				// Calcula um ponto e desenha os outros 7 por simetria.
				x=(int)(raio*Math.cos((alfa*Math.PI)/180.));
				y=(int)(raio*Math.sin((alfa*Math.PI)/180.));
				p = new ControlePonto(cx+x, cy+y, get_cor());
				p.desenharPonto(g);

				p = new ControlePonto(cx+y, cy+x, get_cor());
				p.desenharPonto(g);

				p = new ControlePonto(cx+y, cy-x, get_cor());
				p.desenharPonto(g);

				p = new ControlePonto(cx+x, cy-y, get_cor());
				p.desenharPonto(g);

				p = new ControlePonto(cx-x, cy-y, get_cor());
				p.desenharPonto(g);

				p = new ControlePonto(cx-y, cy-x, get_cor());
				p.desenharPonto(g);

				p = new ControlePonto(cx-y, cy+x, get_cor());
				p.desenharPonto(g);

				p = new ControlePonto(cx-x, cy+y, get_cor());
				p.desenharPonto(g);
			}
		}
	}

	@Override
	public boolean pontoNaForma(Ponto p, int margemDeErro) {
		double distancia = Math.sqrt( Math.pow((p.getX() - _centro.getX()), 2) + Math.pow((p.getY() - _centro.getY()), 2));
		return Math.abs(distancia - _raio) <= margemDeErro;
	}

	//métodos de todas as formas
	@Override
	public void rotacionar(Ponto referencia, double angulo) {
		this._centro.rotacionar(referencia, angulo);
	}

	@Override
	public void escalar(Ponto referencia, double fatorEscala) {
		this._centro.escalar(referencia, fatorEscala);
		this._raio = this._raio * fatorEscala;
	}

	@Override
	public void transladar(Ponto referencia) {
		double fatorX = referencia.getX();
		double fatorY = referencia.getY();
		;
		//vetor de transação
		Ponto t = new Ponto((fatorX) - _centro.getX(),
				(fatorY) - _centro.getY());

		this._centro.transladar(t);

	}
}