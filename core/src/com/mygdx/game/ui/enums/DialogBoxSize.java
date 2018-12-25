package com.mygdx.game.ui.enums;

public enum DialogBoxSize {
    Header("HEADER"),
    SmallHeader("SMALL_HEADER"),
    XSmall("X_XMALL"),
    Small("SMALL"),
    SmallThin("SMALL_THIN"),
    Medium("MEDIUM"),
    Large("LARGE"),
    XLarge("X_LARGE"),
    Full("FULL"),
    ThreeQuarters("THREE_QUARTERS"),
    Quarter("QUARTER"),
    QuarterHalf("QUARTER_HALF"),
    QuarterQuarter("QUARTER_QUARTER"),
    SmallTall("SMALL_TALL");

    private String value;

    DialogBoxSize(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
