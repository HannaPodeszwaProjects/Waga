#ifndef STRUKTURY_H  
#define STRUKTURY_H


#pragma once
#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <iomanip>
#include <limits>
#include <ios>
#include<limits.h>


struct droga;

/**Struktura miasto
@param nazwamiasta nazwa miasta
@param pmiasto wskaznik na nastepne miasto
@param miastaobok wskaznik na pierwszy element list drog
@param odwiedzony true, gdy miasto zostalo odwiedzone, false -- nie zostalo odwiedzone
@param odleglosc_od_centrali odleglosc od centrali
@param pMiastoPoprzednie wskaznik na miasto poprzednie
*/
struct miasto 
{
    std::string nazwamiasta;
    miasto * pmiasto;   
    droga *miastaobok;  
	bool odwiedzony;     
	int odleglosc_od_centrali; 
	miasto * pMiastoPoprzednie;
};

/**Struktura droga
@param trasa odleglosc miedzy miastami
@param pdroga wskaznik na nastepna droge
@param pmiasto wskaznik na odpowiednie miasto
*/
struct droga   
{
        int trasa;
        droga * pdroga;
        miasto * pmiasto;
};

#endif
