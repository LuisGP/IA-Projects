package csp;

import java.util.Vector;

/**
 * Representaci�n de las restricciones
 * Las variables contienen sus restricciones, cada una de las cuales (al ser binarias)
 * tiene otra variable (la segunda implicada). �sta relaci�n ha de mantenerse consistente.
 * 
 * @author Rub�n Bern�l Cervig�n, Luis Gasco Poderoso y Helmuth Winkovics
 *
 */
public class Restriccion {
	/** Variable a la que hace referencia la restricci�n */
	private Variable variable;
	/** Valores que pueden tomar (1) la variable propietaria de �sta restricci�n
	 * y (2) la variable referenciada en esta restricci�n 
	 */
	private Valores[] valor;

	public Restriccion(Variable var, Valores[] val){
		this.variable = var;
		this.valor = val;
	}
	
	/** Constructor de copia */
	public Restriccion(Restriccion r) {
		this.variable = r.getVariable();
		this.valor = new Valores[r.getValores().length];
		
		for(int i=0; i<this.valor.length; i++){
			valor[i] = new Valores(r.getValores()[i].getValor1(),r.getValores()[i].getValor2());
		}
	}

	/** Progar restricci�n seg�n un valor del dominio dado */
	public void propagar(Object v){
		Vector aux_dom = new Vector();
		for(int i = 0; i < this.valor.length; i++){
			if(valor[i].getValor1().equals(v)){
				aux_dom.add(valor[i].getValor2());
			}
		}
		this.variable.setDominio(aux_dom);
	}
	
	/* Devuelve el dominio consistente para el valor dado */
	public Vector inconsistente(Object v){
		Vector aux_dom = new Vector();
		
		for(int i = 0; i < this.valor.length; i++){
			if(valor[i].getValor1().equals(v)){
				aux_dom.add(valor[i].getValor2());
			}
		}
		return aux_dom;
	}

	public Variable getVariable() {
		return this.variable;
	}

	public Valores[] getValores() {
		return this.valor;
	}

	/** Comprueba que dos valores est�n contenidos en la restricci�n */
	public boolean check(Object a, Object b){		
		for(int i = 0; i < valor.length; i++){
			if(((valor[i]).getValor1()).equals(a) &&
			   ((valor[i]).getValor2()).equals(b)){
					return true;
			}
		}
		return false;
	}
	
	/** Devuelve un String con las restricciones precedidas por el identificador
	 * de la variable a la que se refieren (para depuraci�n)
	 */ 
	public String toString(){
		String s = new String("Variable "+this.variable.getId()+": { ");
		
		for(int i = 0; i < this.valor.length; i++){
			s = s + valor[i].toString()+" ";
		}
		
		return (s+"}");
	}

	public void setVariable(Variable var) {
		this.variable = var;
	}

}