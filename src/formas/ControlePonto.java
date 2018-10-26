package formas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import matematica.Ponto;

public class ControlePonto extends Ponto {
   Color _cor;
   String _str;
   Color _corStr;
   
   int larg = 3;
   
   ControlePonto(int x, int y){
      super((double)x, (double)y);
      setCor(Color.BLACK);	 
      setCorStr(Color.BLACK);	 
      setStr("");	 
   }

   public ControlePonto(int x, int y, Color cor){
      super((double)x, (double)y);
      setCor(cor);	 
      setCorStr(Color.BLACK);	 
      setStr("");	 
   }

   ControlePonto(int x, int y, Color cor, String str){
      super((double)x, (double)y);
      setCor(cor);	 
      setCorStr(Color.BLACK);	 
      setStr(str);	 
   }

   ControlePonto(ControlePonto p2d, Color cor){
      super(p2d);	 
      setCor(cor);	 
      setCorStr(Color.BLACK);	 
      setStr("");	 
  }
   
   ControlePonto(){
      super();	 
      setCor(Color.BLACK);	 
      setCorStr(Color.BLACK);	 
      setStr("");	 
  }
   
   private void setCor(Color cor){
   	  _cor = cor;
   }
   private void setCorStr(Color corStr){
   	  _corStr = corStr;
   }
   private void setStr(String str){
   	  _str = str;
   }
   
   public void desenharPonto(GraphicsContext g){
	   g.setFill(_cor);
       g.fillOval((int)getX() -(this.larg/2), (int)getY() - (this.larg/2), this.larg, this.larg);
       
       g.setFill(_corStr);
       g.strokeText(_str, (int)getX() + larg, (int)getY());
   }
}
