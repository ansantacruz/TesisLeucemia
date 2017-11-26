package Vista;

import Modelo.Environment;
import processing.core.PApplet;

/*
 * Esta clase integra a java con processing y muestra graficamente las iteraciones de la simulacion
 */
public class GraphicEnvironment extends PApplet {

	static Environment population = new Environment(); // este objeto es el usado para ejecutar todo el modelo

	static /*
			 * Esta variable representa el ambiente codificado
			 */
	int encodedEnvironment[][];// = population.getGraphicRepresentationOfTheEnvironment();
	static /*
			 * Toma el numero de filas de la matrz ambiente
			 */
	int rowEnvironment;
	/*
	 * Toma el valor de las columnas del ambiente
	 */
	static int columnsEnvironment;

	int contador = 0;

	public static void main(String[] args) {
		PApplet.main("Vista.GraphicEnvironment");
		population.main(args);
		rowEnvironment = population.numberRows;
		columnsEnvironment = population.numberColumns;
		encodedEnvironment = population.getGraphicRepresentationOfTheEnvironment();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see processing.core.PApplet#settings()
	 * 
	 * Este metodo configura la ventana que mostrara la ejecucion grafica del modelo
	 * con un tama�o de 800*800
	 */

	public void settings() {
		size(700, 600);
	}

	public void setup() {
		fill(120, 50, 240);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see processing.core.PApplet#draw() Este metodo es el encargado de dibujar el
	 * automata celular
	 */
	public void draw() {
		contador++;
		for (int x = 0; x < columnsEnvironment; x++) {
			for (int y = 0; y < rowEnvironment; y++) {
				if (encodedEnvironment[y][x] == 0) {
					fill(169, 124, 227);
				} else {
					if (encodedEnvironment[y][x] == 1) {
						fill(255, 255, 0);
					} else {
						if (encodedEnvironment[y][x] == 2) {
							fill(255, 0, 0);
						} else {
							if (encodedEnvironment[y][x] == 3) {
								fill(0, 14, 255);
							} else {
								if (encodedEnvironment[y][x] == 4) {
									fill(255, 255, 255);
								} else {
									if (encodedEnvironment[y][x] == 5) {
										fill(0, 255, 6);
									} else {
										if (encodedEnvironment[y][x] == 6) {
											fill(39, 55, 70);
										}
									}
								}

							}
						}
					}
				}

			//	rect(x * (600/rowEnvironment), y * (600/columnsEnvironment), (600/rowEnvironment), (600/columnsEnvironment));
				rect(x * 5, y * 5, 5, 5);
			}

		}
		if(contador == 2 || contador == 2000 || contador ==750 || contador == 1500)
		{
			saveFrame("line-######.png");
		}
		if(contador<2000) {
		encodedEnvironment = population.iterations();
	}
	}

}
