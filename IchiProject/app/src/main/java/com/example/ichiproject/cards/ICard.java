package com.example.ichiproject.cards;

import androidx.annotation.NonNull;

import com.example.ichiproject.enums.EnCardColor;
import com.example.ichiproject.enums.EnCardEffect;

/**
 * Interfaccia che racchiude le operzioni base delle varie carte
 */
public interface ICard {

    @NonNull
    public EnCardColor getColor();

    @NonNull
    public EnCardEffect getEffect();

}
