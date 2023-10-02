#include <stdio.h>
#include <stdlib.h>
void string_repeater();
int main(int argc, char* argv[]) { //argv = {file, count, string}
    char *endptr;
    long num;
    num = strtol(argv[1], &endptr, 10);
    string_repeater(num, argv[2]);
}
void string_repeater(long num_repititions, char* string) {
    for (int i = 0; i < (int) num_repititions; i++) {
        printf("%s\n", string);
    }
}
