package generar;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import utiles.Arbol;
import utiles.Nodo;
import utiles.Utiles;

/**
 * Clase para generar nuevas frases.
 * 
 * @author Borja Colmenarejo García
 *
 */
public class Generador extends Utiles {

	List<Nodo> nodosSG = new ArrayList<Nodo>(); /* Nodos en singular */
	List<Nodo> nodosPL = new ArrayList<Nodo>(); /* Nodos en plural */
	String clave; /* Etiqueta de intercambio */

	/**
	 * Constructor de la clase
	 * 
	 * @param file
	 *            archivo del cual obtener los nodos.
	 * @param clave
	 *            clave de los nodos a leer.
	 */
	public Generador(String file, String clave) {

		this.clave = clave;
		/* Arboles */
		List<Arbol> treesOriginalsWithGender = getTreesForFile(file);

		/* Obtener los nodos para permutar */
		for (int i = 0; i < treesOriginalsWithGender.size(); i++) {
			/* Nodos en singular */
			nodosSG.addAll(treesOriginalsWithGender.get(i).getNodosClave(clave, false));
			/* Nodos en plural */
			nodosPL.addAll(treesOriginalsWithGender.get(i).getNodosClave(clave, true));
		}

		Collections.shuffle(nodosSG);
		Collections.shuffle(nodosPL);

		System.out.println("Numero de nodos singular: " + nodosSG.size());
		System.out.println("Numero de nodos plural: " + nodosPL.size());
	}

	/**
	 * Genera nuevas frases a partir de un treebank
	 * 
	 * @param fileTrees
	 *            fichero que contiene las estructuras de las oraciones
	 * @param n
	 *            numero de oraciones a generar
	 */
	public void generarFrases(String fileTrees, int n) {

		/* Ficheros */
		BufferedWriter sentencesGen, treesGen;

		try {
			/* Abrir ficheros */
			String output = "sentences.txt";
			String outputT = "trees.lisp";
			sentencesGen = new BufferedWriter(new FileWriter(output));
			treesGen = new BufferedWriter(new FileWriter(outputT));

			/* Generar nuevos arboles */
			List<Arbol> a = generarAux(fileTrees, n);

			/* Escribir nuevos arboles en ficheros */
			for (int j = 0; j < a.size(); j++) {
				sentencesGen.write(a.get(j).getFrase() + "\n");
				treesGen.write(a.get(j).toString() + "\n");
			}

			/* Cerrar ficheros */
			sentencesGen.close();
			treesGen.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo recursivo auxiliar para generar los nuevas frases.
	 * 
	 * @param fileTrees
	 *            archivo que contiene las estructuras desde las cuales crear
	 *            nuevas frases.
	 * @param n
	 *            numero de frases a crear.
	 * @return lista de nuevas frases creadas en forma de arbol.
	 */
	private List<Arbol> generarAux(String fileTrees, int n) {

		List<Arbol> treesAux = new ArrayList<Arbol>();

		List<Arbol> treesOriginals = new ArrayList<Arbol>();
		treesOriginals = getTreesForFile(fileTrees);

		int m = treesOriginals.size();

		int eachTree = n / m;

		for (int i = 0; i < m; i++) {

			List<Nodo> nodos = new ArrayList<Nodo>();
			boolean plural;

			/*
			 * Obtener los nodos (SINGULAR o PLURAL )con los que sera
			 * intercambiado
			 */
			if (!treesOriginals.get(i).getNodosClave(clave, false).isEmpty()) {
				nodos.addAll(nodosSG);
				plural = false;
			} else if (!treesOriginals.get(i).getNodosClave(clave, true).isEmpty()) {
				nodos.addAll(nodosPL);
				plural = true;
			} else {
				System.out.println("El arbol no contiene nodos validos para permutar");
				continue;
			}

			int idx = 0;

			/* Crear los nuevos arboles */
			for (int j = 0; j < eachTree;) {

				treesOriginals = getTreesForFile(fileTrees);
				List<Arbol> tAux = new ArrayList<Arbol>();
				tAux.addAll(treesOriginals);

				idx = (int) (Math.random() * nodos.size() - 1);

				Arbol t = tAux.get(i).swap(nodos.get(idx), clave, plural);
				if (t != null) {
					treesAux.add(t);
					j++;
				}

			}
		}

		return treesAux;
	}

	/**
	 * Crea un arbol a partir de una cadena
	 * 
	 * @param arbol
	 *            cadena que contiene el arbol
	 * @return Arbol creado a partir de la cadena
	 */
	public Arbol createTree(String arbol) {
		Arbol tree = new Arbol();
		ArrayList<Nodo> nodos = new ArrayList<Nodo>();
		Stack<Nodo> stack = new Stack<Nodo>();
		String valor = new String();
		Boolean esValor = false, meterValor = false;

		String[] s = arbol.split("\\s");
		for (int i = 0; i < s.length; i++) {

			/* Para evitar iteraciones vacias */
			if (s[i].equals("")) {
				continue;
			}

			/* Comienzo de nodo */
			if (s[i].startsWith("(")) {
				/* Crear nodo y guardar clave */
				Nodo nodo = new Nodo();
				String clave = s[i].replace("(", "");
				clave = clave.replace(")", "");
				nodo.setClave(clave);

				/* Incluir a la lista de nodos */
				nodos.add(nodo);

				/* Guardar los hijos */
				if (!stack.isEmpty()) {
					/* Nodos hijos del arbol */
					Nodo nodoPadre = stack.pop();
					nodoPadre.addHijo(nodo);
					nodo.setPadre(nodoPadre);
					stack.push(nodoPadre);
				}

				/* Meter a la pila */
				stack.push(nodo);
			}

			/* Guardar el numero del nodo */
			/* Singular */
			if (!stack.isEmpty() && s[i].equals("SG")) {
				Nodo nodoActual = stack.pop();
				nodoActual.setPlural(false);
				stack.push(nodoActual);
			}
			/* Plural */
			if (!stack.isEmpty() && s[i].equals("PL")) {
				Nodo nodoActual = stack.pop();
				nodoActual.setPlural(true);
				stack.push(nodoActual);
			}

			if (s[i].startsWith("\"")) {
				/* Valor para asignar */
				valor = s[i].replaceFirst("\"", "");
				esValor = false;
			}

			/* Si el valor no ha terminado de leerse */
			if (esValor) {
				valor += " " + s[i];
			}

			/* Si termina el valor */
			if (valor.contains("\"")) {
				valor = valor.replace(")", "");
				valor = valor.replace("\"", "");

				/* Si el valor es un ')' */
				if (valor.contentEquals("/PUNCT") || valor.equals(""))
					valor = ")" + valor;

				/* Etiquedo de palabra dentro de la misma */
				if (!valor.contains("/")) {
					Nodo nodoActual = stack.pop();
					nodoActual.setValor(valor);
					valor = valor + "/" + nodoActual.getClave();
					stack.push(nodoActual);
				}

				meterValor = true;
				esValor = false;
			} else {
				esValor = true;
			}

			/* Meter el valor calculado anteriormente */
			if (meterValor) {
				Nodo nodoActual = stack.pop();
				nodoActual.setValor(valor);
				stack.push(nodoActual);
				meterValor = false;
				valor = new String();
			}

			/* Terminar nodo (pueden ser varios a la vez) sacar de la pila */
			if (s[i].endsWith(")")) {
				int idx = s[i].lastIndexOf(")");
				int j = 0;
				while (idx - j >= 0 && s[i].charAt(idx - j) == ')') {
					j++;
					if (!stack.empty())
						stack.pop();
				}
			}
		}

		/*
		 * Guardar el nodo raiz ROOT para comparaciones con la salida (pone ROOT
		 * por defecto)
		 */
		if (!nodos.isEmpty()) {
			Nodo raiz = new Nodo();
			raiz.setClave("ROOT");
			raiz.addHijo(nodos.get(0));
			nodos.add(0, raiz);
			tree.setRaiz(raiz);

			/* Guardar los nodos del arbol, y contruye el arbol */
			tree.setNodos(nodos);
		}

		/* Comprobar si esta bien balanceado (Si no error) */
		if (!stack.isEmpty()) {
			System.out.println("Arbol " + tree.getId() + ": no esta bien balanceado");
			System.out.println(tree.getFrase());
			System.out.println(stack);
			return null;
		}

		return tree;
	}

}
