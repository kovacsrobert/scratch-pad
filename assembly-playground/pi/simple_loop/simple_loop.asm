.global _start

@ R0 = 0
@ R1 = 1
@ while (R0 < 10) { R0 = R0 + 1 }

_start:
	MOV R0, #0
	MOV R1, #1
	B _test_loop

_loop:
	ADD R0, R1

_test_loop:
	CMP R0, #10
	BLT _loop

end:
	MOV R7, #1
	SWI 0

.data
message:
	.ascii " "
