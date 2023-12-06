#include <iostream>
#include <vector>
using namespace std;

int main() {
    vector<int> arr = {1,2,3};
    for (int i = 0; i < 100000000; i++) {
        arr.push_back(i);
    }
}