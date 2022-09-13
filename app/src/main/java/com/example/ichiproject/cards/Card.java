package com.example.ichiproject.cards;

import androidx.annotation.NonNull;

import com.example.ichiproject.enums.EnCardColor;
import com.example.ichiproject.enums.EnCardEffect;

public class Card implements ICard {

    private EnCardColor _color;
    private final EnCardEffect _effect;
    private boolean _effectActive;

    private final int _number;

    public Card(EnCardColor color, EnCardEffect effect, int _number) {
        this._color = color;
        this._effect = effect;
        this._number = _number;
        _effectActive = true;
    }


    @NonNull
    @Override
    public EnCardColor getColor() {
        return this._color;
    }
    public EnCardColor setColor(EnCardColor color) {
        return _color = color;
    }

    @NonNull
    @Override
    public EnCardEffect getEffect() {
        return this._effect;
    }

    public int getNumber() {
        return this._number;
    }

    @NonNull
    @Override
    public String toString() {
        if(_number < 0 ) {
            if (_color == EnCardColor.NULL) return "Card{" + _effect + '}';
            else return "Card{" + _effect + "-" + _color + '}';
        }
        else return "Card{" + _color + "-" + _number + '}';
    }

    /**
     * è un cambia-colore o un cambia-colore+4
     */
    public boolean isWildCard() {
        return _effect.equals(EnCardEffect.WILD) || _effect.equals(EnCardEffect.WILD_DRAW_FOUR);
    }

    public boolean isEffectActive() {
        return _effectActive;
    }

    public void disableEffect() {
        this._effectActive = false;
    }
}
