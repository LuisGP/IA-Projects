package viajante;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Esta clase se encarga exclusivamente de leer un grafo del fichero
 * que se le pasa como argumento al constructor.
 * 
 * @author LuisGP
 *
 */
public class LectorGrafo {
	ArrayList<String> al; // Nodos del grafo (poblaciones)
	FileInputStream fis;
	BufferedReader br;
	ArrayList<int[]> enlaces; // Comunicacion entre nodos
	
	/**
	 * Lee el fichero que define el grafo, asignando a cada poblacion
	 * un indice en el array, el cual se usara para los calculos y
	 * la posterior traduccion indice - ciudad
	 */
	public LectorGrafo(String fichero){
		try{
			fis = new FileInputStream(fichero);
			br = new BufferedReader(new InputStreamReader(fis));
			al = new ArrayList<String>();
			enlaces = new ArrayList<int[]>();
			
			String tmp = br.readLine();
			
			while(tmp != null){
				// Aqui tenemos una linea de la forma "A - B"
				// Una linea que empiece por "--" es un comentario
				if(!tmp.startsWith("--")){
					String sep = " - ";
					String ciudadA = tmp.substring(0,tmp.indexOf(sep));
					String ciudadB = tmp.substring(tmp.indexOf(sep) + sep.length(),tmp.length());
					
					int iA = devolverPosicion(ciudadA);
					int iB = devolverPosicion(ciudadB);
					
					int[] enl = new int[2];
					enl[0] = iA;
					enl[1] = iB;
					
					if(!enlaces.contains(enl)){
						enlaces.add(enl);
					}
					
					//System.out.println(ciudadA + " *** " + ciudadB+ ": "+enl[0]+"-"+enl[1]);
				}
				tmp = br.readLine();
			}
			
		}catch(FileNotFoundException fnfe){
			System.err.println("Fichero no se encuentra");
		}catch(IOException ioe){
			System.err.println("Error leyendo del fichero");
		}
		
	}
	
	public ArrayList<String> getPoblaciones(){
		return al;
	}
	
	public ArrayList<int[]> getRestricciones(){
		return enlaces;
	}
	
	private int devolverPosicion(String ciudad){
		if(al.contains(ciudad)){
			return al.indexOf(ciudad);
		}
		al.add(ciudad);
		return al.indexOf(ciudad);
	}
}
