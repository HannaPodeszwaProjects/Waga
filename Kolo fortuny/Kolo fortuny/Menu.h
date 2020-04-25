#ifndef menu_H
#define menu_H

#pragma once
#include <stdio.h>
#include <stdlib.h>    
#include <time.h> 
#include <stdbool.h>
#include <windows.h>

typedef enum //stany
{
	TURN_OFF, PREPARE_GAME, SETTINGS, RESULTS, GUESS_LETTER, GAME_MENU, SPIN, GUESS_WORD, BUY, WIN, SAVE, WHEEL, INIT, MENU, STOP, NUMBER
} state;

typedef enum //stale w programie
{
	space=32, enter=13, max_size=100, number_letters_alphabet=26
}constant;

/**Struktura letter - litera hasla
@param c dana litera
@param guessed true, gdy litera zostala juz odgadnieta, false - nie zostala odgadnieta
*/
typedef struct
{
	char c;
	bool guessed;	
}letter;

/**Struktura available - dostepna litera
@param c dana litera
@param consonant true, gdy litera jest spolgloska, false - jest samogloska
@param available_char true, gdy litera jest dostepna, false - nie jest dostepna
*/
typedef struct 
{
	char c;
	bool consonant; 
	bool available_char;
}available;

/**Struktura player - gracz
@param nick nick gracza
@param money fundusze gracza
*/
typedef struct
{
	char * nick;
	int money;
}player;

/**Struktura cur_data - glowne dane
@param tab_wheel statyczna tablica kola
@param tab_word dynamiczna tablica z wylosowanym haslem
@param number_players liczba graczy
@param category kategoria
@param chance numer wylosowanej nagrody z kola
@param number_letters liczba litere hasla
@param available_letters statyczna tablica dostepnych liter
@param tab_player dynamiczna tablica z danymi graczy
@param cur_player numer aktualnego gracza
@param state_cur aktualny stan
*/
typedef struct 
{
	int tab_wheel[25]; 
	letter  *tab_word; 
	int number_players; 
	char * category; 
	int chance; 
	int number_letters; 
	available available_letters[number_letters_alphabet]; 
	player *tab_player; 
	int cur_player;
	int state_cur;
}cur_data;

/**Funkcja wypisuje glowne menu
@param data struktura z glownymi danymi 
@return Funkcja zwraca nastepny stan
*/
state do_menu(cur_data *data); 

/**Funkcja wypisuje menu gry
@param data struktura z glownymi danymi
@return Funkcja zwraca nastepny stan
*/
state do_game_menu( cur_data *data);

/**Funkcja wypisuje tabele z losowym slowem
@param data struktura z glownymi danymi
*/
void do_write_tab(cur_data data); 

/**Funkcja wypisuje dostepne spolgloski
@param data struktura z glownymi danymi
@return Funckja zwraca liczbe wystapien danej litery w hasle
*/
int write_available_consonant(cur_data data); 

/**Funkcja wypisuje dostepne fundusze
@param data struktura z glownymi danymi
*/
void write_money(cur_data data);

/**Funkcja wypisuje tekst menu gry
@param data struktura z glownymi danymi
@return Funckja zwraca liczbe wystapien danej litery w hasle
*/
int write_menu(cur_data data);

/**Funkcja wypisuje komunikat i czeka na podanie entera
*/
void hit_enter();

/**Funkcja liczy liczbe liter danego slowa
@param tmp dane slowo
@return Funckja zwraca liczbe liter danego slowa
*/
int count_char(char *tmp);

/**Funkcja wypisuje help
*/
void write_help();

#endif
