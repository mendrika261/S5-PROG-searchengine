package mg.prog.s5recherche.core;

import mg.prog.s5recherche.entity.Adjective;
import mg.prog.s5recherche.entity.Criteria;

import java.util.HashMap;
import java.util.TreeMap;

public class Utils {
    public static <T> T getNearIn(int index, TreeMap<Integer, ?> map) {
        T near = null;
        int min = Integer.MAX_VALUE;
        for (Integer i : map.keySet()) {
            if (Math.abs(index - i) < min) {
                min = Math.abs(index - i);
                near = (T) map.get(i);
            }
        }
        return near;
    }

    public static int getNearIndex(int index, TreeMap<Integer, ?> map) {
        int near = -Integer.MAX_VALUE;
        int min = Integer.MAX_VALUE;
        for (Integer i : map.keySet()) {
            if (Math.abs(index - i) < min) {
                min = Math.abs(index - i);
                near = i;
            }
        }
        return near;
    }

    public static String getSign(Adjective adjective, Criteria criterion) {
        String sign = adjective.getEffect();
        if(sign.equals("-") && criterion.getBestValue().equals("-")) return "-";
        if(sign.equals("+") && criterion.getBestValue().equals("-")) return "+";
        if(sign.equals("-") && criterion.getBestValue().equals("+")) return "-";
        if(sign.equals("+") && criterion.getBestValue().equals("+")) return "+";
        return sign;
    }

    public static String getNextNumber(String string, int indexStart) {
        StringBuilder number = new StringBuilder();
        boolean endDigit = false;
        for (int i = indexStart; i < string.length(); i++) {
            if (endDigit) {
                break;
            } else if (Character.isDigit(string.charAt(i))) {
                number.append(string.charAt(i));
            } else if (!number.isEmpty()) {
                endDigit = true;
            }
        }
        return number.toString();
    }

    public static String getNextString(String string, int indexStart) {
        StringBuilder word = new StringBuilder();
        boolean endWord = false;
        for (int i = indexStart; i < string.length(); i++) {
            if (endWord) {
                break;
            } else if (Character.isLetter(string.charAt(i))) {
                word.append(string.charAt(i));
            } else if (!word.isEmpty()) {
                endWord = true;
            }
        }
        return word.toString();
    }

    public static String getNextValue(String parameterType, int index, String query) {
        String value;
        if(parameterType.equals("numeric")) {
            value = getNextNumber(query, index).isEmpty() ? "0" : getNextNumber(query, index);
        } else {
            value = getNextString(query, index).isEmpty() ? "%" : getNextString(query, index);
        }
        return value;
    }

    public static String[] getNextValues(int parameterNumber, String parameterType, int index, String query) {
        String[] values = new String[parameterNumber];
        for (int i = 0; i < parameterNumber; i++) {
            values[i] = getNextValue(parameterType, index, query);
            index = query.indexOf(values[i]) + values[i].length();
        }
        return values;
    }

    public static double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public static String getNextOperator(String query, int index) {
        int indexAnd = query.indexOf("et", index);
        int indexOr = query.indexOf("ou", index);
        if(indexAnd == -1 && indexOr == -1) return "";
        if(indexAnd == -1) return "OR";
        if(indexOr == -1) return "AND";
        return indexAnd < indexOr ? "AND" : "OR";
    }
}
