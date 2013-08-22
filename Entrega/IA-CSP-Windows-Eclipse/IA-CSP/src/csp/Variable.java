package csp;

import java.util.Vector;


/**
 * Clase que representa una Variable para el problema CSP
 * 
 * @author Rubén Bernál Cervigón, Luis Gasco Poderoso y Helmuth Winkovics
 *
 */
public class Variable {
	
	/** Valor que toma la variable */
	protected Object valor;
	/** Conjunto de restricciones asociados a esta variable*/
	protected Restriccion[] rest;
	/** Identificador de la variable (util según el problema)*/
	protected int id;
	
	/** Dominio de valores posibles para ésta variable */
	protected Vector dominio;
	
	/** Constructor completo */ 
	public Variable(Object val, Restriccion[] r, Vector domi){
		valor = val;
		rest = r;
		dominio = domi;
	}

	/** Constructor por defecto */
	public Variable() {
		valor = new Object();
		rest = new Restriccion[1];
		dominio = new Vector();
		id = -1;
	}

	/** Constructor de copia - Es responsabilidad de las variables
	 * mantener las resticciones coherentes
	 */
	public Variable(Variable var) {
		valor = var.getValor();
		rest = this.copiarRestricciones(var.getRestricciones());
		id = var.getId();
		dominio = var.getDominio();
	}

	/** Mantiene coherencia de restricciones */
	private Restriccion[] copiarRestricciones(Restriccion[] r) {
		Restriccion[] rest = new Restriccion[r.length];
		
		for(int i=0; i < rest.length; i++){
			rest[i] = new Restriccion(r[i]);
		}
		
		return rest;
	}

	public int getId(){
		return id;
	}
	
	public void setId(int i){
		this.id = i;
	}
	
	/** Propaga las restricciones para el valor actual */
	public void propagarRestricciones(){
		for(int i=0; i<rest.length; i++){
			rest[i].propagar(this.valor);
		}
	}
	
	/** Actualiza dominios de las variables dependientes de
	 * las restricciones de esta variable según el valor que
	 * toma.
	 * @return ¿Se han modificado dominios?
	 */
	/*public boolean inconsRest(){
		boolean incon = false;
		Object valor;
		
		Vector aux_dom = this.dominio;
		for(int j=0; j<rest.length; j++){
			for(int i = 0; i < aux_dom.size(); i++){
				valor = aux_dom.get(i);
				if(rest[j].inconsistente(valor).isEmpty()){
					aux_dom.remove(i);
					incon = true;
				}
			}
		}
		
		return incon;
	}*/

	public boolean inconsRest(Restriccion r){
		boolean incon = false;
		Object valor;
		
		Vector aux_dom = this.dominio;
		for(int i = 0; i < aux_dom.size(); i++){
			valor = aux_dom.get(i);
			if(r.inconsistente(valor)){
				aux_dom.remove(i);
				incon = true;
			}
		}
		
		return incon;
	}
	
	public Object getValor(){
		return valor;
	}
	
	public Variable setValue(Object value){
		valor = value;
		return this;
	}
	
	public Vector getDominio(){
		return new Vector(dominio);
	}
	
	/** Comprueba restricciones según una variable y su valor */
	public boolean check(Object valor, Variable var){		
		for(int i=0; i<rest.length; i++){
			if(rest[i].getVariable().getId() == var.getId()){
				return rest[i].check(this.valor,valor);
			}
		}
		return false;
	}
	
	/*public String toString(){
		return valor.toString();
	}*/

	public void setRestricciones(Restriccion[] rest_aux) {
		rest = rest_aux;		
	}
	
	public Restriccion[] getRestricciones(){
		return rest;
	}

	public boolean setDominio(Vector dominio2) {
		//if(!this.dominio.equals(dominio2)){
			this.dominio = dominio2;
			return true;
		//}
		//return false;
	}
	
	public void intersecarDominios(Vector dom){
		Vector aux = new Vector();
		for(int i = 0; i < dom.size(); i++){
			if(dominio.contains(dom.elementAt(i))){
				aux.add(dom.elementAt(i));
			}
		}
		this.dominio = aux;
	}

	/** Mantiene las restricciones acorde con el nuevo conjunto de variables al que se
	 * refiere.
	 * @param nuevas Nuevas variables
	 * @param antiguas Antiguas variables
	 */ 
	public void mantenerRestricciones(Variable[] nuevas, Variable[] antiguas){
		for(int i = 0; i < this.rest.length; i++){
			for(int j = 0; j < nuevas.length; j++){
				if(this.rest[i].getVariable() == antiguas[j]){
					this.rest[i].setVariable(nuevas[j]);
				}
			}
		}
	}
	
	public String toString(){
		String str = new String();
		
		str = "Id: " + this.id;
		
		if(valor != null)
			str += " - Valor: "+valor.toString();
		
		for(int i = 0; i < this.rest.length; i++){
			str += " - Restricciones: " + this.rest[i].toString(); 
		}
		
		return str;
	}
}