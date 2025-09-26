# Andrew Okerlund
#Parth Verma


	.globl main
	.text
main:
	#s0 Contians K
	#s1 Contains C
	#s2 contains Array
	#s4 contains total num of char
	#s5 contains one char at a time
	

infinite:
	#mv s4, a0
	#li t0, 1
	#beq s4, t0, infinite_end
	#Print "Enter word: "
	la a0, enter
	jal ra, printString
	
	#Jump to readstring subroutine
	la a0, str			#Load the array into a0 
	jal ra, readstring		#Read String subroutine
	
	#Print the "Original word"
	la a0, og
	jal ra, printString
	#Print the array
	la a0, str
	jal ra, printString
	#print newline
	la a0, nl
	jal ra, printString
	
	#Print "Alphabetized word: "
	la a0 alpha
	jal ra, printString
in_it:
	#Initialize num and prev as 0.
	li s0, 0 		#k = 0;
	li s1, 0		#C
	
for:
	li t0, 127
	beq s0, t0, for_end	#for(k = 0; k < 127; k++) 
	la s2, str 		#s2 = Array MANIPULATED BY C
while:	#LINE 61
	li t0, 0		#Load 0 into t0
	lb s5, (s2) 		#S5 contains the first byte from S2
	beq t0, s5, while_end 	#While(str[c] != 0)
if1:
	bne s5, s0, if_end	#if (str[c] == k)
	mv a0, s5
	jal ra, printChar	#printf("%c", str[c]);

if_end:
	addi s2, s2, 1		#c++
	b while		
while_end:
	addi s0, s0, 1		#K++
	b for
for_end:
	#Print exit and call Exiting Ecall.
	b infinite
	
	la a0, nl
	jal ra, printString
	
	la a0, ext
	jal ra, printString
	jal ra, Exiting
	
	.data
str: 	.space 160 
enter: 	.string "\nEnter word: "
og:	.string "Original word: "
nl:	.string "\n"
alpha:	.string "Alphabetized word: "
ext: 	.string "Exiting"
