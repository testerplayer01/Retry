import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;

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
        static int[] generateRandomArray () {
            int[] scores = new int[10];
            Random random = new Random();
            for (int i = 0; i < scores.length; i++) {
                scores[i] = random.nextInt(0, 100);
            }
            return scores;
        }



//Задача №2


@Test
void testIsEven2() {
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
        void testCheckAccess2() {
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
        void testGetGrade3(int score) {
            System.out.println("========================================");
            System.out.println("Test method start");
            String grade = logic.getGrade(score);
            String ocenka;
            if (score >= 0 && score <= 20) {
                ocenka = "E";
            } else if (score >= 21 && score <= 40) {
                ocenka = "D";
            } else if (score >= 41 && score <= 60) {
                ocenka = "C";
            } else if (score >= 61 && score <= 80) {
                ocenka = "B";
            } else if (score >= 81 && score <= 100) {
                ocenka = "A";
            } else {
                ocenka = "Error";
            }
            assertEquals(ocenka, grade);
            System.out.println("Баллы: " + score + ", Оценка: " + grade);
            System.out.println("Test method end");
            System.out.println("========================================");
        }
        static int[] generateRandomArray2 () {
            int[] scores = new int[10];
            Random random = new Random();
            for (int i = 0; i < scores.length; i++) {
                scores[i] = random.nextInt(0, 100);
            }
            return scores;
        }
    }





