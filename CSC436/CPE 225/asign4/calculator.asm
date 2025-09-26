	.global main
	.text
	#t0 numOperations
	#t1 firstNum
	#t2 secondNum
	#t3 operation
	#t4 will hold the number that coresponds to the current operator in the if statements
	#t5 the answer
	
main:
#	int numOperations = 0;
	li, t0, 0 #numOperations
#    	int firstNum;
#    	int secondNum;
#    	int operation;
#    	char contLoop;
	la a0, welcomeMessage
	jal ra, printstring
	
	la a0, operationMenu
	jal ra, printstring
	
	while:
#       	printf("Number of operations performed: %d\n", numOperations);
		la a0, numOperations
		jal ra, printstring
		
		mv a0, t0
		jal ra, printint 
		
		la a0, newLine
		jal ra, printstring
		
#       	 printf("Enter number: ");
		la a0, enterFirst
		jal ra, printstring
		
#        	scanf("%d", &firstNum);
		jal ra, readint
		mv t1, a0
		
		#la a0, newLine
		#jal ra, printstring
		
#        	printf("Enter second number: ");
		la a0, enterSecond
		jal ra, printstring
		
#        	scanf("%d", &secondNum);
		jal ra, readint
		mv t2, a0
		
		#la a0, newLine
		#jal ra, printstring
		
#        	printf("Select operation: ");
		la a0, selectOp
		jal ra, printstring
		jal ra, readint
		mv t3, a0
		
#        	scanf("%d", &operation);
		jal ra, readint
		la a0, newLine
		jal ra, printstring
		
		switchStart:
			if1:
#				printf("Result: %d\n", addnums(firstNum, secondNum));
				li t4, 1 #check operator
				bne t3, t4, if2
				
				mv a0, t1 #set arg's
				mv a1, t2
				
				jal ra, addnums
				mv t5, a0
				#print result: answer
				la a0, resultStr
				jal ra, printstring
				mv a0, t5
				jal ra, printint
#                		numOperations++;
				addi t0, t0, 1
#                		break;
				b switchEnd
			if2:
				li t4, 2
				bne t3, t4, if3
				
				mv a0, t1 #set arg's
				mv a1, t2
				
				jal ra, subnums
				mv t5, a0
				#print result: answer
				la a0, resultStr
				jal ra, printstring
				mv a0, t5
				jal ra, printint
#                		numOperations++;
				addi t0, t0, 1
#                		break;
				b switchEnd
			if3:
				li t4, 3
				bne t3, t4, if4
				
				mv a0, t1 #set arg's
				mv a1, t2
				
				jal ra, multnums
				mv t5, a0
				#print result: answer
				la a0, resultStr
				jal ra, printstring
				mv a0, t5
				jal ra, printint
				
#                		numOperations++;
				addi t0, t0, 1
#                		break;
				b switchEnd
			if4:
				li t4, 4
				bne t3, t4, if5
				
				mv a0, t1 #set arg's
				mv a1, t2
				
				jal ra, divnums
				mv t5, a0
				#print result: answer
				la a0, resultStr
				jal ra, printstring
				mv a0, t5
				jal ra, printint
#                		numOperations++;
				addi t0, t0, 1
#                		break;
				b switchEnd
			if5:
				li t4, 3
				bne t3, t4, if6
				
				mv a0, t1 #set arg's
				mv a1, t2
				
				jal ra, andnums
				mv t5, a0
				#print result: answer
				la a0, resultStr
				jal ra, printstring
				mv a0, t5
				jal ra, printint
#                		numOperations++;
				addi t0, t0, 1
#                		break;
				b switchEnd
			if6:
				li t4, 3
				bne t3, t4, if7
				
				mv a0, t1 #set arg's
				mv a1, t2
				
				jal ra, ornums
				mv t5, a0
				#print result: answer
				la a0, resultStr
				jal ra, printstring
				mv a0, t5
				jal ra, printint
#                		numOperations++;
				addi t0, t0, 1
#                		break;
				b switchEnd
			if7:
				li t4, 3
				bne t3, t4, if8
				
				mv a0, t1 #set arg's
				mv a1, t2
				
				jal ra, xornums
				mv t5, a0
				#print result: answer
				la a0, resultStr
				jal ra, printstring
				mv a0, t5
				jal ra, printint
#                		numOperations++;
				addi t0, t0, 1
#                		break;
				b switchEnd
			if8:
				li t4, 3
				bne t3, t4, if9
				
				mv a0, t1 #set arg's
				mv a1, t2
				
				jal ra, lshiftnums
				mv t5, a0
				#print result: answer
				la a0, resultStr
				jal ra, printstring
				mv a0, t5
				jal ra, printint
#                		numOperations++;
				addi t0, t0, 1
#                		break;
				b switchEnd
			if9:
				li t4, 3
				bne t3, t4, if9
				
				mv a0, t1 #set arg's
				mv a1, t2
				
				jal ra, rshiftnums
				mv t5, a0
				#print result: answer
				la a0, resultStr
				jal ra, printstring
				mv a0, t5
				jal ra, printint
#                		numOperations++;
				addi t0, t0, 1
#                		break;
				b switchEnd
			invalid:
#                		numOperations++;
				addi t0, t0, 1
#				printf("Result: Invalid Operation\n");

#               		break;
		switchEnd:
#       		printf("Continue (y/n)?: ");
			
#        		scanf(" %c", &contLoop);
#       		printf("\n");
#       		if (contLoop == 'n') {
#           		break;
				
			
		
	whileEnd:
#   		printf("Number of operations performed: %d\n", numOperations);
		la a0, numOperations
		jal ra, printstring
		
		mv a0, t0
		li a7, 1 #prints the int after the string
		ecall 
#   		printf("Exiting\n");	
		la a0, exiting
		jal ra, printstring
	
		jal ra, exit0
.data
welcomeMessage:
	.string "Welcome to the Calculator program.\n"
operationMenu:
	.string "Operations - 1:add 2:subtract 3:multiply 4:divide 5:and 6:or 7:xor 8:lshift 9:rshift\n\n"
numOperations:
	.string "Number of operations performed: "
enterFirst:
	.string "Enter number: "
enterSecond:
	.string "Enter second number: "
selectOp:
	.string "Select operation: "
resultStr:
	.string "Result: "
continue:
	.string "Continue (y/n)?: "
exiting:
	.string "Exiting\n"
newLine:
	.string "\n"

#int main(void) {
#    int numOperations = 0;
#    int firstNum;
#    int secondNum;
#    int operation;
#    char contLoop;

#    printf("Welcome to the Calculator program.\n");
#    printf("Operations - 1:add 2:subtract 3:multiply 4:divide 5:and 6:or 7:xor 8:lshift 9:rshift\n\n");
#    while(1) {
#        printf("Number of operations performed: %d\n", numOperations);
#        printf("Enter number: ");
#        scanf("%d", &firstNum);
#        printf("Enter second number: ");
#        scanf("%d", &secondNum);
#        printf("Select operation: ");
#        scanf("%d", &operation);
#        switch (operation){
#            case 1:
#                printf("Result: %d\n", addnums(firstNum, secondNum));
#                numOperations++;
#                break;
#            case 2:
#                printf("Result: %d\n", subnums(firstNum, secondNum));
#                numOperations++;
#                break;
#            case 3:
#                printf("Result: %d\n", multnums(firstNum, secondNum));
#                numOperations++;
#                break;
#            case 4:
#                printf("Result: %d\n", divnums(firstNum, secondNum));
#                numOperations++;
#                break;
#            case 5:
#                printf("Result: %d\n", andnums(firstNum, secondNum));
#                numOperations++;
#                break;
#            case 6:
#               printf("Result: %d\n", ornums(firstNum, secondNum));
#                numOperations++;
#                break;
#            case 7:
#                printf("Result: %d\n", xornums(firstNum, secondNum));
#                numOperations++;
#                break;
#            case 8:
#                printf("Result: %d\n", lshiftnums(firstNum, secondNum));
#                numOperations++;
#                break;
#            case 9:
#                /*result = rshiftnums(firstNum, secondNum);*/
#                printf("Result: %d\n", rshiftnums(firstNum, secondNum));
#                numOperations++;
#                break;   
#            default:
#                printf("Result: Invalid Operation\n");
#                numOperations++;
 #               break;
#        }
 #       printf("Continue (y/n)?: ");
#        scanf(" %c", &contLoop);
 #       printf("\n");
 #       if (contLoop == 'n') {
 #           break;
 #       }
 #   }
 #   printf("Number of operations performed: %d\n", numOperations);
 #   printf("Exiting\n");
 #   return 0;
#}
