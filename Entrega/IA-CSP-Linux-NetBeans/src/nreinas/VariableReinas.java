package nreinas;

import java.util.Vector;

import csp.Restriccion;
import csp.Variable;

/**
 * Variables específicas de las Nreinas
 * 
 * @author Rubén Bernál Cervigón, Luis Gasco Poderoso y Helmuth Winkovics
 *
 */
public class VariableReinas extends Variable{
		
	/** Constructor de copia  */
	public VariableReinas(Variable var) {
		super();
		Restriccion[] r = new Restriccion[var.getRestricciones().length];
		
		for(int i=0; i < r.length; i++){
			r[i] = new Restriccion(var.getRestricciones()[i]);
		}
		
		super.setDominio(new Vector(var.getDominio()));
		super.setRestricciones(r);
		super.setValue(new Integer(((Integer)var.getValor()).intValue()));
		super.setId(var.getId());
	}

	public VariableReinas(Object val, Restriccion[] r, Vector dominio) {
		super(val,r,dominio);
	}
	
	/** Mantenimiento de las restricciones para VariableReinas
	 * @see csp.Variable
	 * 
	 * @param nuevas Nuevas variables
	 * @param antiguas Antiguas varaibles
	 */
	public void mantenerRestricciones(VariableReinas[] nuevas, VariableReinas[] antiguas){
		for(int i = 0; i < super.rest.length; i++){
			for(int j = 0; j < nuevas.length; j++){
				if(this.rest[i].getVariable() == antiguas[j]){
					this.rest[i].setVariable(nuevas[j]);
				}
			}
		}
	}
}
