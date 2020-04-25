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

state do_settings( cur_data *data) //obsluguje stan ustawienia
{
	int choice = -1;
	char category[max_size];
	strcpy_s(category, max_size, (*data).category); //usuniecie rozszerzenia z nazwy kategorii
	category[strlen((*data).category) - 4] = 0;
	printf("Zmien:\n\n1.KATEGORIA: %s\n2.ILOSC GRACZY: %d\n0.WYJSCIE\n", category, (*data).number_players);
	scanf_s("%d", &choice, sizeof(int));
	while (getchar() != '\n'); //czyszczenie bufora

	if (choice < 0 || choice > 2)
	{
		system("cls"); //czyszczenie konsoli
		printf("Niepoprawna wartosc.\n");
		return SETTINGS;
	}
	switch (choice)
	{
	case 1:
		change_category(&(*data).category);
		break;
	case 2:
		release_players(&(*data)); //zwolnienie pamieci
		change_number_players(&(*data).number_players);
		return INIT;
	case 0:
		break;
	default:
		break;
	}
	hit_enter();
	return MENU;
}

void change_category(char ** category) //umozliwia zmiane kategorii
{
	char *tab[5] = { "Zwierzeta.txt","Przyslowia.txt"}; // nazwy plikow z kategoriami
	int choice = -1;
	printf("Wybierz nowa kategorie:\n\n1.Zwierzeta\n2.Przyslowia\n");
	scanf_s("%d", &choice, sizeof(int));
	while (getchar() != '\n'); //czyszczenie bufora
	if (choice < 0 || choice > 2)     
	{
		system("cls"); //czyszczenie konsoli
		printf("Niepoprawna wartosc. Zostala wybrana losowa kategoria\n");
		(*category) = tab[rand()%2];
		return;
	}
	(*category)=tab[choice-1];
}

void change_number_players(int * number) //umozliwia zmiane ilosci graczy
{
	int choice = -1;
	printf("Wybierz nowa liczbe graczy:\n\n1\n2\n3\n4\n");
	scanf_s("%d", &choice, sizeof(int));
	while (getchar() != '\n'); //czyszczenie bufora
	if (choice < 1 || choice > 4)  
	{
		system("cls"); //czyszczenie konsoli
		printf("Niepoprawna wartosc. Liczba graczy nie zostala zmieniona\n");
		return;
	}
	(*number) = choice;
}