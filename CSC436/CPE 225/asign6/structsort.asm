.globl swap
.globl selectionSort
.globl printArray

#struct def'n for reference
#struct studentNode {
#   char name[6];
#   int studentid;
#   int coursenum;
#};

 
#/* Recursive function to perform selection sort on subarray `arr[i…n-1]` */
#void selectionSort(studentNode arr[], int i, int n) {
selectionSort:
#callee setup goes here

	addi sp, sp -16		# Create sandbox on stack for subroutine
	sw ra, 12(sp)		# Save the ra on the stack
	sw s2, 8(sp)		# Save the contents of s2 on the stack
	sw s1, 4(sp)		# Save the contents of s1 on the stack
	sw s0, 0(sp)		# Save the contents of s0 on the stack
    
#    /* find the minimum element in the unsorted subarray `[i…n-1]`
#    // and swap it with `arr[i]`  */
#    int j;
#    int min = i;
	mv s0, a0		# Move array in a0 to s0
	mv s1, a1		# Save i into s1 
	mv s2, a2		# Save n in s2
	
	# Initialize min and j
	mv t0, a1		# int min = i;
	and t1, t1, zero	# Clear t1
	addi t1, s1, 1 		# j = i + 1;

#    for (j = i + 1; j < n; j++)    {
for1:
forloop1:
	bge t1, s2, endfor1	# j < n
#        /* if `arr[j]` is less, then it is the new minimum */
#        if (arr[j].studentid < arr[min].studentid) {
if1:
# The index tells us what struct to look at, we then need to look at the id of that specific struct
# The following code turn the index into an offset so (t5 and t6) will contain the struct at the index
# we know need to access the actual struct element.

	# Turning index of J into an offset:
	addi t2, s0, 0		# Put base addy of array into t2
	slli t3, t1, 4		# Convert index at J into an offset
	add t4, t3, t2		# Add offset to base address (Aligns the pointer with the first element in the struct)
	addi t4, t4, 8		# Now look at the second element in the struct
	lw t5, 0(t4)		# read array[j]
	
	
	# Turning index of min into an offset:
	addi t2, s0, 0		# Put base addy of array into t0
	slli t3, t0, 4		# Convert index at MIN into an offset( Aligns the pointer with the first element in the struct)
	add t4, t3, t2		# Add offset to base address 
	addi t6, t6, 8		# Now look at the second element in the struct
	lw t6, 0(t4)		# read array[min]
	
	bgt t5, t6, endif1	# if (arr[j].studentid < arr[min].studentid) {
	mv t0, t1		# min = j;    /* update the index of minimum element */
	

endif1:
	addi t1, t1, 1		# j++
	b for1

endfor1:

#    }
 
#    /* swap the minimum element in subarray `arr[i…n-1]` with `arr[i] */
#caller setup goes here
	mv a0, s0		# array
	mv a1, t0		# min
	mv a2, s1		# i
				
	jal ra, swap		# swap(arr, min, i);




#caller teardown goes here (if needed)
 
#    if (i + 1 < n) {
if2:
	addi t0, s1, 1		# t0 = i + 1
	bgt t0, s2, endif2	# if (i + 1 < n) {

#caller setup goes here

#        selectionSort(arr, i + 1, n);
	mv a0, s0		# Load array from sack into a0
	mv a1, t0		# move i + 1 into a1
	mv a2, s2		# Move n into a2
	jal ra, selectionSort

#caller teardown goes here (if needed)


#    }
endif2:
	lw ra, 12(sp)		# Load the ra before the ret.
	lw s2, 8(sp)
	lw s1, 4(sp)
	lw s0, 0(sp)
	addi sp, sp, 16
	ret
    
#callee teardown goes here (if needed)

 
#/* Function to print `n` elements of array `arr` */ 
#void printArray(studentNode arr[], int n) {
printArray:
#callee setup goes here

#    int i;
	li t0, 0
	mv t1, a0 #t1 holds pointer for arr

#    for (i = 0; i < n; i++) { #a0: arr, a1: int n
for2:
	bge t0, a1, endfor2

forloop2:

#use ecalls to implement printf
#        printf("%d ", arr[i].studentid); #8 offset
	lw t2, 8(t1)
	mv a0, t2
	li a7, 1
	ecall
	
	li a0, '\n'
	li a7, 11
	ecall
	
#        printf("%s ", arr[i].name); #0 offset
	#lw t3, t1
	mv a0, t1
	li a7, 4
	ecall
	
	li a0, '\n'
	li a7, 11
	ecall

#        printf("%d\n", arr[i].coursenum); #12 offset
	lw t4, 12(t1)
	mv a0, t4
	li a7, 1
	ecall
	
	li a0, '\n'
	li a7, 11
	ecall
	ecall
	
	addi t0, t0, 1 #i++
	addi t1, t1, 16 #pointer moves up by 16 bits, to look at next student in the struct
	b for2

#    }
endfor2:

#caller teardown goes here
	ret

#}
 
 

#/* Utility function to swap values at two indices in an array*/
#void swap(studentNode arr[], int i, int j) {
swap:
#caller setup goes here
	addi t2, a0, 0		# Put base addy of array into t2
	slli t3, a1, 4		# Convert index at I into an offset
	add t4, t3, t2		# Add offset to base address
	#lw t5, 0(t4)		# read array[i]
#    studentNode temp = arr[i];

	addi t2, a0, 0 		# Put base addy of array into t0
	slli t3, a2, 4		# Convert index at MIN into an offset
	add t5, t3, t2		# Add offset to base address
	#lw t6, 0(t4)		# read array[min]

#    arr[i] = arr[j];
	# Swap name
	lw t1, (t4)		# Load address of t4 (array[i]) into t1
	lw t2, (t5)		# Load address of t5 (array[min]) into t2
	sw t1, (t5)		# Store val in t1 into address at t5
	sw t2, (t4)		# Store val in t2 into address at t4
	
	# Swap byte padding
	lw t1, 4(t4)		# Load address of t4 + 4(array[i]) into t1
	lw t2, 4(t5)		# Load address of t5 + 4(array[min]) into t2
	sw t1, 4(t5)		# Store val in t1 into address at t5
	sw t2, 4(t4)		# Store val in t2 into address at t4
	
	# Swap id
	lw t1, 8(t4)		# Load address of t4 + 8 (array[i]) into t1
	lw t2, 8(t5)		# Load address of t5 + 8(array[min]) into t2
	sw t1, 8(t5)		# Store val in t1 into address at t5
	sw t2, 8(t4)		# Store val in t2 into address at t4
	
	# Swap coursenum
	lw t1, 12(t4)		# Load address of t4 + 12 (array[i]) into t1
	lw t2, 12(t5)		# Load address of t5 + 12 (array[min]) into t2
	sw t1, 12(t5)		# Store val in t1 into address at t5
	sw t2, 12(t4)		# Store val in t2 into address at t4
	ret


#    arr[j] = temp;


#caller teardown goes here


#}

	
	
	


	
