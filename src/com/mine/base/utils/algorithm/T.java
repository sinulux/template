package com.mine.base.utils.algorithm;
/**
 * 求1+2+3+...+n的和 递归算法
 */
class Recurrent {
    int sum = 0;
    int flag = 1;

    public void count(int number) {

        sum += flag;
        flag++;
        if (flag <= number) {
            count(number);
        }

    }
}

public class T {

    public static void main(String[] args) {
        Recurrent r = new Recurrent();
        r.count(500);
        System.out.println(r.sum);
    }

}