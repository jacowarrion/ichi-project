package com.example.ichiproject.players;

import androidx.annotation.NonNull;

import com.example.ichiproject.GameManager;
import com.example.ichiproject.cards.Card;
import com.example.ichiproject.enums.EnCardColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Player {

    private final int _id;
    private final String _username;
    private final ArrayList<Card> _hand = new ArrayList<>();

    public Player(int id, String username) {
        this._id = id;
        this._username = username;
    }

    public Card[] draw(int count, GameManager gm) {
        Card[] drawnCards = gm.draw(count);
        Collections.addAll(_hand, drawnCards);
        return drawnCards;
    }

    public void playTurn(GameManager gameManager) {
        ArrayList<Card> playableCards = gameManager.getPlayableCardsForPlayer(this);
        if(playableCards.size() > 0) playCard(gameManager, playableCards.get(0));
        gameManager.nextTurn();
    }

    public void playCard(GameManager gameManager, Card card){
        switch (card.getEffect()){
            case WILD:
                card.setColor(EnCardColor.BLUE);
                break;
            case WILD_DRAW_FOUR:
                card.setColor(EnCardColor.RED);
                break;
            default:
                break;
        }
        gameManager.playCard(this, card);
    }

    public Card selectCard(int index) {
        return _hand.get(index);
    }

    public int get_id() {
        return _id;
    }

    public String get_username() {
        return _username;
    }

    public ArrayList<Card> getHand(){
        return _hand;
    }

    @NonNull
    @Override
    public String toString() {
        return "Player{" +
                "_id=" + _id +
                ", _username='" + _username + '\'' +
                ", _hand=" + _hand.toString() +
                '}';
    }

}
