import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 두 정수를 입력받기
        int a = scanner.nextInt();
        int b = scanner.nextInt();

        // 두 정수의 합 출력
        System.out.println(a + b);

        scanner.close(); // Scanner 리소스 해제
    }
}