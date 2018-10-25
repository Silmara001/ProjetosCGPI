package formas;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import matematica.Ponto;

public class ControleLinha extends Forma {

    List<Ponto> pontos = new ArrayList<Ponto>();
    boolean poligono_fechado;

    //Construtores

    ControleLinha(List<Ponto> pontos){
        this.pontos = pontos;
        this._cor = Color.BLACK;
        this.poligono_fechado = false;
    }

    public ControleLinha(List<Ponto> pontos, Color color){
        this.pontos = pontos;
        this._cor = color;
        this.poligono_fechado = false;
    }

    public ControleLinha(List<Ponto> pontos, Color color, boolean fechado){
        this.pontos = pontos;
        this._cor = color;
        this.poligono_fechado = fechado;
    }

    public List<Ponto> getPontos() {
        return pontos;
    }

    //Getters e Setters

    public void setPontos(List<Ponto> pontos) {
        this.pontos = pontos;
    }

    public void addPonto(Ponto ponto) {
        this.pontos.add(ponto);
    }

    public boolean getPoligonoFechado(){
        return this.poligono_fechado;
    }

    public void setPoligono_fechado(boolean fechado) {
        this.poligono_fechado = fechado;
    }

   

    private ArrayList<ControleReta> calcularRetas() {
        ArrayList<ControleReta> controleRetas = new ArrayList<>();


        for(int i = 0; i < getPontos().size() - 1 ; i++){
            Ponto p1 = getPontos().get(i);
            Ponto p2 = getPontos().get(i+1);
            ControleReta controleReta = new ControleReta(p1, p2, this._cor);
            controleRetas.add(controleReta);
        }

        if (this.poligono_fechado){
            Ponto primeiroPonto  = getPontos().get(0);
            Ponto ultimoPonto = getPontos().get(getPontos().size()-1);
            ControleReta controleReta = new ControleReta(primeiroPonto, ultimoPonto, this._cor);
            controleRetas.add(controleReta);
        }

        return controleRetas;
    }

    @Override
    public void desenhar(Graphics g) {
        ArrayList<ControleReta> controleRetas = calcularRetas();
        for(ControleReta r: controleRetas) {
            r.desenhar(g);
        }
    }

    @Override
    public boolean pontoNaForma(Ponto p, int margemDeErro) {
        boolean encontrouForma = false;

        ArrayList<ControleReta> controleRetas = calcularRetas();

        for(ControleReta r: controleRetas) {
            if(r.pontoNaForma(p, margemDeErro)){
                encontrouForma = true;
                break;
            }
        }

        return encontrouForma;
    }

    
//    @Override
//    public void rotacionar(Ponto p, double angulo) {
//        for(Ponto vertice : this.pontos){
//            vertice.rotacionar(p, angulo);
//        }
//    }
//
//    @Override
//    public void escalar(double fatorEscala) {
//        for(Ponto p : this.pontos){
//            p.escalar(fatorEscala);
//        }
//    }
//
//    @Override
//    public void transladar(int distanciaX, int distanciaY) {
//        for(Ponto p : this.pontos){
//            p.transladar(distanciaX, distanciaY);
//        }
//
//    }
}
