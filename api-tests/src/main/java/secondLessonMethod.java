import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class secondLessonMethod {
        public static boolean isEven(int n) {
            return n % 2 == 0;
        }

        public static String checkAccess(int age) {
            if (age > 18) {
                return "Allowed";
            } else {
                return "Denied";
            }
        }

        public static String getGrade(int score){
            if (score >=0 && score <=20){
                return "E";
            }
            else if (score >=21 && score <=40){
                return "D";
            }
            else if (score >=41 && score <=60){
                return "C";
            }
            else if (score >=61 && score <=80){
                return "B";
            }
            else if (score >=81 && score <=100){
                return "A";
            }
            else {
                return "Error!";
            }
        }

    public static int sumToN(int n){
        if (n < 1){
            return 0;
        }
        int sum = 0;
        for (int i = 0; i <= n; i++){
            sum += i;
        }
        return sum;
    }

    public static String blastOff(int start){
        String result = "";
        if (start < 1) {
            return "«Поехали!»";
        }
        for (int i = start; i >= 1; i--){
            result += i + " ";
        }
        if (result.length() > 0) {
            result = result.substring(0, result.length() - 1);
        }
        result += " Поехали!";
        result = "«" + result + "»";
        return result;
    }

    public static boolean isPositive(int n) {
        return (n >= 0)? true : false;
    }

    public static String[] reverse(String[] arr){
        if (arr == null) {
            return new String[0];
        }
        if (arr.length == 0) {
            return new String[0];
        }
        String[] reversedArr = new String[arr.length];
        for (int i = 0; i < arr.length; i++){
            reversedArr[i] = arr[arr.length - 1 - i];
        }
        return reversedArr;
    }

    public static double calcAverage(List<Integer> list){
        if (list == null || list.isEmpty()){
            throw new IllegalArgumentException("Error.ArrayIsEmpty");
        }
        double sum = 0;
        int count = 0;
        for (Integer number : list){
            sum += number;
            count++;
        }
        return sum / count;
    }

    public static List<Integer> getEvenInRange(int start, int end) {
        List<Integer> result = new ArrayList<>();
        boolean first = true;
        if (start > end) {
            return new ArrayList<>();
        }
        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                result.add(i);
            }
        }
        return result;
    }

    public static String getEvenInRange3(int start, int end) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        if (start > end) {
            return "Error";
        }
        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) { // Проверка на чётность
                if (!first) {
                    result.append(" ");
                }
                result.append(i);
                first = false;
            }
        }
        return result.toString();
    }

    public static int findMax(int[] arr) {
        if (arr == null || arr.length == 0){
            throw new IllegalArgumentException("Error.theArrayIsEmpty");
        }
        int max = arr[0];
        for (int i = 1; i < arr.length; i++){
            if (arr[i] > max){
                max = arr[i];
            }
        }
        return max;
    }

    public static boolean hasBug(String[] messages){
        if (messages == null || messages.length == 0){
            return false;
        }
        for (String message : messages){
            if (message != null && message.equalsIgnoreCase("Bug")){
                return true;
            }
        }
        return false;
    }


    public static List<String> removeSpecificName(List<String> list, String nameToRemove){
        if (list == null || list.isEmpty()){
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<>();
        for (String name : list){
            if (!Objects.equals(name, nameToRemove)){
                result.add(name);
            }
        }
        return result;
    }
    }