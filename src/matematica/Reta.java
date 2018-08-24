package matematica;

public class Reta {

	public double obterFx(double x, double a, double b){
		double y = a*x + b;
		return y;
	}
	
	public double obterFy(double y, double a, double b){
		double x = (y - b)/a;
		return x;
	}
	
	public double obterCoeficienteAngular(double x1, double y1, double x2, double y2 ){
		double a = 1; 
		double y = (y2 - y1);
		double x = (x2 - x1);
		if(x != 0){
			a = y/x;
		}
		return a;
	}
	
	public double obterCoeficienteLinear(double x1, double y1, double a){
		double b = y1 - (a * x1);
		return b;
	}
	
	public boolean verificarX1MaiorX2(int x1, int x2){
		boolean verifica = false;
		if (x1 > x2){
			verifica = true;
		}
		return verifica;
	}

	public boolean verificarY1MaiorY2(int y1, int y2){
		boolean verifica = false;
		if (y1 > y2){
			verifica = true;
		}
		return verifica;
	}
	
	public int obterDistancia(int x1, int x2){
		int i = 0;
		
		if(x2 > x1){
			i = x2 - x1;
		}else if( x1 > x2){
			i = x1 - x2;
		}
		return i;
	}	 
}
