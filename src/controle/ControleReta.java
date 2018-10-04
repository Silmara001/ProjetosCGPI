package controle;

import matematica.Reta;
import grafica.Definicao;
import controle.ControlePonto;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

public class ControleReta extends Forma{
	int indicePonto = 1;
	int x1=0, y1=0, x2=0, y2=0;
	int x=0, y=0, xant=0, yant=0;
	
	boolean primeiraVez = true;
	boolean fimElastico = true;
	
	Reta retaMat = new Reta();
	ControlePonto controlePonto = new ControlePonto();
	Definicao definicao;
	private OnDrawForm _listener;
	
	public ControleReta(Definicao definicao){
		this.definicao = definicao;
	}
	
	public void desenharReta(GraphicsContext gc, int x1, int y1, int x2, int y2){
		int  fx, fy, k;
		double a, b;
		
		//caso 1: quando x de p1 e p2 s�o iguais
		if((x1 == x2) && (y1 != y2)){ 
			//Quando y1 > y2
			if( retaMat.verificarY1MaiorY2(y1, y2) ){
				for(k=y2; k<=y1; k++){
					controlePonto.desenharPonto(gc, x1, k, definicao.getEspessura(), "", definicao.getCor());
				}
			}else {
			//Quando y1 < y2
				for(k=y1; k<=y2; k++){
					controlePonto.desenharPonto(gc, x1, k, definicao.getEspessura(), "", definicao.getCor());
				}
			}
		}
			
		//caso 2: quando y de p1 e p2 s�o iguais
		if((y1 == y2) && (x1 != x2)){ 
			//Quando x1 > x2
			if( retaMat.verificarX1MaiorX2(x1, x2) ){
				for(k=x2; k<=x1; k++){
					controlePonto.desenharPonto(gc, k, y1, definicao.getEspessura(), "", definicao.getCor());
				}
			}else { 
			//Quando x1 < x2
				for(k=x1; k<=x2; k++){
					controlePonto.desenharPonto(gc, k, y1, definicao.getEspessura(), "",  definicao.getCor());
				}
			}
		}
		
		//caso 3: quando y e x de p1 e p2 s�o divergentes
		if((y1 != y2) && (x1 != x2)){
			a = retaMat.obterCoeficienteAngular(x1,y1, x2, y2);
			b = retaMat.obterCoeficienteLinear(x1, y1, a);
			//Quando intervalo de y � menor do que o do x, � preciso usar a fun��o da reta em fun��o de y
			if ( retaMat.obterDistancia(x1, x2)  <  retaMat.obterDistancia(y1, y2) ) {
				//Quando x1 > x2
				if( retaMat.verificarY1MaiorY2(y1, y2) ){ 
					for(k=y2; k<=y1; k++){
						fy = (int) retaMat.obterFy(k, a,  b);
						controlePonto.desenharPonto(gc, fy, k, definicao.getEspessura(), "",definicao.getCor());
					}
				}else{
					//Quando x1 < x2				
					for(k=y1; k<=y2; k++){
						fy = (int) retaMat.obterFy(k, a,  b);
						controlePonto.desenharPonto(gc, fy, k, definicao.getEspessura(), "", definicao.getCor());
					}
				}
			}else {
				//Quando x1 > x2
				if( retaMat.verificarX1MaiorX2(x1, x2) ){
					for(k=x2; k<=x1; k++){
						fx = (int) retaMat.obterFx(k, a,  b);
						controlePonto.desenharPonto(gc, k, fx, definicao.getEspessura(), "",definicao.getCor());
					}
				}else{
				//Quando x1 < x2				
					for(k=x1; k<=x2; k++){
						fx = (int) retaMat.obterFx(k, a,  b);
						controlePonto.desenharPonto(gc, k, fx, definicao.getEspessura(), "", definicao.getCor());
					}
				}
			}
		}
		
	}
	
	public Forma clicarReta(Canvas canvas, GraphicsContext gc, OnDrawForm mListener) {
		this._listener = mListener;
		clicar(canvas, gc);
		soltarClique(canvas,  gc);
		arrastarClique(canvas, gc);
		return this;
	}
 	
 	public void clicar(Canvas canvas, GraphicsContext gc) {
 		canvas.setOnMousePressed(event -> {
 			if (event.getButton() == MouseButton.PRIMARY) {
 				if (primeiraVez == true) {
 					x1 = (int)event.getX();
 					y1 = (int)event.getY();
 					controlePonto.desenharPonto(gc, x1, y1, definicao.getEspessura(), "", definicao.getCor());
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
					// "apaga" reta anterior
					definicao.setCor(Color.WHITE);
					definicao.setEspessura(5);
					desenharReta(gc, x1, y1, xant, yant);
					definicao.setEspessura(3);
					
				}
				x = (int)event.getX();
				y = (int)event.getY();
				definicao.setCor(Color.BLACK);
				desenharReta(gc, x1, y1, x, y);
				fimElastico = false;
			}	
		});
	}
	
 	public void soltarClique(Canvas canvas, GraphicsContext gc) {
		canvas.setOnMouseReleased(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				if (fimElastico == false) {
					desenharReta(gc, x1, y1, x, y);
					fimElastico = true;
					primeiraVez = true;
				}
			}
		});
	}
 	
 	public void setIndicePonto(int n){
 		this.indicePonto = n;
 	}
 	
	public int getIndicePonto(){
 		return this.indicePonto;
 	}
}
