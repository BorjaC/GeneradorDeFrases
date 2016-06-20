package generar;

public class Main {

	public static void main(String[] args) {

		if (args.length != 8) {
			error();
			return;
		}

		String sinonimos = "";
		String estructuras = "";
		String clave = "";
		int n = 0;
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-est")) {
				i++;
				estructuras = args[i];
			} else if (args[i].equals("-n")){
				i++;
				n = Integer.parseInt(args[i]);
			} else if (args[i].equals("-sin")){
				i++;
				sinonimos = args[i];
			} else if (args[i].equals("-clave")){
				i++;
				clave = args[i];
			}
		}

		if(estructuras.equals("") || sinonimos.equals("") || clave.equals("") || n == 0){
			error();
			return;
		}
		
		Generador generador = new Generador(sinonimos, clave);
		generador.generarFrases(estructuras, n);
		
		System.out.println("\n" + n + " frases generadas correctamente.");
	}
	
	private static void error(){
		System.err.println("Parametros incorrecto. Los parametros son:");
		System.err.println(
				" -est \"direccion de fichero que contiene las estructuras\"\n"
				+ " -n \"numero de frases a generar\"\n"
				+ " -sin \"direccion de fichero del cual estraer los sintagmas\"\n"
				+ " -clave \"clave para estraer los sinonimos\"");
	}
	
}
