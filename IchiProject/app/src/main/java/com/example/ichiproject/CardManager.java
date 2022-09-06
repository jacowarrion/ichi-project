package com.example.ichiproject;

import com.example.ichiproject.cards.Card;
import com.example.ichiproject.enums.EnCardColor;
import com.example.ichiproject.enums.EnCardEffect;

import java.util.ArrayList;
import java.util.Collections;

public class CardManager {

    private final ArrayList<Card> _deck = new ArrayList<>(0);
    private final ArrayList<Card> _fieldDeck = new ArrayList<>(0);

    public CardManager() {
    }

    /**
     * Inizializza il deck da 108 carte e lo mischia
     */
    public void initDeck() {

        for (EnCardColor color : EnCardColor.values()) {
            if (color == EnCardColor.NULL) {
                // Quatto copie delle WILD CARDS
                for (int i = 0; i < 4; i++) {
                    _deck.add(new Card(EnCardColor.NULL, EnCardEffect.WILD, -1));
                    _deck.add(new Card(EnCardColor.NULL, EnCardEffect.WILD_DRAW_FOUR, -1));
                }
                continue;
            }
            // 1 zero per colore
            _deck.add(new Card(color, EnCardEffect.NULL, 0));
            // 2 copie di ogni numero > 0 e action card per colore
            for (int i = 0; i < 2; i++) {
                for (int j = 1; j < 10; j++) {
                    _deck.add(new Card(color, EnCardEffect.NULL, j));
                }
                _deck.add(new Card(color, EnCardEffect.SKIP, -1));
                _deck.add(new Card(color, EnCardEffect.REVERSE, -1));
                _deck.add(new Card(color, EnCardEffect.DRAW_TWO, -1));
            }
        }
        Collections.shuffle(_deck);

        // prima carta numero
        int i = 0;
        while (!_deck.get(i).getEffect().equals(EnCardEffect.NULL)){
            i++;
        }
        _fieldDeck.add(_deck.remove(i));

    }

    public void playCard(Card card) {
        _fieldDeck.add(card);
    }


    /**
     * Pesca una o più carte
     *
     * @param count Numero di carte da pescare
     * @return Carte pescate
     */
    public Card[] draw(int count) {
        Card[] drawnCards = new Card[count];
        for (int i = 0; i < count; i++) {
            drawnCards[i] = _deck.remove(0);
        }
        return drawnCards;
    }

    /**
     * Ritorna l'ultima carta giocata
     * @return Carta
     */
    public Card getLastCardPlayed() {
        return _fieldDeck.get(_fieldDeck.size() - 1);
    }

}
