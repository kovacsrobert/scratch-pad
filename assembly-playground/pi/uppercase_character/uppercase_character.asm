.global _start

_start:
	MOV R7, #3 @read command
	MOV R0, #0 @from keyboard
	MOV R2, #1 @length of input
	LDR R1, =character @input variable
	SWI 0

_uppercase:
	LDR R1, =character
	LDR R0, [R1]
	BIC R0, R0, #32
	STR R0, [R1]

_write:
	MOV R7, #4 @write command
	MOV R0, #1 @to monitor
	MOV R2, #1 @length of output
	LDR R1, =character @output variable
	SWI 0

end:
	MOV R7, #1
	SWI 0

.data
character:
	.ascii " "
