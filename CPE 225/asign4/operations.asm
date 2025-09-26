.global addnums
addnums:
	add, a0, a0, a1
	ret
	
.global subnums
subnums:
	sub a0, a0, a1
	ret 
	
.global multnums
multnums:
	mul a0, a0, a1
	ret

.global divnums
divnums:
	div a0, a0, a1
	ret

.global andnums
andnums:
	and a0, a0, a1
	ret

.global ornums
ornums:
	or a0, a0, a1
	ret

.global xornums
xornums:
	xor a0, a0, a1
	ret

.global lshiftnums
lshiftnums:
	sll a0, a0, a1
	ret

.global rshiftnums
rshiftnums:
	srl a0, a0, a1
	ret