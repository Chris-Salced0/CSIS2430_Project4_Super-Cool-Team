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

public class Card {
	public int id;
	public String name;
	public String type;
	
	public Card(int id, String name, String type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}
}
