package com.example.ichiproject;

import com.example.ichiproject.cards.Card;
import com.example.ichiproject.enums.EnCardColor;
import com.example.ichiproject.enums.EnCardEffect;
import com.example.ichiproject.players.Player;

import java.util.ArrayList;
import java.util.Collections;

public class GameManager {

    private static final int MAX_PLAYERS = 4;

    private final ArrayList<Player> _players = new ArrayList<>();
    private int _currentRotation = 1;
    private int _currentPlayer = 0;

    private final ArrayList<Card> _deck = new ArrayList<>(0);
    private final ArrayList<Card> _fieldDeck = new ArrayList<>(0);

    /**
     * Inizializza il deck da 108 carte e lo mischia
     */
    public void initDeck() {

        for (EnCardColor color : EnCardColor.values()) {
            if (color == EnCardColor.NULL) {
                // Quatto copie delle WILD CARDS
                for (int i = 0; i < 4; i++) {
                    _deck.add(new Card(EnCardColor.NULL, EnCardEffect.WILD, -1));
                    _deck.add(new Card(EnCardColor.NULL, EnCardEffect.WILD_DRAW_FOUR, -2));
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
                _deck.add(new Card(color, EnCardEffect.SKIP, -3));
                _deck.add(new Card(color, EnCardEffect.REVERSE, -4));
                _deck.add(new Card(color, EnCardEffect.DRAW_TWO, -5));
            }
        }
        Collections.shuffle(_deck);

        // prima carta numero
        int i = 0;
        while (!_deck.get(i).getEffect().equals(EnCardEffect.NULL)) {
            i++;
        }
        _fieldDeck.add(_deck.remove(i));

    }

    /**
     * Pesca una o più carte
     *
     * @param count Numero di carte da pescare
     * @return Carte pescate
     */
    public Card[] draw(int count) {
        if (count > _deck.size()) {
            refillDeck();
        }
        Card[] drawnCards = new Card[count];
        for (int i = 0; i < count; i++) {
            drawnCards[i] = _deck.remove(0);
        }
        return drawnCards;
    }

    /**
     * Ritorna l'ultima carta giocata
     *
     * @return Carta
     */
    public Card getLastCardPlayed() {
        return _fieldDeck.get(_fieldDeck.size() - 1);
    }

    private void refillDeck() {
        System.out.println("REFILL");
        _deck.addAll(_fieldDeck);
        _fieldDeck.clear();
        _fieldDeck.add(_deck.get(0));
        Collections.shuffle(_fieldDeck);
    }

    public GameManager() {
    }

    /**
     * Aggiunge un giocatore
     *
     * @param newPlayer Giocatore
     * @return Giocatore inserito correttamente
     */
    public boolean addPlayer(Player newPlayer) {
        if (_players.size() >= MAX_PLAYERS) return false;
        return _players.add(newPlayer);
    }

    public void init() {
        initDeck();
        _players.forEach(player -> player.draw(5, this));
    }

    /**
     * Risolve l'effetto della carta e da il comando al giocatore successivo
     */
    public void nextTurn() {
        if (_players.size() <= 1) {
            System.out.println("fine");
            return;
        }
        Card lastPlayed = getLastCardPlayed();
        EnCardEffect lastEffect = lastPlayed.isEffectActive() ? lastPlayed.getEffect() : EnCardEffect.NULL;
        switch (lastEffect) {
            case SKIP:
                nextPlayer();
                nextPlayer();
            break;
            case WILD_DRAW_FOUR: {
                nextPlayer();
                _players.get(_currentPlayer).draw(4, this);
                nextPlayer();
            }
            break;
            case REVERSE: {
                _currentRotation = _currentRotation * -1;
                nextPlayer();
                //_players.get(_currentPlayer).draw(1, this);
            }
            break;
            case DRAW_TWO: {
                nextPlayer();
                _players.get(_currentPlayer).draw(2, this);
                nextPlayer();
            }
            break;
            case WILD:
            case NULL: {
                nextPlayer();
            }
            break;
            default:
                break;
        }
        lastPlayed.disableEffect();
        _players.get(_currentPlayer).playTurn(this);
    }

    private void nextPlayer() {
        _currentPlayer += _currentRotation;
        if (_currentPlayer >= _players.size()) _currentPlayer = 0;
        if (_currentPlayer < 0) _currentPlayer = _players.size() - 1;
    }

    public boolean playCard(Player player, Card card) {
        if (!canPlayCard(card)) return false;
        player.getHand().remove(card);
        if (player.getHand().isEmpty()) {
            System.out.println("\n\n\nVICOTRY\n\n\n");
            _players.remove(player);
        }
        return _fieldDeck.add(card);
    }

    public boolean isGameOver(){
        return _players.size() == 1;
    }

    /**
     * Controlla se una carta può essere giocata in questo turno
     *
     * @param card Carta da giocare
     * @return può essere giocata
     */
    public boolean canPlayCard(Card card) {
        if (card.isWildCard()) return true;
        final Card lastPlayed = getLastCardPlayed();
        if (lastPlayed.getColor().equals(card.getColor())) return true;
        return lastPlayed.getNumber() == card.getNumber();
    }

    /**
     * ritorna le carte giocabili che un player ha in mano
     *
     * @param player Player
     * @return le carte giocabili di un player
     */
    public ArrayList<Card> getPlayableCardsForPlayer(Player player) {
        ArrayList<Card> playerHand = player.getHand();
        ArrayList<Card> playableCards = new ArrayList<>();
        for (Card card : playerHand) {
            if (this.canPlayCard(card)) {
                playableCards.add(card);
            }
        }
        return playableCards;
    }

    /**
     * ritorna l'ultima carta giocata
     *
     * @return l'ultima carta giocata
     */
    public Card getLastPlayedCard() {
        return getLastCardPlayed();
    }

    /**
     * Ritorna il player corrente
     *
     * @return player corrente
     */
    public Player getCurrentPlayer() {
        return _players.get(_currentPlayer % _players.size());
    }

}
