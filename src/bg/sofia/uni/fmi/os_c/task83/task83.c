#include <unistd.h>
#include <stdlib.h>
#include <err.h>
#include <stdio.h>
#include <fcntl.h>
#include <string.h>
#include <sys/stat.h>
#include <stdbool.h>

int main(int argc, char* argv[]){

	if (argc > 2 || (argc == 2 && strlen(argv[1]) > 4)) {
		printf("%ld", strlen(argv[1]));
		errx(2, "Incorrect parameters");
	}

	char* readline = NULL;
	char* line = NULL;
	size_t len = 0;
	ssize_t inputlength = 2;
	int a[2];

	while (true) {
		inputlength = getline(&readline, &len, stdin);
		if (inputlength == -1) {
			break;
		}
		line = (char*)malloc(inputlength - 1);

		for (long i = 0; i < inputlength - 1; i++) {
			line[i] = readline[i];
		}

		pipe(a);
		int pid = fork();

		if (pid == 0) {
			close(a[0]);
			dup2(a[1], 1);
			if (argc == 2) {
					execlp(argv[1], argv[1], line, NULL);
			} else {
					execlp("echo", "echo", line, NULL);
			}
		}

		close(a[1]);
		dup2(a[0], 0);

		char symbol;
		int counter = 0;
		while (read(0, &symbol, 1) > 0) {
			write(1, &symbol, 1);
			counter++;
		}
	}

	exit(0);
}
