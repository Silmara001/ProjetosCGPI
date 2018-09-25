package controle;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import grafica.PontoGr;

public class ControlePonto {
	int indicePonto= 1;
	
	public void desenharPonto(GraphicsContext g, int x, int y, int diametro, String nome, Color cor) {
		PontoGr p; 
		// Cria um ponto
		p = new PontoGr(x, y, cor, nome, diametro);
		// Desenha o ponto
		p.desenharPonto(g);
	}
	
	public int getIndicePonto() {
		return this.indicePonto;
	}
	
	public int IncrementarIndicePonto() {
		return this.indicePonto++;
	}
	
	public void clicarPonto(Canvas canvas, GraphicsContext gc) {
		canvas.setOnMousePressed(event -> {
			int x, y;
			if (event.getButton() == MouseButton.PRIMARY) {
				x = (int)event.getX();
				y = (int)event.getY();
				// desenha ponto na posicao clicada
				desenharPonto(gc, x, y, 1, "P"+ getIndicePonto(), Color.BLUE);
				IncrementarIndicePonto();
			}else if (event.getButton() == MouseButton.SECONDARY) {
				x = (int)event.getX();
				y = (int)event.getY();
				// desenha ponto na posicao clicada
				desenharPonto(gc, x, y, 1, "("+ x + ", " + y +")", Color.RED);
			
			}
		});
		canvas.setOnMouseDragged(event -> { });
	}
	
	public void setIndicePonto(int n){
 		this.indicePonto = n;
 	}
}
