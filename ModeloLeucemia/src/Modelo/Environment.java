package Modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Environment {

	private static String path = "C:\\Users\\usuario\\Documents\\Experimentos\\50000-0.9-0.9-300\\5\\Parametros.txt";

	public static int numberRows = 0; // Numero de filas de la matriz ambiente
	public static int numberColumns = 0; // Numero de columnas de la matriz ambiente
	static int initialAmountCells = 0; // Numero de celulas en la poblacion inicial
	static double probabilityOnsetPlateles = 0.0; // Probabilidad acumulada de aparicion de plaquetas en la poblacion
	// inicial
	static double probabilityOnsetRedBloodCells = 0.0;// Probabilidad acumulada de aparicion de globulos rojos en la
	// poblacion inicial
	static double probabilityOnsetWhiteBloodCells = 0.0;// Probabilidad acumulada de aparicion de globulos blancos en la
	// poblacion inicial
	static double probabilityOfInitialPopulationHealth = 0.0; // Probabiliolidad de que se presenten defectos en
																// produccion celular
	static Cell[][] Environment; // Matriz ambiente de tipo celula que guarda objetos de tipo globulo rojo,
									// blanco y plaquetas

	static MotherCells[] BoneMarrow; // Arreglo medula osea de tipo celula que guarda objetos del tipo Celula Madre

	static int[][] graphicRepresentationOfTheEnvironment; // esta matriz almacena valores numericos que identifican cada
	// tipo de celula para que processing las dibuje en la representacion grafica
	public static int iterationsForMovement = 0; // Indicador para el movimiento de las celulas (Cada cuantas
													// iteraciones se mueven)

	static int counterMovement = 0; // contador de iteraciones ocurridas desde el ultimo movimiento
	static int counterDivision = 0; // Contador de iteraciones ocurridad tras la ultima division celular normal
	// Se�ales para creacion de nuevas celulas
	static int timeOfLifePlatetes = 0; // iteraciones de vida para plaquetas
	static int timeOfLifeWhiteBloodCells = 0; // iteraciones de vida para globulos blancos
	static int timeOfLifeRedBloodCells = 0; // iteraciones de vida para globulos rojos
	static int timeOfLifeWhiteBloodCellSick = 0; // En cuanto aumenta la vida de globulos blancos enfermos
	static int maximumNumberOfReproductionsMotherCells = 0; // Maximo cuantas reproducciones puede ejercer una celula
															// madre antes de entrar en apoptosis
	static int minimumNumberCellsNoLackOfIndividualsPL = 0; // PERCEPCION MINIMA DE VECINOS PARA NO DETECTAR FALTA DE
	// INDIVIDUOS EN PLAQUETAS
	static int minimumNumberCellsNoLackOfIndividualsGR = 0; // PERCEPCION MINIMA DE VECINOS PARA NO DETECTAR FALTA DE
	// INDIVIDUOS EN GLOBULOS ROJOS
	static int minimumNumberCellsNoLackOfIndividualsGBS = 0; // PERCEPCION MINIMA DE VECINOS PARA NO DETECTAR FALTA DE
	// INDIVIDUOS GLOBUOS BLANCOS SANOS
	static int minimumNumberCellsNoLackOfIndividualsGBE = 0; // PERCEPCION MINIMA DE VECINOS PARA NO DETECTAR FALTA DE
	// INDIVIDUOS GLOBULOS BLANCOS ENFERMOS
	static double probabiltyOfMyeloblasticSyndrome = 0.0; // Indica la probabilidad de que un globuklo blanco enfermos
															// se
	// quede en la medula osea

	static int countMyeloblasticSyndrome = 0; // mantiene la cuenta de los globulos blancos enfermos que se encuentran
												// en la medula osea

	static int iterationsToSendSignalPL = 0; // cada cuantas iteraciones se evalua si hay falta de individuos y se envia
	// se�al
	static int iterationsToSendSignalGR = 0; // cada cuantas iteraciones se evalua si hay falta de individuos y se envia
	// se�al
	static int iterationsToSendSignalGBS = 0; // cada cuantas iteraciones se evalua si hay falta de individuos y se
	// envia se�al
	static int iterationsToSendSignalGBE = 0; // cada cuantas iteraciones se evalua si hay falta de individuos y se
	// envia se�al

	static int iterationsForNormalCellDivision = 0; // Esta variable almacena el parametro que indica cada cuantas
	// iteraciones se dividen las celulas madres

	static int IterationsCount = 0;
	static int maximumNumberOfReproductionMyeloblasticSyndrome = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ReadParameters(path); // Se leen del txt los parametros iniciales
		Environment = new Cell[numberRows][numberColumns]; // se crea el ambiente con el tama�o requerido por el usuario
		BoneMarrow = new MotherCells[numberColumns]; // Se crea la medula osea correspondiente a un arreglo con las
														// mismas
		// columnas del ambiente
		graphicRepresentationOfTheEnvironment = new int[numberRows][numberColumns]; // se crea la matriz que
		// representara graficamente el
		// ambiente
		InitializeEnvironment(); // Se icicia en null el ambiente de celulas sanguineas
		SeedInitialPopulation(Environment); // se siembra la poblacion inicial en el ambiente sanguineo
		InitializeBonMarrow(); // se inicia en null el ambiente de celulas madre
		SeedInitialPopulationOfMotherCells(BoneMarrow); // Se siembra la poblacion inicial de celulas madre
		encodeEnvironment(Environment);
	}

	/*
	 * Este metodo lee de un .txt los parametros requeridos para correr la
	 * simulacion:
	 */

	private static void ReadParameters(String path) {

		String line; // linea leida en el archivo de parametrizacion
		try {
			FileReader file = new FileReader(path); // archivo de Parametrizacion
			BufferedReader b = new BufferedReader(file);

			for (int i = 0; i < 24; i++) {
				line = b.readLine();
				line = line.substring(line.indexOf(':') + 1, line.length());
				switch (i) {
				case 0:
					// 0. # Filas de la matriz ambiente
					numberRows = Integer.parseInt(line);
					break;
				// * 1. # Columnas de la matriz ambiente
				case 1:
					numberColumns = Integer.parseInt(line);
					break;
				// * 2. Cantidad inicial de celulas
				case 2:
					initialAmountCells = Integer.parseInt(line);
					break;
				// * 3. Probabilidad acumulada de aparicion de plaquetas en poblacion inicial
				case 3:
					probabilityOnsetPlateles = Double.parseDouble(line);
					break;
				// * 4. Probabilidad acumulada de aparicion de Globulos Rojos en poblacion
				// inicial
				case 4:
					probabilityOnsetRedBloodCells = Double.parseDouble(line);
					probabilityOnsetWhiteBloodCells = 1 - probabilityOnsetPlateles - probabilityOnsetRedBloodCells;
					break;
				// * 5. Probabilidad de defectos celulares en reproduccion celular
				case 5:
					probabilityOfInitialPopulationHealth = Double.parseDouble(line);
					break;
				// * 6. Cada cuantas iteraciones las celulas se mueven
				case 6:
					iterationsForMovement = Integer.parseInt(line);
					break;
				// Numero de iteraciones para Division Normal
				case 7:
					iterationsForNormalCellDivision = Integer.parseInt(line);
					break;
				// # de iteraciones que vive una Plaqueta
				case 8:
					timeOfLifePlatetes = Integer.parseInt(line);
					break;
				// # de iteraciones que vive un Globulo Blanco
				case 9:
					timeOfLifeWhiteBloodCells = Integer.parseInt(line);
					break;
				// # de iteraciones que vive un Globulo Rojo
				case 10:
					timeOfLifeRedBloodCells = Integer.parseInt(line);
					break;
				// incremento en iteraciones de vida de globulos blancos Enfermos
				case 11:
					timeOfLifeWhiteBloodCellSick = Integer.parseInt(line);
					break;
				// M�nimo de c�lulas vecinas detectadas para no percibir falta de individuos PL
				case 12:
					minimumNumberCellsNoLackOfIndividualsPL = Integer.parseInt(line);
					break;
				// M�nimo de c�lulas vecinas detectadas para no percibir falta de individuos GR
				case 13:
					minimumNumberCellsNoLackOfIndividualsGR = Integer.parseInt(line);
					break;
				// M�nimo de c�lulas vecinas detectadas para no percibir falta de individuos GBS
				case 14:
					minimumNumberCellsNoLackOfIndividualsGBS = Integer.parseInt(line);
					break;
				// M�nimo de c�lulas vecinas detectadas para no percibir falta de individuos GBE
				case 15:
					minimumNumberCellsNoLackOfIndividualsGBE = Integer.parseInt(line);
					break;
				// cada cuantas iteraciones se evalua si hay falta de individuos y se envia
				// se�al PL
				case 16:
					iterationsToSendSignalPL = Integer.parseInt(line);
					break;
				// cada cuantas iteraciones se evalua si hay falta de individuos y se envia
				// se�al GR
				case 17:
					iterationsToSendSignalGR = Integer.parseInt(line);
					break;
				// cada cuantas iteraciones se evalua si hay falta de individuos y se envia
				// se�al GBS
				case 18:
					iterationsToSendSignalGBS = Integer.parseInt(line);
					break;
				// cada cuantas iteraciones se evalua si hay falta de individuos y se envia
				// se�al GBE
				case 19:
					iterationsToSendSignalGBE = Integer.parseInt(line);
					break;
				// se lee la cantidad de reproducciones que puede tener una celula madre antes
				// de entrar en apoptosis
				case 20:
					maximumNumberOfReproductionsMotherCells = Integer.parseInt(line);
					break;
				// probabilidad de sindrome mieloblastico
				case 21:
					probabiltyOfMyeloblasticSyndrome = Double.parseDouble(line);
					break;
				// Rrpducciones maxima spor celula en sindrome mieloblastico
				case 22:
					maximumNumberOfReproductionMyeloblasticSyndrome = Integer.parseInt(line);
					break;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Este metodo inicializa el ambiente en null para todo i,j y lo deja listo para
	 * sembrar la poblacion inicial.
	 */
	private static void InitializeEnvironment() {

		for (int i = 0; i < numberRows; i++) {
			for (int j = 0; j < numberColumns; j++) {
				Environment[i][j] = null;
			}
		}
	}

	/*
	 * Este meodo se encarga de inicializar el arreglo en null para cada i,j
	 * Dejandolo listo para sembrar la poblacion inicial de celulas madre
	 */
	private static void InitializeBonMarrow() {
		for (int i = 0; i < numberColumns; i++) {
			BoneMarrow[i] = null;

		}
	}

	/*
	 * Este metodo siembra las celulas madre
	 */
	private static void SeedInitialPopulationOfMotherCells(Cell[] boneMarrow) {
		for (int i = 0; i < numberColumns; i++) {

			boneMarrow[i] = new MotherCells(true, maximumNumberOfReproductionsMotherCells); // se siembra la nueva

		}
	}

	/*
	 * Este metodo se encarga de sembrar la poblacion inicial de celulas sanguineas
	 * segun los parametros rescatados del .txt de parametrizacion
	 */

	private static void SeedInitialPopulation(Cell[][] population) {

		int pls = 0, grs = 0, gbs = 0, ple = 0, gre = 0, gbe = 0; // Estas variables almacenan la cantidad de cada tipo
		// de celula sembrada en el ambiente

		int numberOfCellsSown = 0; // Variable que cuenta la cantidad de celulas que se han sembrado

		/*
		 * Genera un pseudoaleatorio entre 0 y el numero de filas -1 para implantar
		 * aleatoriamente una celula
		 */
		int deploymentRow = 0;
		/*
		 * Genera un pseudoaleatorio entre 0 y el numero de columnas -1 para implantar
		 * aleatoriamente una celula
		 */
		int deploymentColumn = 0;

		/*
		 * cellType toma valores entre 0 y 1 para decidir que tipo de celula se siembra
		 * health toma aleatoriamente valor entre 0 y 1 para decidir salud de la celula
		 * sembrada
		 */
		double cellType = 0.0;
		Math.random();
		double health = 0.0;
		Math.random();
		boolean conditionHealth = false;

		/*
		 * Este while garantiza que se siembre la cantidad de celulas establecida en los
		 * parametros
		 */

		while (numberOfCellsSown <= initialAmountCells) {

			deploymentRow = (int) (Math.random() * numberRows); //
			deploymentColumn = (int) (Math.random() * numberColumns);

			/*
			 * Este if verifica si en la posicion obtenida aleatoriamente hay o no una
			 * celula
			 * 
			 * Si hay celula se genera nueva posicion Si NO hay celula se siembra una celula
			 */
			if (population[deploymentRow][deploymentColumn] == null) {
				numberOfCellsSown++;
				cellType = Math.random(); // Se genera PseudoAleatorio para decidir tipo de celula a sembrar
				health = Math.random(); // Se genera Pseudoaleatorio para decidir salud de celula a sembrar

				/*
				 * si health es menor o igual a probabilityOfInitialPopulationHealth la celula
				 * sera sana
				 */
				if (health > probabilityOfInitialPopulationHealth) {
					conditionHealth = true;
				} else {
					conditionHealth = false;
				}

				/*
				 * Se compara cellType contra Prob Acumulada de ocurrencia para PL, GR Y GB a
				 * fin de decidir que tipo de celula se sembrara.
				 */

				// SI SE CUMPLE SEMBRAR PLAQUETAS

				if (cellType > probabilityOnsetRedBloodCells && cellType <= probabilityOnsetPlateles) {
					population[deploymentRow][deploymentColumn] = new Plateles(conditionHealth, timeOfLifePlatetes,
							minimumNumberCellsNoLackOfIndividualsPL, iterationsToSendSignalPL);

					// verifica si la salud es buena o mala par actualizar el contador de pl sanas o
					// enfermas
					if (conditionHealth == true) {
						pls++;
					} else {
						ple++;
					}
				} else {
					// SI SE CUMPLE SEMBRAR GLOBULOS ROJOS

					if (cellType > 0 && cellType <= probabilityOnsetRedBloodCells) {
						population[deploymentRow][deploymentColumn] = new RedBloodCells(conditionHealth,
								timeOfLifeRedBloodCells, minimumNumberCellsNoLackOfIndividualsGR,
								iterationsToSendSignalGR);

						// verifica si la salud es buena o mala par actualizar el contador de gr sanas o
						// enfermas
						if (conditionHealth == true) {
							grs++;
						} else {
							gre++;
						}
					} else {

						population[deploymentRow][deploymentColumn] = new WhiteBloodCells(conditionHealth,
								timeOfLifeWhiteBloodCells, timeOfLifeWhiteBloodCellSick,
								minimumNumberCellsNoLackOfIndividualsGBS, iterationsToSendSignalGBS,
								minimumNumberCellsNoLackOfIndividualsGBS, iterationsToSendSignalGBS);
						if (conditionHealth == true) {
							gbs++;
						} else {
							gbe++;
						}

					}
				}
			}
		}
		/*
		 * System.out.println("**** Cantidades iniciales **** ");
		 * System.out.println("Globulos Rojos sano = " + grs +
		 * "\nGlobulos Rojos enfermo  = " + gre + "\n\nGlobulos Blancos sano = " + gbs +
		 * "\nGlobulos Blancos enfermo = " + gbe + "\n\nPlaquetas sanas= " + pls +
		 * "\nPlaquetas enfermas= " + ple); System.out.println("******************* ");
		 */
	}

	/*
	 * Este metodo es ejcutado repetidamente por la capa de vista **Funcion Draw de
	 * processing** Para aplicar reglas geneticas por cada corrida a la matriz
	 * ambiente
	 */
	public int[][] iterations() {

		IterationsCount++;
		counterMovement++; // Cada vez que Processing llama a este metodo se incrementa en uno el contador
		// de iteraciones
		counterDivision++; // Cada vez que Processing llama a este metodo se incrementa en uno el contador
		// de divisiones

		/*
		 * Si el contador de iteraciones es igual al iterationsForMovement (Variable
		 * parametrizable que indica cada cuantas iteraciones se mueven las celulas) se
		 * inicia nuevamente el contador en 0 y se llama al metodo encargado de realizar
		 * el movimiento de las celulas
		 */
		if (counterMovement == iterationsForMovement) {
			counterMovement = 0;
			MoveCells(Environment);

		}

		if (counterDivision == iterationsForNormalCellDivision) {
			counterDivision = 0;
			NormalCellDivision(Environment);
		}
		countNeighborhood(Environment);
		encodeEnvironment(Environment);

		return graphicRepresentationOfTheEnvironment;
	}

	/*
	 * Este metodo cuenta cuantos vecinos tiene cada celula y de que tipo son
	 */

	private void countNeighborhood(Cell[][] environment) {
		int neighbors[]; // Para los vecinos identificados, almacena la cantidad que corresponde a cada
		// tipo de celula
		String NeighbourType[] = new String[9]; // Almacena el tipo de celula a que corresponde cada vecino
		boolean HearthNeighbour[] = new boolean[8]; // Almacena salud para cada vecino
		String acction = "";
		// Recorre el ambiente para evaluar los vecinos de cada individuo de la
		// poblacion de forma toroidal
		for (int i = 0; i < numberRows; i++) {
			for (int j = 0; j < numberColumns; j++) {
				if (environment[i][j] != null) {
					if (environment[(i - 1 + numberRows) % numberRows][(j - 1 + numberColumns)
							% numberColumns] != null) {
						NeighbourType[0] = environment[(i - 1 + numberRows) % numberRows][(j - 1 + numberColumns)
								% numberColumns].getClass().getSimpleName();
						HearthNeighbour[0] = environment[(i - 1 + numberRows) % numberRows][(j - 1 + numberColumns)
								% numberColumns].isHealth();
					} else {
						NeighbourType[0] = "";
					}

					if (environment[(i - 1 + numberRows) % numberRows][j] != null) {
						NeighbourType[1] = environment[(i - 1 + numberRows) % numberRows][j].getClass().getSimpleName();
						HearthNeighbour[1] = environment[(i - 1 + numberRows) % numberRows][j].isHealth();
					} else {
						NeighbourType[1] = "";
					}

					if (environment[(i - 1 + numberRows) % numberRows][(j + 1) % numberColumns] != null) {
						NeighbourType[2] = environment[(i - 1 + numberRows) % numberRows][(j + 1) % numberColumns]
								.getClass().getSimpleName();
						HearthNeighbour[2] = environment[(i - 1 + numberRows) % numberRows][(j + 1) % numberColumns]
								.isHealth();
					} else {
						NeighbourType[2] = "";
					}

					if (environment[i][(j - 1 + numberColumns) % numberColumns] != null) {
						NeighbourType[3] = environment[i][(j - 1 + numberColumns) % numberColumns].getClass()
								.getSimpleName();
						HearthNeighbour[3] = environment[i][(j - 1 + numberColumns) % numberColumns].isHealth();
					} else {
						NeighbourType[3] = "";
					}

					if (environment[i][(j + 1) % numberColumns] != null) {
						NeighbourType[4] = environment[i][(j + 1) % numberColumns].getClass().getSimpleName();
						HearthNeighbour[4] = environment[i][(j + 1) % numberColumns].isHealth();
					} else {
						NeighbourType[4] = "";
					}

					if (environment[(i + 1) % numberRows][(j - 1 + numberColumns) % numberColumns] != null) {
						NeighbourType[5] = environment[(i + 1) % numberRows][(j - 1 + numberColumns) % numberColumns]
								.getClass().getSimpleName();
						HearthNeighbour[5] = environment[(i + 1) % numberRows][(j - 1 + numberColumns) % numberColumns]
								.isHealth();
					} else {
						NeighbourType[5] = "";
					}

					if (environment[(i + 1) % numberRows][j] != null) {
						NeighbourType[6] = environment[(i + 1) % numberRows][j].getClass().getSimpleName();
						HearthNeighbour[6] = environment[(i + 1) % numberRows][j].isHealth();
					} else {
						NeighbourType[6] = "";
					}

					if (environment[(i + 1) % numberRows][(j + 1) % numberColumns] != null) {
						NeighbourType[7] = environment[(i + 1) % numberRows][(j + 1) % numberColumns].getClass()
								.getSimpleName();
						HearthNeighbour[7] = environment[(i + 1) % numberRows][(j + 1) % numberColumns].isHealth();
					} else {
						NeighbourType[7] = "";
					}

					if (BoneMarrow[j] != null) {
						if (BoneMarrow[j].isWhiteCell == true) {
							NewCell(BoneMarrow[j].generateWhiteBloodCellSick(), j);
							
						}
					}

					neighbors = calculateAmountByCellType(NeighbourType, HearthNeighbour);
					acction = environment[i][j].ReceiveNeighbors(neighbors, i);

					switch (acction) {
					case "Die":
						environment[i][j] = null; // muere la celula;
						break;
					case "DieAndSeendSignalPlatetesBloodCells":
						environment[i][j] = null; // muere la celula;
						if (BoneMarrow[j] != null) {
							NewCell(BoneMarrow[j].CellularDivision("Platetes"), j);// Envia se�al para creacion de nueva
																					// Plaqueta
						}

						break;

					case "DieAndSeendSignalRedBloodCell":
						environment[i][j] = null; // muere la celula;
						if (BoneMarrow[j] != null) {
							NewCell(BoneMarrow[j].CellularDivision("RedCell"), j); // Envia se�al para creacion de nuevo
																					// globulo rojo
						}

						break;

					case "DieAndSeendSignalWhiteBloodCell":
						environment[i][j] = null; // muere la celula;
						if (BoneMarrow[j] != null) {
							NewCell(BoneMarrow[j].CellularDivision("WhiteCell"), j);// Envia se�al para creacion de
																					// nuevo globulo blanco
						}

						break;

					case "SendSignalGBE":
						if (BoneMarrow[j] != null) {
							NewCell(BoneMarrow[j].CellularDivision("WhiteCell"), j);// Envia se�al para creacion de
																					// nuevo globulo blanco enfermo
						}
						break;

					case "SendSignalGBS":
						if (BoneMarrow[j] != null) {
							NewCell(BoneMarrow[j].CellularDivision("WhiteCell"), j); // Envia se�al para creacion de
																						// nuevo globulo blanco sano
						}
						break;

					case "SendSignalPl":
						if (BoneMarrow[j] != null) {
							NewCell(BoneMarrow[j].CellularDivision("Platetes"), j); // Envia se�al para creacion de
																					// nueva plaqueta
						}
						break;

					case "SendSignalGR":
						if (BoneMarrow[j] != null) {
							NewCell(BoneMarrow[j].CellularDivision("RedCell"), j);// Envia se�al para creacion de nuevo
																					// globulo rojo
						}
						break;
					default:

						break;
					}

				}

			}
		}

	}

	private int[] calculateAmountByCellType(String[] cellType, boolean[] heathCell) {

		/*
		 * // arreglo que sera retornado con la cantidad de cada tipo de celula
		 * [pls,ple,grs,gre,gbs,gbe]
		 */
		int[] amountPerCellType = new int[6];

		int healthyPlateletCounter = 0, sickPlateletCounter = 0, healthyRedBloodCellCounter = 0,
				sickRedBloodCellCounter = 0, healthyWhiteBloodCellCounter = 0, redBloodCellCount = 0;

		// vecino 0
		switch (cellType[0]) {
		case "Plateles":
			if (heathCell[0] == true) {
				healthyPlateletCounter++;
			} else {
				sickPlateletCounter++;
			}
			break;

		case "RedBloodCells":
			if (heathCell[0] == true) {
				healthyRedBloodCellCounter++;
			} else {
				sickRedBloodCellCounter++;
			}
			break;

		case "WhiteBloodCells":
			if (heathCell[0] == true) {
				healthyWhiteBloodCellCounter++;
			} else {
				redBloodCellCount++;
			}

			break;
		default:
			break;
		}

		// vecino 1
		switch (cellType[1]) {
		case "Plateles":
			if (heathCell[1] == true) {
				healthyPlateletCounter++;
			} else {
				sickPlateletCounter++;
			}
			break;

		case "RedBloodCells":
			if (heathCell[1] == true) {
				healthyRedBloodCellCounter++;
			} else {
				sickRedBloodCellCounter++;
			}
			break;

		case "WhiteBloodCells":
			if (heathCell[1] == true) {
				healthyWhiteBloodCellCounter++;
			} else {
				redBloodCellCount++;
			}

			break;
		default:
			break;
		}

		// vecino2
		switch (cellType[2]) {
		case "Plateles":
			if (heathCell[2] == true) {
				healthyPlateletCounter++;
			} else {
				sickPlateletCounter++;
			}
			break;

		case "RedBloodCells":
			if (heathCell[2] == true) {
				healthyRedBloodCellCounter++;
			} else {
				sickRedBloodCellCounter++;
			}
			break;

		case "WhiteBloodCells":
			if (heathCell[2] == true) {
				healthyWhiteBloodCellCounter++;
			} else {
				redBloodCellCount++;
			}

			break;
		default:
			break;
		}

		// vecino 3
		switch (cellType[3]) {
		case "Plateles":
			if (heathCell[3] == true) {
				healthyPlateletCounter++;
			} else {
				sickPlateletCounter++;
			}
			break;

		case "RedBloodCells":
			if (heathCell[3] == true) {
				healthyRedBloodCellCounter++;
			} else {
				sickRedBloodCellCounter++;
			}
			break;

		case "WhiteBloodCells":
			if (heathCell[3] == true) {
				healthyWhiteBloodCellCounter++;
			} else {
				redBloodCellCount++;
			}

			break;
		default:
			break;
		}

		// vecino 4
		switch (cellType[4]) {
		case "Plateles":
			if (heathCell[4] == true) {
				healthyPlateletCounter++;
			} else {
				sickPlateletCounter++;
			}
			break;

		case "RedBloodCells":
			if (heathCell[4] == true) {
				healthyRedBloodCellCounter++;
			} else {
				sickRedBloodCellCounter++;
			}
			break;

		case "WhiteBloodCells":
			if (heathCell[4] == true) {
				healthyWhiteBloodCellCounter++;
			} else {
				redBloodCellCount++;
			}

			break;
		default:
			break;
		}

		// vecino 5
		switch (cellType[5]) {
		case "Plateles":
			if (heathCell[5] == true) {
				healthyPlateletCounter++;
			} else {
				sickPlateletCounter++;
			}
			break;

		case "RedBloodCells":
			if (heathCell[5] == true) {
				healthyRedBloodCellCounter++;
			} else {
				sickRedBloodCellCounter++;
			}
			break;

		case "WhiteBloodCells":
			if (heathCell[5] == true) {
				healthyWhiteBloodCellCounter++;
			} else {
				redBloodCellCount++;
			}

			break;
		default:
			break;
		}

		// vecino 6
		switch (cellType[6]) {
		case "Plateles":
			if (heathCell[6] == true) {
				healthyPlateletCounter++;
			} else {
				sickPlateletCounter++;
			}
			break;

		case "RedBloodCells":
			if (heathCell[6] == true) {
				healthyRedBloodCellCounter++;
			} else {
				sickRedBloodCellCounter++;
			}
			break;

		case "WhiteBloodCells":
			if (heathCell[6] == true) {
				healthyWhiteBloodCellCounter++;
			} else {
				redBloodCellCount++;
			}

			break;
		default:
			break;
		}

		// vecino 7
		switch (cellType[7]) {
		case "Plateles":
			if (heathCell[7] == true) {
				healthyPlateletCounter++;
			} else {
				sickPlateletCounter++;
			}
			break;

		case "RedBloodCells":
			if (heathCell[7] == true) {
				healthyRedBloodCellCounter++;
			} else {
				sickRedBloodCellCounter++;
			}
			break;

		case "WhiteBloodCells":
			if (heathCell[7] == true) {
				healthyWhiteBloodCellCounter++;
			} else {
				redBloodCellCount++;
			}

			break;
		default:
			break;
		}

		amountPerCellType[0] = healthyPlateletCounter;
		amountPerCellType[1] = sickPlateletCounter;
		amountPerCellType[2] = healthyRedBloodCellCounter;
		amountPerCellType[3] = sickRedBloodCellCounter;
		amountPerCellType[4] = healthyWhiteBloodCellCounter;
		amountPerCellType[5] = redBloodCellCount;

		return amountPerCellType;
	}

	/*
	 * 
	 * Este metodo codifica numericamente la matriz ambiente a fin de identificar
	 * cada tipo de celula con un valor que pueda ser entendido por processing para
	 * la representacion grafica
	 */
	private static void encodeEnvironment(Cell[][] environment) {

		String typeCell = ""; // identifica el tipo de celula de ambiente[i,j]

		boolean healthCell;// identifica la salud de ambiebte[i][j]

		int pls = 0, ple = 0, grs = 0, gre = 0, gbs = 0, gbe = 0; // Contadores de celulas para cada iteracion
		/*
		 * Esos for recorren el ambiente y codifican la informacion de cada i,j en otra
		 * matriz
		 */
		for (int i = 0; i < numberRows; i++) {
			for (int j = 0; j < numberColumns; j++) {
				if (environment[i][j] != null) {
					typeCell = environment[i][j].getClass().getSimpleName();// captura el tipo de celula que se esta
					// evaluando
					healthCell = environment[i][j].isHealth(); // captura la salud de la celula que se esta evaluando
				} else {
					typeCell = "Empty space";
					healthCell = false;
				}

				switch (typeCell) {

				case "Plateles":

					if (healthCell == true) {
						graphicRepresentationOfTheEnvironment[i][j] = 0;
						pls++;
					} else {
						graphicRepresentationOfTheEnvironment[i][j] = 1;
						ple++;
					}
					break;
				case "RedBloodCells":

					if (healthCell == true) {
						graphicRepresentationOfTheEnvironment[i][j] = 2;
						grs++;
					} else {
						graphicRepresentationOfTheEnvironment[i][j] = 3;
						gre++;
					}

					break;

				case "WhiteBloodCells":

					if (healthCell == true) {
						graphicRepresentationOfTheEnvironment[i][j] = 4;
						gbs++;
					} else {
						graphicRepresentationOfTheEnvironment[i][j] = 5;
						gbe++;
					}

					break;
				case "Empty space":
					graphicRepresentationOfTheEnvironment[i][j] = 6;
					break;
				}
			}
		}

		int[] totalPopulation = new int[7];
		totalPopulation[0] = pls + ple + grs + gre + gbs + gbe;
		totalPopulation[1] = pls;
		totalPopulation[2] = ple;
		totalPopulation[3] = grs;
		totalPopulation[4] = gre;
		totalPopulation[5] = gbs;
		totalPopulation[6] = gbe;

		WriteReports(totalPopulation);
	}

	public int[][] getGraphicRepresentationOfTheEnvironment() {
		return graphicRepresentationOfTheEnvironment;
	}

	/*
	 * Este metodo se encarga de realizar el movimiento para todas las celulas
	 */
	private void MoveCells(Cell[][] environment) {
		/*
		 * / estas variables identifican que vecinos estan vacios o llenos 0 = lleno 1 =
		 * vacio
		 */

		int ramdonNumber = 0;

		for (int i = 0; i < numberRows; i++) {
			for (int j = 0; j < numberColumns; j++) {
				if (environment[i][j] != null) {

					ramdonNumber = (int) (Math.random() * 8);
					switch (ramdonNumber) {

					case 0:
						if (environment[(i - 1 + numberRows) % numberRows][(j - 1 + numberColumns)
								% numberColumns] == null) {

							environment[(i - 1 + numberRows) % numberRows][(j - 1 + numberColumns)
									% numberColumns] = environment[i][j];
							environment[i][j] = null;

						}
						break;

					case 1:
						if (environment[(i - 1 + numberRows) % numberRows][j] == null) {

							environment[(i - 1 + numberRows) % numberRows][j] = environment[i][j];
							environment[i][j] = null;

						}

						break;

					case 2:
						if (environment[(i - 1 + numberRows) % numberRows][(j + 1) % numberColumns] == null) {

							environment[(i - 1 + numberRows) % numberRows][(j + 1) % numberColumns] = environment[i][j];
							environment[i][j] = null;

						}

						break;

					case 3:
						if (environment[i][(j - 1 + numberColumns) % numberColumns] == null) {

							environment[i][(j - 1 + numberColumns) % numberColumns] = environment[i][j];
							environment[i][j] = null;

						}

						break;

					case 4:
						if (environment[i][(j + 1) % numberColumns] == null) {

							environment[i][(j + 1) % numberColumns] = environment[i][j];
							environment[i][j] = null;

						}
						break;

					case 5:
						if (environment[(i + 1) % numberRows][(j - 1 + numberColumns) % numberColumns] == null) {

							environment[(i + 1) % numberRows][(j - 1 + numberColumns)
									% numberColumns] = environment[i][j];
							environment[i][j] = null;
						}
						break;

					case 6:
						if (environment[(i + 1) % numberRows][j] == null) {

							environment[(i + 1) % numberRows][j] = environment[i][j];
							environment[i][j] = null;

						}
						break;

					case 7:
						if (environment[(i + 1) % numberRows][(j + 1) % numberColumns] == null) {

							if (Math.random() >= 0.5) {
								environment[(i + 1) % numberRows][(j + 1) % numberColumns] = environment[i][j];
								environment[i][j] = null;

							}
						}
						break;
					}
				}

			}
		}

	}

	/*
	 * Este metodo recibe como parametro la celula que la madre celula crea y la
	 * implanta en la matriz de poblacion
	 */

	private void NewCell(String cellTye, int j) {

		double myeloblasticSyndrome = 0.0; // numero pseudoaleatorio para ver si se genera el sindrome
		double healthNewCell = Math.random(); // Pseudoalatorio que se comparara contra el parametro de salud para
		boolean health = true;
		int pocitionNull = -1;
		if (healthNewCell > probabilityOfInitialPopulationHealth) { // healthNewCell > 1 - probabilityOfInitialPopulationHealth
			health = true;
		} else {
			health = false;
		}

		switch (cellTye) {

		// "Platele"
		case "Platele":

			if (Environment[0][j] == null) {
				Environment[0][j] = new Plateles(health, timeOfLifePlatetes, minimumNumberCellsNoLackOfIndividualsPL,
						iterationsToSendSignalPL);
			} else {
				for (int i = 0; i < numberRows; i++) {
					if (Environment[i][j] == null) {
						pocitionNull = i;
						i = numberRows;
					}
				}

				if (numberRows > 0) {
					for (int y = pocitionNull; y > 0; y--) {
						Environment[y][j] = Environment[y - 1][j];
						Environment[y - 1][j] = null;
					}

					if (Environment[0][j] == null) {
						Environment[0][j] = new Plateles(health, timeOfLifePlatetes,
								minimumNumberCellsNoLackOfIndividualsPL, iterationsToSendSignalPL);
					}
				} else {
					NewCell(cellTye, j);
				}
			}

			break;

		case "WhiteBloodCell":

			if (Environment[0][j] == null) {
				if (health == true) {
					Environment[0][j] = new WhiteBloodCells(health, timeOfLifeWhiteBloodCells,
							timeOfLifeWhiteBloodCellSick, minimumNumberCellsNoLackOfIndividualsGBS,
							iterationsToSendSignalGBS, minimumNumberCellsNoLackOfIndividualsGBS,
							iterationsToSendSignalGBS);
				} else {
					myeloblasticSyndrome = Math.random();
					if (myeloblasticSyndrome >= 0 && myeloblasticSyndrome <= probabiltyOfMyeloblasticSyndrome) {

						for (int i = 0; i < numberColumns; i++) {
							/*
							 * Si se encuentra un espacio vacio se siembra la celula madre y se termina el
							 * ciclo
							 */
							if (BoneMarrow[i] == null) {
								BoneMarrow[i] = new MotherCells(false, maximumNumberOfReproductionMyeloblasticSyndrome);
								BoneMarrow[i].isWhiteCell = true;
								i = numberColumns;
							}
						}

					} else {
						Environment[0][j] = new WhiteBloodCells(health, timeOfLifeWhiteBloodCells,
								timeOfLifeWhiteBloodCellSick, minimumNumberCellsNoLackOfIndividualsGBS,
								iterationsToSendSignalGBS, minimumNumberCellsNoLackOfIndividualsGBS,
								iterationsToSendSignalGBS);
					}
				}

			} else {
				for (int i = 0; i < numberRows; i++) {
					if (Environment[i][j] == null) {
						pocitionNull = i;
						i = numberRows;
					}
				}

				if (numberRows > 0) {
					for (int y = pocitionNull; y > 0; y--) {
						Environment[y][j] = Environment[y - 1][j];
						Environment[y - 1][j] = null;
					}

					if (Environment[0][j] == null) {

						if (health == true) {
							Environment[0][j] = new WhiteBloodCells(health, timeOfLifeWhiteBloodCells,
									timeOfLifeWhiteBloodCellSick, minimumNumberCellsNoLackOfIndividualsGBS,
									iterationsToSendSignalGBS, minimumNumberCellsNoLackOfIndividualsGBS,
									iterationsToSendSignalGBS);
						} else {
							myeloblasticSyndrome = Math.random();
							if (myeloblasticSyndrome >= 0 && myeloblasticSyndrome <= probabiltyOfMyeloblasticSyndrome) {

								for (int i = 0; i < numberColumns; i++) {
									/*
									 * Si se encuentra un espacio vacio se siembra la celula madre y se termina el
									 * ciclo
									 */
									if (BoneMarrow[i] == null) {
										BoneMarrow[i] = new MotherCells(false,
												maximumNumberOfReproductionMyeloblasticSyndrome);
										;
										BoneMarrow[i].isWhiteCell = true;
										i = numberColumns;
									}
								}

							} else {
								Environment[0][j] = new WhiteBloodCells(health, timeOfLifeWhiteBloodCells,
										timeOfLifeWhiteBloodCellSick, minimumNumberCellsNoLackOfIndividualsGBS,
										iterationsToSendSignalGBS, minimumNumberCellsNoLackOfIndividualsGBS,
										iterationsToSendSignalGBS);
							}
						}

					}
				} else {
					NewCell(cellTye, j);
				}
			}

			break;

		case "RedBloodCell":
			if (Environment[0][j] == null) {
				Environment[0][j] = new RedBloodCells(health, timeOfLifeRedBloodCells,
						minimumNumberCellsNoLackOfIndividualsGR, iterationsToSendSignalGR);
			} else {
				for (int i = 0; i < numberRows; i++) {
					if (Environment[i][j] == null) {
						pocitionNull = i;
						i = numberRows;
					}
				}

				if (numberRows > 0) {
					for (int y = pocitionNull; y > 0; y--) {
						Environment[y][j] = Environment[y - 1][j];
						Environment[y - 1][j] = null;
					}

					if (Environment[0][j] == null) {
						Environment[0][j] = new RedBloodCells(health, timeOfLifeRedBloodCells,
								minimumNumberCellsNoLackOfIndividualsGR, iterationsToSendSignalGR);
					}
				} else {
					NewCell(cellTye, j);
				}
			}
			break;

		case "Proliferate":
			/*
			 * Si hay proloiferacion, se verifica que hallan espacios vacios en la medula
			 * osea.
			 * 
			 * Si hay se siembra la nueva celula en la medula y si no los hay no se siembra
			 * en el amniente sangre.
			 */

			Proliferation();
			break;

		case "MotherCellApoptosis":
			/*
			 * La celula muere y es eliminada de la medula osea
			 */
			BoneMarrow[j] = null;
			break;

		case "WhiteBloodCellSick":

			if (Environment[0][j] == null) {
				Environment[0][j] = new WhiteBloodCells(false, timeOfLifeWhiteBloodCells, timeOfLifeWhiteBloodCellSick,
						minimumNumberCellsNoLackOfIndividualsGBS, iterationsToSendSignalGBS,
						minimumNumberCellsNoLackOfIndividualsGBS, iterationsToSendSignalGBS);
			} else {
				for (int i = 0; i < numberRows; i++) {
					if (Environment[i][j] == null) {
						pocitionNull = i;
						i = numberRows;
					}
				}

				if (numberRows > 0) {
					for (int y = pocitionNull; y > 0; y--) {
						Environment[y][j] = Environment[y - 1][j];
						Environment[y - 1][j] = null;
					}

					if (Environment[0][j] == null) {
						Environment[0][j] = new WhiteBloodCells(false, timeOfLifeWhiteBloodCells,
								timeOfLifeWhiteBloodCellSick, minimumNumberCellsNoLackOfIndividualsGBS,
								iterationsToSendSignalGBS, minimumNumberCellsNoLackOfIndividualsGBS,
								iterationsToSendSignalGBS);
					}
				} else {
					NewCell(cellTye, j);
				}
			}

			break;
		}
	}

	private void NormalCellDivision(Cell[][] environment) {

		for (int i = 0; i < numberColumns; i++) {
			if (BoneMarrow[i] != null) {
				NewCell(BoneMarrow[i].CellularDivision("Normal"), i);
			}

		}
	}
	/*
	 * Este metodo verifica si hay espacio para una nueva celula madre y si es asi
	 * la siembra
	 */

	private void Proliferation() {
		for (int i = 0; i < numberColumns; i++) {
			/*
			 * Si se encuentra un espacio vacio se siembra la celula madre y se termina el
			 * ciclo
			 */
			if (BoneMarrow[i] == null) {
				BoneMarrow[i] = new MotherCells(true, maximumNumberOfReproductionsMotherCells);
			}
		}
	}

	private static void WriteReports(int[] totalPopulation) {
		String pathReport = path.substring(0, path.lastIndexOf('\\'));
		pathReport = pathReport + "\\" + "BloodCount1.csv";
		File BlooCount = new File(pathReport);
		BufferedWriter bw = null;
		if (BlooCount.exists()) {
			// El fichero ya existe
			try {

				FileWriter TextOut = new FileWriter(BlooCount, true);
				TextOut.write(IterationsCount + "," + totalPopulation[1] + "," + totalPopulation[2] + ","
						+ totalPopulation[3] + "," + totalPopulation[4] + "," + totalPopulation[5] + ","
						+ totalPopulation[6] + "," + totalPopulation[0] + " \n");
				TextOut.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// El fichero no existe y hay que crearlo
			try {
				bw = new BufferedWriter(new FileWriter(BlooCount));
				bw.write(
						"Iteration,Healthy Platelets,Sick Platelets,Healthy Red Blood Cells,Sick Red Bllod Cells,Healthy White Blood Cells,Sick White Blood Cells, Total Cells");
				bw.newLine();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
