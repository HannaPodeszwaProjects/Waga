using System;
using System.Globalization;
using System.Collections.Generic;
using MongoDB.Driver;


namespace Listazakupow2
{
    public class Lista
    {
        public string nazwaListy;
        public float cenaKoszyka;
        public float cenaDlaKogos;
        public bool zatwierdzona;
        public bool anulowana;
        public MongoDB.Bson.ObjectId sklep;
        public MongoDB.Bson.ObjectId Id { get; set; } //indeks listy
        public List<Tuple<MongoDB.Bson.ObjectId, String, bool>> produktyWLiscie; //indeksy produktow w liscie
        public DateTime dataZatwierdzenia;


        public Lista()
        {
            zatwierdzona = false;
            produktyWLiscie = new List<Tuple<MongoDB.Bson.ObjectId, String, bool>>();

        }

        public List<Tuple<Produkt, String, bool>> wyswietl_liste()
        {
            List<Tuple<Produkt, String, bool>> do_wypisania  =  new List<Tuple<Produkt, String, bool>>();

            foreach (var item in produktyWLiscie)
            {
                do_wypisania.Add(new Tuple<Produkt, String, bool>(BazaDanych.listaProduktow.Find(x => x.Id == item.Item1), item.Item2, item.Item3));
            }
            return do_wypisania;
        }

        public void dodaj_liste(List<Tuple<MongoDB.Bson.ObjectId, string, bool>> ListaWybranychZakupow, string nazwa)
        {
            Produkt p = new Produkt();
            nazwaListy = nazwa;
            foreach (var item in ListaWybranychZakupow)
            {

                produktyWLiscie.Add(new Tuple<MongoDB.Bson.ObjectId, String, bool>(item.Item1, item.Item2, item.Item3));
            
                p = p.znajdzProdukt(item.Item1);
                p.zmienLiczbeUzyc(1, item.Item1); //uzyty++
               
            }
            BazaDanych.listaList.Add(this); //dodanie do listy
            BazaDanych.bazaList.InsertOne(this); //dodanie do bazy

            return;
        }

        public void aktualizuj_liste(List<Tuple<MongoDB.Bson.ObjectId, string, bool>> ListaAktualnychZakupow, string nazwa)
        {
            produktyWLiscie.Clear();
            Produkt p = new Produkt();
            foreach (var i in this.produktyWLiscie)
            {
                p = p.znajdzProdukt(i.Item1);
                p.zmienLiczbeUzyc(-1, i.Item1); //uzyty--
                
            }
            foreach (var item in ListaAktualnychZakupow)
            {
                produktyWLiscie.Add(new Tuple<MongoDB.Bson.ObjectId, String, bool>(item.Item1, item.Item2, item.Item3));
                p = p.znajdzProdukt(item.Item1);
                p.zmienLiczbeUzyc(1, item.Item1); //uzyty++
              
            }

            nazwaListy = nazwa;

            var filter = Builders<Lista>.Filter.Eq("_id", this.Id);
            var update = Builders<Listazakupow2.Lista>.Update.Set("nazwaListy", this.nazwaListy);
            BazaDanych.bazaList.UpdateOne(filter, update);
            update = Builders<Listazakupow2.Lista>.Update.Set("produktyWLiscie", this.produktyWLiscie);
            BazaDanych.bazaList.UpdateOne(filter, update);
        }


        public void anuluj()
        {
            this.anulowana = true;
            var filter = Builders<Lista>.Filter.Eq("_id", this.Id);
            var update = Builders<Listazakupow2.Lista>.Update.Set("anulowana", this.anulowana);
            BazaDanych.bazaList.UpdateOne(filter, update);
        }

        public void usun_liste()
        {
            Produkt p = new Produkt();
            foreach (var i in this.produktyWLiscie)
            {
                p = p.znajdzProdukt(i.Item1);

                p.zmienLiczbeUzyc(-1, i.Item1); //uzyty--
            }

            var filter = Builders<Listazakupow2.Lista>.Filter.Eq("_id", this.Id);
            BazaDanych.bazaList.DeleteOne(filter);
            BazaDanych.listaList.Remove(this);

        }

        public void zatwierdzListe(float cena)
        {
            this.cenaKoszyka = cena;
            this.zatwierdzona = true;
            this.dataZatwierdzenia = DateTime.SpecifyKind(DateTime.Now, DateTimeKind.Local);


            var filter = Builders<Lista>.Filter.Eq("_id", this.Id);
            var update = Builders<Listazakupow2.Lista>.Update.Set("zatwierdzona", this.zatwierdzona);
            BazaDanych.bazaList.UpdateOne(filter, update);
            update = Builders<Listazakupow2.Lista>.Update.Set("cenaKoszyka", cena);
            BazaDanych.bazaList.UpdateOne(filter, update);
            update = Builders<Listazakupow2.Lista>.Update.Set("cenaDlaKogos", cenaDlaKogos);
            BazaDanych.bazaList.UpdateOne(filter, update);
            update = Builders<Listazakupow2.Lista>.Update.Set("dataZatwierdzenia", dataZatwierdzenia);
            BazaDanych.bazaList.UpdateOne(filter, update);
            update = Builders<Listazakupow2.Lista>.Update.Set("sklep", sklep);
            BazaDanych.bazaList.UpdateOne(filter, update);
        }
        public string dajRok()
        {

            return this.dataZatwierdzenia.Year.ToString();
            
        }
        public string dajMiesiac()
        {
            CultureInfo culture = new CultureInfo("pl-PL");
            return this.dataZatwierdzenia.Month.ToString(culture);
        }
        public string dajDate()
        {
            CultureInfo culture = new CultureInfo("pl-PL");
            return this.dataZatwierdzenia.ToString("t D",culture);
        }
        public string dajMiesiacS()
        
        {
            CultureInfo culture = new CultureInfo("pl-PL");
            return this.dataZatwierdzenia.ToString("MMMM", culture);
        }

    }
}