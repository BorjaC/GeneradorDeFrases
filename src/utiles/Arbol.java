package utiles;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase arbol que almacena las oraciones en forma de arbol.
 *
 * @author Borja Colmenarejo Garcia
 */
public class Arbol {

	private static int ID = 0;

	private int id; /* Identificador numerico del arbol */
	private ArrayList<Nodo> nodos = new ArrayList<Nodo>(); /* Nodos que componen el arbol */
	private Nodo raiz = new Nodo(); /* Nodo raiz del arbol */
	private String frase = ""; /* Frase que representa el arbol */
	private String representacion = ""; /* Representacion del arbol */

	/**
	 * Contructor del arbol, crea un id unico
	 */
	public Arbol() {
		id = ID;
		ID++;
	}

	/**
	 * Devuelve el id del arbol
	 * 
	 * @return id del arbol
	 */
	public int getId() {
		return id;
	}

	/**
	 * Devuelve el nodo raiz del arbol
	 * 
	 * @return nodo raiz del arbol
	 */
	public Nodo getRaiz() {
		return raiz;
	}

	/**
	 * Devuelve la frase que representa el arbol
	 * 
	 * @return frase que representa el arbol
	 */
	public String getFrase() {
		return frase;
	}

	/**
	 * Crea de forma recursiva la frase que representa el arbol
	 * 
	 * @param nodo
	 *            nodo raiz del cual empezar la representacion del arbol
	 * @return frase que representa el arbol
	 */
	private String getFraseAux(Nodo nodo) {
		String ret = "";

		if (nodo.getValor() != "") {
			ret += nodo.getValor() + " ";
		} else {
			for (int i = 0; i < nodo.getNumHijos(); i++) {
				ret += getFraseAux(nodo.getHijos().get(i));
			}
		}
		return ret;
	}

	/**
	 * Guarda los nodos del arbol, y crea las representaciones en forma de arbo,
	 * y la frase del mismo
	 * 
	 * @param nodos
	 *            nodos del arbol
	 */
	public void setNodos(ArrayList<Nodo> nodos) {
		this.nodos.addAll(nodos);
		frase = getFraseAux(raiz);
		representacion = toStringAux(raiz, "");
	}

	/**
	 * Devuelve los nodos del arbol
	 * 
	 * @return nodos del arbol
	 */
	public ArrayList<Nodo> getNodos() {
		return nodos;
	}

	/**
	 * Guarda la raiz del arbol
	 * 
	 * @param raiz
	 *            raiz del arbol
	 */
	public void setRaiz(Nodo raiz) {
		this.raiz = raiz;
	}

	/**
	 * Devuelve la representacion en forma del arbol del mismo
	 */
	public String toString() {
		return representacion;
	}

	/**
	 * Crea la representacion de forma recursiva en forma de arbol del mismo
	 * 
	 * @param nodo
	 *            nodo raiz del cual empezar la representacion
	 * @param esp
	 *            espacio para crear una representacion jerarquica mas clara
	 * @return representacion del arbol
	 */
	private String toStringAux(Nodo nodo, String esp) {
		String ret = "";

		if (nodo.getValor() != "") {
			ret += "\n" + esp + "(" + nodo.getClave() + " \"" + nodo.getValor() + "\")";
		} else {
			ret += "\n" + esp + "(" + nodo.getClave();

			for (int i = 0; i < nodo.getNumHijos(); i++) {
				ret += toStringAux(nodo.getHijos().get(i), " " + esp);
			}

			ret += ")";
		}

		return ret;
	}

	/**
	 * Devuelve los nodos del arbol que contiene la clave pasada y respetan el numero del nodo
	 * 
	 * @param clave clave donde buscar el nodo
	 * @param plural numero del nodo, true plural, false singular
	 * @return la lista de nodos que contienen el arbol con la clave y el numero
	 */
	public List<Nodo> getNodosClave(String clave, boolean plural) {
		return getNodosClave(raiz, clave, plural);
	}

	/**
	 * Funcion recursiva auxiliar que devuelve los nodos del arbol que
	 * contiene la clave pasada y respetan el numero del nodo
	 * 
	 * @param clave clave donde buscar el nodo
	 * @param plural numero del nodo, true plural, false singular
	 * @return la lista de nodos que contienen el arbol con la clave y el numero
	 */
	private List<Nodo> getNodosClave(Nodo nodo, String clave, boolean plural) {
		List<Nodo> ret = new ArrayList<Nodo>();

		if (nodo.getClave().contentEquals(clave) && nodo.getPlural() == plural) {
			ret.add(nodo);
		}

		for (int i = 0; i < nodo.getNumHijos(); i++) {
			ret.addAll(getNodosClave(nodo.getHijos().get(i), clave, plural));
		}

		return ret;
	}

	/**
	 * Intercambia el primer nodo que coincida con la clave y el numero por un nodo dado
	 * 
	 * @param nodo nodo para intercambiar
	 * @param clave clave para buscar
	 * @param plural numero para buscar
	 * @return el nuevo arbol, null si no hay nuevo arbol
	 */
	public Arbol swap(Nodo nodo, String clave, boolean plural) {		
		if (swap(raiz, nodo, clave, plural))
			return this;
		else 
			return null;
	}

	/**
	 * Funcion recursiva auxiliar que intercambia el primer nodo que coincida
	 * con la clave y el numero por un nodo dado
	 * 
	 * @param nodo nodo para intercambiar
	 * @param clave clave para buscar
	 * @param plural numero para buscar
	 * @return el nuevo arbol, null si no hay nuevo arbol
	 */	
	private boolean swap(Nodo nodo1, Nodo nodo2, String clave, boolean plural) {

		boolean ret = false;
		
		if (nodo1 == null || nodo2 == null) {
			return false;
		}

		if (nodo1.getPlural() == plural && nodo1.getClave().contentEquals(clave)) {

			Nodo nodoPadre = nodo1.getPadre();
			int idx;
			idx = nodoPadre.getHijos().indexOf(nodo1);
			nodoPadre.getHijos().remove(idx);
			nodoPadre.getHijos().add(idx, nodo2);

			nodo2.setPadre(nodo1.getPadre());
			nodo2.setClave(clave);

			this.setNodos(nodos);
			ret = true;
		} 
		
		for (int i = 0; i < nodo1.getNumHijos(); i++) {
			if (!ret){
				ret = ret || swap(nodo1.getHijos().get(i), nodo2, clave, plural);
			}
		}
		
		return ret;
	}

	/**
	 * Intercambia el verbo por un sinonimo dado
	 * 
	 * @param sinonimo sinonimo a intercambiar
	 * @param plural numero del verbo
	 * @return arbol con el sinonimo cambiado, o null si no se puede realizar
	 */
	public Arbol sinonimo(String sinonimo, boolean plural){
		if (sinonimo(raiz, sinonimo, plural))
			return this;
		else 
			return null;		
	}

	/**
	 * Funcion auxiliar recursiva que intercambia el verbo por un sinonimo dado
	 * 
	 * @param sinonimo sinonimo a intercambiar
	 * @param plural numero del verbo
	 * @return arbol con el sinonimo cambiado, o null si no se puede realizar
	 */
	private boolean sinonimo(Nodo nodo1, String sinonimo, boolean plural) {

		boolean ret = false;
		
		if (nodo1 == null) {
			return false;
		}

		if (nodo1.getPlural() == plural && nodo1.getClave().contentEquals("V")) {

			nodo1.setValor(sinonimo+"/V");

			this.setNodos(nodos);
			ret = true;
		} 
		
		for (int i = 0; i < nodo1.getNumHijos(); i++) {
			if (!ret){
				ret = ret || sinonimo(nodo1.getHijos().get(i), sinonimo,plural);
			}
		}
		
		return ret;
	}	
}