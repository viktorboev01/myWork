#include <unistd.h>
#include <stdlib.h>
#include <err.h>
#include <stdio.h>
#include <fcntl.h>
#include <string.h>
#include <sys/stat.h>
#include <stdbool.h>

int main(int argc, char* argv[]){

	if(argc != 1) {
		errx(1, "Incorrect number of parameters");
	}

	char* prompt = (char*)malloc(sizeof(char)*28);
	strcpy(prompt, "Enter a command to execute:");

	char *readline = NULL;
	char *line = NULL;
	size_t len = 0;
	ssize_t lineSize = 0;
	char newLine = '\n';

	while(true) {
		for (int i = 0; i < strlen(prompt); i++) {
				write(1, &prompt[i], 1);
		}
		
		write(1, &newLine, 1);
		lineSize = getline(&readline, &len, stdin);
		char *line = (char*)malloc(lineSize - 1);

		for (int i = 0; i < lineSize - 1; i++){
				line[i] = readline[i];
		}

		if (strcmp(line, "exit") == 0){
				break;
		} else {
				execlp(line, line, NULL);
		}
	}
	
	free(readline);
	free(line);
	free(prompt);
}
