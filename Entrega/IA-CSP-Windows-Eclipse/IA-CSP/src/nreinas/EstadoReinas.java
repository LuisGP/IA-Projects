package nreinas;

import csp.Estado;
import csp.Variable;

/**
 * Estado específico del problema de las nreinas
 * La representación no es necesario modificarla, pero
 * sí añadir un método capaz de interpretar ésa representación
 * en función de un tablero de ajedrez
 * 
 * @author Rubén Bernál Cervigón, Luis Gasco Poderoso y Helmuth Winkovics
 *
 */
public class EstadoReinas extends Estado{
	/** Contructor de copia */
	public EstadoReinas(Estado st) {
		super(st);
	}
	
	public EstadoReinas(int length) {
		super(length);
	}

	/** Interpreta la representación para un tablero de ajedrez */
	public int[] getTablero(){
		int[] tablero = new int[super.getLength()];
		
		for(int i=0; i<super.getLength(); i++){
			if(this.getVariable(i) != null)
				tablero[i] = ((Integer)(this.getVariable(i).getValor())).intValue();
			else
				tablero[i] = -1;
		}
		
		return tablero;
	}
	
	/** Coloca la variable en su posicion correcta */
	public void setVariable(Variable var){
		super.setVariable(var,var.getId());
	}
	
	/** Almacena un tablero como estado, dado un problema 
	 * 
	 * @param tab Tablero o estado
	 * @param reinas Problema CSP de las Nreinas, necesario para saber a qué
	 * variables se refiere el tablero
	 */
	public void setFromTablero(int[] tab,Nreinas reinas){
		Variable[] vars = reinas.getVariables();
		
		for(int i=0; i<tab.length; i++){
			vars[i].setValue(new Integer(tab[i]));
			/*
			for(int j=0; i<vars.length; j++){
				if(((Integer)vars[j].getValor()).intValue() == tab[i]){
					
				}
			}*/
			super.setVariable(vars[i]);
		}
	}

}
