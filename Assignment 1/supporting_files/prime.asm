	.data
a:
  2
	.text
main:
	load %x0, $a, %x3
	addi %x0, 2, %x4
	beq %x3, %x4, isPrime
	blt %x3, %x4, notPrime
loop:
	div %x3, %x4, %x5
	beq %x0, %x31, notPrime
	addi %x4, 1, %x4
	beq %x4, %x3, isPrime
	jmp loop
isPrime:
	addi %x0, 1, %x10
	end
notPrime:
	subi %x0, 1, %x10
	end