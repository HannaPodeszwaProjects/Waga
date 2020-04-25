#ifndef settings_H
#define settings_H

#pragma once
#include <stdio.h>
#include <stdlib.h>    
#include <time.h> 
#include <stdbool.h>
#include <windows.h>

/**Funkcja obsluguje stan ustawienia
@param data struktura z glownymi danymi
@return Funkcja zwraca nastepny stan
*/
state do_settings(cur_data *data); 

/**Funkcja umozliwia zmiane kategorii
@param category nazwa kategorii
*/
void change_category(char ** category);

/**Funkcja umozliwia zmiane ilosci graczy
@param number liczba graczy
*/
void change_number_players(int * number);

#endif
