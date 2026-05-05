# Monopoly Simulation Pseudocode

## Main

FOR each strategy:
    FOR run = 1 to 10:
        create Simulation(strategy)

        previousCheckpoint = 0

        FOR each checkpoint:
            turnsToRun = checkpoint - previousCheckpoint
            simulation.runTurns(turnsToRun)
            simulation.saveResults()

            IF checkpoint >= 100000:
                print top squares

            previousCheckpoint = checkpoint

---

## Simulation

FUNCTION runTurns(turns):
    LOOP turns times:
        engine.turn()

---

## TurnEngine

FUNCTION turn():

    IF jail == true:
        handleJailTurn()
        increment landCount
        increment totalMoves
        RETURN

    doubleRoll = 0
    turnOver = false

    WHILE turnOver == false:

        roll dice

        IF doubles:
            doubleRoll++
        ELSE:
            turnOver = true

        IF doubleRoll == 3:
            sendToJail()
            turnOver = true
        ELSE:
            moveForward(sum)
            resolveCurrentSquare()

            IF jail == true:
                turnOver = true

    increment landCount
    increment totalMoves

---

## Jail Handling

FUNCTION handleJailTurn():

    IF player has jail card:
        useJailCard()
        rollAndMoveAfterLeavingJail()
        RETURN

    IF tryForDoublesStrategy == false:
        leave jail
        rollAndMoveAfterLeavingJail()
        RETURN

    jailAttempts++

    roll dice

    IF doubles:
        leave jail
        moveForward()
        resolveCurrentSquare()

    ELSE IF jailAttempts >= 3:
        leave jail
        moveForward()
        resolveCurrentSquare()

---

## Movement

FUNCTION moveForward(steps):
    position = (position + steps) % 40

---

## Resolve Square

FUNCTION resolveCurrentSquare():

    keepResolving = true

    WHILE keepResolving:

        keepResolving = false
        currentSquare = board[position]

        IF "Go To Jail":
            sendToJail()

        ELSE IF "Chance":
            card = drawChanceCard()
            resolveCard(card)

        ELSE IF "Community":
            card = drawCommunityCard()
            resolveCard(card)

        IF player moved again:
            keepResolving = true

---

## Card Handling

FUNCTION resolveCard(card):

    IF type == "Other":
        discard

    IF type == "Hold":
        store jail card

    IF type == "Jail":
        sendToJail()

    IF type == "Advance":
        moveByCardName()
        discard

---

## Send to Jail

FUNCTION sendToJail():
    position = jail index
    jail = true
    reset counters
