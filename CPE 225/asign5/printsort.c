/*Andrew Okerlund
Parth Verma*/
#include <stdio.h>  
#include <stdlib.h>

/*void printWord(char str[]){
    int num = 0;
    printf("Original word: ");
    while (str[num] != 0){
        printf("%c", str[num]);
        num++;
    }
}*/

int readstring(char str[]){
    char enter; 
    int i = 0;
    int total = 0;
    printf("\nEnter word: " );
    while (enter != 10){            
        enter = getchar();
        if(enter != 10){
            str[i] = enter;
            
           /* printf("%d\n", str[i]);*/
        }
        total += 1;
        i++;
    }
    /*printf("%d\n", total);*/
    str[i - 1] = '\0';
    return total;
}

int main(void)
{   
    char str[19]; /*list of chars - the string*/
    int cond;
    int k;  

    while(cond != 1){
        cond = readstring(str); 
        if (cond == 1){
            break;
        } 

        printf("Original Word: " );
        printf("%s \n", str);

        printf("Alphabetized word: ");
            
        for(k = 0; k < 127; k++) {
            int c = 0;
            while(str[c] != '\0') {
                /*printf("%c ", str[c]);*/
                if (str[c] == k){
                /*printf("\nif");*/ 
                    printf("%c", str[c]);
                }
                c++;
            }    
        }
    }
    printf("\nExiting\n");
    printf("\n");
    return 0;
}
/*hello*/