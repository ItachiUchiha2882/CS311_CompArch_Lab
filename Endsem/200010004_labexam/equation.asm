	.data
a:
	1
b:
	2
c:
	3
d:
	1
e:
	3
f:
	5
	.text
main:
	load %x0, $a, %x3
	load %x0, $b, %x4
	load %x0, $c, %x5
	load %x0, $d, %x6
	load %x0, $e, %x7
	load %x0, $f, %x8
	mul %x4, %x8, %x15 ; x15=bf
	mul %x5, %x7, %x16 ; x16=ce
	mul %x3, %x7, %x17 ; x17=ae
	mul %x4, %x6, %x18 ; x18=bd
	sub %x15, %x16, %x19 ; x19=bf-ce
	sub %x17, %x18, %x20 ; x20=ae-bd
	div %x19, %x20, %x10 ; x10=x
	mul %x3, %x8, %x15 ; x15=af
	mul %x5, %x6, %x16 ; x16=cd
	mul %x3, %x7, %x17 ; x17=ae
	mul %x4, %x6, %x18 ; x18=bd
	sub %x15, %x16, %x19 ; x19=af-cd
	sub %x18, %x17, %x20 ; x20=bd-ae
	div %x19, %x20, %x11 ; x11=y
	end
