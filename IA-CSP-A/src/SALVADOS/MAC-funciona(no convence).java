package SALVADOS;

import java.util.Vector;

public class MAC extends VA_haciaDelante{
	
	//protected Vector sols;
	/* Las variables contienen sus restricciones */
	
	private CSP consistenciaDeArcos(CSP csp){
		//Vector abierta = new Vector();
		//Variable actual;
		CSP aux = new CSP(csp);
		
		/*for(int i = 0; i < aux.getVariables().length; i++){
			abierta.add(aux.getVariables()[i]);
		}
		//abierta.add(aux.getVariables()[0]);
		
		while(!abierta.isEmpty()){
			actual = (Variable)abierta.firstElement();
			if(this.borrarValoresInconsistentes(actual)){
				for(int i=0; i<actual.getRestricciones().length; i++){
					abierta.add(actual.getRestricciones()[i].getVariable());
				}
			}
			this.borrarValoresInconsistentes(actual);
			
			abierta.removeElementAt(0);
		}*/
		this.borrarValoresInconsistentes(aux.getVariables()[0]);
		
		return aux;
	}
	
	private boolean borrarValoresInconsistentes(Variable var){
		return var.inconsRest();		
	}
	
	public Estado solve(CSP prob, Estado actual, Heuristica heur) {
		CSP csp;
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
				csp = this.consistenciaDeArcos(prob);
				resultado = this.solve(csp, csp.getActual(), heur);
				if(resultado != null){
					return resultado;
				}else{
					actual.delVariable();
				}
			}else{		//Debemos deshacer la asignación de la variable al estado
				actual.delVariable();
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
	
	private Estado solveAux(CSP prob, Estado actual, Heuristica heur) {
		Variable[] vars = prob.getVariables();
		Variable x;
		Vector dominio;
		Object valor;
		CSP csp;
		
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
				csp = this.consistenciaDeArcos(prob);
				this.solveAux(csp, csp.getActual(), heur);				
			}
			actual.delVariable();
			dominio.remove(valor);
		}
		if(sols.isEmpty())
			return null;
		return (Estado)sols.elementAt(0);
	}
}