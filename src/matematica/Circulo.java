package matematica;

public class Circulo {
	//sen = CO/Hip  cos = CA/Hip tan = CO/CA
	//formula do circulo y = raiz(x^2 + y^2)
	// no primeiro clique pego a origem, no segundo o raio
	
	double raio = 0, cx = 0, cy = 0, rx = 0, ry = 0;
	
	public void setCentro(double x, double y){
		this.cx =x;
		this.cy =y;
	}
	
	public void calcularRaio(){//
		if( (cx == rx) && (cy != ry) ){
			raio = obterDistancia(cy, ry);
		}else if( (cy == ry) && (cx != rx) ){
			raio = obterDistancia(cx, rx);
		}else{
			//considerando o ponto p1 a esquerda e pe a direita
			double ca, co, hip;
			ca = obterDistancia(rx, cx);
			ca = ca * ca;
			//System.out.println("CA: " + ca);
			co = obterDistancia(ry, cy);
			co = co * co;
			//System.out.println("CO: " + co);
			hip = Math.sqrt(ca + co);
			//System.out.println("HIP: " + hip);
			raio = hip;
		}		
	}
	
	public double obterFx(int raio){
		double fx = Math.sqrt((raio * raio) - (rx * rx));
		return fx;
	}
	
	public void setCx(double x){
		this.cx = x;
	}
	
	public void setCy(double y){
		this.cy = y;
	}
	
	public double getCx(){
		return this.cx;
	}
	
	public double getCy(){
		return this.cy;
	}
	
	public double getRx(){
		return this.rx;
	}
	
	public double getRy(){
		return this.ry;
	}
	
	public void setRx(double x){
		this.rx = x;
	}
	
	public void setRy(double y){
		this.ry = y;
	}
	
	public double getRaio(){
		return this.raio;
	}
	
	public void setRaio(int n){
		this.raio = n;
	}
	
	public double obterDistancia(double x, double y){
		double d = 0;
		if(x < y){
			d = y - x;
		}else{
			d = x - y;
		}
		return d;
	}
	
}
