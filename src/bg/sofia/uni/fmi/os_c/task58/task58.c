#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <err.h>
#include <stdint.h>
#include <stdlib.h>

static int cmp_uint32_t(const void* num1, const void* num2);

static int cmp_uint32_t(const void* num1, const void* num2){
    const uint32_t a = *(const uint32_t*)num1;
    const uint32_t b = *(const uint32_t*)num2;
    if (a < b) {
        return -1;
    } else if (a > b) {
        return 1;
    } else {
        return 0;
    }
}

int main(int argc, char* argv[]) {

    const uint32_t INPUT_SIZE = 100000000;
    if (argc != 2){
        errx(1, "Incorrect number of parameters");
    }

    int fd1;

    if ((fd1 = open(argv[1], O_RDONLY)) == -1){
        err(2, "Error while opening file %s", argv[1]);
    }

    uint32_t *numbers = malloc(INPUT_SIZE * sizeof(uint32_t));
    int readSize = 0;
    uint32_t cnt = 0;

    while ((readSize = read(fd1, &numbers[cnt], sizeof(uint32_t))) > 0) {
        //printf("number %d: %d\n", cnt, numbers[cnt]);
        cnt++;
        continue;
    }

    if (readSize == -1) {
        err(3, "Error while reading %s", argv[1]);
    }

    qsort(numbers, cnt, sizeof(uint32_t), cmp_uint32_t);
    close(fd1);

    if ((fd1 = open(argv[1], O_WRONLY, O_TRUNC)) == -1){
        err(2, "Error while opening file %s", argv[1]);
    }

    for (uint32_t i = 0; i < cnt; i++){
        printf("number %d: %d\n", i, numbers[i]);
        write(fd1, &numbers[i], sizeof(uint32_t));
    }

    free(numbers);
    close(fd1);
}
