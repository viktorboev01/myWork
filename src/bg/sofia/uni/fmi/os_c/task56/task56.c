#include <unistd.h>
#include <stdlib.h>
#include <err.h>
#include <stdio.h>
#include <fcntl.h>
#include <string.h>
#include <sys/stat.h>


static int cmpchars(const void *p1, const void *p2) {
        return ( *(char*)p1 - *(char*)p2 );
}

int main(int argc, char* argv[]){

        if (argc != 2){
                errx(1, "Incorrect number of parameters");
        }

        int fd = open(argv[1], O_RDWR);
        struct stat sb;

        if (lstat(argv[1], &sb) == -1) {
        perror("lstat");
        exit(EXIT_FAILURE);
    }

        if (fd == -1){
                err(2, "Error while opening file %s", argv[1]);
        }

        printf("%ld\n", sb.st_size);

        int readSize = 0;
        char *arr = (char*) malloc(sb.st_size * sizeof(char));

        for (int i = 0; i < sb.st_size; i++){
                if (readSize == read(fd, &arr[i], 1)){
                        err(3, "Error while reading %s", argv[1]);
                }
        }

        qsort(arr, sb.st_size, sizeof(char), cmpchars);

        for (int i = 0; i < sb.st_size; i++){
                if(write(1, &arr[i], sizeof(char)) != sizeof(char)) {
                        err(4, "Error while writing on the standard output");
                }
        }

        char newLine = '\n';
        write(1, &newLine, 1);

    exit(0);
}
