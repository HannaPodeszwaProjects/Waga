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

int check_letter(cur_data *data, char letter_c ) //sprawdza czy podana litera wystepuje w hasle
{
	int result=0; //ilosc wystapien danej litery w hasle
	for (int i = 0; (*data).tab_word[i].c!=0; i++)
	{
		if ((*data).tab_word[i].c == letter_c)
		{
			(*data).tab_word[i].guessed = true; //odgadnieta
			result++;
		}
	}
	(*data).available_letters[letter_c - 97].available_char = false; //dana litera nie jest juz dostepna
	return result;
}

state do_guess_letter( cur_data *data) //umozliwia wybranie litery 
{
	
	while (getchar() != '\n');
	write_menu((*data)); //wypisanie menu gry
	printf("Podaj spolgloske  \n\n");
	char choice = getchar();
	while (getchar() != '\n'); //czyszczenie bufora
	if (choice < 'a' || choice > 'z' ) //sprawdzenie czy to litera
	{
		system("cls"); //czyszczenie konsoli
		printf("Niepoprawna wartosc.\n");
		return GUESS_LETTER;
	}

	//sprawdzenie czy litera nie jest samogloska
	if (choice == 'a' || choice == 'e' || choice == 'i' || choice == 'o' || choice == 'u' || choice == 'y') 
	{
		system("cls"); //czyszczenie konsoli
		printf("To jest samogloska.\n");
		return GUESS_LETTER;
	}

	for (int i = 0; i < number_letters_alphabet; i++)
	if ((choice -32) == (*data).available_letters[i].c) //sprawdzenie czy litera byla juz wybrana
	{
		if ((*data).available_letters[i].available_char == false)
		{
			system("cls"); //czyszczenie konsoli
			printf("Ta litera byla juz wybrana.\n");
			return GUESS_LETTER;
		}
	}

	printf("Wybrano: %c\n", choice);
	int result = check_letter(&(*data), choice); //sprawdza czy podana litera wystepuje w hasle
	if (result > 0)
	{
		printf("Ta litera wystapila w hasle %d razy", result);
		//dodanie do funduszu wylosowanej nagrody*ilosc wystapien litery w hasle 
		(*data).tab_player[(*data).cur_player].money += (result*(*data).chance); 
	}
	else
	{
		printf("Tej litery nie ma w hasle");
		(*data).cur_player = ((*data).cur_player + 1) % (*data).number_players;
	}
	hit_enter();
		
	return GAME_MENU;
}

state do_guess_word( cur_data *data) //umozliwia odgadniecie hasla
{
	int result=0;
	char tmp[max_size];
	printf("Podaj haslo:\n");
	fgets(tmp, sizeof (tmp), stdin);
	fseek(stdin, 0, SEEK_END); //usuniecie dodatkowych znakow z bufora
	int n = count_char(tmp);

	if (n != (*data).number_letters) //sprawdzenie czy liczba liter podanego slowa jest inna niz liczba liter hasla
	{
		system("cls"); //czyszczenie konsoli
		printf("Niepoprawne haslo.\n");
		hit_enter();
		return GAME_MENU;
	}
	for (int i = 0; i < (*data).number_letters; i++) //sprawdzenie czy podane haslo pasuje do wylosowanego hasla
	{
		if ((*data).tab_word[i].c == tmp[i])
		{
			result++;
		}
	}
	if (result == (*data).number_letters) //sprawdzenie czy liczba liter jest poprawna
	{
		(*data).tab_player[(*data).cur_player].money += 10000; //dodanie nagrody do funduszy zwyciezcy 
		return WIN;
	}
	else
	{
		(*data).cur_player = ((*data).cur_player + 1) % (*data).number_players; //kolej nastepnego gracza
	}
	printf("Niepoprawne haslo.\n");
	return GAME_MENU;
}

state do_buy( cur_data *data) //umozliwia kupienie samogloski
{
	system("cls"); //czyszczenie konsoli
	printf("Koszt 200.\nDostepne samogloski: ");
	int available = 0;
	for (int i = 0; i < number_letters_alphabet; i++) //wypisanie dostepnych samoglosek
	{
		if ((*data).available_letters[i].available_char) //dostepna
		{
			if (!(*data).available_letters[i].consonant) //samogloska
			{
				printf("%c ", (*data).available_letters[i].c);
				available++;				
			}
		}
	}
	if (available == 0) //jesli zadna samogloska nie jest dostepna
	{
		printf("Brak dostepnych samoglosek.\n");
		hit_enter();
		return GAME_MENU;
	}
	printf("\n");

	printf("Podaj samogloske  \n\n");
	char choice = getchar();
	while (getchar() != '\n'); //czyszczenie bufora
		
	if (choice == 'a' || choice == 'e' || choice == 'i' || choice == 'o' || choice == 'u' || choice == 'y') //sprawdzenie czy litera jest samogloska
	{
		for (int i = 0; i < number_letters_alphabet; i++)
			if ((choice - 32) == (*data).available_letters[i].c) //sprawdzenie czy litera byla juz wybrana
			{
				if ((*data).available_letters[i].available_char == false)
				{
					system("cls"); //czyszczenie konsoli
					printf("Ta litera byla juz wybrana.\n");
					hit_enter();
					return GAME_MENU;
				}
			}
		if ((*data).tab_player[(*data).cur_player].money - 200 < 0) //sprawdzenie czy wystarczy funduszy
		{
			printf("Za malo funduszy.  \n\n");
			return GAME_MENU;
		}
		(*data).tab_player[(*data).cur_player].money -= 200;
		int result = check_letter(&(*data), choice); //sprawdzenie czy litera wystepuje w hasle
		printf("Wybrano: %c\n", choice);
		hit_enter();
	}
	else
	{
		printf("Niepoprawna wartosc");
	}
	return GAME_MENU;
}

state do_win( cur_data *data) //obsluguje stan po wygranej
{
	int choice = -1;
	write_menu((*data)); //wypisanie menu gry
	printf("\n");
	for (int i = 0; i < (*data).number_letters; i++) //ustawienie wszystkich liter jako odgadniete
	{
		(*data).tab_word[i].guessed = 1;
	}

	do_write_tab((*data)); //wypisanie hasla
	printf("*************\n*  WYGRANA  *\n*************\n\n");
	printf("Wygral %s\n\n",(*data).tab_player[(*data).cur_player].nick);
	printf("0.Wyjdz bez zapisywania\n1.Zapisz i wyjdz\n\n");
	scanf_s("%d", &choice, sizeof(int));
	system("cls"); //czyszczenie konsoli
	while (getchar() != '\n'); //czyszczenie bufora
	if (choice < 0 || choice > 1)
	{
		printf("Niepoprawna wartosc.\n");
		return WIN;
	}
	if (choice == 0)
	{
		release_random_word(&(*data)); //zwolnienie pamieci
		return MENU;
	}
	else
	{
		release_random_word(&(*data)); //zwolnienie pamieci
		return SAVE;
	}
}