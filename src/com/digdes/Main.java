package com.digdes;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите строку:");
        String enter = scanner.nextLine();

        //String enter = "3[x3[x]yz]4[xy]z";
        //String enter = "mxy5[x]z]4[xy]z";
        //String enter = "0m3[xy5[x]z]4[xy]z";
        //String enter = "m3[xy5[x]z]4[x0y]z";
        //String enter = "mxy0[5[x]z]4[xy]z";
        //String enter = "m3[xy5[x]z]4[xy]z0";

        if (enter.matches("^[\\d\\[\\]\\w]+$")){ //first check на допустимые символы
            while(enter.matches("(.*)\\d\\[[a-zA-Z]*](.*)")){ //check наличия вложенных строк с маской n[xy]
                enter = find(enter);
            }
            System.out.println(check(enter)); //second check на валидность строки
        }
        else {
            System.out.println("Не валидная строка " + enter);
        }
    }

    public static String check(String in) {
        if(in.matches("^[a-zA-Z]+$")){
            return in;
        }
        return "Была введенна не валидная строка! Результат обработки: " + in;
    }

    public static String find(String in){
        StringBuffer stringBuffer = new StringBuffer(in);
        Pattern pattern = Pattern.compile("(\\d\\[[a-zA-Z]*])");
        Matcher matcher = pattern.matcher(stringBuffer);
        while (matcher.find()){
            String buf = stringBuffer.toString()
                    .replace(matcher.group(), pars(matcher.group())); //модифицируем строку на лету если есть вхождения типа n[xy]
            stringBuffer.delete(0, stringBuffer.length());
            stringBuffer.append(buf);
            matcher.reset(); //при всех апдейтах закрываем матчер иначе StringIndexOutOfBoundsException при 0[]
        }
        return stringBuffer.toString();
    }


    public static String pars(String in) {
            int num = Integer.valueOf(in.replaceAll("\\[.*\\]", "")); //вытаскиваем n
            String letters = in.replaceAll("\\]", "")
                    .replaceAll("\\d*\\[", "");// вытаскиваем стрингу из []
            StringBuffer buf = new StringBuffer();

            for (int i = 0; i < num; i++){ //делаем n копий строки
                buf.append(letters);
            }
        return String.valueOf(buf);
    }
}



