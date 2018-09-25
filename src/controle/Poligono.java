package controle;

import grafica.Definicao;
import javafx.scene.canvas.GraphicsContext;

public class Poligono {
	int x1=0, y1=0, x2=0, y2=0;
	int x=0, y=0, xant=0, yant=0;
	
	boolean primeiraVez = true;
	boolean fimElastico = true;
	
	Definicao definicao;
	ControleReta controleReta;
	
	public void ControlePoligono(Definicao definicao, ControleReta controleReta){
		this.definicao = definicao;
		this.controleReta = controleReta;
	}

	public void desenharPoligono(GraphicsContext gc, double x1, double y1, double x2, double y2){		
		
	}

}
