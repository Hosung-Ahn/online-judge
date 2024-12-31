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