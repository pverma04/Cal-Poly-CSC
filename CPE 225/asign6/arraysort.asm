.globl swap 
.globl selectionSort
 			
 # I want a pointer that points to where the stack pointer is pointing at the bottom of the array
 # I am going to incriment the pointer to the stack pointer and not actually move the stack pointer
 # IS end of array identified when offset is equal to number of elements x size of elements.
 
# ??? Might need to save a1 
 
# addi a0, a0 4 
# IF a0 contains the sp (passed in subroutine call) and i add 4 to it am i manipulating the real stack or just changing the pointer?
 
# void selectionSort(int arr[], int i, int n){
# a0 is arr[], a1 is 0(i), a2 is 10(n)
selectionSort:
# callee setup goes here
	#addi t0, a0, 12		# align the stack pointer at the first value of the array
	addi sp, sp -16		# Create sandbox on stack for subroutine
	sw ra, 12(sp)		# Save the ra on the stack
	sw s2, 8(sp)		# Save the contents of s2 on the stack
	sw s1, 4(sp)		# Save the contents of s1 on the stack
	sw s0, 0(sp)		# Save the contents of s0 on the stack
	

    #/* find the minimum element in the unsorted subarray `[i…n-1]`
    #// and swap it with `arr[i]`  */
     
    # a0 is arr[], a1 is 0(i), a2 is 10(n)
    # s0 contians array
    # s1 contains i
    # s2 contains n
    
    # t0 contains min
    # t1 contains j

# Min and j can be in t reg not s
	#int j;
	mv s0, a0		# Move array in a0 to s0
	mv s1, a1		# Save i into s1 
	mv s2, a2		# Save n in s2
	
	mv t0, a1		# int min = i;
	
	
    #    for (j = i + 1; j < n; j++)    {
	and t1, t1, zero	# Clear s2
	addi t1, s1, 1 		# j = i + 1;
for:	
forloop:
	bge t1, s2, endfor	# j < n MAY NEED TO CHANGE A2 to an s reg
	
#        /* if `arr[j]` is less, then it is the new minimum */

if1:	
	#Turning index of J into an offset:
	addi t2, s0, 0		# Put base addy of array into t2
	slli t3, t1, 2		# Convert index at J into an offset
	add t4, t3, t2		# Add offset to base address
	lw t5, 0(t4)		# read array[j]
	
	#Turning index of min into an offset:
	addi t2, s0, 0		# Put base addy of array into t0
	slli t3, t0, 2		# Convert index at MIN into an offset
	add t4, t3, t2		# Add offset to base address
	lw t6, 0(t4)		# read array[min]
	
	bgt t5, t6, endif1	# if (arr[j] < arr[min]) {
	mv t0, t1		# min = j;    /* update the index of minimum element */
	
endif1:
	addi t1, t1, 1		# j++
	b for
endfor:
	
	
#    /* swap the minimum element in subarray `arr[i…n-1]` with `arr[i] */
#    swap(arr, min, i);

#caller setup and subroutine call for swap goes here.
	mv a0, s0		# array
	mv a1, t0		# min
	mv a2, s1		# i
	
	jal ra, swap

#caller teardown for swap goes here (if needed).
 

if2:
	addi t0, s1, 1		# t0 = i + 1
	bgt t0, s2, endif2	# if (i + 1 < n) {	

#        selectionSort(arr, i + 1, n);
#caller setup and subroutine call for selectionSort goes here.
	mv a0, s0		# Load array from sack into a0
	mv a1, t0		# move i + 1 into a1
	mv a2, s2		# Move n into a2
	jal ra, selectionSort

#caller teardown for selectionSort goes here (if needed).

#    }
endif2:
# add difference back onto the stack
# load values back.
	lw ra, 12(sp)		# Load the ra before the ret.
	lw s2, 8(sp)
	lw s1, 4(sp)
	lw s0, 0(sp)
	addi sp, sp, 16
	ret

	
# callee teardown goes here


#}

 

#/* Utility function to swap values at two indices in an array*/
#void swap(int arr[], int i, int j) {
swap: 
# swap callee setup goes here
	addi t2, a0, 0		# Put base addy of array into t2
	slli t3, a1, 2		# Convert index at I into an offset
	add t4, t3, t2		# Add offset to base address
	#lw t5, 0(t4)		# read array[i]
	
	addi t2, a0, 0 		# Put base addy of array into t0
	slli t3, a2, 2		# Convert index at MIN into an offset
	add t5, t3, t2		# Add offset to base address
	#lw t6, 0(t4)		# read array[min]

#    int temp = arr[i];
#    arr[i] = arr[j];
	lw t1, (t4)		# Load address of t4 (array[i]) into t1
	lw t2, (t5)		# Load address of t5 (array[min]) into t2
	sw t1, (t5)		# Store val in t1 into address at t5
	sw t2, (t4)		# Store val in t2 into address at t4
	ret

#    arr[j] = temp;


# swap callee teardown goes here



#}
