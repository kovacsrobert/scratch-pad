hello_world.exe: hello_world.o
	ld -o hello_world.exe hello_world.o

hello_world.o: hello_world.asm
	as -g -o hello_world.o hello_world.asm

clean:
	rm hello_world.exe hello_world.o
