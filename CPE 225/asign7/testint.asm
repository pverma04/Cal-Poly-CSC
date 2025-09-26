#Parth Verma and Andrew Okerlund
		.globl main
main:
	jal init
	
	
	li a0, '*'
testint:
	jal printChar
	
	b testint
	
