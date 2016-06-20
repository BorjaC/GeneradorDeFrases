package utiles;

import java.util.ArrayList;

/**
 * Clase nodo que almacena uno de los nodos del arbol.
 *
 * @author Borja Colmenarejo Garcia
 */
public class Nodo {

	private String clave = ""; /*
								 * Funcion que representa el nodo dentro de la
								 * oracion
								 */
	private String valor = ""; /*
								 * Palabra que representa el nodo, no siempre un
								 * nodo contiene una palabra
								 */
	private ArrayList<Nodo> hijos = new ArrayList<Nodo>(); /*
															 * Nodos que cuelgan
															 * de este
															 */
	private int numHijos = 0; /* Numero de hijos que tiene el nodo */
	private Nodo padre = null; /* Padre del nodo, si lo tiene null si no */
	private Boolean plural; /*
							 * Marcar el genero del nodo (Para verbos (VP) y
							 * sintagmas nominales (NP))
							 */

	/**
	 * Contructor de la clase
	 */
	public Nodo() {
		plural = false;
	}

	/**
	 * Clave del nodo
	 * 
	 * @return devuelve la clave del nodo
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * Valor del nodo
	 * 
	 * @return devuelve el valor del nodo
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * Hijos del nodo
	 * 
	 * @return devuelve los hijos del nodo, Array vacio si no tiene ninguno
	 */
	public ArrayList<Nodo> getHijos() {
		return hijos;
	}

	/**
	 * Numero de hijos del nodo
	 * 
	 * @return devuelve el numero de hijos del nodo
	 */
	public int getNumHijos() {
		return numHijos;
	}

	/**
	 * Guarda la clave del nodo
	 * 
	 * @param clave
	 *            clave para guardar
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * Guarda el valor de nodo
	 * 
	 * @param valor
	 *            valor para guardar
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * Incluye un hijo
	 * 
	 * @param hijo
	 *            hijo para guardar
	 */
	public void addHijo(Nodo hijo) {
		hijos.add(hijo);
		numHijos++;
	}

	/**
	 * Rrepresentacion del nodo
	 * 
	 * @return representacion del nodo
	 */
	public String toString() {
		if (valor != "")
			return "(" + clave + " \"" + valor + "\")";
		else
			return "(" + clave + ")";
	}

	/**
	 * Devuelve el nodo padre
	 * 
	 * @return nodo padre
	 */
	public Nodo getPadre() {
		return padre;
	}

	/**
	 * Guarda el padre del nodo
	 * 
	 * @param padre
	 *            padre del nodo
	 */
	public void setPadre(Nodo padre) {
		this.padre = padre;
	}

	/**
	 * Devuelve si es singular o plural
	 * 
	 * @return true si es plural, false si es singular
	 */
	public Boolean getPlural() {
		return plural;
	}

	/**
	 * Guarda si es plural o singular
	 * 
	 * @param plural
	 *            true si es plural, false si es singular
	 */
	public void setPlural(Boolean plural) {
		this.plural = plural;
	}

}