#ifndef prepare_game_H
#define preapre_game_H

#pragma once
#include <stdio.h>
#include <stdlib.h>    
#include <time.h> 
#include <stdbool.h>
#include <windows.h>

/**Funkcja przygotowuje gre
@param data struktura z glownymi danymi
@return Funkcja zwraca nastepny stan
*/
state do_prepare_game( cur_data *data); 

/**Funkcja losuje haslo
@param data struktura z glownymi danymi
@param tab_words tablica slow z pliku
@j liczba slow z pliku
*/
void random_word(cur_data *data, char**tab_words, int j); 

/**Funkcja czyta z pliku slowa i wpisuje je do dynamicznej tablicy
@param file plik wejsciowy
@return Funkcja zwraca wczytane slowo
*/
char* read_word(FILE *file); 

/**Funkcja czyta z pliku wszystkie slowa
@param data struktura z glownymi danymi
@param j liczba slow w pliku
@return Funkcja zwraca tablice wszystkich slow z pliku
*/
char ** read_file(cur_data data, int *j);  

/**Funkcja przygotowuje tablice dostepnych liter
@param data struktura z glownymi danymi
*/
void do_available_letters(cur_data *data); 

/**Funkcja tworzy tablice graczy
@param data struktura z glownymi danymi
@return Funkcja zwraca nastepny stan
*/
state do_init(cur_data *data); 

/**Funkcja wpisuje nick do dynamicznej tablicy
@return Funkcja zwraca wpisany nick
*/
char *do_nick(); 
#endif