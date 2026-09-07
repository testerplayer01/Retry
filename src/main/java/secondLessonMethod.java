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
    }