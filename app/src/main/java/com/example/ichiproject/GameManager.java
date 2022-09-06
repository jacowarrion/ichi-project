package com.example.ichiproject;

import com.example.ichiproject.cards.Card;
import com.example.ichiproject.enums.EnCardColor;
import com.example.ichiproject.enums.EnCardEffect;

import java.util.ArrayList;

public class GameManager {

    private final CardManager _cardManager = new CardManager();
    private final ArrayList<Player> _players = new ArrayList<>();
    private int _currentRotation = 1;
    private int _currentPlayer = 0;
    private EnCardColor _currentColor = EnCardColor.NULL;

    public GameManager() {
    }

    /**
     * Aggiunge un giocatore
     *
     * @param newPlayer Giocatore
     * @return Giocatore inserito correttamente
     */
    public boolean addPlayer(Player newPlayer) {
        return _players.add(newPlayer);
    }

    public void init() {
        _cardManager.initDeck();
        _currentColor = _cardManager.getLastCardPlayed().getColor();
        _players.forEach(player -> player.draw(5, _cardManager));
    }

    /**
     * Risolve l'effetto della carta e da il comando al giocatore successivo
     */
    public void nextTurn() {
        Card lastPlayed = _cardManager.getLastCardPlayed();
        switch (lastPlayed.getEffect()) {
            case SKIP: {
                _currentPlayer = Math.abs(_currentPlayer + _currentRotation) % _players.size();
                _currentPlayer = Math.abs(_currentPlayer + _currentRotation) % _players.size();
                _players.get(_currentPlayer).draw(1, _cardManager);
            }
            break;
            case REVERSE: {
                _currentRotation = _currentRotation * -1;
                _currentPlayer = Math.abs(_currentPlayer + _currentRotation) % _players.size();
                _players.get(_currentPlayer).draw(1, _cardManager);
            }
            break;
            case DRAW_TWO: {
                _currentPlayer = Math.abs(_currentPlayer + _currentRotation) % _players.size();
                _players.get(_currentPlayer).draw(2, _cardManager);
                _currentPlayer = Math.abs(_currentPlayer + _currentRotation) % _players.size();
            }
            break;
            case WILD: {
                if (lastPlayed.isEffectActive()) {
                    _currentColor = lastPlayed.getColor();
                }
                _currentPlayer = Math.abs(_currentPlayer + _currentRotation) % _players.size();
                _players.get(_currentPlayer).draw(1, _cardManager);
            }
            break;
            case WILD_DRAW_FOUR: {
                if (lastPlayed.isEffectActive()) {
                    _currentColor = lastPlayed.getColor();
                }
                _currentPlayer = Math.abs(_currentPlayer + _currentRotation) % _players.size();
                _currentPlayer = Math.abs(_currentPlayer + _currentRotation) % _players.size();
                _players.get(_currentPlayer).draw(1, _cardManager);
            }
            break;
            case NULL: {
                _currentPlayer = Math.abs(_currentPlayer + _currentRotation) % _players.size();
                _players.get(_currentPlayer).draw(1, _cardManager);
            }
            break;
            default:
                break;
        }
    }

    public void playCard(Card card) {
        if (!canPlayCard(card)) return;
        if (card.isWildCard()) {
            _currentColor = card.getColor();
            if (card.getEffect().equals(EnCardEffect.WILD_DRAW_FOUR)) {

            }
        }
        _cardManager.playCard(card);
    }

    /**
     * Controlla se una carta può essere giocata in questo turno
     *
     * @param card Carta da giocare
     * @return boolean
     */
    public boolean canPlayCard(Card card) {
        if (card.isWildCard()) return true;
        if (_currentColor.equals(card.getColor())) return true;
        final Card lastPlayed = _cardManager.getLastCardPlayed();
        return lastPlayed.getNumber() == card.getNumber();
    }

    public Player getCurrentPlayer() {
        return _players.get(_currentPlayer % _players.size());
    }

}
