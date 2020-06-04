package com.ay.lxunhan;

import java.math.BigDecimal;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan
 * @date 2020/6/1
 */
public class Text {
    public static void main(String[] arg){
        double a=20.0;
        double b=15;
        System.out.println(subtract(a,b));

    }

    public static String subtract(double a,double b){
        double d =a-b;
        BigDecimal bigDecimal=new BigDecimal(d);
        return String.valueOf(bigDecimal.setScale(0, BigDecimal.ROUND_UP));
    }
}
