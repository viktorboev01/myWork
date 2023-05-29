#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <err.h>
#include <stdint.h>

struct pair {
        uint32_t startPoint;
        uint32_t length;
};

int main(int argc, char* argv[]) {
        if (argc != 4){
                errx(1, "Incorrect number of parameters");
        }

        //first file - <x, y> positions
        //second file - int[] n1, n2, ... numbers
        //third file - to write the selected numbers from numbers

        int fd1;
        int fd2;
        int fd3;

        if ((fd1 = open(argv[1], O_RDONLY)) == -1){
                err(2, "Error while opening file %s", argv[1]);
        }


        if ((fd2 = open(argv[2], O_RDONLY)) == -1) {
                close(fd1);
                err(3, "Error while opening file %s", argv[2]);
        }

        if ((fd3 = open(argv[3], O_WRONLY)) == -1) {
                close(fd1);
                close(fd2);
                err(4, "Error while opening or creating file %s", argv[3]);
        }

        struct pair pair;
        int readSize1 = 0;
        uint32_t num = 0;

        while ((readSize1 = read(fd1, &pair, sizeof(pair))) > 0) {
                printf("I read %d bytes; %d; %d\n", readSize1, pair.startPoint, pair.length);
                printf("Start point: %ld\n", pair.startPoint * sizeof(uint32_t));
                lseek(fd2, (pair.startPoint * sizeof(uint32_t)), SEEK_SET);


                for (long i = 0; i < pair.length; i++){
                        if (read(fd2, &num, sizeof(uint32_t)) == -1) {
                                err(6, "Error while reading file %s", argv[2]);
                        }
                        printf("num: %d\n", num);
                        if (write(fd3, &num, sizeof(uint32_t)) == -1) {
                                err(7, "Error while writing file %s", argv[3]);
                        }

                }

        }

        if (readSize1 == -1) {
                err(5, "Error while reading file %s", argv[1]);
        }

        close(fd1);
        close(fd2);
        close(fd3);
}
