import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class W2 {

    @Test
    void test(){
        File someFile = new File("1.txt");
        String[] strings = someFile.list();
        try {
            for (String str : strings) {
                System.out.println(str);
            }
        } catch (NullPointerException e){
            System.out.println("Obrabotka ispolnyaetsya");
            throw e;
        }
        System.out.println("End of code");
    }

    @Test
    public void test22(){
        File someFile = new File("1.txt");
        String[] strings = someFile.list();
        try {
            new FileReader(someFile).read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("End of code");
    }

    @Test
    public void test222(){
        File someFile = new File("1.txt");
        String[] strings = someFile.list();
        throw new RuntimeException("An error occurred");
    }

    @Test
    public void tesst(){
        Integer dig = new Random().nextInt(0, 10);
        Integer expectedResult = 5;
//        assert dig.equals(expectedResult);
//        Assertions.assertEquals(expectedResult, dig);
        assertEquals(expectedResult, dig);
  ;  }

    @Test
    public void test3(){
        Random random = new Random();
        String actualResult = "";
        for (int i = 0; i < 10; i++){
            actualResult = actualResult + random.nextInt(20, 70);
        }
        org.assertj.core.api.Assertions.assertThat(actualResult)
                .as("String must be equals")
                .isEqualToIgnoringCase("343434343455");
    }

//    @Test
//    public void test34(){
//        Random random = new Random();
//        Integer actualResult = random.nextInt(1, 10);
//        Integer expectedBound = 10;
//
//        Assertions.assertThat(actualResult)
//                .as("String must be equals")
//                .isLessThan(expectedBound);
//
//        org.assertj.core.api.Assertions.assertTrue(actualResult < expectedBound);
//    }
}
