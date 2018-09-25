package controle;

import grafica.Definicao;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

public class ControleRetangulo {
	int x1=0, y1=0, x2=0, y2=0;
	int x=0, y=0, xant=0, yant=0;
	
	boolean primeiraVez = true;
	boolean fimElastico = true;
	
	Definicao definicao;
	ControleReta controleReta;
	
	public ControleRetangulo(Definicao definicao, ControleReta controleReta){
		this.definicao = definicao;
		this.controleReta = controleReta;
	}

	public void desenharRetangulo(GraphicsContext gc, double x1, double y1, double x2, double y2){		
		//reta: pt(x1, y1) e pt(x2, y1)
		controleReta.desenharReta(gc, (int)x1, (int)y1, (int)x2, (int)y1);
		//reta: pt(x1, y1) e pt(x1, y2)
		controleReta.desenharReta(gc, (int)x1, (int)y1, (int)x1, (int)y2);
		//reta: pt(x1, y2) e pt(x2, y2)
		controleReta.desenharReta(gc, (int)x1, (int)y2, (int)x2, (int)y2);
		//reta: pt(x2, y1) e pt(x2, y2)
		controleReta.desenharReta(gc, (int)x2, (int)y1, (int)x2, (int)y2);
	}
	
	public void clicarRetangulo(Canvas canvas, GraphicsContext gc) {
		clicar(canvas, gc);
		soltarClique(canvas,  gc);
		arrastarClique(canvas, gc);
	}
 	
 	public void clicar(Canvas canvas, GraphicsContext gc) {
		canvas.setOnMousePressed(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				if (primeiraVez == true) {
					x1 = (int)event.getX();
					y1 = (int)event.getY();
					primeiraVez = false;
				} 
			}
		});
	}
	
 	public void soltarClique(Canvas canvas, GraphicsContext gc) {
		canvas.setOnMouseDragged(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				if (fimElastico == false) {
					xant = x;
					yant = y;
					// "apaga" reta anterior
					definicao.setCor(Color.WHITE);
					definicao.setEspessura(5);
					desenharRetangulo(gc, x1, y1, xant, yant);
					definicao.setEspessura(3);
				}
				x = (int)event.getX();
				y = (int)event.getY();
				definicao.setCor(Color.BLACK);
				desenharRetangulo(gc, x1, y1, x, y);
				fimElastico = false;
			}
		});
	}
	
 	public void arrastarClique(Canvas canvas, GraphicsContext gc) {
		canvas.setOnMouseReleased(event -> {
			if (fimElastico == false) {
				desenharRetangulo(gc, x1, y1, x, y);
				fimElastico = true;
				primeiraVez=true;
			}
		});
	}
}