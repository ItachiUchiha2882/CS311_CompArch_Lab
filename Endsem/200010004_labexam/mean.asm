	.data
a:
	1
b:
	1
c:
	2
d:
	3
e:
	5
	.text
main:
	load %x0, $a, %x3
	load %x0, $b, %x4
	load %x0, $c, %x5
	load %x0, $d, %x6
	load %x0, $e, %x7
	add %x3, %x4, %x8
	add %x8, %x5, %x8
	add %x8, %x6, %x8
	add %x8, %x7, %x8
	divi %x8, 5, %x10
	end
