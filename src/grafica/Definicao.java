package grafica;

import javafx.scene.paint.Color;

public class Definicao {
	Color cor = Color.BLACK;
	int espessura = 3;
	int vPixels = 600, hPixels = 1200;
	
	public int getVPixels(){
		return this.vPixels;
	}
	
	public int getHPixels(){
		return this.hPixels;
	}
	
	public Color getCor(){
		return this.cor;
	}
	
	public void setCor(Color cor){
		this.cor = cor;
	}
	
	public int getEspessura(){
		return this.espessura;
	}
	
	public void setEspessura(int n){
		this.espessura = n;
	}
}
