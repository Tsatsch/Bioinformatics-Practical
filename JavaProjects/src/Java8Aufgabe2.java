
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

    public class Java8Aufgabe2 {

        static class Innerclass implements Computer{        //innerclass for addition
            @Override
            public double compute(double a, double b) {
                return a+b;
            }
        }

        public static void main(String[] args) throws IOException {
            double[] a = new double[10];
            double[] b = new double[10];
            Random rnd = new Random();
            for (int i = 0; i < a.length; i++) {
                a[i] = rnd.nextDouble();
                b[i] = rnd.nextDouble();
            }

            Innerclass add = new Innerclass();              //using the innerclass

            Computer sub = new Computer() {                 //anonymous class for subtraction
                @Override
                public double compute(double a, double b) {
                    return a-b;
                }
            };

            Computer mult = (double first, double second) -> {  //lambda for multiplication
                return first * second;
            };

            Computer div = Java8Aufgabe2::division;             //method-reference for division (i created an own division method)

            System.out.println(Arrays.toString(computeAll(a, b, add)));
            System.out.println(Arrays.toString(computeAll(a, b, sub)));
            System.out.println(Arrays.toString(computeAll(a, b, mult)));
            System.out.println(Arrays.toString(computeAll(a, b, div)));
        }

        private static double[] computeAll(double[] a, double[] b, Computer computer) {
            double[] re = new double[a.length];
            for (int i = 0; i < a.length; i++) {
                re[i] = computer.compute(a[i], b[i]);
            }
            return re;
        }

        public static double division (double a, double b){     // my own division method for method-refernce
            return a/b;
        }

        private interface Computer {
            double compute(double a, double b);
        }
    }
