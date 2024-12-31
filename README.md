# online-judge
spring boot 에서 docker 를 사용해 사용자가 작성한 코드를 받아서 독립적인 환경에서 안전하게 코드를 실행합니다. 

코드를 실행해서 출력물을 확인하고 실행시간(ms 단위) 과 메모리 사용량 (kb 단위) 를 알 수 있습니다.
 
컴파일 에러, 런타임 에러, 메모리 초과, 시간 초과가 발생했는지 알 수 있으며 에러가 발생한 경우 에러 로그를 알 수 있습니다.

# 프리뷰
input 은 아래 파일로 동일 할 때 여러 언어에서 다양한 상황의 코드를 실행합니다.
```
10 20
```

## 파이썬
### 작동 성공
실행 코드
```python
a, b = map(int, input().split())  # 입력을 받아 공백으로 나눠 정수로 변환
print(a + b)  # 두 수의 합을 출력
```
출력
```
{"success": true, "timeUsageMs": 0, "memoryUsageKb": 8600, "output": "30\n"}
```

### 런타임 에러
실행 코드
```python
arr = [1,2,3]
x = arr[5]
```
출력
```
{"success": false, "error": "RUNTIME_ERROR", "details": "Traceback (most recent call last):\n  File \"/app/Main.py\", line 3, in <module>\n    x = arr[5]\n        ~~~^^^\nIndexError: list index out of range\n"}
```

### 시간 초과
실행 코드
```python
for _ in range(1000000000) :
    pass
```
출력
```
{"success": false, "error": "TIMEOUT_ERROR", "details": "Execution exceeded time limit of 1 seconds."}
```

###  메모리 초과
실행 코드
```python
arr = [0] * 1000000000
```
출력
```
{"success": false, "error": "MEMORYOUT_ERROR", "details": "Process exceeded memory limit."}
```

## C++
### 작동 성공
실행코드
```cpp
#include <iostream>
using namespace std;

int main() {
    int a, b;
    cin >> a >> b;
    cout << a + b << endl;
}
```
출력
```
{"success": true, "timeUsageMs": 0, "memoryUsageKb": 2968, "output": "30\n"}
```
### 컴파일 에러
실행코드
```cpp
#include <iostream>
using namespace std;

int main() {
    int a, b;
    cin >> a >> b
    cout << a + b << endl;
}
```
출력
```
{"success": false, "error": "COMPILE_ERROR", "details": "Main.cpp: In function 'int main()':\nMain.cpp:6:18: error: expected ';' before 'cout'\n    6 |     cin >> a >> b\n      |                  ^\n      |                  ;\n    7 |     cout << a + b << endl;\n      |     ~~~~          \n"}
```
### 런타임 에러
실행코드
```cpp
#include <iostream>
#include <vector>

using namespace std;

int main() {
    int a, b;

    // 배열 인덱스 초과
    vector<int> array(10); // 크기가 10인 벡터 생성
    for (int i = 0; i <= 10; i++) { // `i <= 10`은 잘못된 접근
        array[i] = i; // 10번째 인덱스에 접근하면 런타임 에러 발생
    }

    cin >> a >> b;
    cout << a + b << endl;

    return 0;
}
```
출력
```
{"success": false, "error": "RUNTIME_ERROR", "details": "Fatal glibc error: malloc.c:2599 (sysmalloc): assertion failed: (old_top == initial_top (av) && old_size == 0) || ((unsigned long) (old_size) >= MINSIZE && prev_inuse (old_top) && ((unsigned long) old_end & (pagesize - 1)) == 0)\n"}
```
###  시간 초과
실행코드
```cpp
#include <iostream>
using namespace std;

int main() {
    int a, b;
    for (int i=0;i<1000000000;i++) {
        a = i;
    }
    cin >> a >> b;
    cout << a + b << endl;
}
```
출력
```
{"success": false, "error": "TIMEOUT_ERROR", "details": "Execution exceeded time limit of 1 seconds."}
```
### 메모리 초과
실행코드
```cpp
#include <iostream>
#include <vector>

using namespace std;

int main() {
    int a, b;

    // 256MB 이상의 메모리를 할당하는 벡터
    vector<int> largeArray;
    try {
        largeArray.resize(200000000);
    } catch (bad_alloc&) {
        cerr << "Memory allocation failed" << endl;
        return 1;
    }

    // 메모리 할당된 벡터를 사용하는 루프
    for (int i = 0; i < largeArray.size(); i++) {
        largeArray[i] = i;
    }

    cin >> a >> b;
    cout << a + b << endl;

    return 0;
}
```
출력
```
{"success": false, "error": "MEMORYOUT_ERROR", "details": "Process exceeded memory limit."}
```
## Java
### 작동 성공
실행코드
```java
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
```
출력
```
{"success": true, "timeUsageMs": 20, "memoryUsageKb": 40876, "output": "30\n"}
```
### 컴파일 에러
실행코드
```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 두 정수를 입력받기
        int a = scanner.nextInt()
        int b = scanner.nextInt();

        // 두 정수의 합 출력
        System.out.println(a + b);

        scanner.close(); // Scanner 리소스 해제
    }
}
```
출력
```
{"success": false, "error": "COMPILE_ERROR", "details": "Main.java:8: error: ';' expected\n        int a = scanner.nextInt()\n                                 ^\n1 error\n"}
```
### 런타임 에러
실행코드
```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 두 정수를 입력받기
        int a = scanner.nextInt();
        int b = scanner.nextInt();

        // 런타임 에러
        int c = 1 / 0;

        // 두 정수의 합 출력
        System.out.println(a + b);

        scanner.close(); // Scanner 리소스 해제
    }
}
```
출력
```
{"success": false, "error": "RUNTIME_ERROR", "details": "Exception in thread \"main\" java.lang.ArithmeticException: / by zero\n\tat Main.main(Main.java:12)\n"}
```
###  시간 초과
실행코드
```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 두 정수를 입력받기
        int a = scanner.nextInt();
        int b = scanner.nextInt();

        // 시간 초과
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 두 정수의 합 출력
        System.out.println(a + b);

        scanner.close(); // Scanner 리소스 해제
    }
}
```
출력
```
{"success": false, "error": "TIMEOUT_ERROR", "details": "Execution exceeded time limit of 1 seconds."}
```
### 메모리 초과
실행코드
```java
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
```
출력
```
{"success": false, "error": "MEMORYOUT_ERROR", "details": "Process exceeded memory limit."}

```
