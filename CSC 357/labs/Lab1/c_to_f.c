#include <stdio.h>

double convert(double iT_in_c);

int main() {
    double temp_c;
    double temp_f;
    
    //Get user input
    printf("Please enter a temperature, in Celsius: ");
    scanf("%lf", &temp_c);

    temp_f = convert(temp_c);
    
    printf("In Fahrenheit: " "%lf\n", temp_f);
    
    if (temp_f >= 100) {
        printf("Warning: Heat Wave!\n");
    } else if (temp_f >= 80) {
        printf("Hot\n");
    } else if (temp_f >= 41) {
        printf("Normal\n");
    } else if (temp_f >= 10) {
        printf("Cold\n");
    }

    return 0;
}

double convert(double iT_in_c){
    double temp_f = (iT_in_c * 1.8) + 32;
    return temp_f;
}
