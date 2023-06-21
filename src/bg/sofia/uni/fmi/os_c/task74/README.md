Инженерите от съседната лабораторя ползват специализиран хардуер и софтуер за
прехвърляне на данни по радио, но за съжаление имат два проблема:

• в радио частта: дълги поредици битове само 0 или само 1 чупят преноса;

• в софтуерната част: софтуерът, който ползват, може да прехвърля само файлове с четен брой
байтове дължина.

Помогнете на колегите си, като напишете програма на C, която решава тези проблеми, като подготвя
файлове за прехвърляне. Програмата трябва да приема два задължителни позиционни аргумента
– имена на файлове. Примерно извикване:

$ ./main input.bin output.bin

Програмата чете данни от input.bin и записва резултат след обработка в output.bin. Програмата
трябва да работи като encoder, който имплементира вариант на Manchester code, т.е.:

• за всеки входен бит 1 извежда битовете 10, и

• за всеки входен бит 0 извежда битовете 01

Например, следните 8 бита вход
1011 0110 == 0xB6
по описаният алгоритъм дават следните 16 бита изход
1001 1010 0110 1001 == 0x9A69