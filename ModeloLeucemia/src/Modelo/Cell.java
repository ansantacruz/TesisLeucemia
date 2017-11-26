package Modelo;

/*
 * Super clase de la cual heredan todos los tipos de celula:
 * 	1. Globulos Rojos
 *  2. Globulos Blancos
 *  3. Plaquetas
 */
public interface Cell {

	boolean Health=true; // Todas las celulas tienen un estado de salud
	/*
	 *  Todas las celulas reciben un arreglo con la cantidad de vecinos que tienen.
	 *  Estructura del arreglo:
	 *  [#PLS,#PLE,#GRS,#GRE,#GBS,#GBE] 
	 */
	public String ReceiveNeighbors(int [] quantityNeighbors,int position); 

	
	public boolean isHealth();
}
