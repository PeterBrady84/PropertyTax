package com.peter.propertytax;

/**
 * Created by Peter Brady on 05/04/2017.
 */

public class PropertyValueBand {
    private int min;            // inclusive lower limit for band
    private int max;            // inclusive upper limit for band

    // constructor
    public PropertyValueBand(int min, int max) {
        this.min = min;
        this.max = max;
    }

    // midpoint in a band at which property is valued to LPT
    public int midPoint() {
        return min + ((max - min) / 2);             // integer division
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
