package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;
public class Menu {
	Image logo;
	Image fondo;
	Image boton1;
	Image boton2;
	Image boton1selec;
	Image boton2selec;
	boolean cantJugador;
	boolean debeGenerar;
	public Menu() {
		cantJugador=false;
		debeGenerar=true;
		logo = Herramientas.cargarImagen("logo.png");
		fondo = Herramientas.cargarImagen("fondomenu.png");
		boton1 = Herramientas.cargarImagen("boton1.png");
		boton2 = Herramientas.cargarImagen("boton2.png");
		boton1selec = Herramientas.cargarImagen("boton1selec.png");
		boton2selec = Herramientas.cargarImagen("boton2selec.png");
	}
	public void mostrar(Entorno e) {
		e.dibujarImagen(fondo, e.ancho()/2, e.alto()/2, 0, 0.8);
		e.dibujarImagen(logo, e.ancho()/2, 100, 0, 1);
		if(!cantJugador) {
			e.dibujarImagen(boton1selec, e.ancho()/2, 350, 0, 0.8);
			e.dibujarImagen(boton2, e.ancho()/2, 550, 0, 0.8);
		}
		if(cantJugador) {
			e.dibujarImagen(boton1, e.ancho()/2, 350, 0, 0.8);
			e.dibujarImagen(boton2selec, e.ancho()/2, 550, 0, 0.8);
		}
		
		

	}
}