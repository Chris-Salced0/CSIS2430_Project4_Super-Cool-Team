# Verification Plan – Monopoly Simulation

## 1. Purpose
This plan verifies that the Monopoly simulation correctly implements movement rules, jail behavior, card effects, deck handling, and statistical outputs.

## 2. Testing Strategy
We verify correctness using:
- Unit testing (individual methods)
- Integration testing (TurnEngine behavior)
- Data validation (counts and totals)
- Statistical validation (probability trends)

## 3. Unit Tests

### 3.1 Dice Rolling
- Verify each die returns values between 1–6
- Run multiple rolls to confirm variability

### 3.2 Movement
- Verify movement wraps around board:
  - Example: position 39 + 6 → 5
- Verify position always remains between 0–39

### 3.3 Doubles Rule
- Verify doubles increment counter
- Verify 3 consecutive doubles sends player to jail
- Verify counter resets after non-double

### 3.4 Jail Behavior

#### Strategy A (tryForDoublesStrategy = false)
- Player exits jail immediately
- Player rolls and moves in same turn

#### Strategy B (tryForDoublesStrategy = true)
- Player attempts doubles up to 3 turns
- Player exits jail on successful doubles
- Player exits after 3 failed attempts

### 3.5 Card Behavior
- "Advance" moves player correctly
- "Go Back 3 Spaces" works correctly
- Railroad cards move to next railroad
- Utility cards move to next utility
- "Go to Jail" sends player correctly
- "Hold" cards (Get Out of Jail) are stored and later used

### 3.6 Deck Behavior
- Cards are removed from draw pile
- Cards go to discard pile after use
- When draw pile is empty:
  - discard pile is reshuffled into draw pile

NOTE: "Hold" cards are NOT immediately discarded and should remain out of the deck until used.

### 3.7 Square Resolution
- Landing on "Chance" draws a Chance card
- Landing on "Community" draws a Community card
- Landing on "Go To Jail" sends player to jail
- Chain effects resolve correctly (loop logic works)

## 4. Integration Testing
Run simulation for 1,000 turns:
- No crashes
- Positions remain valid
- Counts increase correctly

## 5. Data Validation (CRITICAL)

Verify:
SUM of all landCounts == board.totalMoves

If false → simulation is incorrect

## 6. Statistical Validation
For large simulations:
- Jail should be among top squares
- Illinois Avenue should be high
- Railroads should be above average

## 7. Convergence Testing
- Results vary at 1,000 turns
- Results stabilize at 100,000+
- Results nearly identical at 1,000,000

## 8. Strategy Comparison
- Strategy B increases time in jail
- Nearby squares show slight variation
- Overall distributions converge

## 9. Edge Cases
- Empty deck reshuffles correctly
- No infinite loops in card chaining
- Jail loop behaves correctly
- No invalid indices occur

## 10. Final Checklist
- [ ] Dice valid
- [ ] Movement correct
- [ ] Jail logic correct
- [ ] Cards correct
- [ ] Deck reshuffle correct
- [ ] Counts match totalMoves
- [ ] Convergence observed
