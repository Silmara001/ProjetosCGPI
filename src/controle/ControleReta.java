package controle;

import matematica.Reta;
import grafica.RetaGr;
import controle.ControlePonto;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;

public class ControleReta {
	int contClique = 0;
	int indicePonto = 1;
	
	Reta retaMat = new Reta();
	RetaGr retaGr = new RetaGr();
	ControlePonto controlePonto = new ControlePonto();
	
	public void desenharReta(GraphicsContext gc, int x1, int y1, int x2, int y2){
		int  fx, fy, k;
		double a, b;
		
		//caso 1: quando x de p1 e p2 são iguais
		if((x1 == x2) && (y1 != y2)){ 
			//Quando y1 > y2
			if( retaMat.verificarY1MaiorY2(y1, y2) ){
				for(k=y2; k<=y1; k++){
					controlePonto.desenharPonto(gc, x1, k, retaGr.getEspessuraReta(), "", retaGr.getCorReta());
				}
			}else {
			//Quando y1 < y2
				for(k=y1; k<=y2; k++){
					controlePonto.desenharPonto(gc, x1, k, retaGr.getEspessuraReta(), "", retaGr.getCorReta());
				}
			}
		}
			
		//caso 2: quando y de p1 e p2 são iguais
		if((y1 == y2) && (x1 != x2)){ 
			//Quando x1 > x2
			if( retaMat.verificarX1MaiorX2(x1, x2) ){
				for(k=x2; k<=x1; k++){
					controlePonto.desenharPonto(gc, k, y1, retaGr.getEspessuraReta(), "", retaGr.getCorReta());
				}
			}else { 
			//Quando x1 < x2
				for(k=x1; k<=x2; k++){
					controlePonto.desenharPonto(gc, k, y1, retaGr.getEspessuraReta(), "",  retaGr.getCorReta());
				}
			}
		}
		
		//caso 3: quando y e x de p1 e p2 são divergentes
		if((y1 != y2) && (x1 != x2)){
			a = retaMat.obterCoeficienteAngular(x1,y1, x2, y2);
			b = retaMat.obterCoeficienteLinear(x1, y1, a);
			//Quando intervalo de y é menor do que o do x, é preciso usar a função da reta em função de y
			if ( retaMat.obterDistancia(x1, x2)  <  retaMat.obterDistancia(y1, y2) ) {
				//Quando x1 > x2
				if( retaMat.verificarY1MaiorY2(y1, y2) ){ //melhorar a função: esconder os argumentos
					for(k=y2; k<=y1; k++){
						fy = (int) retaMat.obterFy(k, a,  b);
						controlePonto.desenharPonto(gc, fy, k, retaGr.getEspessuraReta(), "",retaGr.getCorReta());
					}
				}else{
					//Quando x1 < x2				
					for(k=y1; k<=y2; k++){
						fy = (int) retaMat.obterFy(k, a,  b);
						controlePonto.desenharPonto(gc, fy, k, retaGr.getEspessuraReta(), "", retaGr.getCorReta());
					}
				}
			}else {
				//Quando x1 > x2
				if( retaMat.verificarX1MaiorX2(x1, x2) ){ //melhorar a função: esconder os argumentos
					for(k=x2; k<=x1; k++){
						fx = (int) retaMat.obterFx(k, a,  b);
						controlePonto.desenharPonto(gc, k, fx, retaGr.getEspessuraReta(), "",retaGr.getCorReta());
					}
				}else{
				//Quando x1 < x2				
					for(k=x1; k<=x2; k++){
						fx = (int) retaMat.obterFx(k, a,  b);
						controlePonto.desenharPonto(gc, k, fx, retaGr.getEspessuraReta(), "", retaGr.getCorReta());
					}
				}
			}
		}
		
	}
	
	public void clicarReta(Canvas canvas, GraphicsContext gc) {
		canvas.setOnMousePressed(event -> {
			int x, y;
			if (event.getButton() == MouseButton.PRIMARY) {
				x = (int)event.getX();
				y = (int)event.getY();
				// desenha ponto na posicao clicada
				controlePonto.desenharPonto(gc, x, y,retaGr.getEspessuraReta(), "P"+ getIndicePonto(),retaGr.getCorReta());
				indicePonto++;
				verificarClickReta(x, y, gc);
				
				//verificar o primeiro clique para pegar as coordenadas do ponto
			} else if (event.getButton() == MouseButton.SECONDARY) {
				x = (int)event.getX();
				y = (int)event.getY();
				// desenha ponto na posicao clicada
				controlePonto.desenharPonto(gc, x, y, retaGr.getEspessuraReta(), "("+ x + ", " + y +")", retaGr.getCorReta());
				indicePonto++;
				verificarClickReta(x, y, gc);
			}
		});
	}
	
 	public void verificarClickReta(int x, int y, GraphicsContext gc){
		if(contClique == 0){
			contClique++;
			retaGr.setX1(x);
			retaGr.setY1(y);
		}else if(contClique == 1){
			contClique = 0;
			retaGr.setX2(x);
			retaGr.setY2(y);
			desenharReta(gc, retaGr.getX1(), retaGr.getY1(), retaGr.getX2(), retaGr.getY2());
			retaGr.setPontos();
		}
	}
 	
 	public void setContClique(int n){
 		this.contClique = n;
 	}
 	
 	public void setIndicePonto(int n){
 		this.indicePonto = n;
 	}
 	
	public int getIndicePonto(){
 		return this.indicePonto;
 	}	
}
