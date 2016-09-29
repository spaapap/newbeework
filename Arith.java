package com.jetmap.read;

import java.math.BigDecimal;

/**
 * Created by Admin on 6/22/2016.
 //                            _ooOoo_
 //                           o8888888o
 //                           88" . "88
 //                           (| -_- |)
 //                            O\ = /O
 //                        ____/`---'\____
 //                      .   ' \\| |// `.
 //                       / \\||| : |||// \
 //                     / _||||| -:- |||||- \
 //                       | | \\\ - /// | |
 //                     | \_| ''\---/'' | |
 //                      \ .-\__ `-` ___/-. /
 //                   ___`. .' /--.--\ `. . __
 //                ."" '< `.___\_<|>_/___.' >'"".
 //               | | : `- \`.;`\ _ /`;.`/ - ` : | |
 //                 \ \ `-. \_ __\ /__ _/ .-` / /
 //         ======`-.____`-.___\_____/___.-`____.-'======
 //                            `=---='
 //
 //         .............................................
 //                  佛祖镇楼                  BUG辟易
 //          佛曰:
 //                  写字楼里写字间，写字间里程序员；
 //                  程序人员写程序，又拿程序换酒钱。
 //                  酒醒只在网上坐，酒醉还来网下眠；
 //                  酒醉酒醒日复日，网上网下年复年。
 //                  但愿老死电脑间，不愿鞠躬老板前；
 //                  奔驰宝马贵者趣，公交自行程序员。
 //                  别人笑我忒疯癫，我笑自己命太贱；
 //                  不见满街漂亮妹，哪个归得程序员？
 */


public class Arith {


    //默认除法运算精度
    private static final int DEF_DIV_SCALE = 6;

    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }
    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }
    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后6位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1,double v2){
        return div(v1,v2,DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1,double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
       
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v,int scale){

        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
