package com.splitpay.utils;

/**
 * Created by shashank on 30-01-2017.
 */

public class SpendMoney {

    public String whatFor;
    public String amount;
    public boolean divideWithRoomies;

    public SpendMoney() {

    }

    public SpendMoney(String whatFor, String amount, boolean divideWithRoomies) {
        this.whatFor = whatFor;
        this.amount = amount;
        this.divideWithRoomies = divideWithRoomies;
    }
}
