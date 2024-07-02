package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Princesa {
	double x;
	double y;
	Image img1;
	Image img2;
	Image img3;
	Image img4;
	Image img5;
	Image img6;
	Image cara;
	Image carainv;
	Image corazon;
	Image corazoninv;
	int vida;
	int contHud;
	int contSalto;
	int contInv;
	int contEst;
	int puntaje;
	double escala;
	double alto;
	double ancho;
	boolean invencibilidad;
	boolean tieneEstrella;
	boolean estaApoyado;
	boolean apoyadoIzq;
	boolean apoyadoDer;
	boolean estaSaltando; // false = no esta saltando
	boolean estaCayendo;
	boolean dir; // false = izquierda true = derecha
	boolean comprobarJugador; //false = princesa 1 true = princesa 2
	public Princesa(int x, int y,boolean comprobar) {
		this.x = x;
		this.y = y;
		this.contSalto=0;
		this.vida=3;
		this.invencibilidad=false;
		this.tieneEstrella=false;
		this.contInv=0;
		this.contHud=0;
		this.puntaje=0;
		this.comprobarJugador=comprobar;
		if(!comprobarJugador) {
			img1 = Herramientas.cargarImagen("princesaizq2.png");
			img2 = Herramientas.cargarImagen("princesader2.png");
			img3 = Herramientas.cargarImagen("princesainvizq.png");
			img4 = Herramientas.cargarImagen("princesainvder.png");
			img5 = Herramientas.cargarImagen("princesaizqestrella.gif");
			img6 = Herramientas.cargarImagen("princesaderestrella.gif");
			cara = Herramientas.cargarImagen("caraprincesa.png");
			carainv = Herramientas.cargarImagen("caraprincesainv.png");
		}
		else {
			img1 = Herramientas.cargarImagen("princesa2izq.png");
			img2 = Herramientas.cargarImagen("princesa2der.png");
			img3 = Herramientas.cargarImagen("princesa2invizq.png");
			img4 = Herramientas.cargarImagen("princesa2invder.png");
			img5 = Herramientas.cargarImagen("princesa2izqestrella.gif");
			img6 = Herramientas.cargarImagen("princesa2derestrella.gif");
			cara = Herramientas.cargarImagen("caraprincesa2espejo.png");
			carainv = Herramientas.cargarImagen("caraprincesa2invespejo.png");
		}
			
		corazon = Herramientas.cargarImagen("corazon.png");
		corazoninv = Herramientas.cargarImagen("corazon2.png");
		dir=false;
		estaApoyado=false;
		escala=0.03;
		alto=img1.getHeight(null)*escala;
		ancho=img1.getWidth(null)*escala;
	}
	public void dibujarse(Entorno entorno) {
		if(dir && !this.invencibilidad && !this.tieneEstrella) {
			entorno.dibujarImagen(img2, x, y, 0,escala);
		}
		if(dir && this.invencibilidad && !this.tieneEstrella) {
			entorno.dibujarImagen(img4, x, y, 0,escala);
		}
		if(dir && this.tieneEstrella) {
			entorno.dibujarImagen(img6, x, y, 0,escala);
		}
		if(!dir && !this.invencibilidad && !this.tieneEstrella) {
			entorno.dibujarImagen(img1, x, y, 0,escala);
		}
		if(!dir && this.invencibilidad && !this.tieneEstrella) {
			entorno.dibujarImagen(img3, x, y, 0,escala);
		}
		if(!dir && this.tieneEstrella) {
			entorno.dibujarImagen(img5, x, y, 0,escala);
		}

	}
	public void dibujarHud(Entorno entorno, double x, double y) {
		if(!comprobarJugador && contHud!=200) {
			this.contHud++;
			entorno.dibujarImagen(cara, x, y, 0,0.08);
			for(int k = 1; k <=vida; k++){
				entorno.dibujarImagen(corazon, x+45*k, y, 0,0.08);
			}
			
		}
		if(comprobarJugador && contHud!=200) {
			this.contHud++;
			for(int k = 1; k <=vida; k++){
				entorno.dibujarImagen(corazon, x-45*k, y, 0,0.08);
			}
			entorno.dibujarImagen(cara, x, y, 0,0.08);
		}
		if(!comprobarJugador && contHud==200) {
			entorno.dibujarImagen(carainv, x, y, 0,0.08);
			for(int k = 1; k <=vida; k++){
				entorno.dibujarImagen(corazoninv, x+45*k, y, 0,0.08);
			}
		}
		if(comprobarJugador && contHud==200) {
			for(int k = 1; k <=vida; k++){
				entorno.dibujarImagen(corazoninv, x-45*k, y, 0,0.08);
			}
			entorno.dibujarImagen(carainv, x, y, 0,0.08);
		}
	}
	public void moverseizq(boolean direccion) {
		if(!apoyadoIzq && (estaApoyado || estaSaltando) && this.x>0+ancho/4) {
			if(!direccion) {
				this.x-=3;
			}
			this.dir = direccion;	
		}
	}
	public void moverseder(boolean direccion) {
		if(!apoyadoDer && (estaApoyado || estaSaltando) && this.x<1280-ancho/4) {
			if(direccion) {
				this.x+=3;
			}
		}
	
		this.dir = direccion;	
	}
	
	public void caer() {
		if(!estaApoyado && !estaSaltando) {
			estaCayendo=true;
			this.y+=5;
		}
		if(estaSaltando) {
			
			contSalto++;
			this.y-=8;
		}
		if(contSalto==28) {
			contSalto=0;
			estaSaltando=false;
		}
	}
	public void invencible() {
		if(invencibilidad) {
			contInv++;
		}
		if(contInv==150) {
			contInv=0;
			invencibilidad=false;
		}
	}
	public void restarVida() {
		this.invencibilidad=true;
		this.contHud=0;
		this.vida--;
	}
	public void estrella() {
		if(tieneEstrella) {
			contEst++;
		}
		if(contEst==300) {
			contEst=0;
			tieneEstrella=false;
		}
	}
	public double getTecho() {
		return y - alto/2;
	}
	public double getPiso() {
		return y + alto/2;
	}
	public double getDer() {
		return x + ancho/4;
	}
	public double getIzq() {
		return x - ancho/4;
	}
}
