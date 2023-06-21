#include <unistd.h>
#include <stdlib.h>
#include <err.h>
#include <stdio.h>
#include <fcntl.h>
#include <string.h>
#include <sys/stat.h>
#include <stdbool.h>
#include <stdint.h>

void writeString(const char* string, int fd);
void printNum(int num, int fd);

void writeString(const char* string, int fd) {
	for(long i = 0; i < strlen(string); i++) {
			write(fd, &string[i], 1);
	}
}

void printNum(int num, int fd) {
	int digit = num % 10;
	char symbol = '0' + digit;

	if (num >= 10){
			printNum(num / 10, fd);
	}

	write(fd, &symbol, 1);
}

int main(int argc, char* argv[]){

	if (argc != 3) {
		errx(1, "Incorrect");
	}

	int fd1 = open(argv[1], O_RDONLY);
	int fd2 = open(argv[2], O_WRONLY | O_TRUNC | O_CREAT, S_IRWXU);

	uint16_t num = 0;
	uint32_t counter = 0;

	struct stat sb;
	fstat(fd1, &sb);

	uint16_t* arr = (uint16_t*)malloc(sb.st_size);

	while (read(fd1, &num, sizeof(uint16_t)) > 0) {
		if (counter == sb.st_size / 2) {
				break;
		}
		arr[counter] = num;
		counter++;
	}

	const char* label = "int arrN = ";
	const char* label2 = "int arr[] = {";

	const char* newLine = "\;\n";
	char closeBracket = '}';
	const char* space = ", ";

	writeString(label, fd2);
	printNum(sb.st_size, fd2);
	writeString(newLine, fd2);
	writeString(label2, fd2);
	for (uint16_t i = 0; i < counter - 1; i++) {
		printNum(arr[i], fd2);
		writeString(space, fd2);
	}

	printNum(arr[counter - 1], fd2);
	write(fd2, &closeBracket, 1);
	writeString(newLine, fd2);

	exit(0);
}
