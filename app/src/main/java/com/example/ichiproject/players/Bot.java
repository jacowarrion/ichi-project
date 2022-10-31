package com.example.ichiproject.players;

import com.example.ichiproject.GameManager;
import com.example.ichiproject.cards.Card;
import com.example.ichiproject.enums.EnCardColor;

import java.util.ArrayList;
import java.util.Random;

public class Bot extends Player{

    public Bot(int id, String username) {
        super(id, username);
    }

    public void playTurn(GameManager gameManager) {
        System.out.println("Player: " + get_username());
        System.out.println("Carta nel field: " + gameManager.getLastPlayedCard());
        ArrayList<Card> playableCards = gameManager.getPlayableCardsForPlayer(this);
        System.out.println("Carte in mano:   " + getHand());
        System.out.println("Carte giocabili: " + playableCards);
        if(playableCards.isEmpty()){
            Card drawnCard = draw(1, gameManager)[0];
            System.out.println("Carta pescata:   " + drawnCard);
            gameManager.canPlayCard(drawnCard);
            if(!gameManager.canPlayCard(drawnCard)){
                System.out.println(get_username() + " skipped the turn\n");
            } else {
                playCard(gameManager, drawnCard);
                System.out.println("Carta giocata:   " + drawnCard + "\n");
            }
            gameManager.nextTurn();
            return;
        }
        int cardIndex = new Random().nextInt(playableCards.size());
        Card p1Card = playableCards.get(cardIndex);
        playCard(gameManager, p1Card);
        System.out.println("Carta giocata:   " + p1Card + "\n");
        gameManager.nextTurn();
    }

    public boolean playCard(GameManager gameManager, Card card){
        switch (card.getEffect()){
            case WILD:
            case WILD_DRAW_FOUR:
                card.setColor(EnCardColor.getColors()[new Random().nextInt(4)]);
            default:
                break;
        }
        return gameManager.playCard(this, card);
    }

}
