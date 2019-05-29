package formas;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import matematica.Ponto;

public class ControleLinha extends Forma {

	List<Ponto> pontos = new ArrayList<Ponto>();
	boolean poligono_fechado;


	ArrayList<ControleReta> controleRetas;
	
	//Construtores

	public ControleLinha(List<Ponto> pontos){
		this.pontos = pontos;
		this._cor = Color.BLACK;
		this.poligono_fechado = false;
	}

	public ControleLinha(List<Ponto> pontos, Color color){
		this.pontos = pontos;
		this._cor = color;
		this.poligono_fechado = false;
	}

	public ControleLinha(List<Ponto> pontos, Color color, boolean fechado){
		this.pontos = pontos;
		this._cor = color;
		this.poligono_fechado = fechado;
	}

	public List<Ponto> getPontos() {
		return pontos;
	}

	//Getters e Setters

	public void setPontos(List<Ponto> pontos) {
		this.pontos = pontos;
	}

	public void addPonto(Ponto ponto) {
		this.pontos.add(ponto);
	}

	public boolean getPoligonoFechado(){
		return this.poligono_fechado;
	}

	public void setPoligono_fechado(boolean fechado) {
		this.poligono_fechado = fechado;
	}



	public ArrayList<ControleReta> calcularRetas() {
		controleRetas = new ArrayList<>();
		for(int i = 0; i < getPontos().size() - 1 ; i++){
			Ponto p1 = getPontos().get(i);
			Ponto p2 = getPontos().get(i+1);
			ControleReta controleReta = new ControleReta(p1, p2, this._cor);
			controleRetas.add(controleReta);
		}

		if (this.poligono_fechado){
			Ponto primeiroPonto  = getPontos().get(0);
			Ponto ultimoPonto = getPontos().get(getPontos().size()-1);
			ControleReta controleReta = new ControleReta(primeiroPonto, ultimoPonto, this._cor);
			controleRetas.add(controleReta);
		}

		return controleRetas;
	}
	
	

	public ArrayList<ControleReta> getControleRetas() {
		return controleRetas;
	}

	public void setControleRetas(ArrayList<ControleReta> controleRetas) {
		this.controleRetas = controleRetas;
	}

	public boolean isPoligono_fechado() {
		return poligono_fechado;
	}

	@Override
	public void desenhar(GraphicsContext g) {
		controleRetas = calcularRetas();
		for(ControleReta r: controleRetas) {
			r.desenhar(g);
		}
	}

	@Override
	public boolean pontoNaForma(Ponto p, int margemDeErro) {
		boolean encontrouForma = false;

		for(ControleReta r: controleRetas) {
			if(r.pontoNaForma(p, margemDeErro)){
				encontrouForma = true;
				break;
			}
		}
		return encontrouForma;
	}
	

	@Override
	public void rotacionar(Ponto referencia, double angulo) {
//		double xcenter = pontos.get(1).getX() - ((pontos.get(1).getX() - pontos.get(0).getX())/2);
//		double ycenter =pontos.get(1).getY() - ((pontos.get(1).getY() - pontos.get(0).getY())/2);
//
//		Ponto centro = new Ponto(xcenter, ycenter);

		for(Ponto vertice : this.pontos){
			vertice.rotacionar(referencia, angulo);
		}
	}

	@Override
	public void escalar(Ponto referencia, double fatorEscala) {
		for(Ponto p : this.pontos){
			p.escalar(referencia, fatorEscala);
		}
	}

	@Override
	public void transladar(Ponto p) {
		double fatorX = p.getX();
		double fatorY = p.getY();
		;
		//vetor de transação
		Ponto referencia = new Ponto((fatorX) - pontos.get(0).getX(),
				(fatorY) - pontos.get(0).getY());

		for(Ponto vertice : this.pontos){
			vertice.transladar(referencia);
		}
	}
	
	

}
