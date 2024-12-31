#include <iostream>
using namespace std;

int main() {
    int a, b;
    for (int i=0;i<500000000;i++) {
        a = i;
    }
    cin >> a >> b;
    cout << a + b << endl;
}