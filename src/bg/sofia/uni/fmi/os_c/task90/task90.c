#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

void writeString(int fd, const char* string);
void readString(int fd);

void writeString(int fd, const char* string) {
	for(long unsigned i = 0; i < strlen(string); i++) {
			write(fd, &string[i], 1);
	}
}


void readString(int fd) {
	char symbol;
	while (read(fd, &symbol, 1) > 0) {
	}
}

int main(int argc, char* argv[]){

	const char* ding = "DING\n";
	const char* dong = "DONG\n";

	int n = atoi(argv[1]);
	int d = atoi(argv[2]);

	for (int i = 0; i < n; i++) {

		int a[2];
		pipe(a);
		pid_t pid = fork();

		if (pid == 0){
			close(a[1]);
			readString(a[0]);
			writeString(1, dong);
			close(a[0]);
			break;
		}

		close(a[0]);
		writeString(1, ding);
		writeString(a[1], "a");
		close(a[1]);
		wait(NULL);
		sleep(d);
	}
	exit(0);
}
