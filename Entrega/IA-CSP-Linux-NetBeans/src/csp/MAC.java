package csp;

import java.util.Vector;

/**
 * Clase que implementa el método MAC
 * 
 * @author Rubén Bernál Cervigón, Luis Gasco Poderoso y Helmuth Winkovics
 *
 */
public class MAC extends VA_haciaDelante{
	
	//protected Vector sols;
	/* Las variables contienen sus restricciones */
	
	/** Dado un CSP, devuelve otro problema CSP equivalente arco consistente */
	private CSP consistenciaDeArcos(CSP csp){
		Vector <Variable> abierta = new Vector<Variable>();
		Variable actual;
		CSP aux = csp/*new CSP(csp)*/;
		
		for(int i = 0; i < aux.getVariables().length; i++){
			abierta.add(aux.getVariables()[i]);
		}
		
		while(!abierta.isEmpty()){
			actual = (Variable)abierta.firstElement(); // Fijamos la variable
			// Consistencia a cada restriccion suya
			for(int i=0; !actual.getDominio().isEmpty() && i<actual.getRestricciones().length; i++){
				if(this.borrarValoresInconsistentes(actual, actual.getRestricciones()[i])){
					for(int j = 0; j < actual.getRestricciones().length; j++){ // Para cada vecino de actual
						if((i != j) && !abierta.contains(actual.getRestricciones()[i].getVariable())){ // Salvo "Xi"
							abierta.add(actual.getRestricciones()[j].getVariable());
						}
					}
				}
			}
			/*
			if(this.borrarValoresInconsistentes(actual)){
				for(int i=0; i<actual.getRestricciones().length; i++){
					if(!abierta.contains(actual.getRestricciones()[i].getVariable())){
						abierta.add(actual.getRestricciones()[i].getVariable());
					}
				}
			}*/
					
			abierta.removeElementAt(0);
		}
		
		return aux;
	}
	
	/** Borra los valores inconsistentes del dominio de una variable dada */
	/*private boolean borrarValoresInconsistentes(Variable var){
		return var.inconsRest();		
	}*/
	
	private boolean borrarValoresInconsistentes(Variable var, Restriccion rest){
		return var.inconsRest(rest);		
	}
	
	public Estado solve(CSP prob, Estado actual, Heuristica heur) {
		Variable[] vars = prob.getVariables();
		Variable x;
		Vector dominio;
		// Salvamos los dominios originales
		Vector[] dominios = new Vector[vars.length];
		Object valor;
		Estado resultado = null;
		
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
				actual.propagarRestricciones();
				this.consistenciaDeArcos(prob);
				//resultado = this.solve(csp, actual, heur);
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
	
	/** Usado por "getAllSolutions" como apoyo */
	private Estado solveAux(CSP prob, Estado actual, Heuristica heur) {
		Variable[] vars = prob.getVariables();
		Variable x;
		Vector dominio;
		// Salvamos los dominios originales
		Vector[] dominios = new Vector[vars.length];
		Object valor;
		//CSP csp;
		
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
				actual.propagarRestricciones();
				
				this.consistenciaDeArcos(prob);
				
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