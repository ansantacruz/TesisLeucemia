package Modelo;

public class RedBloodCells implements Cell {

	boolean health;
	int timeOfLifeCell = 0;
	int iterationsCounters = 0;
	int minimalPerception = 0; // minimo cuantos individuos del mismo tipo para no sentir falta de poblacion
	int iterationsToEendSignal = 0; // cada cuantas iteraciones se evalua si hay falta de poblacion

	int minimumOfNeighborsDetectedInStipulatedTime = 0;
	int iterationSignalCounter = 0; // cuenta las iteraciones para el envio de señal.
	int detectedNeighbors = 0; // cuenta en el lapso de tiempo estipulado cuantas celulas del mismo tiempo vio
	/*
	 * El Constructor recibe como parametro la salud de la celula Esto permite la
	 * creacion de celulas sanas y enfermas cuando se presente el caso
	 */

	public RedBloodCells(boolean healthParameter, int timeOfLife, int MinimalPerception, int IterationsToEendSignal) {
		// TODO Auto-generated constructor stub
		this.health = healthParameter;
		this.timeOfLifeCell = timeOfLife;
		iterationsCounters = (int) (Math.random() * timeOfLifeCell);
		this.minimalPerception = MinimalPerception;
		this.iterationsToEendSignal = IterationsToEendSignal;
		minimumOfNeighborsDetectedInStipulatedTime = iterationsToEendSignal
				* minimumOfNeighborsDetectedInStipulatedTime;
	}

	/*
	 * Este metodo recibe un arreglo que cotiene la cantidad de vecinos que tiene
	 * por cada tipo de celula. Por ejemplo: [0,3,1,2,0,2] indica que este celula
	 * tiene como vecinos 0 plaquetas sanas, 3 plaquetas enfermas, 1 globulo rojo
	 * sano, 2 globulos rojos enfermos, ningun globulo blanco sano y 2 globulos
	 * blancos enfermos.
	 * 
	 * Con La Informacion contenida en el arreglo, el agente decide que accion
	 * ejecutar. (Reglas Geneticas del automata celular).
	 * 
	 * Retorna un String que identifica estandarizadamente la accion a ejecutar.
	 * Este String lo recibe el ambiente para garantizar que se ejecute la accion.
	 * 
	 */
	@Override
	public String ReceiveNeighbors(int[] quantityNeighbors, int i) {
		String Action = "";
		iterationsCounters++;
		iterationSignalCounter++;
		detectedNeighbors = detectedNeighbors + quantityNeighbors[2] + quantityNeighbors[3];

		if (iterationSignalCounter <= iterationSignalCounter) {
			if (iterationsCounters > timeOfLifeCell) {
				Action = Apoptosis();
			} else {
				if (health == true) {
					if (quantityNeighbors[5] >= 3 || quantityNeighbors[4] >= 2) {
						Action = "Die";
					}
				} else {
					if (quantityNeighbors[5] >= 3) {
						Action = "Die";
					}
				}

			}
		} else {
			if (detectedNeighbors < minimumOfNeighborsDetectedInStipulatedTime) {
				Action = "SendSignalGR";
			}

			iterationSignalCounter = 0;
			detectedNeighbors = 0;
		}

		return Action;
	}

	public boolean isHealth() {
		// TODO Auto-generated method stub
		return health;
	}

	public String Apoptosis() {
		return "DieAndSeendSignalRedBloodCell";
	}

}
