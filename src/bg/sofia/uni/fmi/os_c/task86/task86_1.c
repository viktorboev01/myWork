#include <err.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>

int main(int argc, char* argv[]){

	if (argc != 2){
		errx(1, "Incorrect number of parameters");
	}

	const char* path = "/tmp/mkfifo";
	mkfifo(path, 0666);
	int fd = open(path, O_WRONLY);

	int a[2];
	pipe(a);
	int pid = fork();

	if (pid == 0) {
		close(a[0]);
		dup2(a[1], 1);
		execlp("cat", "cat", argv[1], NULL);
		exit(0);
	}

	char symbol;
	close(a[1]);
	while (read(a[0], &symbol, 1) > 0) {
		write(fd, &symbol, 1);
	}
	close(a[0]);
	exit(0);
}
