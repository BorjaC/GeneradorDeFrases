# GeneradorDeFrases

Para la ejecución son necesarios los ficheros:

•	Sintagmas, fichero que contiene las oraciones correctamente etiquetados con el número lingüístico (singular o plurar) del cual se extraen los sintagmas que serán empleados para crear nuevas frases.
•	Estructuras, fichero que contiene las estructuras de las oraciones que serán generadas. 

Los parámetros para la ejecución serán siempre los siguientes:

•	-est “fichero estructuras”: marca que lo siguiente a leer es el fichero estructuras.
•	-sin “fichero sintagmas”: marca que lo siguiente a leer es el fichero sintagmas.
•	-n “numero entero”: marca el número de oraciones que se van a generar. Este número de oraciones es dividido por el número de estructuras que existe para generar, por lo que es aconsejable emplear múltiplos del número de estructuras.
•	-clave “cadena”: marca que la clave para realizar el intercambio de sintagmas. Por ejemplo, NPSUBJ marca que la frase nueva se creara intercambiando el NPSUBJ de la frase del fichero estructuras por un NPSUBJ obtenido del fichero sintagmas aleatoriamente.

El orden de los parámetros se puede realizar de cualquier forma. A tener en cuenta:

•	En todos los casos es necesario es necesario emplear todos los parámetros.
•	La generación de frases no entiende de concordancia más allá del número, es posible que no exista concordancia semántica. Es responsabilidad de cada cual mantenerla cuidando los sintagmas y las estructuras empleados para el intercambio.
•	Si no existen sintagmas dentro de las estructuras que contengan la clave, la generación de nuevas frases a partir de esa estructura no será posible y saltara un error avisando de ello por cada vez que se haya intentado usar esa estructura para generar nuevas frases.

Ejemplos de ejecución:
	
java -jar generador.jar -est “estructuras” -sin “sintagmas” -n 10 -clave NPSUBJ

La salida esperada son dos ficheros (uno contiene las frases en forma e árbol y la otra las frases en forma de oración) con 10 nuevas frases generadas intercambiando los sintagmas NPSUBJ por otros diferentes.
