#ifndef Release_memory_H
#define Release_memory_H

#pragma once
#include <stdio.h>
#include <stdlib.h>    
#include <time.h> 
#include <stdbool.h>
#include <windows.h>

/**Funkcja zwalnia pamiec zaalokowana na wyniki
@param tab tablica wynikow
@param j liczba wynikow
*/
void release_results(result ** tab, int j);

/**Funkcja zwalnia pamiec zaalokowana na graczy
@param data struktura z glownymi danymi
@return Funkcja zwraca nastepny stan
*/
state release_players(cur_data * data);

/**Funkcja zwalnia pamiec zaalokowana na slowa z pliku
@param tab tablica slow z pliku
*/
void release_tab_words(char***tab, int j);

/**Funkcja zwalnia pamiec zaalokowana na haslo
@param data struktura z glownymi danymi
*/
void release_random_word(cur_data * data);

#endif