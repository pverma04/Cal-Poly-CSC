# CPE 225
# Assignment 1
# Name: Parth
.data
Name: .string "Parth" # Your name in double quotes

# For signed, 8-bit numbers, what is the value (in decimal) 
#   of each of the following 2's complement binary numbers:

# Replace DDDD with the signed decimal equivalent 
#    of the provided 2's complement binary number
# To provide an example, the first question is answered.

decval1:	.word 0  # 00000000b 
#decval1: .word 0  # 00000000b 
decval2:	.word 26	# 00011010b
decval3:	.word -1	# 11111111b
decval4:	.word -127	# 10000001b
decval5:	.word -128	# 10000000b
decval6:	.word -75	# 10110101b

# What is the largest unsigned value that fits in 16 bits.
unsigned16bmin:	.word	0  # The minimum value of an unsigned number is 0.
unsigned16bmax:	.word	65535  # Replace DDDDD with the maximum value that fits in 16bits.

# What are the minimum and maximum SIGNED values that fit in 16 bits.
signed16bmin:	.word	-32768  # Replace DDDDD with the minimum value that fits in 16bits.
signed16bmax:	.word	32767  # Replace DDDDD with the maximum value that fits in 16bits.

# Provide two 8 bit values that causes a Carry without a Sign overflow:
CarryNoOV1:	.word	43  # Replace DDD with a decimal value.
CarryNoOV2:	.word	44  # Replace DDD with a decimal value.
# The 8bit result of CarryNoOV1 + CarryNoOV2 results 
#   in a Carry without a Sign overflow.

# Provide two 8 bit values that causes a Sign Overflow without a Carry overflow:
SignNoCarry1:	.word	43  # Replace DDD with a decimal value.
SignNoCarry2:	.word	108  # Replace DDD with a decimal value.
# The 8bit result of SignNoCarry1 + SignNoCarry2 results 
#   in a Sign Overflow without a Carry.

# Bitwise Logic Operations

# Provide the answer in hexadecimal:
#	0x25A6 & 0x5087 = ________
bitval1:	.word 0x0086	# replace the HHHH with your answer in hexadecimal

#	0xABCD | 0x1DDD = ________
bitval2:	.word 0xBFDD	# replace the HHHH with your answer in hexadecimal

# Determine both the operation (ADD/AND/OR/NOT/XOR) and the value needed to 
#   clear bit 7 of the following value to zero (clear bit 7) in a single operation.
#   Leave the rest of the bits unchanged. (Hint: expand the values to binary.)
#   See the discussion on "Clear Mask" in the reading.
#
#	 0x97CA (ADD/AND/OR/NOT/XOR) CLEARMASK  =  ????

			.eqv CLEAROP AND	# replace OPERATION with one of (ADD/AND/OR/NOT/XOR).
CLEARMASK: .word 0xFF7F	# replace HHHH with hexadecimal VALUE you selected.

# Determine both the operation (ADD/AND/OR/NOT/XOR) and the value needed to 
#   set bit 5 of the following value to one (set bit 5) in a single operation.
#   Leave the rest of the bits unchanged.
#   See the discussion on "Set Mask" in the reading.
#
#	0x85CA (ADD/AND/OR/NOT/XOR) SETMASK  =  ????

 .eqv SETOP OR	# replace OPERATION with one of (ADD/AND/OR/NOT/XOR).
SETMASK:	.word 0x0020	# replace HHHH with hexadecimal VALUE you selected.

# Determine both the operation (ADD/AND/OR/NOT/XOR) and the value needed to 
#   test bit if bit 6 of an unknown test value is set.
#   See the discussion on "Test Mask" in the reading.
#
#	testval (ADD/AND/OR/NOT/XOR) TESTMASK =  ????
 .eqv TESTOP AND  # replace OPERATION with one of (ADD/AND/OR/NOT/XOR).
TESTMASK: .word 0x0040	# replace HHHH with hexadecimal VALUE you selected.




 

 
