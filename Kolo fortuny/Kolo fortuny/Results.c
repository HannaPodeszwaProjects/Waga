#include <stdio.h>
#include <stdlib.h>     
#include <time.h> 
#include <stdbool.h>

#include "menu.h"
#include "play.h"
#include "prepare_game.h"
#include "results.h"
#include "settings.h"
#include "wheel.h"
#include "Release_memory.h"

int sort_nick(const void * a, const void * b) //funkcja porownuje elementy po nicku
{
	return strcmp( (*(result*)a).nick,(*(result*)b).nick);
}

int sort_money(const void * a, const void * b) //funkcja porownuje elementy po funduszach
{
	return ((*(result*)b).money - (*(result*)a).money);
}

state do_results( cur_data *data) //obsluguje stan wyniki
{
	int choice =- 1;
	int j; //liczba elementow tablicy

	result * tab=read_file_results(&j);
	if (tab == NULL) //gdy nie ma pliku
	{
		return MENU;
	}
	printf("Wypisz wynik posortowany po:\n1.nicku\n2.funduszach\n\n3.Usun wyniki\n0.Wyjdz\n");
	scanf_s("%d", &choice, sizeof(int));
	while (getchar() != '\n'); //czyszczenie bufora
	system("cls"); //czyszczenie konsoli
	switch (choice)
	{
	case 1:
		qsort(tab, j, sizeof(*tab), sort_nick); //po nicku
		printf("\nWyniki posortowane po nicku:\n");
		for (int i = 0; i < j; i++)
		{
			printf("\n%s\t%d\n", tab[i].nick, tab[i].money);
		}
		break;
	case 2:
		qsort(tab, j, sizeof(*tab), sort_money); //po funduszach
		printf("\nWyniki posortowane po funduszach:\n");
		for (int i = 0; i < j; i++)
		{
			printf("\n%d\t%s\n", tab[i].money, tab[i].nick);
		}
		break;
	case 3:
		remove("wyniki"); //usuniecie pliku z wynikami
		printf("Wyniki zostaly usuniete.\n");
		break;
	default:
			printf("Niepoprawna wartosc.\n");
			break;
	}
		
	hit_enter();
	release_results(&tab,j);
	return MENU;
}


state do_save( cur_data *data) //zapisuje wynik
{
	FILE * file = NULL;
	fopen_s(&file, "wyniki", "a");
	if (file)
	{
		for (int i = 0; i < (*data).number_players; i++)
		{
			fprintf(file, "%s\n%d\n", (*data).tab_player[i].nick, (*data).tab_player[i].money);
		}
		fclose(file);
		return MENU;
	}
	else
	{
		printf("Blad otwarcia pliku.\n");
	}
	return MENU;
}

result * read_file_results(int * j) //wpisuje do tablicy wyniki z pliku
{
	char * tmp = -1; 
	result * tab = NULL;
	FILE * file = NULL;
	fopen_s(&file, "wyniki", "r");
	 *j = 0; //liczba wynikow
	if (file)
	{
		tab = (result*)malloc(sizeof(result));
		while (tmp != NULL)
		{
			tmp = read_word(file); //wczytuje nick z pliku do dynamicznej tablicy
			if (tmp != NULL)
			{
				tab = realloc(tab, ((*j) + 1) * sizeof(result));
				tab[(*j)].nick = tmp;
				fscanf_s(file, "%d\n",&(tab[(*j)].money));
				(*j)++;			
			}
		}
		fclose(file);
	}
	else
	{
		printf("Brak wynikow.\n");
		tab = NULL;
		hit_enter();
	}
	return tab;
}