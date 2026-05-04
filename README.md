# SuperCoolTeam | CS 2430-501 | Capstone: Monopoly Simulation

- Tayler Covington-Holler (Communications Lead)
- Cameron Dickinson (Implementation Lead)
- Maxwell Jardine (Implementation Lead)
- Christopher Salcedo Cardenas (Verification Lead)

## Project Description
This project, written in Java, simulates the classic Monopoly board game (2018 U.S. rules, standard version) for the specific purpose of answering probability questions such as: Which squares are landed on the most? and How do different “get out of jail” strategies affect long-run landing probabilities? The simulation tracks landing frequencies over up to 1,000,000 turns across all 40 board squares under two jail-exit strategies (Strategy A: immediate exit; Strategy B: try for doubles up to 3 turns).

## How to Run

- Open the project in a Java IDE
- Navigate to Main.java
- Run Main.java


## Files

- Main.java — run program
- Board.java — board setup + card decks
- Square.java — square info (id, name, and type)
- Card.java — card data (Chance and Community Chest cards)
- TurnEngine.java — game logic
- JailExitStrategyA.java — immediate exit
- JailExitStrategyB.java — try for doubles 
- Simulation.java — run simulation


## Requirements

Java JDK 8 or higher
