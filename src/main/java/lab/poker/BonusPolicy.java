package lab.poker;
import java.util.List;

/** House rule: a straight flush or a full house earns a bonus. */
public class BonusPolicy {
    private final PokerHandEvaluator evaluator = new PokerHandEvaluator();
    public boolean qualifies(List<Card> hand) {
        boolean isStraight = evaluator.isStraight(hand);
        boolean isFlush = evaluator.isFlush(hand);
        boolean isFullHouse = evaluator.isFullHouse(hand);
        return (isStraight && isFlush) || isFullHouse;
    }
}
