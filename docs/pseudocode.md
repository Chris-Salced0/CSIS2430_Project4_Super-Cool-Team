==================================================
MonopolySimulation (Main Controller)
==================================================

FUNCTION runAllSimulations()

    FOR EACH strategy IN [StrategyA, StrategyB]

        FOR simulation = 1 TO 10

            CALL resetGame(strategy)

            FOR turn = 1 TO 1,000,000

                CALL turnEngine.simulateTurn()

                IF turn == 1,000 OR 10,000 OR 100,000 OR 1,000,000
                    CALL outputResults(strategy, simulation, turn)

            END FOR

        END FOR

    END FOR

END FUNCTION


FUNCTION resetGame(strategy)

    CREATE new Player
    CREATE new Board
    CREATE new Deck AS chanceDeck
    CREATE new Deck AS communityDeck

    INITIALIZE both decks (shuffle cards)

    CREATE TurnEngine WITH:
        player
        board
        chanceDeck
        communityDeck
        strategy

END FUNCTION


FUNCTION outputResults(strategy, simulation, turn)

    PRINT strategy, simulation number, turn count

    FOR EACH square IN board

        count = square.landCount
        percentage = (count / turn) * 100

        PRINT square.name, count, percentage

    END FOR

END FUNCTION



==================================================
TurnEngine (Core Game Logic)
==================================================

FUNCTION simulateTurn()

    IF player.inJail == TRUE
        IF handleJail() == FALSE
            RETURN

    SET doublesCount = 0

    REPEAT

        roll1 = RANDOM(1–6)
        roll2 = RANDOM(1–6)
        steps = roll1 + roll2

        IF roll1 == roll2
            doublesCount = doublesCount + 1
        ELSE
            doublesCount = 0

        IF doublesCount == 3
            CALL sendToJail()
            RETURN

        CALL movePlayer(steps)

        CALL resolveSquare()

    UNTIL roll1 != roll2

END FUNCTION



FUNCTION movePlayer(steps)

    player.position = (player.position + steps) MOD 40

END FUNCTION



FUNCTION resolveSquare()

    square = board.getSquare(player.position)

    IF square IS GoToJailSquare
        CALL sendToJail()
        RETURN

    ELSE IF square IS ChanceSquare
        card = chanceDeck.drawCard()
        CALL applyCard(card)

    ELSE IF square IS CommunityChestSquare
        card = communityDeck.drawCard()
        CALL applyCard(card)

    END IF

    CALL board.incrementLanding(player.position)

END FUNCTION



FUNCTION applyCard(card)

    IF card.type == "MOVE"
        player.position = card.targetPosition

        IF board.getSquare(player.position) IS GoToJailSquare
            CALL sendToJail()
            RETURN

    ELSE IF card.type == "GO_TO_JAIL"
        CALL sendToJail()
        RETURN

    ELSE IF card.type == "GET_OUT_OF_JAIL"
        player.getOutOfJailCards = player.getOutOfJailCards + 1

    END IF

END FUNCTION



FUNCTION handleJail()

    IF player.getOutOfJailCards > 0
        player.getOutOfJailCards = player.getOutOfJailCards - 1
        player.inJail = FALSE
        RETURN TRUE

    IF strategy IS StrategyA
        player.inJail = FALSE
        RETURN TRUE

    ELSE IF strategy IS StrategyB

        player.jailTurns = player.jailTurns + 1

        roll1 = RANDOM(1–6)
        roll2 = RANDOM(1–6)

        IF roll1 == roll2
            player.inJail = FALSE
            RETURN TRUE

        IF player.jailTurns == 3
            player.inJail = FALSE
            RETURN TRUE

        RETURN FALSE

    END IF

END FUNCTION



FUNCTION sendToJail()

    player.position = JAIL_INDEX
    player.inJail = TRUE
    player.jailTurns = 0

END FUNCTION
