package csp;

import java.util.Vector;

/**
 * Clase que representa un problema CSP genérico.
 * Las clases que deseen usar la librería deberían heredar de esta clase.
 * 
 * @author Rubén Bernál Cervigón, Luis Gasco Poderoso y Helmuth Winkovics
 *
 */
public class CSP {
	/** Variables del problema, tienen asociadas sus propias restricciones */
	protected Variable[] vars;
	//Restriccion rest;
	/** Estado inicial */
	protected Estado inicial;
	/** Estado actual */
	protected Estado actual;
	
	/** Constructor por defecto */
	public CSP(){
		vars = null;
		inicial = null;
		actual = null;
	}
	
	/** Constructor a partir de un array de variables */
	public CSP(Variable[] var/*, Restriccion rest*/, Estado init){
		this.vars = var;
		//this.rest = rest;
		this.inicial = init;
		this.actual = init;
	}
	
	/** Constructor a partir de un vector de varaibles */
	public CSP(Vector vars, Estado init){
		this.vars = new Variable[vars.size()];
		
		for(int i=0; i<this.vars.length; i++){
			this.vars[i] = (Variable)vars.get(i);
		}
		
		this.inicial = init;
		this.actual = new Estado(init);
	}
	
	/** Contructor de copia */
	public CSP(CSP csp){
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
		for(int i=0; i<(csp.getActual().getVariableArray()).length; i++){
			if(csp.getActual().getVariable(i) != null){
				indice = csp.getActual().getVariable(i).getId();
				this.inicial.setVariable(this.vars[indice],indice);
				this.actual.setVariable(this.vars[indice],indice);
			}
		}
	}
	
	public Variable[] getVariables(){
		return vars;
	}
	
	/*public Restriccion getRestricciones(){
		return rest;
	}*/
	
	public Estado getActual(){
		return actual;
	}
	
	/** Resuelve el problema, devolviendo una solucion si existe */
	public Estado solve(Metodo method, Heuristica heur){
		return method.solve(this,actual,heur);
	}
	
	/** Resuelve el problema devolviendo todas las soluciones si existen */
	public Estado[] getAllSolutions(Metodo method, Heuristica heur){
		return method.getAllSolutions(this,actual,heur);
	}
	
	/** Representación del problema en String (para depuración)
	 * muestra los dominios de cada una de las variables
	 */
	public String toString(){
		String s = new String();
		
		for(int i = 0; i < this.vars.length; i++){
			s = s + this.vars[i].getDominio();
		}
		
		return s;
	}

	public void setActual(Estado csp_actual) {
		this.actual = csp_actual;
	}
}
