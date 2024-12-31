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