#include <unistd.h>
#include <stdlib.h>
#include <err.h>
#include <stdio.h>
#include <fcntl.h>
#include <string.h>
#include <sys/stat.h>
#include <stdbool.h>

int main(int argc, char* argv[]){
	int a[2];
	pipe(a);
	int pid = fork();
	if (pid == 0) {
		close(a[0]);
		dup2(a[1], 1);
		execlp("cat", "cat", "/etc/passwd", NULL);
	}

	close(a[1]);
	int b[2];
	pipe(b);

	pid = fork();
	if (pid == 0) {
		dup2(a[0], 0);
		dup2(b[1], 1);
		close(b[0]);
		execlp("cut", "cut", "-d", ":", "-f", "7", NULL);
	}

	close(a[0]);
	pipe(a);
	close(b[1]);
	pid = fork();
	if (pid == 0) {
		dup2(a[1], 1);
		dup2(b[0], 0);
		close(a[0]);
		execlp("sort", "sort", NULL);
	}

	close(a[1]);
	close(b[0]);
	dup2(a[0], 0);
	execlp("uniq", "uniq", "-c", NULL);

	exit(0);
}
