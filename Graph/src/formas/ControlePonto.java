package formas;


import java.awt.*;

import matematica.Ponto;

public class ControlePonto extends Ponto {
   Color _cor;
   String _str;
   Color _corStr;
   
   int larg = 3;
   
   ControlePonto(int x, int y){
      super((double)x, (double)y);
      setCor(Color.black);	 
      setCorStr(Color.black);	 
      setStr("");	 
   }

   public ControlePonto(int x, int y, Color cor){
      super((double)x, (double)y);
      setCor(cor);	 
      setCorStr(Color.black);	 
      setStr("");	 
   }

   ControlePonto(int x, int y, Color cor, String str){
      super((double)x, (double)y);
      setCor(cor);	 
      setCorStr(Color.black);	 
      setStr(str);	 
   }

   ControlePonto(ControlePonto p2d, Color cor){
      super(p2d);	 
      setCor(cor);	 
      setCorStr(Color.black);	 
      setStr("");	 
  }
   
   ControlePonto(){
      super();	 
      setCor(Color.black);	 
      setCorStr(Color.black);	 
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
   
   public void desenharPonto(Graphics g){
       g.setColor(_cor);
       g.fillOval((int)getX() -(this.larg/2), (int)getY() - (this.larg/2), this.larg, this.larg);
       
       g.setColor(_corStr);
       g.drawString(_str, (int)getX() + larg, (int)getY());
   }
}
