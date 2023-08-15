#include <stdio.h>
#include <stdbool.h>
int main(void){
    int userInteger;
    int setCount = 0;
    char contInput;  
    int i = 0;
    printf("Welcome to the CountOnes program.\n");
    while (1) {
        printf("Please enter a number: ");
        scanf("%d", &userInteger);
        for(i = 0; i < 32; i++){
            if ((userInteger & 1) == 1) { 
                setCount++;
            }
            userInteger = userInteger >> 1;
        } 
        printf("The number of bits set is: %d\n", setCount);
        printf("Continue (y/n)?: ");
        scanf(" %c", &contInput);
        if (contInput == 'n') {
            break;
        }
        setCount = 0;
        printf("\n");
    }
    printf("Exiting\n");
    return 0;
}
