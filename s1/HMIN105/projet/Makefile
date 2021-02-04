CC = gcc
CFLAGS = -Wall -Iinclude
LDFLAGS = -lpthread

# liste des fichiers sources 
SRCS0=client.c
SRCS1=server.c

#order
MSQ = @
RM = $(MSQ)rm -f
MKDIR_P = mkdir -p obj bin

#message
MSG = $(MSQ)echo
MSG_MKDIR = $(MSG) "[~]Creating directory..."
MSG_OK = $(MSG) "[+]done!"

BIN=bin/client  bin/server
default: $(BIN)

init:
	$(MSG_MKDIR)
	$(MKDIR_P)
	$(MSG_OK)

#clean
clean :
	$(MSG) "[~]Cleanup...."
	$(RM) $(BIN) obj/*.o *~
	$(MSG_OK)

#compilation
obj/%.o: %.c
	$(CC) $(CFLAGS) -c $< -o $@

bin/client: $(SRCS0:%.c=obj/%.o)
	$(CC) -o $@ $+ $(LDFLAGS)

bin/server: $(SRCS1:%.c=obj/%.o)
	$(CC) -o $@ $+ $(LDFLAGS)