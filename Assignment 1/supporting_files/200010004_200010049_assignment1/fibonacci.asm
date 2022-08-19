	.data
n:
	10
	.text
main:
	load %x0, $n, %x3
	addi %x0, 65535, %x20
	addi %x0, 0, %x4
	addi %x0, 1, %x5
	addi %x0, 2, %x8
	addi %x4, 1, %x6
	store %x4, 0, %x20
	blt %x3, %x8, exit
	subi %x20, 1, %x20
loop:
	store %x5, 0, %x20
	subi %x20, 1, %x20
	add %x4, %x5, %x7
	addi %x5, 0, %x4
	addi %x7, 0, %x5
	addi %x6, 1, %x6
	beq %x6, %x3, exit
	jmp loop
exit:
	end