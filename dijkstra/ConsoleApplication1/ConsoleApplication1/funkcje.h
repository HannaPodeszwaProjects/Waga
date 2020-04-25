#ifndef funkcje_H  
#define funkcje_H


#pragma once   
#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <iomanip>
#include <limits>
#include <ios>
#include<limits.h>

#include "struktury.h"

/**Funkcja dodaje nowe miasto do listy
@param pHead wskaznik na pierwszy element listy
@param nowanazwa nazwa miasta dodawanego do listy
@return Funkcja zwraca wskaznik na nowoutworzone miasto.
*/
miasto * stworz_miasto(miasto * &pHead, const std::string &nowanazwa);

/**Funkcja dodaje nowe elementy do listy dróg
@param kilometry odlegosc pomiedzy miastami
@param nowe_miasto1 wskaznik na  miasto poczatkowe 
@param nowe_miasto2 wskaznik na  miasto docelowe
@return Funkcja nie zwraca niczego.
*/
void stworz_droga( int kilometry, miasto * & nowe_miasto1, miasto * & nowe_miasto2);

/**Funkcja wypisuje liste miast
@param pHead wskaznik na pierwszy element listy
@return Funkcja nie zwraca niczego.
*/
void wypisz_miasto(miasto * pHead);

/**Funkcja wypisuje liste drog
@param pHead_droga wskaznik na pierwszy element listy
@return Funkcja nie zwraca niczego.
*/
void wypisz_droga(droga * pHead_droga);

/**Funkcja, wykorzystuj¹ca algorytm Dijkstry do znalezienia najkrotszych drog z miasta startowego do pozostalych miast   
@param startowy miasto, od ktorego beda rozpoczynac sie wszystkie trasy
@param pHead wskaznik na pierwszy element listy
@return Funkcja zwraca true, gdy miasto startowe bylo w liscie i false, gdy nie bylo w liscie
*/
bool Dijkstra(const std::string &startowy, miasto*  pHead);

/**Funkcja zapisuje wynik (wszystkie trasy do miast wraz z odleglosciami) do pliku wyjsciowego							
@param pHead wskaznik na pierwszy element listy
@param wyjscie nazwa pliku wyjsciowego
@return Funkcja nie zwraca niczego.
*/
void wypisz_wynik(miasto * pHead, const std::string &wyjscie);

/**Funkcja wypisuje trase do wskazanego miasta								
@param pHead wskaznik na pierwszy element listy
@param wyjscie nazwa pliku wyjsciowego
@return Funkcja nie zwraca niczego.
*/
void wypisz_miasta(miasto * pHead, std::ostream &wyjscie);

/**Funkcja sprawdza argumenty wywolania programu
@param ile ilosc argumentow
@param params tablica argumentow
@param[out] wejscie plik wejsciowy
@param[out] wyjscie plik wyjsciowy
@param[out] start miasto startowe
@return Funkcja zwraca true, gdy podane argumenty byly poprawne i false, gdy byly bledne
*/
bool sprawdz_argumenty(int ile, char ** params, std::string & wejscie, std::string & wyjscie, std::string & start);

/**Funkcja wczytuje dane (miasta oraz odleglosci) z pliku
@param wejscie nazwa pliku wejsciowego
@param[in,out] pGlowa wskaznik na pierwszy element listy
*/
void wczytajzPliku(const std::string &wejscie,miasto * &pGlowa);

/**Funkcja usuwa listy drog wszystkich miast
@param pmiasto wskaznik na kolejne miasto
@return Funkcja nie zwraca niczego.
*/
void usun_drogi( miasto*pmiasto);

/**Funkcja usuwa liste miast
@param[in,out] pHead wskaznik na pierwszy element listy
@return Funkcja nie zwraca niczego.
*/
void usun_miasta(miasto* &pHead);

/**Funkcja usuwa liste miast i drog
@param glowa_miasta wskaznik na pierwszy element listy miast
@return Funkcja nie zwraca niczego.
*/
void usun(miasto * glowa_miasta); 

/**Funkcja wyswietla pomoc
@param ile ilosc argumentow
@param params tablica argumentow
@return Funkcja nie zwraca niczego.
*/
void help(int ile, char ** params);

#endif
