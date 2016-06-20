package utiles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta utiles; de esta se extienden las herramientas, y ya implementa metodos utiles
 *
 * @author Borja Colmenarejo Garcia
 */
public abstract class Utiles {

	/* Rutas de las carpetas donde almacenar los diferentes ficheros */
	public static String URL_TRAIN = "data/train/";
	public static String URL_INPUT = "data/input/";
	public static String URL_OUTPUT = "data/output/";
	public static String URL_SENTENCE = "data/sentence/";
	public static String URL_MODEL = "data/model/";
	public static String URL_MODELTXT = "data/model/text/";

	/* Rutas de las carpetas donde almacenar los diferentes ficheros */
	public static String TRAIN = "train";
	public static String INPUT = "input";
	public static String OUTPUT = "output";
	public static String SENTENCE = "sentence";
	public static String MODEL = "model";
	public static String MODELTXT = "modeltxt";

	/* Extension de los ficheros */
	public final static String TXT = ".txt";
	public final static String GZ = ".gz";
	public final static String LISP = ".lisp";

	/**
	 * Métodos abstractos que se implementan en cada herramienta. Es el metodo encargado de leer
	 * un arbol, por lo que hay que ajustarlo al fichero de entrada utilizado y lo que se necesite
	 * del mismo.
	 * 
	 * @param arbol arbol en forma de cadena
	 * @return un arbol creado a partir de la cadena con los nodos correspondientes
	 */
	public abstract Arbol createTree(String arbol);
	
	/**
	 * Guardar los arboles del treebank en un lista
	 * 
	 * @param filename
	 *            nombre del fichero que contiene el treebank (.lisp)
	 * @return devuelve la lista de los arboles leidos
	 */
	protected List<Arbol> getTreesForFile(String filename) {
		BufferedReader file;
		List<Arbol> trees = new ArrayList<Arbol>();
		String s;
		String tree = new String();

		try {
			file = new BufferedReader(new FileReader(filename));

			while ((s = file.readLine()) != null) {			// A

				if (s.equals("") && !tree.isEmpty()) {		// B
					Arbol arbol = createTree(tree);			// C
					if (arbol != null)
						trees.add(arbol);
					tree = new String();
				} else if (!s.equals("")) {					// D
					tree += s;
				}											// E
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return trees;										// F
	}

	/**
	 * Crea los directorios temporales necesarios
	 * 
	 */
	protected void createDirectories() {
		File file = new File(URL_INPUT);
		file.mkdirs();
		file = new File(URL_MODEL);
		file.mkdirs();
		file = new File(URL_MODELTXT);
		file.mkdirs();
		file = new File(URL_OUTPUT);
		file.mkdirs();
		file = new File(URL_SENTENCE);
		file.mkdirs();
		file = new File(URL_TRAIN);
		file.mkdirs();
	}

	/**
	 * Elimina un directorio pasado por argumento
	 * 
	 * @param url url del directorio a eliminiar
	 */
	protected void deleteDirectory(String url) {
		File d = new File(url);

		String[] files = d.list();
		for (int i = 0; i < files.length; i++) {
			File f = new File(url + "/" + files[i]);
			f.delete();
		}

		d.delete();
	}

	/**
	 * Elimina los directorios temporales
	 * 
	 */
	protected void deleteDirectories() {
		deleteDirectory(URL_INPUT);
		deleteDirectory(URL_MODELTXT);
		deleteDirectory(URL_MODEL);
		deleteDirectory(URL_OUTPUT);
		deleteDirectory(URL_SENTENCE);
		deleteDirectory(URL_TRAIN);
	}

	/**
	 * Copia el contenido de un archivo en otro
	 * 
	 * @param dest archivo destino abierto como buffer
	 * @param src ruta del archivo de origen
	 */
	public void copyText(BufferedWriter dest, String src) {
		BufferedReader fileSrc;

		try {
			fileSrc = new BufferedReader(new FileReader(src));

			String s;
			dest.write("\n");
			while ((s = fileSrc.readLine()) != null) {
				dest.write(s + "\n");
			}

			fileSrc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Imprime un arbol a partir de un nodo dado
	 * 
	 * @param nodo nodo padre del cual imprimir el arbol
	 */
	public void printArbolDesdeNodo(Nodo nodo) {
		Arbol arbol = new Arbol();
		ArrayList<Nodo> nodos = new ArrayList<Nodo>();
		nodos.add(nodo);
		arbol.setRaiz(nodo);
		arbol.setNodos(nodos);
		System.out.println(arbol);
	}

}
