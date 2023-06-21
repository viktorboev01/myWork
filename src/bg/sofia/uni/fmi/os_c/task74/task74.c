#include <stdio.h>
#include <unistd.h>
#include <err.h>
#include <stdint.h>
#include <fcntl.h>

uint16_t getPow(int num, int pow);

uint16_t getPow(int num, int pow) {
	uint16_t res = 1;
	while(pow-- != 0){
		res *= num;
	}
	return res;
}

int main(int argc, char* argv[]) {
	if (argc != 3) {
		errx(1, "Incorrect number of parameters. Please enter 3");
	}

	int fd1 = open(argv[1], O_RDONLY); //Check
	int fd2 = open(argv[2], O_WRONLY); //Check

	uint8_t num = -1;
	uint16_t num2 = 0;

	//Check
	while (read (fd1, &num, sizeof(uint8_t)) > 0) {
		num2 = 0;
		for (int i = 0; i < 8; i++){
			int odd = (num>>i)&1;
			if (odd == 1){
				num2 += getPow(2, (2*i) + 1);
			} else {
				num2 += getPow(2, (2*i));
			}
		}
		//Check
		write(fd2, &num2, sizeof(uint16_t));
	}
}
