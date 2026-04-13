package com.overlast.cap.sanity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.StandardException;

@Getter
@Setter
public class Sanity implements ISanity {

    // Create sanity variables.
    private float sanity = 100.00f;
    private float maxSanity = 100.00f;
    private float minSanity = 0.00f;

    // Methods for messing with sanity or getting it.
    public void increase(float amount) {

        this.set(this.sanity + amount);

    }

    public void decrease(float amount) {

        this.set(this.sanity - amount);

    }

    public void set(float amount) {


        if (amount > this.maxSanity) {

            amount = this.maxSanity;
        } else if (amount < this.minSanity) {

            amount = this.minSanity;
        }
        this.sanity = amount;
    }
}