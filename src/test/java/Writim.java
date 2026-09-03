import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Map;
import java.util.Random;


@Tag("Smoke")
public class Writim{
    private boolean isNotEven(int value){
        return !(value % 2 == 0);
    }

    @Test
    @Tag("Smoke")
    void envTest1(){
        System.out.println("test circuit: " + System.getProperty("CIRCUIT!"));
        System.out.println("test account: " + System.getenv("TEST_LOGIN"));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 10025, 1000})
    void isNotEvenTest(int value){
        System.out.println("Test with value = " + value);
        if (isNotEven(value) == true){
            System.out.println("Нечетное!");
        } else {
            System.out.println("Четное!");
        }
    }

    @ParameterizedTest
    @CsvSource({"0, false", "1, true", "1000, false", "2, true"})
    void isNotEvenSecondTest(int value, boolean expectedResult){
        if (isNotEven(value) == expectedResult){
            System.out.println("Прошло");
        } else {
            System.out.println("Не прошло");
        }
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"Tester"})
    void roleTest(Role role){
        switch (role){
            case Admin -> {
                System.out.println("Login as ADMIN");
            }
            case Tester -> {
                System.out.println("Login as TESTER");
            }
            case Observer -> {
                System.out.println("Can`t run test as OBSERVER");
            }
        }
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void TenDivTest(int value){
        System.out.println(value % 10);
    }

     static List<Integer> getTestData(){
        return List.of(2, 43, 2342, 23, 4588);
    }

//    @RepeatedTest(10)
//    void testAgeMethod(){
//        Random random = new Random();
//        int age = random.nextInt(0, 100);
//        boolean actualResult = checkAge(age);
//        System.out.println(actualResult);
//    }
}

