package models;

import interfaces.CalculateOperation;
/**
 * Реализация арифметических операций для строк.
 * Выполняет нестандартные операции над строками:
 */
public class CalculateString implements CalculateOperation<String> {

    /**
     * Выполняет попарное соединение символов двух строк.
     * @param a первая строка для обработки.
     * @param b вторая строка для обработки.
     * @return новая строка с попарным соединением символов.
     * @throws IllegalArgumentException если одна из строк равна null.
     */
    @Override
    public String multiplication(String a, String b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Строки не должны быть null");
        }

        StringBuilder result=new StringBuilder();
        for(Character c1:a.toCharArray()){
            for(Character c2: b.toCharArray())
            {
                result.append(c1).append(c2);
            }
        }
        return result.toString();
    }

    /**
     * Выполняет конкатенацию двух строк с добавлением пробела между ними.
     * @param a первая строка (левый операнд).
     * @param b вторая строка (правый операнд).
     * @return новая строка, состоящая из двух строк, разделённых пробелом.
     * @throws IllegalArgumentException если одна из строк равна null.
     */
    @Override
    public String addition(String a, String b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Строки не должны быть null");
        }
        return a+" "+ b;
    }

    /**
     * Выполняет удаление первого вхождения второй строки из первой.
     * @param a исходная строка, из которой производится удаление.
     * @param b подстрока для удаления.
     * @return новая строка после удаления первого вхождения подстроки b.
     * @throws IllegalArgumentException если одна из строк равна null.
     */
    @Override
    public String subtraction(String a, String b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Строки не должны быть null");
        }

        return a.replaceFirst(b,"");
    }
    /**
     * Выполняет вырезание части первой строки по длине второй строки.
     *
     * @param a исходная строка, из которой вырезаются символы.
     * @param b строка, длина которой определяет количество символов для вырезания.
     * @return новая строка, содержащая первые n символов строки a, где n = длина строки b.
     * @throws IllegalArgumentException если одна из строк равна null или длина строки b больше длины строки a.
     */
    @Override
    public String division(String a, String b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Строки не должны быть null");
        }
        if (b.length() > a.length()) {
            throw new IllegalArgumentException("Длина строки b не может превышать длину строки a");
        }
        StringBuilder result =new StringBuilder();
        for(int i=0;i<b.length();i++){
            result.append(a.charAt(i));
        }
        return result.toString();
    }
}
