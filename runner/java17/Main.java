import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int[] arr = new int[10000000];
        for (int i = 0; i < 10000000; i++) {
            arr[i] = i;
        }
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        System.out.print(a + b);
    }
}