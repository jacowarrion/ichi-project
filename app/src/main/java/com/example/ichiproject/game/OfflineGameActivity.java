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
import com.example.ichiproject.players.Bot;
import com.example.ichiproject.players.Player;

import java.util.Random;

public class OfflineGameActivity extends AppCompatActivity {

    private GameManager gameManager;
    private Player player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_game);
        gameManager = new GameManager();
        player = new Player(new Random().nextInt(), "player1");
        gameManager.addPlayer(player);
        Bot bot1 = new Bot(new Random().nextInt(), "bot1");
        Bot bot2 = new Bot(new Random().nextInt(), "bot2");
        Bot bot3 = new Bot(new Random().nextInt(), "bot3");
        gameManager.addPlayer(bot1);
        gameManager.addPlayer(bot2);
        gameManager.addPlayer(bot3);
        gameManager.init();

        LinearLayout layout = findViewById(R.id.playerHand);

        for (Card card : player.getHand()) {

            View view = getLayoutInflater().inflate(R.layout.card, layout, false);
            view.getBackground().setColorFilter(getColor(card.getColorId()), PorterDuff.Mode.SRC_ATOP);
            TextView textView = view.findViewById(R.id.number_top);
            textView.setText(String.valueOf(card.getNumber()));
            TextView textView2 = view.findViewById(R.id.number_bottom);
            textView2.setText(String.valueOf(card.getNumber()));
            TextView textView3 = view.findViewById(R.id.symbol);
            textView3.setText(String.valueOf(card.getEffect()));
            if(card.getColor().equals(EnCardColor.NULL)){
                textView.setTextColor(getColor(R.color.white));
                textView2.setTextColor(getColor(R.color.white));
                textView3.setTextColor(getColor(R.color.white));
            }
            layout.addView(view);
        }

        LinearLayout layout2 = findViewById(R.id.linearLayout);

        for (Card card : bot1.getHand()) {

            View view = getLayoutInflater().inflate(R.layout.card, layout2, false);
            view.getBackground().setColorFilter(getColor(card.getColorId()), PorterDuff.Mode.SRC_ATOP);
            TextView textView = view.findViewById(R.id.number_top);
            textView.setText(String.valueOf(card.getNumber()));
            TextView textView2 = view.findViewById(R.id.number_bottom);
            textView2.setText(String.valueOf(card.getNumber()));
            TextView textView3 = view.findViewById(R.id.symbol);
            textView3.setText(String.valueOf(card.getEffect()));
            if(card.getColor().equals(EnCardColor.NULL)){
                textView.setTextColor(getColor(R.color.white));
                textView2.setTextColor(getColor(R.color.white));
                textView3.setTextColor(getColor(R.color.white));
            }
            layout2.addView(view);
        }

    }
}