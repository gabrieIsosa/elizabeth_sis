package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Item {
	double x;
	double y;
	Image img1;
	double escala;
	double alto;
	double ancho;
	int tipo;
	boolean estaApoyado;
	public Item(double x, double y) {
		this.x = x;
		this.y = y;
		estaApoyado=false;
		if(Math.random()>0.5) {
			tipo=1;
			img1 = Herramientas.cargarImagen("estrella.png");
		}
		else {
			tipo=0;
			img1 = Herramientas.cargarImagen("corazon.png");
		}
		
		escala=0.05;
		alto=img1.getHeight(null)*escala;
		ancho=img1.getWidth(null)*escala;
	}
	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(img1, x, y, 0,escala);
	}
	public void caer() {
		if(!estaApoyado) {
			this.y+=1;
		}
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
