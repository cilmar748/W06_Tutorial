package lab.poker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;
class BonusPolicyTest {
    @ParameterizedTest
    @CsvSource({
        "2H 3H 4H 5H 6H, true",
        "7C 7D 7H 9S 9C, true",
        "2C 3D 4H 5S 6C, false",
        "2H 5H 8H JH KH, false",
        "2C 5D 8H JS KC, false"
    })
    void bonusExamples(String cards, boolean expected) {
        assertEquals(expected, new BonusPolicy().qualifies(Hands.of(cards)));
    }
    // MC/DC for qualifies(): (isStraight && isFlush) || isFullHouse.
    // Pairs prove each of the three conditions independently decides the outcome.
    @Test
    void qualifiesMcDc() {
        BonusPolicy policy = new BonusPolicy();
        // isStraight && isFlush -> true (straight flush)
        assertTrue(policy.qualifies(Hands.of("2H 3H 4H 5H 6H")));
        // A=true,B=true,C=false already true above.
        // A independent (B=true,C=false): flip A false -> false (flush only)
        assertFalse(policy.qualifies(Hands.of("2H 5H 8H JH KH")));
        // B independent (A=true,C=false): flip B false -> false (straight only)
        assertFalse(policy.qualifies(Hands.of("2C 3D 4H 5S 6C")));
        // C independent (A=false,B=false): flip C true -> true (full house)
        assertTrue(policy.qualifies(Hands.of("7C 7D 7H 9S 9C")));
        // C independent (A=false,B=false): flip C false -> false (high card)
        assertFalse(policy.qualifies(Hands.of("2C 5D 8H JS KC")));
    }
}
