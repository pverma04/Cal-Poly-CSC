/* 
I am working with Andrew Okerlund on this assignment, he is submitting the ASM code
*/

#include <stdio.h>
#include "operations.h"

int main(void) {
    int numOperations = 0;
    int firstNum;
    int secondNum;
    int operation;
    char contLoop;

    printf("Welcome to the Calculator program.\n");
    printf("Operations - 1:add 2:subtract 3:multiply 4:divide 5:and 6:or 7:xor 8:lshift 9:rshift\n\n");
    while(1) {
        printf("Number of operations performed: %d\n", numOperations);
        printf("Enter number: ");
        scanf("%d", &firstNum);
        printf("Enter second number: ");
        scanf("%d", &secondNum);
        printf("Select operation: ");
        scanf("%d", &operation);
        switch (operation){
            case 1:
                printf("Result: %d\n", addnums(firstNum, secondNum));
                numOperations++;
                break;
            case 2:
                printf("Result: %d\n", subnums(firstNum, secondNum));
                numOperations++;
                break;
            case 3:
                printf("Result: %d\n", multnums(firstNum, secondNum));
                numOperations++;
                break;
            case 4:
                printf("Result: %d\n", divnums(firstNum, secondNum));
                numOperations++;
                break;
            case 5:
                printf("Result: %d\n", andnums(firstNum, secondNum));
                numOperations++;
                break;
            case 6:
                printf("Result: %d\n", ornums(firstNum, secondNum));
                numOperations++;
                break;
            case 7:
                printf("Result: %d\n", xornums(firstNum, secondNum));
                numOperations++;
                break;
            case 8:
                printf("Result: %d\n", lshiftnums(firstNum, secondNum));
                numOperations++;
                break;
            case 9:
                /*result = rshiftnums(firstNum, secondNum);*/
                printf("Result: %d\n", rshiftnums(firstNum, secondNum));
                numOperations++;
                break;   
            default:
                printf("Result: Invalid Operation\n");
                numOperations++;
                break;
        }
        printf("Continue (y/n)?: ");
        scanf(" %c", &contLoop);
        printf("\n");
        if (contLoop == 'n') {
            break;
        }
    }
    printf("Number of operations performed: %d\n", numOperations);
    printf("Exiting\n");
    return 0;
}
