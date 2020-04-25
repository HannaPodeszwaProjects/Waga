#ifndef wheel_H
#define wheel_H

#pragma once
#include <stdio.h>
#include <stdlib.h>    
#include <time.h> 
#include <stdbool.h>
#include <windows.h>

/**Funkcja czyta z pliku wartosci na kole i wpisuje je do tablicy
@param data struktura z glownymi danymi
@return Funkcja zwraca nastepny stan
*/
state do_wheel( cur_data *data); 

/**Funkcja obsluguje krecenie kolem
@param data struktura z glownymi danymi
@return Funkcja zwraca nastepny stan
*/
state do_spin( cur_data *data);

/**Funkcja wyznacza mocy krecenia kolem
@return Funkcja zwraca liczbe oznaczajaca moc krecenia kolem
*/
int power(); 

#endif
