using MongoDB.Bson;
using MongoDB.Driver;
using MongoDB.Driver.Core;
using MongoDB.Driver.Linq;
using System;
using System.Collections.Generic;
using System.Text;

namespace Listazakupow2
{
    public static class BazaDanych
    {
        static IMongoClient _klient= new MongoClient();
        static IMongoDatabase _baza = _klient.GetDatabase("listazakupow");
        public static IMongoCollection<Produkt> bazaProduktow = _baza.GetCollection<Produkt>("mojafajnakolekcja");
        //private static List<Szablon> szablon;

        public static List<Produkt> listaProduktow = BazaDanych.Get();
        public static List<Produkt> Get() =>
            bazaProduktow.Find(Produkt => true).ToList();

        public static IMongoCollection<Lista> bazaList = _baza.GetCollection<Lista>("aktualne_listy");
        public static List<Lista> listaList = BazaDanych.GetLista();
        public static List<Lista> GetLista() => bazaList.Find(Lista => true).ToList();



        public static IMongoCollection<Szablon> bazaSzablonow = _baza.GetCollection<Szablon>("szablony");
        public static List<Szablon> listaSzablonow = BazaDanych.GetSzablon();
        public static List<Szablon> GetSzablon() => bazaSzablonow.Find(Lista => true).ToList();

        public static IMongoCollection<Sklep> bazaSklepow = _baza.GetCollection<Sklep>("sklepy");
        public static List<Sklep> listaSklepow = BazaDanych.GetSklep();
        public static List<Sklep> GetSklep() => bazaSklepow.Find(Lista => true).ToList();


    }
}


