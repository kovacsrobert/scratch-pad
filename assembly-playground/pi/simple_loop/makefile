simple_loop: simple_loop.o
	ld -o simple_loop.exe simple_loop.o

simple_loop.o: simple_loop.asm
	as -o simple_loop.o simple_loop.asm

clean:
	rm simple_loop.exe simple_loop.o
