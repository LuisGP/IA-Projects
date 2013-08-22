package csp;

import java.util.Vector;

/**
 * Clase que implementa el método Vuelta atrás cronológia
 * 
 * @author Rubén Bernál Cervigón, Luis Gasco Poderoso y Helmuth Winkovics
 *
 */
public class Vuelta_atras implements Metodo {
	/** Registro de todas las soluciones encontradas */
	private Vector sols;

	public Estado solve(CSP prob, Estado actual, Heuristica heur) {
		Variable[] vars = prob.getVariables();
		Variable x;
		Vector dominio;
		Object valor;
		Estado resultado;
		
		if(actual.getLength() == vars.length){
			return actual;
		}
		
		x = heur.getVariables(prob);
		dominio = x.getDominio();
		
		while(!dominio.isEmpty()){
			valor = heur.getNextValor(dominio); //dominio dado
			x.setValue(new Integer(((Integer)valor).intValue()));
			actual.setVariable(x);
			if(actual.cumpleRestricciones()){
				resultado = this.solve(prob, actual, heur);
				if(resultado != null){
					return resultado;
				}else{
					actual.delVariable(x);
				}
			}else{		//Debemos deshacer la asignación de la variable al estado
				actual.delVariable(x);
			}
			dominio.remove(valor);
		}		
		return null;
	}

	public Estado[] getAllSolutions(CSP prob, Estado actual, Heuristica heur){
		sols = new Vector();
		Estado[] solucion;
		
		this.solveAux(prob,actual,heur);
		
		solucion = new Estado[sols.size()];
		
		for(int i=0; i<sols.size(); i++){
			solucion[i] = (Estado)sols.get(i);
		}
		
		return solucion;
	}
	
	/** Metodo auxiliar para "getAllSolutions" */
	private Estado solveAux(CSP prob, Estado actual, Heuristica heur) {
		Variable[] vars = prob.getVariables();
		Variable x;
		Vector dominio;
		Object valor;
		
		if(actual.getLength() == vars.length){
			sols.add(new Estado(actual));
			return actual;
		}
		
		x = heur.getVariables(prob);
		dominio = x.getDominio();
		
		while(!dominio.isEmpty()){
			valor = heur.getNextValor(dominio); //dominio dado
			x.setValue(new Integer(((Integer)valor).intValue()));
			actual.setVariable(x);
			if(actual.cumpleRestricciones()){
				this.solveAux(prob, actual, heur);				
			}
			actual.delVariable(x);
			dominio.remove(valor);
		}
		if(sols.isEmpty())
			return null;
		return (Estado)sols.elementAt(0);
	}
}
