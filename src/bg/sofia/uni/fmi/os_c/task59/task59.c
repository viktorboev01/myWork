#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <err.h>
#include <stdint.h>
#include <stdlib.h>

int main(int argc, char* argv[]) {

    if (argc != 5){
        errx(1, "Incorrect number of parameters");
    }

    int fd1;
    int fd2;
    int fd3;
    int fd4;

    //DAT - uint_8
    if ((fd1 = open(argv[1], O_RDONLY)) == -1) {
        err(2, "Error while opening file %s", argv[1]);
    }

    //IDX - uint16_t - offset from the beggining of the file;
    //              uint8_t - length of the string;
    //              uint8_t - magic;
    if ((fd2 = open(argv[2], O_RDONLY)) == -1) {
        close(fd1);
        err(2, "Error while opening file %s", argv[2]);
    }

    if ((fd3 = open(argv[3], O_WRONLY | O_CREAT | O_TRUNC, S_IRWXU)) == -1) {
        close(fd1);
        close(fd2);
        err(3, "Error while creating file %s", argv[3]);
    }

    if ((fd4 = open(argv[4], O_WRONLY | O_CREAT | O_TRUNC, S_IRWXU)) == -1) {
        close(fd1);
        close(fd2);
        close(fd3);
        err(3, "Error while creating file %s", argv[4]);
    }

    int readSizeIDX = 0;
    uint16_t pos = 0;
    uint16_t start = 0;
    uint8_t byte = 0;
    char letter;

    while ((readSizeIDX = read(fd2, &pos, sizeof(uint16_t))) > 0) {
        if (read(fd2, &byte, sizeof(uint8_t)) == -1) {
            err(5, "Error while reading file %s", argv[2]);
        }

        lseek(fd1, pos, SEEK_SET);

        if (read(fd1, &letter, sizeof(char)) == -1) {
            err(6, "Error while reading file %s", argv[1]);
        }

        if (letter >= 'A' && letter <= 'Z') {
            if (write(fd3, &letter, sizeof(char)) == -1) {
                err(7, "Error while writing in file %s", argv[3]);
            }

            for (uint8_t i = 1; i < byte; i++) {
                    if (read(fd1, &letter, sizeof(char)) == -1) {
                        err(6, "Error while reading file %s", argv[1]);
                    }

                    if (write(fd3, &letter, sizeof(char)) == -1) {
                        err(7, "Error while writing in file %s", argv[3]);
                    }
            }

            if (write(fd4, &start, sizeof(uint16_t)) == -1) {
                err(8, "Error while writing file %s", argv[4]);
            }

            if (write(fd4, &byte, sizeof(uint8_t)) == -1) {
                err(8, "Error while writing file %s", argv[4]);
            }

            start += (uint16_t)byte;
            byte = 0;
            if (write(fd4, &byte, sizeof(uint8_t)) == -1) {
                err(8, "Error while writing file %s", argv[4]);
            }
        }


        if ( read(fd2, &byte, sizeof(uint8_t)) == -1) {
            err(5, "Error while reading file %s", argv[2]);
        }
    }

    if (readSizeIDX == -1) {
        close(fd1);
        close(fd2);
        close(fd3);
        close(fd4);
        err(4, "Error reading file %s", argv[2]);
    }

    close(fd1);
    close(fd2);
    close(fd3);
    close(fd4);
}
