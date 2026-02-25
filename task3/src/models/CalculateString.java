package models;

import interfaces.CalculateOperation;

public class CalculateString implements CalculateOperation<String> {

    @Override
    public String multiplication(String a, String b) {
        StringBuilder result=new StringBuilder();
        for(Character c1:a.toCharArray()){
            for(Character c2: b.toCharArray())
            {
                result.append(c1).append(c2);
            }
        }
        return result.toString();
    }

    @Override
    public String addition(String a, String b) {
        return a+" "+ b;
    }

    @Override
    public String subtraction(String a, String b) {
        return a.replaceFirst(b,"");
    }

    @Override
    public String division(String a, String b) {

        StringBuilder result =new StringBuilder();
        for(int i=0;i<b.length();i++){
            result.append(a.charAt(i));
        }
        return result.toString();
    }
}
