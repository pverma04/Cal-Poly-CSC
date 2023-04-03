.global main
.text 
main:
if:
	ble  a0, zero, end
	mv a0, zero
	ret
	
else:

end:
	