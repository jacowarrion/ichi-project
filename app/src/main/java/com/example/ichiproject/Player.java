package com.example.ichiproject;

import androidx.annotation.NonNull;

import com.example.ichiproject.cards.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Player {

    private int _id;
    private String _username;
    private ArrayList<Card> _hand = new ArrayList<>();

    public Player(int id, String username) {
        this._id = id;
        this._username = username;
    }

    public Card[] draw(int count, GameManager gm) {
        Card[] drawnCards = gm.draw(count);
        Collections.addAll(_hand, drawnCards);
        return drawnCards;
    }

    public void play(int index) {
        _hand.remove(index);
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
