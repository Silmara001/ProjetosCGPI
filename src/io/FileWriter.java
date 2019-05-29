package io;
import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import formas.*;
import javafx.scene.paint.Color;
import matematica.Circulo;
import matematica.Ponto;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FileWriter {

	public static void write(File file, List<Forma> formas) {

	  try {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		Document doc = docBuilder.newDocument();
		Element figuraElement = doc.createElement("Figura");
		doc.appendChild(figuraElement);

		for (Forma forma : formas) {

		    if(forma instanceof ControleReta){
                createReta(doc, figuraElement, (ControleReta) forma);
            } else if (forma instanceof Circulo) {
                createCirculo(doc, figuraElement, (Circulo) forma);
            } else if (forma instanceof ControleRetangulo) {
                createRetangulo(doc, figuraElement, (ControleRetangulo) forma);
            } else if (forma instanceof ControleLinha) {
                createLinhaPoligonal(doc, figuraElement, (ControleLinha) forma);
            }
        }

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(file);
		transformer.transform(source, result);

	  } catch (Exception tfe) {
		tfe.printStackTrace();
	  }
	}

	private static void createLinhaPoligonal(Document doc, Element figuraElement, ControleLinha controleLinha) {
		String tagName;

		if(controleLinha.getPoligonoFechado()){
            tagName = "Poligono";
        } else {
		    tagName = "LinhaPoligonal";
        }


		Element linhaPoligonalElement = doc.createElement(tagName);
		figuraElement.appendChild(linhaPoligonalElement);
		
		for(Ponto p : controleLinha.getPontos()) {
			linhaPoligonalElement.appendChild(createPonto(p, doc));
		}
		linhaPoligonalElement.appendChild(createCor(doc, controleLinha.get_cor()));
	}

	private static void createRetangulo(Document doc, Element figuraElement, ControleRetangulo controleRetangulo) {
		Element retanguloElement = doc.createElement("Retangulo");
		figuraElement.appendChild(retanguloElement);
		
		retanguloElement.appendChild(createPonto(controleRetangulo.getVertice1(), doc));
		retanguloElement.appendChild(createPonto(controleRetangulo.getVertice2(), doc));
		retanguloElement.appendChild(createCor(doc, controleRetangulo.get_cor()));
	}


	private static void createCirculo(Document doc, Element figuraElement, Circulo circulo) {
		Element circuloElement = doc.createElement("Circulo");
		figuraElement.appendChild(circuloElement);
		
		circuloElement.appendChild(createPonto(circulo.getCentro(), doc));
		Element raio = doc.createElement("Raio");
		raio.appendChild(doc.createTextNode((Conversor.pixelToRelative(circulo.getRaio()))+""));
		circuloElement.appendChild(raio);
		circuloElement.appendChild(createCor(doc, circulo.get_cor()));
	}


	private static void createReta(Document doc, Element figuraElement, ControleReta controleReta) {
		Element retaElement = doc.createElement("Reta");
		figuraElement.appendChild(retaElement);
		
		retaElement.appendChild(createPonto(controleReta.getP1(), doc));
		retaElement.appendChild(createPonto(controleReta.getP2(), doc));
		retaElement.appendChild(createCor(doc, controleReta.get_cor()));
	}
	
	
	private static Element createPonto(Ponto ponto, Document doc) {
		Element dot = doc.createElement("Ponto");
		Element px = doc.createElement("x");
		Element py = doc.createElement("y");
		
		px.appendChild(doc.createTextNode(Conversor.pixelToRelative(ponto.getX())));
		py.appendChild(doc.createTextNode(Conversor.pixelToRelative(ponto.getY())));
		
		dot.appendChild(px);
		dot.appendChild(py);
		return dot;
	}
	
	private static Element createCor(Document doc, Color corForm) {
		Element cor = doc.createElement("Cor");
		Element r = doc.createElement("R");
		Element g = doc.createElement("G");
		Element b = doc.createElement("B");
		
		/*
		r.appendChild(doc.createTextNode(corForm.getRed()+""));
		g.appendChild(doc.createTextNode(corForm.getGreen()+""));
		b.appendChild(doc.createTextNode(corForm.getBlue()+""));
		*/
		
		r.appendChild(doc.createTextNode("0"));
		g.appendChild(doc.createTextNode("0"));
		b.appendChild(doc.createTextNode("0"));
		
		
		cor.appendChild(r);
		cor.appendChild(g);
		cor.appendChild(b);
		return cor;
	}
	
}
