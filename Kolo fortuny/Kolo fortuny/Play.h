#ifndef play_H
#define play_H

#pragma once
#include <stdio.h>
#include <stdlib.h>    
#include <time.h> 
#include <stdbool.h>
#include <windows.h>

/**Funkcja umozliwia wybranie litery 
@param data struktura z glownymi danymi
@return Funkcja zwraca nastepny stan
*/
state do_guess_letter( cur_data *data);  

/**Funkcja sprawdza czy podana litera wystepuje w hasle
@param data struktura z glownymi danymi
@param letter_c wybrana litera
@return Funkcja zwraca liczbe wystapien danej litery w hasle
*/
int check_letter(cur_data *data, char letter_c);

/**Funkcja umozliwia odgadniecie hasla
@param data struktura z glownymi danymi
@return Funkcja zwraca nastepny stan
*/
state do_guess_word( cur_data *data);

/**Funkcja umozliwia kupienie samogloski
@param data struktura z glownymi danymi
@return Funkcja zwraca nastepny stan
*/
state do_buy( cur_data *data);

/**Funkcja obsluguje stan po wygranej
@param data struktura z glownymi danymi
@return Funkcja zwraca nastepny stan
*/
state do_win( cur_data *data); 

#endif