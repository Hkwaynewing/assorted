package apt;

import assorted.afterpay.TokenCard;
import assorted.afterpay.TokenCard.Card;
import assorted.afterpay.TokenCard.Wallet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenCardTest {

    @Test
    void canPurchase() {
        TokenCard newSolution = new TokenCard();

        newSolution.backColorStore.redTo = 1;

        Card card = new Card();
        card.redTo = 3;
        card.blueTo = 2;
        card.backCo = "blue";

        Wallet wallet = new Wallet();
        wallet.redTo = 2;
        wallet.blueTo = 2;

        assertTrue(newSolution.canPurchase(card, wallet));
    }

    @Test
    void cannotPurchase() {
        TokenCard newSolution = new TokenCard();

        Card card = new Card();
        card.redTo = 3;
        card.blueTo = 2;

        Wallet wallet = new Wallet();
        wallet.redTo = 2;
        wallet.blueTo = 2;

        assertFalse(newSolution.canPurchase(card, wallet));

    }

    @Test
    void purchase_cannot() {
        TokenCard newSolution = new TokenCard();

        Card card = new Card();
        card.redTo = 3;
        card.blueTo = 2;

        Wallet wallet = new Wallet();
        wallet.redTo = 2;
        wallet.blueTo = 2;

        assertFalse(newSolution.purchase(card, wallet));
    }

    @Test
    void purchase_can() {
        TokenCard newSolution = new TokenCard();
        newSolution.backColorStore.redTo = 1;

        Card card = new Card();
        card.redTo = 1;
        card.blueTo = 2;
        card.backCo = "blue";

        Wallet wallet = new Wallet();
        wallet.redTo = 2;
        wallet.blueTo = 2;

        assertTrue(newSolution.purchase(card, wallet));
        assertEquals(2, wallet.redTo);
        assertEquals(0, wallet.blueTo);
        assertEquals(0, wallet.whiteTo);
        assertEquals(1, newSolution.backColorStore.blueTo);

    }
}