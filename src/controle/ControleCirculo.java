package controle;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import controle.ControlePonto;
import matematica.Circulo;
import grafica.CirculoGr; 

public class ControleCirculo {
	int contClique = 0;
	Circulo circuloMat = new Circulo();
	ControlePonto controlePonto = new ControlePonto();
	CirculoGr circuloGr = new CirculoGr();

	
	/*public void desenharCirculo(GraphicsContext gc, int cx, int cy, int rx, int ry){
		circuloMat.calcularRaio();
		int raio = (int) circuloMat.getRaio() - 1, x, y, angulo = 0;
		for(angulo = 0; angulo <= 45; angulo++){
			x = (int)(raio * Math.cos(angulo) ) ;
			y = (int) (raio * Math.sin(angulo) ) ;
			controlePonto.desenharPonto(gc, cx + x, cy + y, 3, "", Color.BLUE);
		 }
	}*/
	
	public void arc8(GraphicsContext gc, int cx, int cy, int rx, int ry){
		 circuloMat.calcularRaio();
		 int x = 0, y = (int)circuloMat.getRaio(), u = 1, v = 2 * (int) circuloMat.getRaio() -1, e = 0;
		 while(x <= y){
			 controlePonto.desenharPonto(gc, cx + x, cy + y, 1, "", Color.BLUE);
			 x++; e += u; u += 2;
			 if (v < 2 * e) {y--; e-= v; v-= 2;}
		 }
	}
	//utilizando algoritmo do PDF Walderson Shimokawa
	void desenharCirculo(GraphicsContext gc, int cx, int cy){
		circuloMat.calcularRaio();
		int x = 0, y = (int)circuloMat.getRaio(), u = 1, v = 2 * (int)circuloMat.getRaio() - 1, E = 0;
		while (x < y){
			controlePonto.desenharPonto(gc, cx + y, cy - x, circuloGr.getEspessura(), "", circuloGr.getCor()); //1* parte do 1* quadrante
			controlePonto.desenharPonto(gc, cx - x, cy - y, circuloGr.getEspessura(), "", circuloGr.getCor()); //1* parte do 2* quadrante
			controlePonto.desenharPonto(gc, cx - y, cy + x, circuloGr.getEspessura(), "", circuloGr.getCor()); //1* parte do 3* quadrante
			controlePonto.desenharPonto(gc, cx + x, cy + y, circuloGr.getEspessura(), "", circuloGr.getCor()); //1* parte do 4 *quadrante
			x++;
			E = E + u;
			u = u + 2;
			if (v < (2 * E) ){
				y--;
				E = E - v;
				v = v - 2;
			}
			if (x > y) break;
	
			controlePonto.desenharPonto(gc, cx + x, cy - y, circuloGr.getEspessura(), "", circuloGr.getCor()); //2* parte do 1* quadrante
			controlePonto.desenharPonto(gc, cx - y, cy - x, circuloGr.getEspessura(), "", circuloGr.getCor()); //2* parte do 2* quadrante
			controlePonto.desenharPonto(gc, cx - x, cy + y, circuloGr.getEspessura(), "", circuloGr.getCor()); //2* parte do 3* quadrante
			controlePonto.desenharPonto(gc, cx + y, cy + x, circuloGr.getEspessura(), "", circuloGr.getCor()); //2* parte do 4* quadrante
		}
	}
	
	public void clicarCirculo(Canvas canvas, GraphicsContext gc) {
		canvas.setOnMousePressed(event -> {
			int x, y;
			if (event.getButton() == MouseButton.PRIMARY) {
				x = (int)event.getX();
				y = (int)event.getY();
				controlePonto.desenharPonto(gc, x, y, circuloGr.getEspessura(), "", Color.RED);
				verificarClickCirculo(x , y, gc);
				
			} else if (event.getButton() == MouseButton.SECONDARY) {
				x = (int)event.getX();
				y = (int)event.getY();
				controlePonto.desenharPonto(gc, x, y, circuloGr.getEspessura(), "("+ x + ", " + y +")", Color.RED);
				verificarClickCirculo(x , y, gc);
			}
		});
	}
	
 	public void verificarClickCirculo(int x, int y, GraphicsContext gc){
		if(contClique == 0){
			contClique++;
			circuloMat.setCx(x);
			circuloMat.setCy(y);
		}else if(contClique == 1){
			contClique = 0;
			circuloMat.setRx(x);
			circuloMat.setRy(y);
			desenharCirculo(gc, (int)circuloMat.getCx(), (int)circuloMat.getCy()); 
		}
	}
 	
 	public void setContClique(int n){
 		this.contClique = n;
 	}
	
}
