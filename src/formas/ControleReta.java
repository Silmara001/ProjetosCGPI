package formas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import matematica.Ponto;
import matematica.Reta;

public class ControleReta extends Forma {

	Ponto p1, p2;
	Reta eq;

	//Construtores
	public ControleReta(int x1, int y1, int x2, int y2){
		setP1(new Ponto(x1, y1));
		setP2(new Ponto(x2, y2));
		this.eq = new Reta(this.p1, this.p2);
		this._cor = Color.BLACK;

	}

	ControleReta(Ponto p1, Ponto p2){
		setP1(new Ponto(p1));
		setP2(new Ponto(p2));
		eq = new Reta(this.p1, this.p2);
		this._cor = Color.BLACK;
	}

	public ControleReta(int x1, int y1, int x2, int y2, Color color){
		setP1(new Ponto(x1, y1));
		setP2(new Ponto(x2, y2));
		this.eq = new Reta(this.p1, this.p2);
		this._cor = color;

	}

	public ControleReta(Ponto p1, Ponto p2, Color color){
		setP1(new Ponto(p1));
		setP2(new Ponto(p2));
		eq = new Reta(this.p1, this.p2);
		this._cor = color;
	}

	//Sets
	public void setP1(Ponto p1) {
		this.p1 = p1;
	}

	public void setP2(Ponto p2) {
		this.p2 = p2;
	}


	//Gets
	public Ponto getP1() {
		return p1;
	}

	public Ponto getP2() {
		return p2;
	}


	//calculos especificos da Reta

	public double calculaM(){
		double m=(p2.getY()-p1.getY())/(p2.getX()-p1.getX());
		return m;
	}

	//Calcula b da equa��o da reta (y = m*x + b)
	public double calculaB(){

		double m=calculaM();
		double b=p2.getY()-(m*p2.getX());
		return b;
	}

	public double calculaDistanciaEntreRetaEPonto(Ponto p) {
		double distancia;
		double a = this.eq.getA();
		double b = this.eq.getB();
		double c = this.eq.getC();
		if (Double.isInfinite(a)){
			distancia = Math.abs(this.p1.getX() - p.getX());
		} else {
			distancia = Math.abs((a * p.getX() + b * p.getY() + c)) / Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
		}
		return distancia;
	}


	@Override
	public void desenhar(GraphicsContext g) {
		double b=calculaB();
		double m=calculaM();

		System.out.println("Controle da Reta: P1 -> " + p1.getX()+ "," + p1.getY());

		//Caso 1) Caso em que o intervalo em y � maior
		if(Math.abs(p2.getY()-p1.getY())>Math.abs(p2.getX()-p1.getX())){

			//Caso 1.1)Caso em que y1 > y2
			if(p1.getY()>p2.getY()){

				//System.out.println("Intervalo em Y eh maior com y1 > y2 .");
				if (p1.getX()==p2.getX()){
					ControlePonto ponto=new ControlePonto((int)p1.getX(),(int)p1.getY(),_cor);
					ponto.desenharPonto(g);
					for(double y=p2.getY();y<p1.getY();++y){
						ponto=new ControlePonto((int)(p1.getX()),(int)y,_cor);
						ponto.desenharPonto(g);
					}
				}
				else{
					ControlePonto ponto=new ControlePonto((int)p2.getX(),(int)p2.getY(),_cor);
					ponto.desenharPonto(g);
					for(double y=p2.getY();y<p1.getY();++y){
						ponto=new ControlePonto((int)((y-b)/m),(int)y,_cor);
						ponto.desenharPonto(g);
					}
				}

				//Caso 1.2)Caso em que x1 = x2
			}else if(p1.getX()==p2.getX()){

				//System.out.println("Intervalo em Y eh maior com Reta vertical .");
				ControlePonto ponto=new ControlePonto((int)p1.getX(),(int)p1.getY(),_cor);
				ponto.desenharPonto(g);
				for(double y=p1.getY();y<p2.getY();++y){
					ponto=new ControlePonto((int)(p1.getX()),(int)y,_cor);
					ponto.desenharPonto(g);
				}
				//Caso 1.3)Caso em que x1 < x2
			}else{

				//System.out.println("Intervalo em Y eh maior com x1 < x2 .");
				ControlePonto ponto=new ControlePonto((int)p1.getX(),(int)p1.getY(),_cor);
				ponto.desenharPonto(g);
				for(double y=p1.getY();y<p2.getY();++y){
					ponto=new ControlePonto((int)((y-b)/m),(int)y,_cor);
					ponto.desenharPonto(g);
				}

			}

			//Caso 2)Caso em que o intervalo em x � maior
		}else{

			//Caso 2.1)Caso em que x1 > x2
			if(p1.getX()>p2.getX()){

				//System.out.println("Intervalo em X eh maior com x1 > x2 .");
				ControlePonto ponto=new ControlePonto((int)p2.getX(),(int)p2.getY(),_cor);
				ponto.desenharPonto(g);
				for(double x=p2.getX();x<p1.getX();++x){
					ponto=new ControlePonto((int)x,(int)(b+m*x),_cor);
					ponto.desenharPonto(g);
				}

				//Caso 2.2)Caso em que x1 = x2
			}else if(p1.getX()==p2.getX()){

				//System.out.println("Intervalo em X eh maior com Reta Vertical .");
				ControlePonto ponto=new ControlePonto((int)p1.getX(),(int)p1.getY(),_cor);
				ponto.desenharPonto(g);
				for(double x=p1.getX();x<p2.getX();++x){
					ponto=new ControlePonto((int)(p1.getX()),(int)(b+m*x),_cor);
					ponto.desenharPonto(g);
				}

				//Caso 2.3)Caso em que x1 < x2
			}else{

				//System.out.println("Intervalo em X eh maior com x1 < x2 .");
				ControlePonto ponto=new ControlePonto((int)p1.getX(),(int)p1.getY(),_cor);
				ponto.desenharPonto(g);
				for(double x=p1.getX();x<p2.getX();++x){
					ponto=new ControlePonto((int)x,(int)(b+m*x),_cor);
					ponto.desenharPonto(g);
				}
			}
		}

	}

	public boolean pontoNaForma(Ponto p, int margemDeErro){
		boolean pontoNaReta = (calculaDistanciaEntreRetaEPonto(p) <= margemDeErro);
		boolean dentroDoLimiteDeX = (p.getX() >= (this.getP1().getX() - margemDeErro) && p.getX() <= (this.getP2().getX() + margemDeErro)) ||
				(p.getX() >= (this.getP2().getX() - margemDeErro) && p.getX() <= (this.getP1().getX() + margemDeErro));
		boolean dentroDoLimiteDeY = (p.getY() >= (this.getP1().getY() - margemDeErro) && p.getY() <= (this.getP2().getY() + margemDeErro)) ||
				(p.getY() >= (this.getP2().getY() - margemDeErro) && p.getY() <= (this.getP1().getY() + margemDeErro));
		return (pontoNaReta && dentroDoLimiteDeY && dentroDoLimiteDeX);
	}

	//métodos de todas as formas

	@Override
	public void rotacionar(Ponto referencia, double angulo) {
//		double xcenter = p2.getX() - ((p2.getX() - p1.getX())/2);
//		double ycenter =p2.getY() - ((p2.getY() - p1.getY())/2);
//
//		Ponto centro = new Ponto(xcenter, ycenter);

		this.p1.rotacionar(referencia, angulo);
		this.p2.rotacionar(referencia, angulo);
		this.eq = new Reta(this.p1, this.p2);

	}

	@Override
	public void escalar(Ponto referencia, double fatorEscala) {
		this.p1.escalar(referencia, fatorEscala);
		this.p2.escalar(referencia, fatorEscala);
		this.eq = new Reta(this.p1, this.p2);
	}

	@Override
	public void transladar(Ponto p) {
		double fatorX = p.getX();
		double fatorY = p.getY();
		;
		//vetor de transação
		Ponto t = new Ponto((fatorX) - p1.getX(),
				(fatorY) - p1.getY());
		this.p1.transladar(t);
		this.p2.transladar(t);
		this.eq = new Reta(this.p1, this.p2);
	}
}
