package com.study.calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class BaseCalculator {
//    将运算符归结为operSet字符数组
    private final char[] operSet = {'+', '-', '*', '/', '(', ')', '#'};

    private final Map<Character, Integer> operMap = new HashMap<Character, Integer>() {{
        put('+', 0);
        put('-', 1);
        put('*', 2);
        put('/', 3);
        put('(', 4);
        put(')', 5);
        put('#', 6);
    }};
//    比较运算符的优先级
    private final char[][] operPrior = {
            /* (o1,o2)  +    -    *    /    (    )    # */
            /*  +  */ {'>', '>', '<', '<', '<', '>', '>'},
            /*  -  */ {'>', '>', '<', '<', '<', '>', '>'},
            /*  *  */ {'>', '>', '>', '>', '<', '>', '>'},
            /*  /  */ {'>', '>', '>', '>', '<', '>', '>'},
            /*  (  */ {'<', '<', '<', '<', '<', '=', ' '},
            /*  )  */ {'>', '>', '>', '>', ' ', '>', '>'},
            /*  #  */ {'<', '<', '<', '<', '<', ' ', '='},
    };

    private char getPrior(char oper1, char oper2) {
        return operPrior[operMap.get(oper1)][operMap.get(oper2)];
    }

    private double operate(double a, char oper, double b) {
        switch (oper) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    return Double.MAX_VALUE; //处理异常
                }
                return a / b;
            default:
                return 0;
        }
    }

    private double calSubmath(String math) {
        try {
            if (math.length() == 0) {
                return Double.MAX_VALUE;
            } else {
                if (!hasOper(math.substring(1, math.length())) || math.contains("E-")) {
                    return Double.parseDouble(math);
                }

                int flag = 0;
                if (math.charAt(0) == '-') {
                    flag = 1;
                    math = math.substring(1, math.length());
                }

                Stack<Character> operStack = new Stack<>(); //oper栈
                Stack<Double> numStack = new Stack<>();     //num栈

                operStack.push('#');
                math += "#";

                String tempNum = "";

                //遍历输入的输入表达式math，将遍历出的字符都赋值给charOfMath
                for (int i = 0; i < math.length(); i++) {
                    char charOfMath = math.charAt(i);

                    //(1)num进栈
//                    charOfMath如果是运算符或者charOfMath是"-"或者charofMath是"（"
                    if (!isOper(charOfMath)
                            || charOfMath == '-' && math.charAt(i - 1) == '(') {
//                        直接将charOfMath添加进入后缀表达式中
                        tempNum += charOfMath;
//                        遍历下一个字符
                        i++;
                        charOfMath = math.charAt(i);
//                        如果chaofMath是字符，将后缀表达式tempNum转化成Double类型的包装类
                        if (isOper(charOfMath)) {
                            double num = Double.parseDouble(tempNum);
//                            如果flag等于1，说明已经输入完毕，将num转变为复数，并且将计算标志flag还原为0
                            if (flag == 1) {
                                num = -num;
                                flag = 0;
                            }
//                            将num压入numStack数字栈中
                            numStack.push(num);
//                            将后缀表达式tempNum置为空
                            tempNum = "";
                        }

                        i--;
                    }

                    //oper进栈
                    else {
//                        用getPrior方法比较，弹出所有优先级大于或者等于该运算符的栈顶元素，然后将该运算符入栈
                        switch (getPrior(operStack.peek(), charOfMath)) {
                            case '<':
                                operStack.push(charOfMath);
                                break;
//                            遇到右括号：执行出栈操作，并将出栈的元素输出，直到弹出栈的是左括号，左括号不输出。
                            case '=':
                                operStack.pop();
                                break;

                            case '>':
                                char oper = operStack.pop();
                                double b = numStack.pop();
                                double a = numStack.pop();
//                                用operate方法计算a,b，发现等于Double.MAX_VALUE，返回Double.MAX_VALUE，说明计算错误
                                if (operate(a, oper, b) == Double.MAX_VALUE)
                                    return Double.MAX_VALUE;
//                                将计算结果压入数字栈numStack
                                numStack.push(operate(a, oper, b));
                                i--; //继续比较该oper与栈顶oper的关系
                                break;
                        }
                    }
                }
                return numStack.peek(); //返回栈顶元素，也就是num
            }
            //抛出异常
        } catch (Exception e) {
            return Double.MAX_VALUE;
        }
    }

    //计算math，添加了一些特殊math的处理
    double cal(String math) {
        try {
            if (math.length() == 0) {
                return Double.MAX_VALUE;
            } else {
                if (!hasOper(math.substring(1, math.length())) || math.contains("E-")) {
                    return Double.parseDouble(math);
                }
                else {
                    return calSubmath(math);
                }
            }
        } catch (Exception e) {
            return Double.MAX_VALUE;
        }
    }

    private boolean hasOper(String s) {
        return s.contains("+") || s.contains("-") || s.contains("*") || s.contains("/");
    }

    boolean isOper(char c) {
        int i;
        for (i = 0; i < operSet.length; i++) {
            if (c == operSet[i]) {
                break;
            }
        }
        return i != operSet.length;
    }


}
