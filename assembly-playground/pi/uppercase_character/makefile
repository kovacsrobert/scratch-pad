uppercase_character: uppercase_character.o
	ld -o uppercase_character.exe uppercase_character.o

uppercase_character.o: uppercase_character.asm
	as -o uppercase_character.o uppercase_character.asm

clean:
	rm uppercase_character.exe uppercase_character.o
