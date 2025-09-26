.text
.globl main
main:
	andi	t1,t1, 0  # clear t1
	andi	t4,t4, 0  # clear t4
	addi	t4,t4, 10 # load t4 with #10
	la	t2, tbl       # load @tbl
loop:
	lw	t3,(t2)       # load the next number to be added
	addi	t2,t2, 4  # increment the pointer
	add	t1,t1,t3      # add the next number to the sum
	addi	t4,t4,-1  # decrement the counter
	bgtz	t4, loop  # do it again if the counter is not yet zero
	
