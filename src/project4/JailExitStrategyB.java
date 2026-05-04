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
 * Represents Jail Strategy B.
 *
 * If the player does not have a Get Out Of Jail Free card, the player attempts 
 * to roll doubles for up to 3 turns. If unsuccessful after 3 attempts, the 
 * simulation assumes the player pays $50 and leaves Jail on the 4th turn.
 */
public class JailExitStrategyB {

    /**
     * Returns jail strategy name.
     *
     * @return Strategy B name
     */
    public String getName() {
        return "Strategy B - Try for Doubles";
    }

    /**
     * Tells the TurnEngine whether this strategy tries to roll doubles before
     * leaving Jail.
     *
     * @return true because Strategy B attempts to roll doubles
     */
    public boolean triesForDoubles() {
        return true;
    }
}
