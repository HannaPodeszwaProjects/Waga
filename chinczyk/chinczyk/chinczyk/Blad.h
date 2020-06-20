#pragma once
#ifndef Blad_H
#define Blad_H

class blad
{
public:
	virtual void wypisz_blad() = 0;
};

class zly_znak : public blad //znak inny niz 1,2,3 lub 4
{
public:
	virtual void wypisz_blad();
	virtual ~zly_znak();
};

//pionek juz jest na srodku planszy
class pionek_koniec : public blad {
public:
	virtual void wypisz_blad();
	virtual ~pionek_koniec();
};

//pionek jest w bazie a kostka nie jest 6
class pionek_baza : public blad {
public:
	virtual void wypisz_blad();
	virtual ~pionek_baza();
};

//pionek zbilby inny pionek na mecie
class meta_kolizja : public blad {
public:
	virtual void wypisz_blad();
	virtual ~meta_kolizja();
};

//pionek wypadlby za plansze
class za_daleko : public blad {
public:
	virtual void wypisz_blad();
	virtual ~za_daleko();
};



#endif