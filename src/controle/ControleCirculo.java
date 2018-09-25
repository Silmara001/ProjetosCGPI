package controle;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import controle.ControlePonto;
import matematica.Circulo;
//import grafica.CirculoGr;
import grafica.Definicao; 

public class ControleCirculo {
	public ControleCirculo(Definicao definicao){
		this.definicao = definicao;
	}
	
	int x1=0, y1=0, x2=0, y2=0;
	int x=0, y=0, xant=0, yant=0;
	
	boolean primeiraVez = true;
	boolean fimElastico = true;
	
	Circulo circuloMat = new Circulo();
	ControlePonto controlePonto = new ControlePonto();
	Definicao definicao;

	public void arc8(GraphicsContext gc, int cx, int cy, int rx, int ry){
		 circuloMat.calcularRaio();
		 int x = 0, y = (int)circuloMat.getRaio(), u = 1, v = 2 * (int) circuloMat.getRaio() -1, e = 0;
		 while(x <= y){
			 controlePonto.desenharPonto(gc, cx + x, cy + y, 1, "", Color.BLUE);
			 x++; e += u; u += 2;
			 if (v < 2 * e) {y--; e-= v; v-= 2;}
		 }
	}
	
	//Walderson Shimokawa
	void desenharCirculo(GraphicsContext gc, int cx, int cy){
		circuloMat.calcularRaio();
		int x = 0, y = (int)circuloMat.getRaio(), u = 1, v = 2 * (int)circuloMat.getRaio() - 1, E = 0;
		while (x < y){
			controlePonto.desenharPonto(gc, cx + y, cy - x, definicao.getEspessura(), "", definicao.getCor()); //1* parte do 1* quadrante
			controlePonto.desenharPonto(gc, cx - x, cy - y, definicao.getEspessura(), "", definicao.getCor()); //1* parte do 2* quadrante
			controlePonto.desenharPonto(gc, cx - y, cy + x, definicao.getEspessura(), "", definicao.getCor()); //1* parte do 3* quadrante
			controlePonto.desenharPonto(gc, cx + x, cy + y, definicao.getEspessura(), "", definicao.getCor()); //1* parte do 4 *quadrante
			x++;
			E = E + u;
			u = u + 2;
			if (v < (2 * E) ){
				y--;
				E = E - v;
				v = v - 2;
			}
			if (x > y) break;
	
			controlePonto.desenharPonto(gc, cx + x, cy - y, definicao.getEspessura(), "", definicao.getCor()); //2* parte do 1* quadrante
			controlePonto.desenharPonto(gc, cx - y, cy - x, definicao.getEspessura(), "", definicao.getCor()); //2* parte do 2* quadrante
			controlePonto.desenharPonto(gc, cx - x, cy + y, definicao.getEspessura(), "", definicao.getCor()); //2* parte do 3* quadrante
			controlePonto.desenharPonto(gc, cx + y, cy + x, definicao.getEspessura(), "", definicao.getCor()); //2* parte do 4* quadrante
		}
	}
	
	public void clicarCirculo(Canvas canvas, GraphicsContext gc) {
		clicar(canvas, gc);
		soltarClique(canvas,  gc);
		arrastarClique(canvas, gc);
	}
	
	//
	public void clicar(Canvas canvas, GraphicsContext gc) {
		canvas.setOnMousePressed(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				if (primeiraVez == true) {
					x1 = (int)event.getX();
					y1 = (int)event.getY();
					circuloMat.setCx(x1);
					circuloMat.setCy(y1);
					primeiraVez = false;
				}
			}
		});
	}
	
 	public void arrastarClique(Canvas canvas, GraphicsContext gc) {
		canvas.setOnMouseDragged(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				if (fimElastico == false) {
					xant = x;
					yant = y;
					circuloMat.setRx(xant);
					circuloMat.setRy(yant);

					// "apaga" reta anterior
					definicao.setCor(Color.WHITE);
					definicao.setEspessura(5);
					desenharCirculo(gc, xant, yant); 
					definicao.setEspessura(3);
				}
				x = (int)event.getX();
				y = (int)event.getY();
				circuloMat.setRx(x);
				circuloMat.setRy(y);

				definicao.setCor(Color.BLACK);
				desenharCirculo(gc, x, y);
				fimElastico = false;
			}	
		});
	}
	
 	public void soltarClique(Canvas canvas, GraphicsContext gc) {
		canvas.setOnMouseReleased(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				if (fimElastico == false) {
					fimElastico = true;
					primeiraVez=true;
				}
			}
		});
	}
 
}
