package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JApplet;

import viajante.Viajante;

/**
 * Esta clase invoca a GrafoPanel para obtener el grafo a partir
 * del CSP del viajante
 * 
 * @author LuisGP
 *
 */
public class Grafo extends JApplet{

    GrafoPanel grafoPanel;
    private ArrayList<String> ciudades;
	private ArrayList<int[]> enlaces;
	private Viajante viajante;

	public Grafo(Viajante v){
		super();
		this.viajante = v;
		this.ciudades = v.getCiudades();
		this.enlaces = v.getEnlaces();
		getContentPane().setLayout(new BorderLayout());
        grafoPanel = new GrafoPanel(viajante);
        grafoPanel.setBackground(Color.white);
        getContentPane().add(grafoPanel);
		grafoPanel.repaint();
	}
	
    public void init(){
        ;
    }
}