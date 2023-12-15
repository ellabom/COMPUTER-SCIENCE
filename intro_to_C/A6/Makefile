.PHONY: clean 

CFLAGS:-Wall -Wextra

rs: rs.o
	gcc -o rs rs.o

rs.o: rs.c 
	gcc $(CFLAGS) -c rs.c

clean:
	rm -f *.o rs
