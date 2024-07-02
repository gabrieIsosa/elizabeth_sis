package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Bomba {
	double x;
	double y;
	Image img1;
	Image img2;
	double escala;
	double alto;
	double ancho;
	boolean dir;
	public Bomba(double x, double y, boolean direccion) {
		this.x = x;
		this.y = y;
		img1 = Herramientas.cargarImagen("bomba.png");
		this.dir=direccion;
		this.escala=0.08;
		this.alto=img1.getHeight(null)*escala;
		this.ancho=img1.getWidth(null)*escala;
	}
	public void dibujarse(Entorno entorno) {
		if(this.dir) {
			entorno.dibujarImagen(img1, x, y, 0,escala);
		}
		else {
			entorno.dibujarImagen(img1, x, y, 0,escala);
		}
	}
	public void moverse() {
		if(this.dir) {
			this.x+=2;
		}
		else {
			this.x-=2;
		}
	}
	public void caerBala(double yBala) {
		double caida=yBala+50;
		if(this.y<caida) {
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
