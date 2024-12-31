import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 대규모 ArrayList 생성 (256MB 이상 메모리 사용)
        ArrayList<int[]> largeArray = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int[] chunk = new int[16 * 1024 * 1024]; // 약 64MB (16M * 4 bytes)
            for (int j = 0; j < chunk.length; j++) {
                chunk[j] = j; // 데이터 채우기
            }
            largeArray.add(chunk);
        }
        // 두 정수를 입력받기
        int a = scanner.nextInt();
        int b = scanner.nextInt();

        // 두 정수의 합 출력
        System.out.println(a + b);

        scanner.close(); // Scanner 리소스 해제
    }
}