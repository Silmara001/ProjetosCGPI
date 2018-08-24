package grafica;

import javafx.scene.paint.Color;
import grafica.RetaGr;
import matematica.Reta;

public class RetaGr extends Reta {
	int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
	Color cor = Color.GRAY;
	int espessura = 3;
	
	public int getX1(){
		return this.x1; 
	}
	
	public int getX2(){
		return this.x2; 
	}
	
	public int getY1(){
		return this.y1; 
	}
	
	public int getY2(){
		return this.y2; 
	}
	
	public Color getCorReta() {
		return this.cor;
	}
	
	public void setX1(int n){
		this.x1 = n; 
	}
	
	public void setX2(int n){
		this.x2 = n; 
	}
	
	public void setY1(int n){
		this.y1 = n; 
	}

	public void setY2(int n){
		this.y2 = n; 
	}
	
	public void setPontos(){
		this.x1 = 0;
		this.y1 = 0;
		this.x2 = 0;
		this.y2 = 0;
	}
	
	public int getEspessuraReta(){
 		return this.espessura;
 	}
}
