package grafica;

import javafx.scene.paint.Color;

public class CirculoGr {
	Color cor = Color.BLACK;
	int espessura = 3;
	
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
