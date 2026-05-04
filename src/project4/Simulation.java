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
 * Represents one Monopoly simulation run.
 * 
 * This class owns one board and one turn engine. It can run turns, save results,
 * and print the top landed-on squares.
 */
public class Simulation {
	private Board board;
	private TurnEngine engine;
	private String strategyName;
	private int runNumber;

	/**
	 * Creates one simulation.
	 *
	 * @param strategyName the name of the jail strategy
	 * @param tryForDoubles true if the player tries for doubles in jail
	 * @param runNumber the run number
	 */
	public Simulation(String strategyName, boolean tryForDoubles, int runNumber) {
		board = new Board();
		engine = new TurnEngine(board, tryForDoubles);
		this.strategyName = strategyName;
		this.runNumber = runNumber;
	}

	/**
	 * Runs the simulation for the given number of turns.
	 *
	 * @param turns the number of turns to run
	 */
	public void runTurns(int turns) {
		for (int i = 0; i < turns; i++) {
			engine.turn();
		}
	}

	/**
	 * Saves the board results to a CSV file.
	 *
	 * @param totalTurns the total turns completed so far
	 * @param outputFolder the folder where results are saved
	 */
	public void saveResults(int totalTurns, String outputFolder) {
		File folder = new File(outputFolder);

		if (!folder.exists()) {
			folder.mkdirs();
		}

		String cleanStrategyName = strategyName.replace(" ", "_").replace("-", "");
		String fileName = outputFolder + "/" + cleanStrategyName + "_Run" + runNumber + "_" + totalTurns + ".csv";

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
			System.out.println("Could not save results file.");
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Prints the top 5 landed-on squares.
	 *
	 * @param totalTurns the total turns completed so far
	 */
	public void printTopSquares(int totalTurns) {
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

		System.out.println();
		System.out.println(strategyName + " | Run " + runNumber + " | " + totalTurns + " turns");
		System.out.println("Top 5 landed-on squares:");

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
	 * Makes text safe for a CSV file.
	 *
	 * @param value the text being written
	 * @return the CSV-safe text
	 */
	private String escapeCsv(String value) {
		if (value.contains(",") || value.contains("\"")) {
			value = value.replace("\"", "\"\"");
			return "\"" + value + "\"";
		}

		return value;
	}
}
