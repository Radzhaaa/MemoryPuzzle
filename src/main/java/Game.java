import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    private List<Integer> cards;

    public Game() {
        cards = new ArrayList<>();
    }

    public void initializeGame(int numPairs) {
        for (int i = 0; i < numPairs; i++) {
            cards.add(i);
            cards.add(i);
        }
        Collections.shuffle(cards);
    }

    public int getCard(int index) {
        return cards.get(index);
    }

    public boolean isMatch(int card1, int card2) {
        return card1 == card2;
    }

    public int getNumCards() {
        return cards.size();
    }
}

