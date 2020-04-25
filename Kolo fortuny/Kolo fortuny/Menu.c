#include <stdio.h>
#include <stdlib.h>     
#include <time.h> 
#include <stdbool.h>
#include <windows.h>
#include "Menu.h"
#include "Play.h"
#include "Prepare_game.h"
#include "Results.h"
#include "Settings.h"
#include "Wheel.h"
#include "Release_memory.h"

state do_menu(cur_data *data) //wypisuje glowne menu
{

	system("cls"); //czyszczenie konsoli
	int choice = -1;
	char category[max_size];
	strcpy_s(category, max_size, (*data).category); //usuniecie rozszerzenia z nazwy kategorii
	category[strlen((*data).category) - 4] = 0;
	printf("\n\n**************************\n*      KOLO FORTUNY      *\n**************************\n\n");
	printf("1.GRAJ\n\nKATEGORIA: %s\nILOSC GRACZY: %d\n\n2.USTAWIENIA\n3.WYNIKI\n\n0.WYJSCIE\n\n", category, (*data).number_players);
	scanf_s("%d", &choice, sizeof(int));
	fseek(stdin, 0, SEEK_END); //usuniecie dodatkowych znakow z bufora

	if (choice < 0 || choice > 3)
	{
		
		printf("Niepoprawna wartosc.\n");
		hit_enter();
		return MENU;
	}
	return choice;
}

int write_available_consonant(cur_data data) //wypisuje dostepne spolgloski
{
	int number=0; //liczba dostepnych liter 
	printf("Dostepne spolgloski:\n");
	for (int i = 0; i < number_letters_alphabet; i++)
	{
		if (data.available_letters[i].available_char) //dostepne litery
		{
			if (data.available_letters[i].consonant) //spolgloski
			{
				number++;
				printf("%c ", data.available_letters[i].c);
			}
		}
	}
	printf("\n");
	return number;
}

void write_money(cur_data data) //wypisuje dostepne fundusze
{
	for (int i = 0; i < data.number_players; i++)
	{
		printf("Gracz %d: %s \t", i + 1, data.tab_player[i].nick);
	}
	printf("\n");
	for (int i = 0; i < data.number_players; i++)
	{
		printf("Fundusze: %d \t", data.tab_player[i].money);
	}
}

int write_menu(cur_data data) //wypisuje tekst menu gry
{
	system("cls"); //czyszczenie konsoli             
	printf("\n**************************\n*      KOLO FORTUNY      *\n**************************\n\n");
	write_money(data); //wypisuje dostepne fundusze
	char category[max_size];
	strcpy_s(category,max_size, data.category); //usuniecie rozszerzenia z nazwy kategorii
	category[strlen(data.category) - 4]=0;
	printf("\n\n      Kategoria: %s\n\n", category);
	printf("      Kolej: %s\n\n", data.tab_player[data.cur_player].nick);
	if (data.chance == -1)
	{
		printf("---------------------\n-        BANKRUT        -\n---------------------\n", data.chance);
	}
	else if (data.chance == 0)
	{
		printf("---------------\n-     PASS     -\n---------------\n", data.chance);
	}
	else if (data.chance > 1 && data.chance < 2000)
	{
		printf("---------------\n-     %d     -\n---------------\n", data.chance);
	}
	do_write_tab(data); //wypisuje tabele z losowym slowem
	return write_available_consonant(data);
}

state do_game_menu(cur_data *data) //wypisuje menu gry
{
	int choice = -1;
	int number =write_menu((*data)); //wypisuje tekst menu gry
	printf("Wybierz akcje:\n\n1.Zakrec kolem\n2.Zgaduj haslo\n3.Kup samogloske\n0.Opusc gre\n");
	
	int not_guessed = 0;
	for (int i = 0; i < (*data).number_letters; i++) //sprawdzenie, czy w hasle sa nieodgadniete litery
	{
		if ((*data).tab_word[i].guessed == false)
		{
			not_guessed++;
		}
	}
	if (not_guessed == 0)
	{
		return WIN;
	}
	scanf_s("%d", &choice, sizeof(int));
	fseek(stdin, 0, SEEK_END);

	if (number == 0 && choice == 1)
	{
		printf("Brak dostepnych spolglosek. Wybierz inna akcje.");
		hit_enter();
		return GAME_MENU;
	}
	else if (choice < 0 || choice > 3)
	{
		system("cls"); //czyszczenie konsoli
		printf("Niepoprawna wartosc.\n");
		hit_enter();
		return GAME_MENU;
	}
	else if (choice == 0)
	{
		release_random_word(&(*data));
		return MENU;
	}
	else
	{
		return (choice + GAME_MENU);
	}
}


void do_write_tab(cur_data data) //wypisuje puste miejsca i odgadniete lietry
{
	int n;
	for (n = 0; data.tab_word[n].c != 0; n++)
	{
		if (data.tab_word[n].guessed == false)
			printf(" _ ");
		else
			printf(" %c ", data.tab_word[n].c);
	}
	printf("\n\n");
}

void hit_enter()
{
	printf("\nNacisnij enter by kontynuowac\n");
	if (getchar() == enter)
	{
		system("cls"); //czyszczenie konsoli
	}
}

int count_char(char *tmp)
{
	int n = 0;
	for (; tmp[n] != '\n' && tmp[n] != 0; n++);
	return n;
}

void write_help() 
{
	printf("KOLO FORTUNY\n\n");
	printf("Gra polega na losowaniu kwot na tytulowym kole,\nodpowiednim dobieraniu (malych,bez polskich znakow) liter i odgadywaniu hasel,\n");
	printf("stanowiac polaczenie gry w wisielca i ruletke.\n\nargv[1]: liczba graczy (od 1 do 4)\n\n");
}