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
	public boolean inconsRest(){
		boolean incon = false;
		Object valor;
		/*
		Para cada v? Di hacer % borrar valores inconsistentes (sólo de Di)
		Si ¬Existe v’? Dj tal que (v,v’) ? Ry(xi, xj) entonces
		Di ? Di \ {v}
		borrado ? si
		*/
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
		/*
		boolean incon = false;	
		Vector aux_dom = new Vector();
		//Vector imp_dom = new Vector();
		Vector saved = new Vector(); // Dominio implicado, el de la otra variable
		//Vector neg_dom = new Vector();
		
		for(int j=0; j<rest.length; j++){
			saved = rest[j].getVariable().getDominio();
			aux_dom = rest[j].inconsistente(this.valor);

			aux_dom = this.interseccion(saved,aux_dom);
			//neg_dom = this.restaDominios(saved,aux_dom);
			
			if(!this.dominiosIguales(aux_dom,saved)){
				rest[j].getVariable().actDominio(aux_dom);
				if(aux_dom.isEmpty()){
					rest[j].getVariable().setValue(new Integer(0));
				}else{
					rest[j].getVariable().setValue(aux_dom.firstElement());
				}
				incon = true;
			}
			aux_dom.removeAllElements();
		}*/
		
		return incon;
	}
	
	/* <- a - b */
	/*private Vector restaDominios(Vector a, Vector b) {
		Vector r = new Vector();
		
		for(int i=0; i < a.size(); i++){
			if(!b.contains(a.get(i))){
				r.add(a.get(i));
			}
		}
		
		return r;
	}*/

	/** Interseccion entre dos dominios */
/*	private Vector interseccion(Vector a, Vector b) {
		Vector r = new Vector();
		int tam_a = a.size(), tam_b = b.size();
		
		if(tam_a <= tam_b){
			for(int i=0; i<tam_a; i++){
				if(b.contains(a.get(i))){
					r.add(a.get(i));
				}
			}
		}else{
			for(int i=0; i<tam_b; i++){
				if(a.contains(b.get(i))){
					r.add(b.get(i));
				}
			}
		}
		
		return r;
	}

	/** Compara dominios */
/*	private boolean dominiosIguales(Vector a, Vector b) {
		boolean igual = a.size() == b.size();
		
		if(!igual)
			return false;
		
		for(int i=0; i<a.size(); i++){
			if(!b.contains(a.get(i))){
				return false;
			}
		}
		return true;
	}

	/** Actualiza un dominio */
/*	private boolean actDominio(Vector imp_dom) {
		this.dominio = new Vector();
		
		for(int k=0; k<imp_dom.size(); k++){
			dominio.add(imp_dom.get(k));
		}
		return true;
	}*/

	public Object getValor(){
		return valor;
	}
	
	/*public Variable[] expandir(Heuristica heur){
		return heur.expandir(this);
	}*/
	
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
			if(rest[i].getVariable().getValor().equals(var.getValor())){
				return rest[i].check(this.valor,valor);
			}
		}
		return false;
	}
	
	public String toString(){
		return valor.toString();
	}

	public void setRestricciones(Restriccion[] rest_aux) {
		rest = rest_aux;		
	}
	
	public Restriccion[] getRestricciones(){
		return rest;
	}

	public boolean setDominio(Vector dominio2) {
		if(!this.dominio.equals(dominio2)){
			this.dominio = dominio2;
			return true;
		}
		return false;
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
}