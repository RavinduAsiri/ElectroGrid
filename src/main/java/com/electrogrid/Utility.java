package com.electrogrid;

import java.math.BigDecimal;

public class Utility {

    public BigDecimal calculateAmountUsingUnits(int units) {
        if (units <= 50){
            return BigDecimal.valueOf(units * 10L);
        }else if (units <= 100){
            return BigDecimal.valueOf(units * 20L);
        }else {
            return BigDecimal.valueOf(units * 50L);
        }
    }
}
