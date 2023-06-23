#include <err.h>
#include <errno.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdint.h>

void close_all(void);

int fd[3] = {0, 0, 0};

void close_all(void){
	int _errno = errno;
	for (int i = 0; i < 2; i++){
		if (fd[i] > 0){
				close(fd[i]);
		}
	}
	errno = _errno;
}

int main(int argc, char* argv[]){

	const char* res = "result.txt";

	if(argc != 3) {
		errx(1, "Incorrect number of parameters");
	}

	fd[0] = open(argv[1], O_RDONLY);
	if (fd[0] == -1) {
		err(2, "The first argument cannot be opened");
	}

	fd[1] = open(argv[2], O_RDONLY);
	if (fd[1] == -1) {
		close_all();
		err(2, "The second argument cannot be opened");
	}

	fd[2] = open(res, O_WRONLY | O_TRUNC | O_CREAT, S_IRUSR | S_IWUSR);

	int readSize = 0;
	uint8_t bits = 0;
	uint16_t num = 0;

	while( (readSize = read(fd[0], &bits, sizeof(uint8_t))) > 0) {
		for (int i = 0; i < 8; i++) {
			if (read(fd[1], &num, sizeof(uint16_t)) == -1){
				close_all();
				err(3, "Error while reading %s", argv[2]);
			}
			if (!(bits>>i)&1) {
				if (write(fd[2], &num, sizeof(uint16_t)) == -1){
						close_all();
						err(4, "Error while writing %s", res);
				}
			}
		}
	}

	close_all();
	if(readSize == -1) {
		err(3, "Error while reading %s", argv[1]);
	}

	exit(0);
}
