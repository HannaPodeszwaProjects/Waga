using MongoDB.Driver;
using MongoDB.Bson;
using System;
using System.Collections.Generic;
using System.Text;

namespace Listazakupow2
{
    public class Szablon
    {

        public string nazwaSzablonu;
        public float cenaSzablonu;
        public float cenaDlaKogos;

        public List<Tuple<MongoDB.Bson.ObjectId, String, bool>> listaProduktowWSzablonie;
        public List<Tuple<MongoDB.Bson.ObjectId, float>> listaSklepow = new List<Tuple<MongoDB.Bson.ObjectId, float>>();

        public MongoDB.Bson.ObjectId Id { get; set; }
        
        public Szablon()
        {

        }

        public Szablon(string nazwa)
        {
            this.nazwaSzablonu = nazwa;
            listaProduktowWSzablonie = new List<Tuple<MongoDB.Bson.ObjectId, String, bool>>();
        }
    


        public void zapiszSzablon(List<Tuple<MongoDB.Bson.ObjectId, String, bool>> ListaWybranychZakupow)
        {
            Produkt p = new Produkt();
            foreach (var i in ListaWybranychZakupow)
            {
                listaProduktowWSzablonie.Add(i);
                p = p.znajdzProdukt(i.Item1);
                p.zmienLiczbeUzyc(1, i.Item1); //uzyty++
            }
            BazaDanych.listaSzablonow.Add(this);

            BazaDanych.bazaSzablonow.InsertOne(this);
        }

        public void zapiszSzablonZListy(Lista l)
        {

            listaProduktowWSzablonie = l.produktyWLiscie;
            Produkt p = new Produkt();
            foreach (var i in listaProduktowWSzablonie)
            {
                p = p.znajdzProdukt(i.Item1);
                p.zmienLiczbeUzyc(1, i.Item1); //uzyty++
            }
            cenaSzablonu = l.cenaKoszyka;
            if (!(l.sklep.Equals(ObjectId.Empty)))
            {
                Tuple<MongoDB.Bson.ObjectId, float> t = new Tuple<MongoDB.Bson.ObjectId, float>(l.sklep, l.cenaKoszyka); //sklep + cena
                this.listaSklepow.Add(t);
            }

            BazaDanych.listaSzablonow.Add(this);

            BazaDanych.bazaSzablonow.InsertOne(this);
        }

        public void aktualizujSzablon(List<Tuple<MongoDB.Bson.ObjectId, String, bool>> ListaWybranychZakupow, String nazwa)
        {
            listaProduktowWSzablonie.Clear();
            Produkt p = new Produkt();
            foreach (var i in this.listaProduktowWSzablonie)
            {
                p = p.znajdzProdukt(i.Item1);
                p.zmienLiczbeUzyc(-1, i.Item1); //uzyty--

            }
            foreach (var item in ListaWybranychZakupow)
            {
                listaProduktowWSzablonie.Add(item);
                p = p.znajdzProdukt(item.Item1);
                p.zmienLiczbeUzyc(1, item.Item1); //uzyty++
            }

            nazwaSzablonu = nazwa;

            var filter = Builders<Szablon>.Filter.Eq("_id", this.Id);
            var update1 = Builders<Szablon>.Update.Set("listaProduktowWSzablonie", listaProduktowWSzablonie);
            var update2 = Builders<Szablon>.Update.Set("nazwaSzablonu", nazwaSzablonu);
            var update3 = Builders<Szablon>.Update.Set("cenaSzablonu", 0);
            BazaDanych.bazaSzablonow.UpdateOne(filter, update1);
            BazaDanych.bazaSzablonow.UpdateOne(filter, update2);
            BazaDanych.bazaSzablonow.UpdateOne(filter, update3);
            var update4 = Builders<Szablon>.Update.Set("listaSklepow", listaSklepow);
            BazaDanych.bazaSzablonow.UpdateOne(filter, update4);
        }


        public void usunSzablon()
        {
            Produkt p = new Produkt();
            foreach (var i in this.listaProduktowWSzablonie)
            {
                p = p.znajdzProdukt(i.Item1);

                p.zmienLiczbeUzyc(-1, i.Item1); //uzyty--
            }
            var filter = Builders<Szablon>.Filter.Eq("_id", this.Id);
            BazaDanych.bazaSzablonow.DeleteOne(filter);
            BazaDanych.listaSzablonow.Remove(this);
        }


    }
}
