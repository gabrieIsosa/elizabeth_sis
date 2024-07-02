package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Meteorito {
	double x;
	double y;
	Image img1;
	double escala;
	double alto;
	double ancho;

	
	public Meteorito(double x, double y) {
		this.x = x;
		this.y = y;
		img1 = Herramientas.cargarImagen("meteoritoJefe.png");
		escala=0.2;
		alto=img1.getHeight(null)*escala;
		ancho=img1.getWidth(null)*escala;
	}
	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(img1, x, y, 0,escala);
	}
	public void caer() {
		this.y+=2;
	}
	public double getTecho() {
		return y - alto/2;
	}
	public double getPiso() {
		return y + alto/2;
	}
	public double getDer() {
		return x + ancho/2;
	}
	public double getIzq() {
		return x - ancho/2;
	}
}
