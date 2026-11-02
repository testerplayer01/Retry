import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Locale.filter;
import static org.junit.jupiter.api.Assertions.assertEquals;


//Задача №1
@Tag("Lesson")
public class secondLessonTest {
    private final secondLessonMethod logic = new secondLessonMethod();

    @Test
    void testIsEven() {
        System.out.println("========================================");
        System.out.println("Test method start");
        Random random = new Random();
        int number = random.nextInt(1, 100);
        System.out.println("Сгенерированое число " + number);
        boolean result = logic.isEven(number);
        System.out.println("isEven result: " + result);
        System.out.println("Test method end");
        System.out.println("========================================");

    }

    @Test
    void testCheckAccess() {
        System.out.println("========================================");
        System.out.println("Test method start");
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int age = random.nextInt(0, 99);
            System.out.println("Сгенерированый возраст " + age);
            String result = logic.checkAccess(age);
            System.out.println("checkAccess result: " + result);
        }
        System.out.println("Test method end");
        System.out.println("========================================");
    }

    @ParameterizedTest
    @MethodSource("generateRandomArray")
    void testGetGrade(int score) {
        System.out.println("========================================");
        System.out.println("Test method start");
        String grade = logic.getGrade(score);
        String expected;
        if (score >= 0 && score <= 20) {
            expected = "E";
        } else if (score >= 21 && score <= 40) {
            expected = "D";
        } else if (score >= 41 && score <= 60) {
            expected = "C";
        } else if (score >= 61 && score <= 80) {
            expected = "B";
        } else if (score >= 81 && score <= 100) {
            expected = "A";
        } else {
            expected = "Error!";
        }
        assertEquals(expected, grade);
        System.out.println("Баллы: " + score + ", Оценка: " + grade);
        System.out.println("Test method end");
        System.out.println("========================================");
    }

    static int[] generateRandomArray() {
        int[] scores = new int[10];
        Random random = new Random();
        for (int i = 0; i < scores.length; i++) {
            scores[i] = random.nextInt(0, 100);
        }
        return scores;
    }


    //Задача №2
    private static void checkResult(String testName, boolean condition) {
        if (condition) {
            System.out.println(testName + " TEST PASSED");
        } else {
            System.out.println(testName + " TEST FAILED");
            org.junit.jupiter.api.Assertions.fail(testName + " failed");
        }
    }

    @Test
    @DisplayName("Test isEven method with random numbers")
    void testIsEven2() {
        Random random = new Random();
        int number = random.nextInt(1, 100);
        System.out.println("Сгенерированое число " + number);
        boolean result = logic.isEven(number);
        System.out.println("isEven result: " + result);
        checkResult("testIsEven2 -", result == (number % 2 == 0));
    }

    @Test
    @DisplayName("Test checkAccess method with random ages")
    void testCheckAccess2() {
        Random random = new Random();
        int age = random.nextInt(0, 99);
        System.out.println("Сгенерированый возраст " + age);
        String result = logic.checkAccess(age);
        System.out.println("checkAccess result: " + result);
        checkResult("testCheckAccess2 -", result.equals(age > 18 ? "Allowed" : "Denied"));
    }

    @Test
    @DisplayName("Test sumToN method with random numbers")
    void testSumToN() {
        Random random = new Random();
        int n = random.nextInt(0, 100);
        System.out.println("Сгенерированое число " + n);
        int result = logic.sumToN(n);
        int expected = n * (n + 1) / 2;
        System.out.println("sumToN result: " + result);
        checkResult("testSumToN -", result == expected);

    }

    @Test
    @DisplayName("Test blastOff method with random numbers")
    void testBlastOff() {
        Random random = new Random();
        int start = random.nextInt(1, 100);
        System.out.println("Сгенерированное число " + start);
        String result = logic.blastOff(start);
        System.out.println("blastOff result: " + result);
        checkResult("testBlastOff -", result.startsWith("«") && result.endsWith("Поехали!»"));
    }

    @RepeatedTest(3)
    @DisplayName("Test isPositive method with random numbers")
    void testIsPositive() {
        Random random = new Random();
        int number = random.nextInt(-100, 100);
        System.out.println("Сгенерированое число " + number);
        boolean result = logic.isPositive(number);
        System.out.println("isPositive result: " + result);
        checkResult("testIsPositive -", result == (number > 0));
    }

    @RepeatedTest(7)
    @DisplayName("Test reverse method with random strings")
    void reverse() {
        String[] source = generateRandomString(ThreadLocalRandom.current().nextInt(5, 25));
        String[] expected = new String[source.length];
        for (int i = 0; i < source.length; i++) {
            expected[i] = source[source.length - 1 - i];
        }
        String[] result = secondLessonMethod.reverse(source);
        System.out.println("Исходный массив: " + Arrays.toString(source));
        System.out.println("Ожидаемый обратный массив: " + Arrays.toString(expected));
        System.out.println("Фактический обратный массив: " + Arrays.toString(result));
        checkResult("reverse -", Arrays.equals(expected, result));
    }

    private String[] generateRandomString(int length) {
        String[] arr = new String[length];
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            arr[i] = String.valueOf(characters.charAt(random.nextInt(characters.length())));
        }
        return arr;
    }

    @RepeatedTest(5)
    @DisplayName("Test calcAverage method with random numbers")
    void testCalcAverage() {
        List<Integer> nums = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            nums.add(ThreadLocalRandom.current().nextInt(0, 1000));
        }
        double summ = 0;
        for (int num : nums) {
            summ += num;
        }
        double expected = summ / nums.size();
        double result = logic.calcAverage(nums);
        boolean passed = Math.abs(result - expected) < 0.0001;
        System.out.println("Исходный список: " + nums);
        System.out.println("Ожидаемое среднее значение: " + expected);
        System.out.println("Фактическое среднее значение: " + result);
        checkResult("calcAverage(repeated)", passed);
    }

    @RepeatedTest(5)
    @DisplayName("Test getEvenInRange method with random ranges")
    void testGetEvenInRange() {
        Random random = new Random();
        int start = random.nextInt(0, 100);
        int end = random.nextInt(0, 100);
        List<Integer> result = logic.getEvenInRange(start, end);
        System.out.println("Диапазон: " + start + " - " + end);
        System.out.println("Чётные числа: " + result);
        checkResult("getEvenInRange(repeated)", !result.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "0, E",
            "20, E",
            "21, D",
            "41, C",
            "60, C",
            "61, B",
            "80, B",
            "81, A",})
    @DisplayName("Test getGrade method with various scores")
    void testGetGrade2(int score, String expectedGrade) {
        String grade = logic.getGrade(score);
        System.out.println("Баллы: " + score + ", Оценка: " + grade);
        checkResult("getGrade2 -", grade.equals(expectedGrade));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 44, 33, 45, -855",
            "512, 0, 0, 0, 0",
            "-1, -2, -3, -4, -5",
            "100, 200, 300, 400, 500"})
    @DisplayName("Test findMax method with various arrays")
    void testFindMax(int a, int b, int c, int d, int e) {
        int[] arr = {a, b, c, d, e};
        int expectedMax = Arrays.stream(arr).max().orElse(Integer.MIN_VALUE);
        int resultMax = logic.findMax(arr);
        System.out.println("Массив: " + Arrays.toString(arr));
        System.out.println("Ожидаемый максимум: " + expectedMax);
        System.out.println("Фактический максимум: " + resultMax);
        checkResult("findMax -", expectedMax == resultMax);
    }

    @ParameterizedTest
    @CsvSource({
            "tic, tac, bug",
            "oh, no, BUG",
            "тупо, проверка, теста"
    })
    @DisplayName("Test hasBugs method with various inputs")
    void testHasBugs(String v1, String v2, String v3) {
        String[] values = {v1, v2, v3};
        boolean result = logic.hasBug(values);
        boolean expected = Arrays.stream(values).anyMatch(s -> s != null && s.equalsIgnoreCase("bug"));
        System.out.println("Массив значений: " + Arrays.toString(values));
        System.out.println("Ожидаемый результат: " + expected);
        System.out.println("Фактический результат: " + result);
        checkResult("hasBugs -", result == expected);
    }

@ParameterizedTest
@CsvSource(value = {
        "'Anatoly, Armen, Goga, Sasha, Valentin'; Goga",
        "'John, Jane, Doe, Smith, Brown'; Brown",
        "'Nastya, Lena, Katya, Daria, Snezhana'; Snezhana"
}, delimiter = ';')
@DisplayName("Test removeSpecificName method with various inputs")
    void testRemoveSpecificName(String allNames, String nameToRemove) {
        List<String> names = Arrays.stream(allNames.split(","))
                .map(String::trim)
                .toList();
        List<String> result = logic.removeSpecificName(names, nameToRemove);
        List<String> expected = names.stream()
                .filter(name -> !name.equalsIgnoreCase(nameToRemove))
                .toList();
        System.out.println("Исходный список: " + names);
        System.out.println("Имя для удаления: " + nameToRemove);
        System.out.println("Ожидаемый результат: " + expected);
        System.out.println("Фактический результат: " + result);
        checkResult("removeSpecificName -", result.equals(expected));
    }
}





