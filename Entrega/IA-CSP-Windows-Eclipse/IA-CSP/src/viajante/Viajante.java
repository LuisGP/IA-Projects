package viajante;

import interfaz.Grafo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JApplet;
import javax.swing.JFrame;

import csp.CSP;
import csp.Estado;
import csp.Heuristica;
import csp.Metodo;
import csp.MinValRes;
import csp.Restriccion;
import csp.VA_haciaDelante;
import csp.Valores;
import csp.Variable;

/**
 * CSP específico del problema del viajante
 * 
 * @author LuisGP
 *
 */
public class Viajante extends CSP{
	
	/** Metodo a usar */
	private Metodo metodo;
	/** Heuristica a usar */
	private Heuristica heur;
	/** Array de soluciones */
	private Estado[] soluciones;
	/** Ciudad de inicio */
	private int inicio;
	private ArrayList<String> ciudades;
	private ArrayList<int[]> enlaces;
	
	/** Constructor de copia */
	public Viajante(Viajante csp){
		this.vars = new Variable[csp.getVariables().length];
		
		for(int i=0; i<this.vars.length; i++){
			this.vars[i] = new Variable(csp.getVariables()[i]);
		}
		
		//Reestablecer enlaces en restricciones
		for(int i=0; i<this.vars.length; i++){
			this.vars[i].mantenerRestricciones(this.vars,csp.getVariables());
		}
		
		this.inicial = new Estado(this.vars.length);
		this.actual = new Estado(this.vars.length);
		int indice;
		for(int i=0; i<csp.getActual().getLength(); i++){
			indice = csp.getActual().getVariable(i).getId();
			this.inicial.setVariable(this.vars[indice]);
			this.actual.setVariable(this.vars[indice]);
		}
	}
	
	/** Constructor que inicializa el problema dado el fichero
	 * que define el grafo.
	 * 
	 * @param fichero
	 */
	public Viajante(String fichero){
		LectorGrafo lg = new LectorGrafo(fichero);
		
		ciudades = lg.getPoblaciones();
		enlaces = lg.getRestricciones();
		Vector<Integer> dominio = new Vector<Integer>();
		
		for(int i = 0; i < ciudades.size(); i++){
			dominio.add(new Integer(i));
		}
		
		if(ciudades != null){
			// Para n ciudades, habra n variables (hay que volver a la inicial)
			this.vars = new Variable[ciudades.size()+1];
			for(int i = 0; i < this.vars.length; i++){
				this.vars[i] = new Variable(null,null,dominio);
			}
			
			// Asociar restricciones
			Valores[] v2 = noRepite(ciudades.size());
			Valores[] v3 = repite(ciudades.size());
			Valores[] vals = generarValores();
			Valores[] valsNeg = generarValoresNegativos();
			for(int i = 0; i < this.vars.length; i++){
				Vector<Restriccion> rests = new Vector<Restriccion>();
				for(int j = 0; j < this.vars.length; j++){
					//Valores[] vals = generarValores();
					//Valores[] v2 = noRepite(ciudades.size());
					//Valores[] v3 = repite(ciudades.size());
					if(j != i){
						Restriccion rest;
						if(j-i == 1){
							// Siguiente
							rest = new Restriccion(this.vars[j],vals);
						}else if(j-i == -1){
							// Anterior
							rest = new Restriccion(this.vars[j],valsNeg);
						}else if(Math.abs(j-i) == vars.length-1){
							// Inicio y Fin han de ser el mismo
							rest = new Restriccion(this.vars[j],v3);
						}else{
							// Resto, basta que no se repitan
							rest = new Restriccion(this.vars[j],v2);
						}
						rests.add(rest);
					}
				}
				this.vars[i].setRestricciones(generarRestricciones(rests));
				this.vars[i].setId(i);
			}
			//System.out.println("Hecho");
		}
		
		this.inicial = new Estado(this.vars.length);
		this.actual = this.inicial;
	}
	
	private Valores[] repite(int n){
		Vector<Valores> vals = new Vector<Valores>();
		for(int i = 0; i < n; i++){
			vals.add(new Valores(new Integer(i), new Integer(i)));
		}
		
		Valores[] v = new Valores[vals.size()];
		
		for(int i = 0; i < v.length; i++){
			v[i] = vals.get(i);
		}
		
		return v;
	}
	
	private Valores[] noRepite(int n){
		Vector<Valores> vals = new Vector<Valores>();
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if(j != i){
					vals.add(new Valores(new Integer(i), new Integer(j)));
				}
			}
		}
		
		Valores[] v = new Valores[vals.size()];
		
		for(int i = 0; i < v.length; i++){
			v[i] = vals.get(i);
		}
		
		return v;
	}
	
	private Restriccion[] generarRestricciones(Vector<Restriccion> rests) {
		Restriccion[] array = new Restriccion[rests.size()];
		for(int i = 0; i < array.length; i++){
			array[i] = rests.get(i);
		}
		return array;
	}

	private Valores[] generarValores() {
		Vector<Valores> vals = new Vector<Valores>();
		for(int i = 0; i < enlaces.size(); i++){
			int[] elem = enlaces.get(i);
			Valores val = new Valores(new Integer(elem[0]),new Integer(elem[1]));
			vals.add(val);
		}
		
		Valores[] tmp = new Valores[vals.size()];
		for(int i = 0; i < tmp.length; i++){
			tmp[i] = vals.elementAt(i);
		}
		
		return tmp;
	}
	
	private Valores[] generarValoresNegativos() {
		Vector<Valores> vals = new Vector<Valores>();
		for(int i = 0; i < enlaces.size(); i++){
			int[] elem = enlaces.get(i);
			Valores val = new Valores(new Integer(elem[1]),new Integer(elem[0]));
			vals.add(val);
		}
		
		Valores[] tmp = new Valores[vals.size()];
		for(int i = 0; i < tmp.length; i++){
			tmp[i] = vals.elementAt(i);
		}
		
		return tmp;
	}
	
	public Estado[] resolverTodas(){
		Estado[] temp = this.getAllSolutions(metodo,heur);
		soluciones = new Estado[temp.length];
		
		for(int i = 0; i<temp.length; i++){
			soluciones[i] = new Estado(temp[i]);
		}
		return soluciones;
	}
	
	public void setMetodo(Metodo met){
		this.metodo = met;
	}
	
	public void setHeuristica(Heuristica heur){
		this.heur = heur;
	}
	
	public Estado[] getSoluciones(){
		if(this.soluciones == null){
			return this.resolverTodas();
		}else{
			return this.soluciones;
		}
	}
	
	public void imprimirSoluciones(){
		Estado[] sols = this.getSoluciones();
		
		if(sols.length != 0)
			for(int i=0; i<sols.length; i++){
				System.out.println(representa(sols[i]));
			}
		else
			System.out.println("No existe solución");
		
	}

	public String representa(Estado estado) {
		String ruta = new String("Ruta: ");
		for(int i = 0; i < estado.getLength(); i++){
			ruta = ruta + this.ciudades.get((Integer)estado.getVariable(i).getValor());
			if(i != estado.getLength() - 1){
				ruta = ruta + ", ";
			}
		}
		return ruta;
	}
	
	public void ciudadInicial(String city){
		if(ciudades.contains(city)){
			this.inicio = ciudades.indexOf(city);
			this.vars[0].setValue(new Integer(this.inicio));
			this.inicial.setVariable(this.vars[0]);
		}
	}
	
	public Estado solve(){
		return this.metodo.solve(this,actual,this.heur);
	}
	
	public void pintaGrafo(){
		/* GUI */
		JFrame f = new JFrame("Linea");
 
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        JApplet applet = new Grafo(this);
        f.getContentPane().add(applet, BorderLayout.CENTER);
        applet.init();
        
        f.setSize(new Dimension(200+10*this.getCiudades().size(),100+10*this.getCiudades().size()));
        f.setVisible(true);
	}

	public static void main(String args[]){
		Viajante p = new Viajante("SinSolucionA.gv");
		//Viajante p = new Viajante("Peninsula_Iberica.gv");
		
		//p.setMetodo(new Vuelta_atras()); // Por defecto
		//p.setMetodo(new MAC());
		p.setMetodo(new VA_haciaDelante());
		
		//p.setHeuristica(new Heuristica()); // Sin heuristica
		p.setHeuristica(new MinValRes());
		//p.setHeuristica(new Grado_Heuristico());
		
		p.ciudadInicial("null"); // null, busca todas
		//p.ciudadInicial("Madrid"); // null, busca todas
		
		Calendar c = new GregorianCalendar();
		long t1 =c.getTimeInMillis();
		
		p.resolverTodas();
		//p.solve();
		
		Calendar c2 = new GregorianCalendar();
		long t2 =c2.getTimeInMillis();
		long ttot= t2-t1;
		System.out.println("Tiempo respuesta: " + ttot);
		
		//p.solve(p.metodo,p.heur);
		p.imprimirSoluciones();
		
		p.pintaGrafo();
	}

	public ArrayList<String> getCiudades() {
		return ciudades;
	}

	public ArrayList<int[]> getEnlaces() {
		return enlaces;
	}
}
