Условие:

Инженерите от съседната лабораторя работят с комплекти SCL/SDL файлове, напр.
input.scl/input.sdl.

В SCL файла са записани нива на сигнали (ниско 0 или високо 1), т.е., файлът се третира като състоящ се от битове. В SDL файла са записани данни от тип uint16_t, като всеки елемент съответства
позиционно на даден бит от SCL файла.

Помогнете на колегите си, като напишете програма на C, която да бъде удобен инструмент за
изваждане в нов файл само на тези SDL елементи, които са имали високо ниво в SCL файла,
запазвайки наредбата им.