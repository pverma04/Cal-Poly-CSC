#Parth Verma and Andrew Okerlund
.globl init isr

init:
	addi sp, sp -16
	sw ra, 12(sp)
# Set the handler�s address.
	la t0, isr			# get address of handler
	csrrw zero, utvec, t0 		# set utvec (5) to the handler�s address
	
# Enable receiving device interrupts
	li t1, 0x0100			# Should it be 128?
	csrrs zero, 4, t1 		# set bit 8 of uie register 
	
# Enable global interrupt checking.
	csrrsi zero, ustatus, 1		 # set global interrupt enable bit in ustatus (0)
	
# Enable sending of device interrupts
	lw t2, RCR
	lw t5, (t2)
	ori t4, t5, 2
	sw t4, (t2)
	 
# Prints the message �\nInitializing Interrupts\n� to the MMIO window.
	la a0, initstr
	jal printString
	lw ra, 12(sp)
	addi sp, sp, 16
	ret
	

isr:
	# Preserve reg
	addi sp, sp, -32
	sw t0, 28(sp)
	sw t1, 24(sp)
	sw s0, 20(sp)
	sw ra, 16(sp)
	sw t2, 12(sp)
	sw t3, 8(sp)
	
	# t0 contains count
	# t1 
	# t2 used for immediates
	# t3 reads epc
	
	lw t0, count		# Load count from data
	addi t0, t0, 1		# Update the count
	
	li t2, 5
	blt t0, t2, normal	# check if count = 5
	
	la t2, main
	#li t2, 0x00400000	# Load pc for main
	
	csrrs t3, 65, zero	  # read epc, set 0 bits.
	csrrw zero, 65, t2     	  # write pc of main, discarding old value
	li t0, 0
	
	sw t0, count, t1	# Store the count

normal:
	sw t0, count, t1	# Store the count
	la a0, interstr		# print key pressed string
	jal printString
	
	lw a0, RDR		# laod address of RDR
	lbu a0, (a0)		# Load ascii from RDR to t2
	
	mv s0, a0		# Save ascii in s0 for jal
	jal printChar		# Print the entered char
	
	li a0, '\n'		# print newline
	jal printChar
	
	mv a0, s0		# Put char back into a0 for return.
	
	#lw t0, TCR
	#lw t0, (t0)

	#lw t0, TDR
	#sw a0, (t0)
	
	# Teardown
	
	lw t0, 28(sp)
	lw t1, 24(sp)
	lw s0, 20(sp)
	lw ra, 16(sp)
	lw t2, 12(sp)
	lw t3, 8(sp)
	addi sp, sp, 32
	uret
	

	.data
RCR:	.word	0xffff0000
RDR:	.word	0xffff0004
TCR:	.word	0xffff0008
TDR:	.word	0xffff000c
count: 	.word	0

initstr: .string "\nInitializing Interrupts\n"
interstr: .string "\nkey pressed is : "
