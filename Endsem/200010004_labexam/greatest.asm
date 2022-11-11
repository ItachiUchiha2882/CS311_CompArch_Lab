	.data
a:
	5
	4
	3
	2
	1
b:
	1
	2
	3
	4
	5
n:
	5
	.text
main:
	load %x0, $n, %x3 ; x3=n
	addi %x0, 65535, %x10 ; x10=65535
	addi %x0, 0, %x4 ; x4=i
loop:
	beq %x4, %x3, exit
	bgt %x4, %x3, exit
	load %x4, $a, %x5 ; x5=a[x4]=a[i]
	load %x4, $b, %x6 ; x6=b[x4]=b[i]
	addi %x4, 1, %x4
	bgt %x5, %x6, storeA
	beq %x5, %x6, storeB
	blt %x5, %x6, storeB
storeA:
	store %x5, 0, %x10
	subi %x10, 1, %x10
	jmp loop
storeB:
	store %x6, 0, %x10
	subi %x10, 1, %x10
	jmp loop
exit:
	end
