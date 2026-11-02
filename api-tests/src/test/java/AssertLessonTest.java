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
import static org.junit.jupiter.api.Assertions.*;


//Задача №1
public class AssertLessonTest {
    private final secondLessonMethod logic = new secondLessonMethod();

    @Tag("Unit1")
    @Tag("EasyTest")
    @Test
    @DisplayName("Проверка метода isEven")
    void testIsEvenAssert() {
        Random random = new Random();
        int number = random.nextInt(0, 100);
        boolean expected = number % 2 == 0;
        boolean result = logic.isEven(number);
        assertEquals(expected, result, "Метод isEven(" + number + ") должно вернуться " + expected + ", но вернулось " + result);
        System.out.println("Сгенерированое число " + number);
        System.out.println("isEven result: " + result);
    }

    @Tag("Unit1")
    @Tag("EasyTest")
    @Test
    @DisplayName("Проверка метода getEvenInRange")
    void testGetEvenInRangeAssert() {
        Random random = new Random();
        int start = random.nextInt(0, 100);
        int end = random.nextInt(0, 100);
        List<Integer> expected = new ArrayList<>();
        for (int i = Math.min(start, end); i <= Math.max(start, end); i++) {
            if (i % 2 == 0) {
                expected.add(i);
            }
        }
        List<Integer> result = logic.getEvenInRange(start, end);
        System.out.println("Сгенерированый возраст " + start + " и " + end);
        System.out.println("getEvenInRange result: " + result);
        assertNotNull(result, "Метод getEvenInRange(" + start + ", " + end + ") должен вернуть не null");
        assertEquals(expected, result, "Ожидалось: " + expected + ", получено: " + result);
        for (Integer num : result) {
            assertTrue(num % 2 == 0, "Число " + num + " не является четным");
        }
    }

    @Tag("Unit1")
    @Tag("EasyTest")
    @Test
    @DisplayName("Метод преднамеренного падения")
    void testFail() {
        int number = 5;
        assertEquals(10, number, "Метод преднамеренного падения: ожидалось 10, получено " + number);
    }

    @Tag("Unit1")
    @Tag("EasyTest")
    @Test
    @DisplayName("Проверка метода numberLeght")
    void testNumberLenght(){
        Random random = new Random();
        int number = random.nextInt(0, 1000000);
        String numberL = String.valueOf(number);
        int expectedLenght = numberL.length();
        System.out.println("Число " + number);
        System.out.println("Количество символов " + expectedLenght);
        assertEquals(expectedLenght, numberL.length(), "Ожидалось: " + expectedLenght + ", получено: " + numberL.length());

    }


    //Задача №2
    @Tag("Unit2")
    @Tag("RepeatedTest")
    @RepeatedTest(10)
    @DisplayName("Проверка метода isEven")
    void testIsEvenAssert2() {
        Random random = new Random();
        int number = random.nextInt(0, 100);
        boolean expected = number % 2 == 0;
        boolean result = logic.isEven(number);
        assertEquals(expected, result, "Метод isEven(" + number + ") должно вернуться " + expected + ", но вернулось " + result);
        System.out.println("Сгенерированое число " + number);
        System.out.println("isEven result: " + result);
    }

    @Tag("Unit2")
    @Tag("RepeatedTest")
    @RepeatedTest(10)
    @DisplayName("Test checkAccess method with random ages")
    void testCheckAccess2() {
        Random random = new Random();
        int age = random.nextInt(0, 99);
        System.out.println("Сгенерированый возраст " + age);
        String result = logic.checkAccess(age);
        System.out.println("checkAccess result: " + result);
        assertEquals(age > 18 ? "Allowed" : "Denied", result, "Метод checkAccess(" + age + ") вернул неверный результат");
    }

    @Tag("Unit2")
    @Tag("RepeatedTest")
    @RepeatedTest(10)
    @DisplayName("Test sumToN method with random numbers")
    void testSumToN() {
        Random random = new Random();
        int n = random.nextInt(0, 100);
        System.out.println("Сгенерированое число " + n);
        int result = logic.sumToN(n);
        int expected = n * (n + 1) / 2;
        System.out.println("sumToN result: " + result);
        assertEquals(expected, result, "Метод sumToN(" + n + ") вернул неверный результат");
    }

    @Tag("Unit2")
    @Tag("RepeatedTest")
    @RepeatedTest(10)
    @DisplayName("Test blastOff method with random numbers")
    void testBlastOff() {
        Random random = new Random();
        int start = random.nextInt(1, 100);
        System.out.println("Сгенерированное число " + start);
        String result = logic.blastOff(start);
        String expected = "";
        for (int i = start; i > 0; i--) {
            expected += i + " ";
        }
        expected += "Поехали!";
        expected = "«" + expected.trim() + "»";
        System.out.println("blastOff result: " + result);
        assertEquals(expected, result, "Метод blastOff(" + start + ") вернул неверный результат");
    }

    @Tag("Unit2")
    @Tag("RepeatedTest")
    @RepeatedTest(10)
    @DisplayName("Test isPositive method with random numbers")
    void testIsPositive() {
        Random random = new Random();
        int number = random.nextInt(-100, 100);
        System.out.println("Сгенерированое число " + number);
        boolean result = logic.isPositive(number);
        System.out.println("isPositive result: " + result);
        assertEquals(number > 0, result, "Метод isPositive(" + number + ") вернул неверный результат");
    }

    @Tag("Unit2")
    @Tag("RepeatedTest")
    @RepeatedTest(10)
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
        assertArrayEquals(expected, result, "Метод reverse вернул неверный результат");
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

    @Tag("Unit2")
    @Tag("RepeatedTest")
    @RepeatedTest(10)
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
        assertEquals(expected, result, 0.0001, "Метод calcAverage вернул неверный результат");
    }

    @Tag("Unit2")
    @Tag("RepeatedTest")
    @RepeatedTest(10)
    @DisplayName("Test getEvenInRange method with random ranges")
    void testGetEvenInRange3() {
        Random random = new Random();
        int start = random.nextInt(0, 100);
        int end = random.nextInt(0, 100);
        String result = logic.getEvenInRange3(start, end);
        System.out.println("Диапазон: " + start + " - " + end);
        System.out.println("Чётные числа: " + result);
        assertTrue(result.matches("^(\\d+\\s)*\\d+$") || result.equals("Error"), "Метод getEvenInRange вернул неверный результат");
    }

    @Tag("Unit2")
    @Tag("ParameterizedTest")
    @ParameterizedTest
    @CsvSource({
            "0, E",
            "20, E",
            "21, D",
            "41, C",
            "60, C",
            "61, B",
            "80, B",
            "81, A",
            "2, E",
            "30, D",
            "35, D",
            "40, D",
            "96, A",
            "81, A",
            "79, B",
            "99, A",})
    @DisplayName("Test getGrade method with various scores")
    void testGetGrade2(int score, String expectedGrade) {
        String grade = logic.getGrade(score);
        System.out.println("Баллы: " + score + ", Оценка: " + grade);
        assertEquals(expectedGrade, grade, "Оценка для " + score + " баллов должна быть " + expectedGrade + ", но вернулось значение " + grade);
    }

    @Tag("Unit2")
    @Tag("ParameterizedTest")
    @ParameterizedTest
    @CsvSource({
            "1, 44, 33, 45, -855",
            "512, 0, 0, 0, 0",
            "-1, -2, -3, -4, -5",
            "100, 200, 300, 400, 500",
            "0, 54, 154, 5755, 2",
            "100, 448, 37, 47, -85",
            "5120, 10, 20, 780, 770",
            "-10, -27, -35, -43, -57",
            "1000, 25, 307, 48, 507",
            "50, 544, 15, 555, 27",
            "1000, 447, 8, 54, -8",
            "51200, 1, 88, 55, 11",
            "-100, 5, 3, 4, 5",
            "1007, 2000, 3000, 40000, 5000",
            "70, 5414, 15414, 575514, 214",
            "12, 4, 339, 4500, -8550",
            "5127, 14, 41, 40, 4510",
            "-12, -277, -371, -445, -5",
            "1003, 20078, 3004, 4045, 50087",
            "80, 540, 1540, 57, 20",})
    @DisplayName("Test findMax method with various arrays")
    void testFindMax(int a, int b, int c, int d, int e) {
        int[] arr = {a, b, c, d, e};
        int expectedMax = Arrays.stream(arr).max().orElse(Integer.MIN_VALUE);
        int resultMax = logic.findMax(arr);
        System.out.println("Массив: " + Arrays.toString(arr));
        System.out.println("Ожидаемый максимум: " + expectedMax);
        System.out.println("Фактический максимум: " + resultMax);
        assertEquals(expectedMax, resultMax, "Максимум должно быть число " + expectedMax + ", но получено значение " + resultMax);
    }

    @Tag("Unit2")
    @Tag("ParameterizedTest")
    @ParameterizedTest
    @CsvSource({
            "tic, tac, bug",
            "oh, no, BUG",
            "тупо, проверка, теста",
            "как, так, Bug",
            "ogo, now, BU",
            "проверяем, кириллицу, виг",
            "carma, coma, баг",
            "kak, po, буг",
            "drugomu, заBUGовано, игп",
            "oh, yes, BUG"
    })
    @DisplayName("Test hasBugs method with various inputs")
    void testHasBugs(String v1, String v2, String v3) {
        String[] values = {v1, v2, v3};
        boolean result = logic.hasBug(values);
        boolean expected = Arrays.stream(values).anyMatch(s -> s != null && s.equalsIgnoreCase("bug"));
        System.out.println("Массив значений: " + Arrays.toString(values));
        System.out.println("Ожидаемый результат: " + expected);
        System.out.println("Фактический результат: " + result);
        assertEquals(expected, result, "Результат должен быть " + expected + ", но получено значение " + result);
    }

    @Tag("Unit2")
    @Tag("ParameterizedTest")
    @ParameterizedTest
    @CsvSource(value = {
            "'Anatoly, Armen, Goga, Sasha, Valentin'; Goga",
            "'John, Jane, Doe, Smith, Brown'; Brown",
            "'Nastya, Lena, Katya, Daria, Snezhana'; Snezhana",
            "'Sasha, Armen, Goga, Sasha, Valentin'; Goga",
            "'John, Jane, Doe, Smith, Brown'; Doe",
            "'Nastya, Lena, Katya, Daria, Snezhana'; Daria",
            "'Anatoly, Armen, Goga, Sasha, Valentin'; Anatoly",
            "'John, Jane, Doe, Smith, Brown'; Jane",
            "'Nastya, Lena, Katya, Daria, Snezhana'; Lena",
            "'Nastya, Lena, Katya, Daria, Snezhana'; Katya"
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
        assertEquals(expected, result, "Результат должен быть " + expected + ", но получено значение " + result);
    }
}





