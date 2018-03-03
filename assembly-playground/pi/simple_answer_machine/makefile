simple_answer_machine: simple_answer_machine.o
	ld -o simple_answer_machine.exe simple_answer_machine.o

simple_answer_machine.o: simple_answer_machine.asm
	as -o simple_answer_machine.o simple_answer_machine.asm

clean:
	rm simple_answer_machine.exe simple_answer_machine.o
