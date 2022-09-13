package com.example.ichiproject;

import com.example.ichiproject.cards.Card;
import com.example.ichiproject.enums.EnCardColor;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class TestGameManager {

    public static GameManager turnManager = new GameManager();

    @Test
    public void drawCard(){
        GameManager gm = new GameManager();

        Player p1 = new Player(1,"Player1");
        Player p2 = new Player(2,"Player2");
        Player p3 = new Player(3,"Player3");
        Player p4 = new Player(4,"Player4");

        gm.addPlayer(p1);
        gm.addPlayer(p2);
        gm.addPlayer(p3);
        gm.addPlayer(p4);

        gm.init();

        while (gm.nextTurn()){
            Player p = gm.getCurrentPlayer();
            playerCycle(gm, p);
        }
        /*
        gm.nextTurn();

        Card p2Card = p2.selectCard(1);
        gm.playCard(p2Card);
        p2.play(1);

        gm.nextTurn();

        Card p1Card2 = p1.selectCard(1);
        gm.playCard(p1Card2);
        p1.play(1);

        gm.nextTurn();

        Card p2Card2 = p2.selectCard(1);
        gm.playCard(p2Card2);
        p2.play(1);*/

    }

    private void playerCycle(GameManager gm, Player p){
        System.out.println("Player: " + p.get_username());
        System.out.println("Carta nel field: " + gm.getLastPlayedCard());
        ArrayList<Card> playableCards = gm.getPlayableCardsForPlayer(p);
        System.out.println("Carte in mano:   " + p.getHand());
        System.out.println("Carte giocabili: " + playableCards);
        if(playableCards.isEmpty()){
            System.out.println("Carta Pescata:   " + Arrays.toString(p.draw(1, gm)));
            playableCards = gm.getPlayableCardsForPlayer(p);
            if(playableCards.isEmpty()){
                System.out.println(p.get_username() + " skipped the turn\n");
                return;
            }
        }
        int cardIndex = new Random().nextInt(playableCards.size());
        Card p1Card = playableCards.get(cardIndex);
        switch (p1Card.getEffect()){
            case WILD:
                p1Card.setColor(EnCardColor.BLUE);
                break;
            case WILD_DRAW_FOUR:
                p1Card.setColor(EnCardColor.RED);
                break;
            default:
                break;
        }
        gm.playCard(p, p1Card);
        System.out.println("Carta giocata:   " + p1Card + "\n");
    }

}
