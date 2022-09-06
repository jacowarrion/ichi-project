package com.example.ichiproject;

import com.example.ichiproject.cards.Card;

import org.junit.Test;

public class TestGameManager {

    public static GameManager turnManager = new GameManager();

    @Test
    public void drawCard(){
        GameManager gm = new GameManager();

        Player p1 = new Player(1,"Player1");
        Player p2 = new Player(2,"Player2");

        gm.addPlayer(p1);
        gm.addPlayer(p2);

        gm.init();

        gm.nextTurn();

        Card p1Card = p1.selectCard(1);
        gm.playCard(p1Card);
        p1.play(1);

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
        p2.play(1);

    }

}
