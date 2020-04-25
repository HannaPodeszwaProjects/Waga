#ifndef results_H
#define results_H

#pragma once
#include <stdio.h>
#include <stdlib.h>     
#include <time.h> 
#include <stdbool.h>

/**Struktura result - wynik
@param nick nick gracza
@param money fundusze gracza
*/
typedef struct
{
	char * nick;
	int money;
}result;

/**Funkcja obsluguje stan wyniki
@param data struktura z glownymi danymi
@return Funkcja zwraca nastepny stan
*/
state do_results( cur_data *data); 

/**Funkcja zapisuje wynik
@param data struktura z glownymi danymi
@return Funkcja zwraca nastepny stan
*/
state do_save( cur_data *data); 

/**Funkcja wpisuje do tablicy wyniki z pliku
@param j liczba wynikow
@return Funkcja zwraca tablice wynikow
*/
result * read_file_results(int * j);

/**Funkcja porownuje elementy tablicy po nicku
@param a pierwszy element tablicy
@param b kolejny element tablicy
@return Funkcja zwraca 1 - a>b, -1 - a<b, 0 - a=b
*/
int sort_nick(const void * a, const void * b);

/**Funkcja porownuje elementy tablicy po funduszach
@param a pierwszy element tablicy
@param b kolejny element tablicy
@return Funkcja zwraca 1 - b>a, -1 - b<a, 0 - a=b
*/
int sort_money(const void * a, const void * b);

#endif