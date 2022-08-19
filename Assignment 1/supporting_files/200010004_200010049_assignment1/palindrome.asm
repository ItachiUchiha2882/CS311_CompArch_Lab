	.data
a:
	101
	.text
main:
	load %x0, $a, %x3
	add %x0, %x3, %x4
	add %x0, %x0, %x5
	blt %x3, %x0, no
loop:
	divi %x3, 10, %x3
	muli %x5, 10, %x5
	add %x5, %x31, %x5
	beq %x0, %x3, check
	jmp loop
check:
	beq %x4, %x5, yes
	bne %x4, %x5, no
yes:
	addi %x0, 1, %x10
	end
no:
	subi %x0, 1, %x10
	end