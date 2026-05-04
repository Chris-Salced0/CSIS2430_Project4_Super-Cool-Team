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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TurnEngine {
	public int[] results = {0, 0};
	public int doubleRoll = 0;
	public int playerPosition = 0;
	public boolean jail = false;
	public Board board;

	private boolean tryForDoublesStrategy;
	private int jailAttempts;

	private boolean hasChanceJailCard;
	private boolean hasCommunityJailCard;

	private List<Card> chanceDrawPile;
	private List<Card> chanceDiscardPile;
	private List<Card> communityDrawPile;
	private List<Card> communityDiscardPile;

	/**
	 * Makes a TurnEngine using Strategy A by default.
	 *
	 * @param board the Monopoly board
	 */
	public TurnEngine(Board board) {
		this(board, false);
	}

	/**
	 * Makes a TurnEngine with the selected jail strategy.
	 *
	 * @param board the Monopoly board
	 * @param tryForDoublesStrategy false for Strategy A, true for Strategy B
	 */
	public TurnEngine(Board board, boolean tryForDoublesStrategy) {
		this.board = board;
		this.tryForDoublesStrategy = tryForDoublesStrategy;

		jailAttempts = 0;
		hasChanceJailCard = false;
		hasCommunityJailCard = false;

		chanceDrawPile = new ArrayList<Card>(board.chanceDeck);
		chanceDiscardPile = new ArrayList<Card>();

		communityDrawPile = new ArrayList<Card>(board.communityDeck);
		communityDiscardPile = new ArrayList<Card>();

		Collections.shuffle(chanceDrawPile);
		Collections.shuffle(communityDrawPile);
	}

	/**
	 * Rolls two six-sided dice.
	 *
	 * @return the two dice values
	 */
	public int[] roll() {
		for (int i = 0; i < 2; i++) {
			results[i] = (int)(Math.random() * 6) + 1;
		}

		return results;
	}

	/**
	 * Runs one full player turn.
	 */
	public void turn() {
		if (jail) {
			handleJailTurn();
			board.board.get(playerPosition).incrementLandCount();
			board.totalMoves++;
			return;
		}

		boolean turnOver = false;
		doubleRoll = 0;

		while (!turnOver) {
			int[] roll = roll();
			int die1 = roll[0];
			int die2 = roll[1];
			boolean rolledDoubles = die1 == die2;

			if (rolledDoubles) {
				doubleRoll++;
			} else {
				turnOver = true;
			}

			if (doubleRoll == 3) {
				sendToJail();
				turnOver = true;
			} else {
				moveForward(die1 + die2);
				resolveCurrentSquare();

				if (jail) {
					turnOver = true;
				}
			}
		}

		board.board.get(playerPosition).incrementLandCount();
		board.totalMoves++;
	}

	/**
	 * Handles a turn that starts while the player is in jail.
	 */
	private void handleJailTurn() {
		if (hasChanceJailCard || hasCommunityJailCard) {
			useJailCard();
			rollAndMoveAfterLeavingJail();
			return;
		}

		if (!tryForDoublesStrategy) {
			jail = false;
			jailAttempts = 0;
			rollAndMoveAfterLeavingJail();
			return;
		}

		jailAttempts++;

		int[] roll = roll();
		int die1 = roll[0];
		int die2 = roll[1];

		if (die1 == die2) {
			jail = false;
			jailAttempts = 0;
			moveForward(die1 + die2);
			resolveCurrentSquare();
		} else if (jailAttempts >= 3) {
			jail = false;
			jailAttempts = 0;
			moveForward(die1 + die2);
			resolveCurrentSquare();
		}
	}

	/**
	 * Rolls and moves after the player gets out of jail.
	 */
	private void rollAndMoveAfterLeavingJail() {
		int[] roll = roll();
		moveForward(roll[0] + roll[1]);
		resolveCurrentSquare();
	}

	/**
	 * Moves the player forward around the board.
	 *
	 * @param spaces number of spaces to move
	 */
	private void moveForward(int spaces) {
		playerPosition = (playerPosition + spaces) % 40;
	}

	/**
	 * Resolves the square the player landed on.
	 */
	private void resolveCurrentSquare() {
		boolean keepResolving = true;

		while (keepResolving) {
			keepResolving = false;

			Square currentSquare = board.board.get(playerPosition);

			if (currentSquare.type.equals("Go To Jail")) {
				sendToJail();
			} else if (currentSquare.type.equals("Chance")) {
				Card card = drawChanceCard();
				resolveCard(card, true);
			} else if (currentSquare.type.equals("Community")) {
				Card card = drawCommunityCard();
				resolveCard(card, false);
			}

			if (!jail) {
				Square newSquare = board.board.get(playerPosition);

				if (newSquare.type.equals("Chance") && !currentSquare.type.equals("Chance")) {
					keepResolving = true;
				} else if (newSquare.type.equals("Community") && !currentSquare.type.equals("Community")) {
					keepResolving = true;
				} else if (newSquare.type.equals("Go To Jail")) {
					keepResolving = true;
				}
			}
		}
	}

	/**
	 * Resolves the card that was drawn.
	 *
	 * @param card the card that was drawn
	 * @param isChance true if it was a Chance card
	 */
	private void resolveCard(Card card, boolean isChance) {
		if (card.type.equals("Other")) {
			discardCard(card, isChance);
			return;
		}

		if (card.type.equals("Hold")) {
			if (isChance) {
				hasChanceJailCard = true;
			} else {
				hasCommunityJailCard = true;
			}
			return;
		}

		if (card.type.equals("Jail")) {
			discardCard(card, isChance);
			sendToJail();
			return;
		}

		if (card.type.equals("Advance")) {
			moveByCardName(card.name);
			discardCard(card, isChance);
		}
	}

	/**
	 * Moves the player based on the card name.
	 *
	 * @param cardName the card name or instruction
	 */
	private void moveByCardName(String cardName) {
		if (cardName.equals("Go Back 3 Spaces")) {
			playerPosition = (playerPosition + 37) % 40;
		} else if (cardName.equals("Railroad")) {
			playerPosition = nextRailroad(playerPosition);
		} else if (cardName.equals("Utility")) {
			playerPosition = nextUtility(playerPosition);
		} else {
			int newPosition = board.indexOf(cardName);

			if (newPosition != -1) {
				playerPosition = newPosition;
			}
		}
	}

	/**
	 * Finds the next railroad.
	 *
	 * @param currentPosition the player's current position
	 * @return the next railroad index
	 */
	private int nextRailroad(int currentPosition) {
		if (currentPosition < 5 || currentPosition >= 35) {
			return 5;
		} else if (currentPosition < 15) {
			return 15;
		} else if (currentPosition < 25) {
			return 25;
		} else {
			return 35;
		}
	}

	/**
	 * Finds the next utility.
	 *
	 * @param currentPosition the player's current position
	 * @return the next utility index
	 */
	private int nextUtility(int currentPosition) {
		if (currentPosition < 12 || currentPosition >= 28) {
			return 12;
		} else {
			return 28;
		}
	}

	/**
	 * Draws a Chance card.
	 *
	 * @return the card that was drawn
	 */
	private Card drawChanceCard() {
		if (chanceDrawPile.isEmpty()) {
			chanceDrawPile.addAll(chanceDiscardPile);
			chanceDiscardPile.clear();
			Collections.shuffle(chanceDrawPile);
		}

		return chanceDrawPile.remove(0);
	}

	/**
	 * Draws a Community Chest card.
	 *
	 * @return the card that was drawn
	 */
	private Card drawCommunityCard() {
		if (communityDrawPile.isEmpty()) {
			communityDrawPile.addAll(communityDiscardPile);
			communityDiscardPile.clear();
			Collections.shuffle(communityDrawPile);
		}

		return communityDrawPile.remove(0);
	}

	/**
	 * Puts a used card in the correct discard pile.
	 *
	 * @param card the used card
	 * @param isChance true if it was a Chance card
	 */
	private void discardCard(Card card, boolean isChance) {
		if (isChance) {
			chanceDiscardPile.add(card);
		} else {
			communityDiscardPile.add(card);
		}
	}

	/**
	 * Uses a Get Out of Jail Free card.
	 */
	private void useJailCard() {
		if (hasChanceJailCard) {
			hasChanceJailCard = false;
			chanceDiscardPile.add(new Card(7, "Out of Jail Free", "Hold"));
		} else if (hasCommunityJailCard) {
			hasCommunityJailCard = false;
			communityDiscardPile.add(new Card(1, "Out of Jail Free", "Hold"));
		}

		jail = false;
		jailAttempts = 0;
	}

	/**
	 * Sends the player to jail.
	 */
	private void sendToJail() {
		playerPosition = board.indexOf("Jail");
		jail = true;
		jailAttempts = 0;
		doubleRoll = 0;
	}
}
