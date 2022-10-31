package com.example.ichiproject.game;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BlendMode;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ichiproject.GameManager;
import com.example.ichiproject.R;
import com.example.ichiproject.cards.Card;
import com.example.ichiproject.enums.EnCardColor;
import com.example.ichiproject.enums.EnCardEffect;
import com.example.ichiproject.players.Bot;
import com.example.ichiproject.players.Player;

import java.util.Random;

public class OfflineGameActivity extends AppCompatActivity {

    private GameManager gameManager;
    private Player player;
    private Card selectedCard = null;
    private View selectedCardView = null;
    private Card cardDrawn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_game);
        gameManager = new GameManager();
        player = new Player(1, "player1");
        gameManager.addPlayer(player);
        Bot bot1 = new Bot(2, "bot1");
        gameManager.addPlayer(bot1);
        gameManager.init();
        setCardOnField(gameManager.getLastPlayedCard());
        LinearLayout layout = findViewById(R.id.playerHand);

        this.setCardsOnPlayersHand(layout, player, false);

        LinearLayout layout2 = findViewById(R.id.bot1Hand);
        this.setCardsOnPlayersHand(layout2, bot1, true);

        // bottone per giocare la carta
        findViewById(R.id.playCardBtn).setOnClickListener(v -> {
            if (selectedCard != null) {
                if (gameManager.playCard(player, selectedCard)) {
                    layout.removeView(selectedCardView);
                    setCardOnField(selectedCard);
                    selectedCard = null;
                    selectedCardView = null;
                }
            }
        });
        findViewById(R.id.drawCardBtn).setOnClickListener(v -> {
            if (cardDrawn == null) {
                cardDrawn = player.draw(1, gameManager)[0];
                ((TextView) v).setText("Pass turn");
            } else {
                gameManager.nextTurn();
            }
        });
    }

    private void setCardsOnPlayersHand(LinearLayout layout, Player player, boolean isBot) {
        for (Card card : player.getHand()) {
            View view = getLayoutInflater().inflate(R.layout.card, layout, false);
            view.getBackground().setColorFilter(getColor(card.getColorId()), PorterDuff.Mode.SRC_ATOP);
            TextView textView = view.findViewById(R.id.number_top);
            textView.setText(String.valueOf(card.getNumber()));
            TextView textView2 = view.findViewById(R.id.number_bottom);
            textView2.setText(String.valueOf(card.getNumber()));
            TextView textView3 = view.findViewById(R.id.symbol);
            if (card.getEffect().equals(EnCardEffect.NULL)) {
                textView3.setText(String.valueOf(card.getNumber()));
                textView3.setTextSize(40f);
            } else {
                textView3.setText(String.valueOf(card.getEffect()));
            }
            if (card.getColor().equals(EnCardColor.NULL)) {
                textView.setTextColor(getColor(R.color.white));
                textView2.setTextColor(getColor(R.color.white));
                textView3.setTextColor(getColor(R.color.white));
            }
            view.setOnClickListener(v -> {
                if (gameManager.canPlayCard(card)) {
                    // non posso giocare altre carte se ho pescato
                    if (cardDrawn != null && card != cardDrawn) return;
                    selectedCard = card;
                    selectedCardView = v;
                    System.out.println("can play card" + card);
                } else {
                    System.out.println("cant play card" + card);
                }
            });
            layout.addView(view);
        }
    }

    private void setCardOnField(Card card) {
        LinearLayout layout = findViewById(R.id.field);
        layout.removeAllViews();
        View view = getLayoutInflater().inflate(R.layout.card, layout, false);
        view.getBackground().setColorFilter(getColor(card.getColorId()), PorterDuff.Mode.SRC_ATOP);
        TextView textView = view.findViewById(R.id.number_top);
        textView.setText(String.valueOf(card.getNumber()));
        TextView textView2 = view.findViewById(R.id.number_bottom);
        textView2.setText(String.valueOf(card.getNumber()));
        TextView textView3 = view.findViewById(R.id.symbol);
        textView3.setText(String.valueOf(card.getEffect()));
        if (card.getColor().equals(EnCardColor.NULL)) {
            textView.setTextColor(getColor(R.color.white));
            textView2.setTextColor(getColor(R.color.white));
            textView3.setTextColor(getColor(R.color.white));
        }
        layout.addView(view);

    }
}