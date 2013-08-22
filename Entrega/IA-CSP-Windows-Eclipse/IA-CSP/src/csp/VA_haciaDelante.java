package csp;

import java.util.Vector;

/**
 * Clase que implementa el método Vuelta atrás con comprobación hacia delante
 * 
 * @author Rubén Bernál Cervigón, Luis Gasco Poderoso y Helmuth Winkovics
 *
 */
public class VA_haciaDelante implements Metodo {
	/** Aqui almacenaremos todas las soluciones según las vayamos encontrando*/
	protected Vector sols;
	
	/** Método para propagar las restricciones */
	protected Estado propagarRestricciones(Estado estado){
		estado.propagarRestricciones();
		return estado;
	}

	public Estado solve(CSP prob, Estado actual, Heuristica heur) {
		Variable[] vars = prob.getVariables();
		Variable x;
		Vector dominio;
		Object valor;
		Estado resultado = null;
		Vector[] dominios = new Vector[vars.length];
		
		if(actual.getLength() == vars.length){
			return actual;
		}
		
		for(int i = 0; i < dominios.length; i++){
			dominios[i] = vars[i].getDominio();
		}
		
		x = heur.getVariables(prob);
		dominio = x.getDominio();
		
		while(!dominio.isEmpty()){
			valor = heur.getNextValor(dominio); //dominio dado
			x.setValue(new Integer(((Integer)valor).intValue()));
			actual.setVariable(x,x.getId());
			if(actual.cumpleRestricciones()){
				this.propagarRestricciones(actual);
				//resultado = this.solve(prob, actual, heur);
				
				if(!dominioVacio(vars))
					resultado = this.solve(prob, actual, heur);
				
				// Restauramos los dominios para continuar
				for(int i = 0; i < dominios.length; i++){
					if(vars[i] == x)
						vars[i].setDominio(new Vector(dominio));
					else
						vars[i].setDominio(dominios[i]);
				}
				
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
	
	/** Método auxiliar usado por "getAllSolutions" */
	private Estado solveAux(CSP prob, Estado actual, Heuristica heur) {
		Variable[] vars = prob.getVariables();
		Variable x;
		Vector dominio;
		Object valor;
		Vector[] dominios = new Vector[vars.length];
		
		if(actual.getLength() == vars.length){
			sols.add(new Estado(actual));
			return actual;
		}
		
		for(int i = 0; i < dominios.length; i++){
			dominios[i] = vars[i].getDominio();
		}
		
		x = heur.getVariables(prob);
		dominio = x.getDominio();
		
		while(!dominio.isEmpty()){
			valor = heur.getNextValor(dominio); //dominio dado
			x.setValue(new Integer(((Integer)valor).intValue()));
			actual.setVariable(x,x.getId());
			if(actual.cumpleRestricciones()){
				this.propagarRestricciones(actual);
				//this.solveAux(prob, actual, heur);
				if(!dominioVacio(vars))
					this.solveAux(prob, actual, heur);
				
				// Restauramos los dominios para continuar
				for(int i = 0; i < dominios.length; i++){
					if(vars[i] == x)
						vars[i].setDominio(new Vector(dominio));
					else
						vars[i].setDominio(dominios[i]);
				}
				
			}
			actual.delVariable(x);
			dominio.remove(valor);
		}
		if(sols.isEmpty())
			return null;
		return (Estado)sols.elementAt(0);
	}
	
	private boolean dominioVacio(Variable[] vars) {
		for(int i = 0; i < vars.length; i++)
			if(vars[i].getDominio().isEmpty())
				return true;
		return false;
	}
}
