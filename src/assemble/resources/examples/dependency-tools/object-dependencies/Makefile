CC= gcc
LD= gcc
OBJS= src/a.o src/b.o src/c.o src/d.o src/e.o src/main.o
EXE = legacyC.out
CFLAGS= -g -Wall

.c.o:
	$(CC) $(CFLAGS) -c $< -o $@

all: $(OBJS)
	$(LD) $(CFLAGS) $^ -o $(EXE)
	
clean:
	rm -f $(OBJS) $(EXE)