	.global main
	.text
	
	#t0: userInteger
	#t1: int i in the for loop
	#t2: 32 (to end the for loop)
	#t3: 1 (to check bitwise)
	#t4: result of bitwise AND
	#t5: setBitCount
	#t6: char (n/else) to continue the loop
	#a1: n char

main: 
	la a0, welcomeMessage #load string label to a0 - tells us where the string starts
	li a7, 4 #tells a7 to "print string"
	ecall
	whileInit: #start while loop
	la a0, newLine
	li a7, 4
	ecall
	
	li a7, 4 #reset a7 to print String
	la a0, enterNumPrompt
	ecall
	
	li a7, 5 #tells a7 to readInt
	ecall
	
	mv t0, a0 #set t0 to user's inputted number
	
	forInit:
		and t1, zero, zero #int i = 0
		li t2, 32 #upper bound for int i
		li t3, 1 #stores 1 to check userInt & 1 == 1
		li t5, 0 #setBitCounter
		li a1, 'n'
		
	forExpr:
		bge t1, t2, forEnd #if t1 >= t2, end loop
	forBody:
		and t4, t0, t3 #save userInt & 1 (t3) to t4
		bne t4, t3, ifEnd #if t4 /= t3 (1), do not add to count
		addi t5, t5, 1 #if they are equal, increase setBitCount by 1
	ifEnd:
		srl t0, t0, t3 #shift t0 by t3 (1) bits and set in t0
 	forCont:
		addi t1, t1, 1 #i++
		b forExpr #top of the loop
	forEnd:
		la a0, printSetBits #print message
		li a7, 4 #tells a7 to print string
		ecall
		
		mv a0, t5 #ready to print int of setBits
		li a7, 1 #tells a7 to print an int
		ecall
		
		la a0, continue #ready to print continue? message
		li a7, 4 #tells a7 to print string
		ecall
		
		li a7, 12 #ready to read a char
		ecall
		
		mv t6, a0 #set t6 to what's in a0 (the char to continue or not)
		la a0, newLine
		li a7, 4
		ecall
		bne t6, a1, whileInit #start while again
		
	whileEnd:
		la a0, end
		li a7, 4
		ecall
	
	li a7, 10 #ends program
	ecall

	
.data
welcomeMessage:
	.string "Welcome to the CountOnes program.\n"
enterNumPrompt:
	.string "Please enter a number: "
printSetBits:
	.string "The number of bits set is: "
continue:
	.string "\nContinue (y/n)?: "
end:
	.string "Exiting\n"
newLine:
	.string "\n"






#include <stdio.h>
#include <stdbool.h>
#int main(void){
 #   int userInteger;
  #  int setCount = 0;
  #  char contInput;  
  #  int i = 0;
  #  printf("Welcome to the CountOnes program.\n");
  #  while (1) {
  #      printf("Please enter a number: ");
  #      scanf("%d", &userInteger); in t0
  #      for(i = 0; i < 32; i++){
  #          if ((userInteger & 1) == 1) { 
  #              setCount++;
  #          }
  #          userInteger = userInteger >> 1;
  #      } 
  #      printf("The number of bits set is: %d", setCount);
  #      printf("\nContinue (y/n)?: ");
  #      scanf(" %c", &contInput);
  #      if (contInput == 'n') {
  #          break;
  #      }
  #      setCount = 0;
  #      printf("\n");
  #  }
  #  printf("Exiting\n");
  #  return 0;
#}
