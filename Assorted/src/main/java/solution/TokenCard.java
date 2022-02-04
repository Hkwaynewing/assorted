package solution;

/*
Part 1: can_purchase

We’re implementing a board game where a player can purchase various cards using colored tokens.
Each card has a cost associated with it like “3 blue tokens, 2 red tokens” or “1 white token”.
The initial token colors are ‘red’, ‘green’, ‘black’, ‘blue’, and ‘white’ and are not
interchangeable with each other.

Write a function or method called ‘can_purchase’ or ‘canPurchase’ that takes in a card and
a wallet containing counts of colored tokens and then returns true or false if the player can
purchase a card.


Part 2: purchase

Now that you have a can_purchase function, write a function or method called `purchase` that
can be used to actually purchase a card using a given wallet.


Part 3: discounts

After some play testing, we want to add a ‘discounts’ mechanic to purchases to help make the later
stages of the game more exciting. Each card needs to be changed to have a backing color that
matches one of the token colors. Then, when a player purchases a card, they’ll receive an
additional 1 token discount on any costs associated with that backing color.

For example:

Player A purchases a white-backed card and then wants to buy a second card that costs 2 white
tokens and 3 red tokens. Player A would only need to pay 1 white token and 3 red tokens thanks
to their discount. If they were to collect further white-backed cards then they could reduce
the white token cost down to zero (but no further).

card1
    backCo = white
    redTo = 1
    whiteTo = 2

card2
    backCo = blue
    whiteTo = 1 -> X
    blueTo = 2 -> 2

Part 4: gold tokens

Next, we’d like to add a ‘gold token’ that could be used in place of any other color token.
Gold tokens are collected and spent like other ones with the exception that there are no
gold-backed cards to provide discounts.

wallet has a golden token
once spent -> 0

*/
public class TokenCard {

    public final BackColorStore backColorStore;

    public TokenCard() {
        backColorStore = new BackColorStore();
    }

    public boolean purchase(Card card, Wallet wallet) {
        if (canPurchase(card, wallet)) {
            wallet.redTo -= card.redTo - backColorStore.redTo;
            wallet.blackTo -= card.blackTo - backColorStore.blackTo;
            wallet.blueTo -= card.blueTo - backColorStore.blueTo;
            wallet.greenTo -= card.greenTo - backColorStore.greenTo;
            wallet.whiteTo -= card.whiteTo - backColorStore.whiteTo;

            updateBackCoStore(card);

            return true;
        }
        return false;
    }

    private void updateBackCoStore(Card card) {
        //update backCo
        switch (card.backCo) {
            case "red":
                backColorStore.redTo += 1;
                break;
            case "black":
                backColorStore.blackTo += 1;
                break;
            case "blue":
                backColorStore.blueTo += 1;
                break;
            case "green":
                backColorStore.greenTo += 1;
                break;
            case "white":
                backColorStore.whiteTo += 1;
                break;
        }
    }

    public boolean canPurchase(Card card, Wallet wallet) {
        if (wallet.redTo + backColorStore.redTo - card.redTo >= 0) {

//            goldToken-1>=0
            if (wallet.blackTo + backColorStore.blackTo - card.blackTo >= 0) {
//                -1 + -2
                if (wallet.blueTo + backColorStore.blueTo - card.blueTo >= 0) {
                    if (wallet.greenTo + backColorStore.greenTo - card.greenTo >= 0) {
                        if (wallet.whiteTo + backColorStore.whiteTo - card.whiteTo >= 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static class BackColorStore {

        Integer redTo = 0;
        Integer blackTo = 0;
        Integer blueTo = 0;
        Integer greenTo = 0;
        Integer whiteTo = 0;
    }

    public static class Wallet {

        Integer redTo = 0;
        Integer blackTo = 0;
        Integer blueTo = 0;
        Integer greenTo = 0;
        Integer whiteTo = 0;

        Integer goldTo = 0;
    }

    public static class Card {

        String backCo;

        Integer redTo = 0;
        Integer blackTo = 0;
        Integer blueTo = 0;
        Integer greenTo = 0;
        Integer whiteTo = 0;
    }

    public class Token {

        String color;
    }

//    public enum Color{
//        RED,
//        BLACK,
//        BLUE,
//        GREEN,
//        WHITE
//    }
}
