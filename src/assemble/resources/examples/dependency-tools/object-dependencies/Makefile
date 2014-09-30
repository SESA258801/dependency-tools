CC= gcc
LD= gcc
OBJS= src/a.o src/b.o src/c.o src/d.o src/e.o src/main.o
FILE = legacyC
CFLAGS= -g -Wall
ifneq (,$(findstring Windows, $(OS))) # Handle Windows-specific executable file
    EXE = $(FILE).exe
else
	EXE = $(FILE)
endif

.c.o:
	$(CC) $(CFLAGS) -c $< -o $@

all: $(OBJS)
	$(LD) $(CFLAGS) $^ -o $(EXE)
	
clean:
	rm -f $(OBJS) $(EXE)
