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

import java.util.ArrayList;
import java.util.List;

public class Board {
	public String[] names = {"Go", "Mediterranean Avenue", "Community Chest", "Baltic Avenue", "Income Tax", "Reading Railroad",
							 "Oriental Avenue", "Chance", "Vermont Avenue", "Connecticut Avenue", "Jail", "St. Charles Place",
							 "Electric Company", "States Avenue", "Virginia Avenue", "Pennsylvania Railroad", "St. James Place",
							 "Community Chest", "Tennessee Avenue", "New York Avenue", "Free Parking", "Kentucky Avenue", "Chance",
							 "Indiana Avenue", "Illinois Avenue", "B & O Railroad", "Atlantic Avenue", "Ventnor Avenue", "Water Works",
							 "Marvin Gardens", "Go To Jail", "Pacific Avenue", "North Carolina Avenue", "Community Chest", "Pennsylvania Avenue",
							 "Short Line", "Chance", "Park Place", "Luxury Tax", "Boardwalk"};
	public List<Square> board = new ArrayList<Square>();
	public List<Card> communityDeck = new ArrayList<Card>();
	public List<Card> chanceDeck = new ArrayList<Card>();;
	public int totalMoves;
	
	public Board() {
		
		//Set up board squares
		for (int i = 0; i < 40; i++) {
			if(names[i].equals("Chance")) {
				board.add(new Square(i, names[i], "Chance", 0));
			} else if(names[i].equals("Community Chest")) {
				board.add(new Square(i, names[i], "Community", 0));
			} else if(names[i].contains("Railroad") || names[i].contains("Line")) {
				board.add(new Square(i, names[i], "Railroad", 0));
			} else if(names[i].equals("Electric Company") || names[i].equals("Water Works")) {
				board.add(new Square(i, names[i], "Utility", 0));
			} else if(names[i].equals("Jail")) {
				board.add(new Square(i, names[i], "Jail", 0));
			} else if(names[i].equals("Go To Jail")) {
				board.add(new Square(i, names[i], "Go To Jail", 0));
			} else if(names[i].equals("Go")) {
				board.add(new Square(i, names[i], "Go", 0));
			} else {
				board.add(new Square(i, names[i], "Other", 0));
			}
		}
		
		//Set up Chance Deck
		chanceDeck.add(new Card(0, "Boardwalk", "Advance"));
		chanceDeck.add(new Card(1, "Go", "Advance"));
		chanceDeck.add(new Card(2, "Illinois Avenue", "Advance"));
		chanceDeck.add(new Card(3, "St. Charles Place", "Advance"));
		chanceDeck.add(new Card(4, "Railroad", "Advance"));
		chanceDeck.add(new Card(5, "Railroad", "Advance"));
		chanceDeck.add(new Card(6, "Utility", "Advance"));
		chanceDeck.add(new Card(7, "Out of Jail Free", "Hold"));
		chanceDeck.add(new Card(8, "Go Back 3 Spaces", "Advance"));
		chanceDeck.add(new Card(9, "Go to Jail", "Jail"));
		chanceDeck.add(new Card(10, "Reading Railroad", "Advance"));
		for(int i = 11; i < 16; i++) {
			chanceDeck.add(new Card(i, "Other", "Other"));
		}
		
		//Set up Community Deck
		communityDeck.add(new Card(0, "Go", "Advance"));
		communityDeck.add(new Card(1, "Out of Jail Free", "Hold"));
		communityDeck.add(new Card(2, "Go to Jail", "Jail"));
		for(int i = 3; i < 16; i++) {
			communityDeck.add(new Card(i, "Other", "Other"));
		}
	}
	
	//Gets index of any square name on the board.
	public int indexOf(String name) {
		for(int i = 0; i < board.size(); i++) {
			if(board.get(i).name == name) {
				return i;
			} 
		}
		return -1;
	}
}
