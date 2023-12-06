#include <iostream>
#include <vector>
using namespace std;

int main() {
    vector<int> arr;
    for (int i=0;i<100000000;i++) {
        arr.push_back(i);
    }
    int a, b; cin >> a >> b;
    cout << a + b;
}