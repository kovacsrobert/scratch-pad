.global _start

_start:
	MOV R7, #3 @read command
	MOV R0, #0 @from keyboard
	MOV R2, #10 @length of input
	LDR R1, =message @input variable
	SWI 0

_write:
	MOV R7, #4 @write command
	MOV R0, #1 @to monitor
	MOV R2, #5 @length of output
	LDR R1, =message @output variable
	SWI 0


end:
	MOV R7, #1
	SWI 0

.data
message:
	.ascii " "
