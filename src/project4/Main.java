/*
 * Team Name:    SuperCoolTeam
 * Team Members: Tayler Covington-Holler (Communication Lead)
 *               Cameron Dickinson (Implementation Lead)
 *               Christopher Salcedo Cardenas (Verification Lead)
 *               Maxwell Jardine (Implemntation Lead)
 * Course:       CS 2430-501
 * Project:      Project 4 – Capstone: Monopoly Simulation
 * Primary Author: Cameron Dickinson
 */

package project4;


/*
 * This project focuses on using many different aspects we've 
 * learning throughout this class to turn a real world problem
 * into a computational model. For this example, a simulation
 * of the Monopoly board game will be used. That way we have an
 * easy and understandable way to answer some probability questions
 * about how the game works, mainly movement, chance and community
 * chest behavior, and jail rules.
 * 
 * Part 1 - Board and Data Structures
 * Define all 40 Monopoly squares, using data structures to track
 * the total moves, store name and type, track how many times
 * a square is landed on, and represent chance and community
 * chest decks as shuffled collections with discard and reshuffle 
 * abilities.
 * 
 * Part 2 - Turn Engine and Movement Rules
 * Implement a function/method that simulates one complete
 * player turn using classic rules. Must include:
 * Rolling two dice and moving forward the resulted amount
 * of times, tracking consecutive doubles, sending player to jail
 * on 3 doubles, resolving Chance and Community Chest cards, resolving
 * Go to Jail square, and incrementing the landing counter for the square you end on.
 * 
 * Part 3 - Two Jail-Exit Strategies
 * Implement both A and B strategies, Strategy A being that if you have
 * a Get out of Jail Free card, immediately use it, otherwise assume $50
 * have been paid and leave Jail on next turn. Strategy B being if you
 * have a Get out of Jail Free card, use it immediately, otherwise attempt
 * to roll doubles for up to 3 turns, if unsuccessful, assume $50 paid and
 * leave Jail on next turn
 * 
 * Part 4 - Batch Simulation and Data Collection
 * Run 10 simulations for each strategy, collecting landing
 * counts after 1,000, 10,000, 100,000 and 1,000,000 turns.
 * Output raw landing counts for each square, and percentage for 
 * each square
 * 
 * Part 5 - Comparative Output / Run Summary
 * Final output section for report. Constantly appearing squares,
 * do both strategies even out at 1,000,000 turns, and any anomalies.
 * 
 * Extra Cred - Visualization or Cross-Validation
 * Generate a visual plot/chart of landing percentages for 1,000,000
 * turns. 
 * 
 */
public class Main {

	public static void main(String[] args) {
		Board mainBoard = new Board();
		TurnEngine tg = new TurnEngine(mainBoard);	
		tg.turn();
	}
}
