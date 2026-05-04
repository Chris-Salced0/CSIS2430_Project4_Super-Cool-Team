/*
 * Team Name:    SuperCoolTeam
 * Team Members: Tayler Covington-Holler (Communication Lead)
 *               Cameron Dickinson (Implementation Lead)
 *               Christopher Salcedo Cardenas (Verification Lead)
 *               Maxwell Jardine (Implementation Lead)
 * Course:       CS 2430-501
 * Project:      Project 4 – Capstone: Monopoly Simulation
 * Primary Author: Maxwell Jardine
 */

package project4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Runs the Monopoly simulations and saves the results.
 * 
 * This class runs both jail strategies. It runs each strategy 10 times and saves
 * the board results at each required turn amount.
 */
public class Simulation {

	private static final int[] CHECKPOINTS = {1000, 10000, 100000, 1000000};
	private static final int NUMBER_OF_RUNS = 10;
	private static final String OUTPUT_FOLDER = "output";

	/**
	 * Runs the full simulation from this class.
	 *
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		runAllSimulations();
	}

	/**
	 * Runs all simulations for Strategy A and Strategy B.
	 */
	public static void runAllSimulations() {
		System.out.println("Programming Project 4 - Monopoly Simulation");
		System.out.println("Team: SuperCoolTeam");
		System.out.println();

		JailExitStrategyA strategyA = new JailExitStrategyA();
		JailExitStrategyB strategyB = new JailExitStrategyB();

		runStrategy(strategyA.getName(), strategyA.triesForDoubles());
		runStrategy(strategyB.getName(), strategyB.triesForDoubles());

		System.out.println();
		System.out.println("All simulations complete.");
		System.out.println("CSV files were saved in the '" + OUTPUT_FOLDER + "' folder.");
	}

	/**
	 * Runs one jail strategy for all 10 runs.
	 *
	 * @param strategyName the name of the strategy
	 * @param tryForDoubles true if the strategy tries to roll doubles in jail
	 */
	private static void runStrategy(String strategyName, boolean tryForDoubles) {
		System.out.println("Running " + strategyName + "...");

		for (int runNumber = 1; runNumber <= NUMBER_OF_RUNS; runNumber++) {
			Board board = new Board();
			TurnEngine engine = new TurnEngine(board, tryForDoubles);

			int previousCheckpoint = 0;

			for (int checkpoint : CHECKPOINTS) {
				int turnsToRun = checkpoint - previousCheckpoint;

				for (int i = 0; i < turnsToRun; i++) {
					engine.turn();
				}

				saveResults(board, strategyName, runNumber, checkpoint);
				printShortSummary(board, strategyName, runNumber, checkpoint);

				previousCheckpoint = checkpoint;
			}
		}
	}

	/**
	 * Saves the current board results into a CSV file.
	 *
	 * @param board the board with the landing counts
	 * @param strategyName the strategy being used
	 * @param runNumber the current run number
	 * @param totalTurns the number of turns completed
	 */
	private static void saveResults(Board board, String strategyName, int runNumber, int totalTurns) {
		File folder = new File(OUTPUT_FOLDER);

		if (!folder.exists()) {
			folder.mkdirs();
		}

		String cleanStrategyName = strategyName.replace(" ", "_").replace("-", "");
		String fileName = OUTPUT_FOLDER + "/" + cleanStrategyName + "_Run" + runNumber + "_" + totalTurns + ".csv";

		try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
			writer.println("Strategy,Run,Turns,Index,Square,Type,Count,Percentage");

			for (Square square : board.board) {
				double percentage = (square.landCount * 100.0) / totalTurns;

				writer.printf("%s,%d,%d,%d,%s,%s,%d,%.6f%n",
						escapeCsv(strategyName),
						runNumber,
						totalTurns,
						square.id,
						escapeCsv(square.name),
						escapeCsv(square.type),
						square.landCount,
						percentage);
			}
		} catch (IOException e) {
			System.out.println("Error saving file: " + fileName);
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Prints the top 5 landed-on squares for a run.
	 *
	 * @param board the board with the landing counts
	 * @param strategyName the strategy being used
	 * @param runNumber the current run number
	 * @param totalTurns the number of turns completed
	 */
	private static void printShortSummary(Board board, String strategyName, int runNumber, int totalTurns) {
		System.out.println();
		System.out.println(strategyName + " | Run " + runNumber + " | " + totalTurns + " turns");
		System.out.println("Top 5 landed-on squares:");

		Square[] sortedSquares = board.board.toArray(new Square[0]);

		for (int i = 0; i < sortedSquares.length - 1; i++) {
			for (int j = i + 1; j < sortedSquares.length; j++) {
				if (sortedSquares[j].landCount > sortedSquares[i].landCount) {
					Square temp = sortedSquares[i];
					sortedSquares[i] = sortedSquares[j];
					sortedSquares[j] = temp;
				}
			}
		}

		for (int i = 0; i < 5; i++) {
			Square square = sortedSquares[i];
			double percentage = (square.landCount * 100.0) / totalTurns;

			System.out.printf("%d. %s - %d landings - %.4f%%%n",
					i + 1,
					square.name,
					square.landCount,
					percentage);
		}
	}

	/**
	 * Makes text safe to put in a CSV file.
	 *
	 * @param value the text being written
	 * @return the CSV-safe text
	 */
	private static String escapeCsv(String value) {
		if (value.contains(",") || value.contains("\"")) {
			value = value.replace("\"", "\"\"");
			return "\"" + value + "\"";
		}

		return value;
	}
}
