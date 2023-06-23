#include <err.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>

int main(int argc, char* argv[]){

	if (argc != 2) {
		errx(1, "Incorrect number of parameters");
	}

	const char* myfifo = "/tmp/mkfifo";
	mkfifo(myfifo, 0666);
	int fd = open(myfifo, O_RDONLY);

	dup2(fd, 0);
	execlp(argv[1], argv[1], NULL);
}
