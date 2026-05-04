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

/**
 * Represents Jail Strategy A.
 * 
 * Strategy A uses a Get Out of Jail Free card immediately if the player has one.
 * If the player does not have a card, the simulation assumes the player pays $50
 * and leaves Jail immediately on the next turn.
 */
public class JailExitStrategyA {

	/**
	 * Gets the name of this jail strategy.
	 *
	 * @return the strategy name
	 */
	public String getName() {
		return "Strategy A - Immediate Exit";
	}

	/**
	 * Tells the TurnEngine whether this strategy tries to roll doubles before
	 * leaving Jail.
	 *
	 * @return false because Strategy A leaves Jail immediately
	 */
	public boolean triesForDoubles() {
		return false;
	}
}
