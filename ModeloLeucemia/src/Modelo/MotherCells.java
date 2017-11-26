package Modelo;

import java.util.Random;

public class MotherCells implements Cell {

	boolean healt;
	int replication = 0;
	int proliferation = 0;
	int maximumNumberOfReproductionsMotherCells = 0;
	int divisionCount = 0;
	boolean isWhiteCell = false;

	public boolean isHealt() {
		return healt;
	}

	public void setHealt(boolean healt) {
		this.healt = healt;
	}

	public int getReplication() {
		return replication;
	}

	public void setReplication(int replication) {
		this.replication = replication;
	}

	public int getProliferation() {
		return proliferation;
	}

	public void setProliferation(int proliferation) {
		this.proliferation = proliferation;
	}

	public int getMaximumNumberOfReproductionsMotherCells() {
		return maximumNumberOfReproductionsMotherCells;
	}

	public void setMaximumNumberOfReproductionsMotherCells(int maximumNumberOfReproductionsMotherCells) {
		this.maximumNumberOfReproductionsMotherCells = maximumNumberOfReproductionsMotherCells;
	}

	public int getDivisionCount() {
		return divisionCount;
	}

	public void setDivisionCount(int divisionCount) {
		this.divisionCount = divisionCount;
	}

	public boolean isWhiteCell() {
		return isWhiteCell;
	}

	public void setWhiteCell(boolean isWhiteCell) {
		this.isWhiteCell = isWhiteCell;
	}

	public MotherCells(boolean healt, int maximumNumberOfReproductionsMotherCells) {
		super();
		this.healt = healt;
		this.maximumNumberOfReproductionsMotherCells = maximumNumberOfReproductionsMotherCells;
	}

	@Override
	public String ReceiveNeighbors(int[] quantityNeighbors, int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isHealth() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * Este es el metodo llamado para la creacion de nuevas celulas
	 * 
	 * El parametro solicitado indica que tipo de celula esta realizando la
	 * solicitud y de esa forma decidir que arbol de decision usar
	 */
	public String CellularDivision(String Request) {
		divisionCount++;

		String RetornedCell = "";

		if (divisionCount > maximumNumberOfReproductionsMotherCells) {
			RetornedCell = "MotherCellApoptosis";
		} else {
			switch (Request) {

			case "WhiteCell":
				RetornedCell = WhiteBloodCellDecisionTree();
				break;
			case "Platetes":
				RetornedCell = PlateletDecisionTree();
				break;
			case "RedCell":
				RetornedCell = NormalCellDivisionDecisionTree();
				break;
			case "Normal":
				RetornedCell = NormalCellDivisionDecisionTree();
				break;
			}

		}

		return RetornedCell;
	}

	/*
	 * Este metodo ejecuta el arbol de decision de las plaquetas
	 */
	private String PlateletDecisionTree() {
		/*
		 * Esta variable es la que almacena las probabilidades obtenidas para moverse
		 * entre cada etapa del arbol
		 * 
		 */

		double probability = Math.random();
		String CellCreated = "";
		/*
		 * ***********PRIMERA ETAPA DEL ARBOL ***** La primera etapa del arbol se ajusta
		 * a la funcion de probabilidad acumulada Mostrada en la tabla:
		 * _________________________ X | f(x) | F(X) | Linfoide | 0.005 | 0.005|
		 * Prolifera | 0.1 | 0.105| Mieloide | 0.895 | 1 | _________________________|
		 */
		if (probability >= 0 && probability <= 0.005) {

			/*
			 * ***** SEGUNDA ETAPA DEL ARBOL ****
			 * 
			 * Se ha creado una Celula Madre Linfoide
			 * 
			 * Cuando se tiene una Celula madre Linfoide, la probabilidad de generacion d
			 * eun globulo blanco es de 1
			 * 
			 */

			CellCreated = "WhiteBloodCell";
		} else {
			/*
			 * ***********PRIMERA ETAPA DEL ARBOL ***** La primera etapa del arbol se ajusta
			 * a la funcion de probabilidad acumulada Mostrada en la tabla:
			 * _________________________ X | f(x) | F(X) | Linfoide | 0.005 | 0.005|
			 * Prolifera | 0.1 | 0.105| Mieloide | 0.895 | 1 | _________________________|
			 */

			if (probability > 0.005 && probability <= 0.105) {
				CellCreated = "Proliferate";
			} else {
				if (probability > 0.105) {
					/*
					 * ***** SEGUNDA ETAPA DEL ARBOL ****
					 * 
					 * Se ha creado una Celula Madre Mieloide
					 * 
					 * Cuando se tiene una CElula madre mieloide, la obtencion de un GR, PL o GB
					 * obedece a la funcion de probabilidad Aculumada mostrado en la siguiente
					 * tabla:
					 * 
					 * __________________ x | f(X) | F(X)| GR | 0.1 | 0.1 | GB | 0.1 | 0.2 | PL |
					 * 0.8 | 1 | ________________|
					 */

					probability = Math.random();

					if (probability >= 0 && probability <= 0.1) {
						CellCreated = "WhiteBloodCell";
						
					} else {
						if (probability > 0.1 && probability <= 0.5) {
							CellCreated = "RedBloodCell";
						} else {
							if (probability > 0.5) {
								CellCreated = "Platele";
							}
						}
					}
				}
			}
		}
		return CellCreated;
	}

	/*
	 * Este metodo ejecuta el arbol de decision de los globulos blancos
	 */

	private String WhiteBloodCellDecisionTree() {

		/*
		 * Esta variable es la que almacena las probabilidades obtenidas para moverse
		 * entre cada etapa del arbol
		 * 
		 */

		double probability = Math.random();
		String CellCreated = "";
		/*
		 * ***********PRIMERA ETAPA DEL ARBOL ***** La primera etapa del arbol se ajusta
		 * a la funcion de probabilidad acumulada Mostrada en la tabla:
		 * _________________________ X | f(x) | F(X) | Prolifera | 0.1 | 0.1 | Mieeloide
		 * | 0.4 | 0.5 | Linfoide | 0.5 | 1 | _________________________|
		 */

		if (probability >= 0 && probability <= 0.1) {
			// Prolifera

			CellCreated = "Proliferate";
		} else {
			/*
			 * ***********PRIMERA ETAPA DEL ARBOL ***** La primera etapa del arbol se ajusta
			 * a la funcion de probabilidad acumulada Mostrada en la tabla:
			 * _________________________ X | f(x) | F(X) | Prolifera | 0.1 | 0.1 | Mieeloide
			 * | 0.4 | 0.5 | Linfoide | 0.5 | 1 | _________________________|
			 */

			if (probability > 0.1 && probability <= 0.5) {
				// CM Mieloide

				/*
				 * ***** SEGUNDA ETAPA DEL ARBOL ****
				 * 
				 * Se ha creado una Celula Madre Mieloide
				 * 
				 * Cuando se tiene una CElula madre mieloide, la obtencion de un GR, PL o GB
				 * obedece a la funcion de probabilidad Aculumada mostrado en la siguiente
				 * tabla:
				 * 
				 * __________________ x | f(X) | F(X)| GR | 0.1 | 0.1 | PL | 0.1 | 0.2 | GB |
				 * 0.8 | 1 | ________________|
				 */

				probability = Math.random();

				if (probability >= 0 && probability <= 0.1) {
					CellCreated = "Platele";
				} else {
					if (probability > 0.1 && probability <= 0.4) {
						CellCreated = "RedBloodCell";
					} else {
						if (probability > 0.4) {
							CellCreated = "WhiteBloodCell";
						}
					}
				}
			} else {
				if (probability > 0.5) {
					// CM Linfoide

					CellCreated = "WhiteBloodCell";
				}
			}
		}

		return CellCreated;
	}

	private String NormalCellDivisionDecisionTree() {
		/*
		 * Esta variable es la que almacena las probabilidades obtenidas para moverse
		 * entre cada etapa del arbol
		 * 
		 */

		double probability = Math.random();
		String CellCreated = "";

		if (probability >= 0 && probability <= 0.005) {
			// CM Linfoide

			CellCreated = "WhiteBloodCell";
		} else {
			if (probability > 0.005 && probability < 0.105) {
				// prolifera
				CellCreated = "Proliferate";
			} else {
				if (probability > 0.105) {
					probability = Math.random();

					if (probability >= 0 && probability <= 0.009) {
						CellCreated = "WhiteBloodCell";
					} else {
						if (probability > 0.009 && probability <= 0.06) {
							CellCreated = "Platele";
						} else {
							if (probability > 0.06) {
								CellCreated = "RedBloodCell";
							}
						}
					}
				}
			}
		}
		return CellCreated;
	}

	/*
	 * Cuando la celula es un blanco enfermo solo crea celulas enfermas
	 */
	public String generateWhiteBloodCellSick() {
		divisionCount++;
		String RetornedCell = null;

		if (divisionCount > maximumNumberOfReproductionsMotherCells) {
			RetornedCell = "MotherCellApoptosis";;
		} else {
			RetornedCell = "WhiteBloodCellSick";

		}

		return RetornedCell;
	}

}
