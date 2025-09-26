 include <stdio.h>
 int main(){
 	char n[10];
 	int x;
       	char *p = n;
	printf("Enter a three-digit nonnegative number: ");
       	scanf("%s",n);
	
	for(int i = 0; i < 10; i++){
		x = x + ((int)p + i);
	}
      	printf("The number is %d\n",x);
	return 0;
}
