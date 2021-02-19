using MongoDB.Bson;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;

namespace Listazakupow2
{
    public class Produkt
    {
        public ObjectId Id { get; set; }
        public string nazwa { get; set; }
        public float cena { get; set; }
       // public string ilosc { get; set; }
        public List<String> kategorie { get; set; }
        public int uzyty;

        public Produkt()
        {
            kategorie = new List<String>();
            nazwa = "nul";
            cena = 0;
           // ilosc = "brak informacji";
        }

        public Produkt(string nazw, float cen)
        {
            kategorie = new List<String>();
            nazwa = nazw;
            cena = cen;
        }

        private Produkt nowy_produkt(string nazw,float cen)
        {
            kategorie = new List<String>();
            nazwa = nazw;
            cena = cen;
            return new Produkt();
        }

        public void usunProdukt()
        {
            var filter = Builders<Listazakupow2.Produkt>.Filter.Eq("_id", this.Id);
            BazaDanych.bazaProduktow.DeleteOne(filter);
            BazaDanych.listaProduktow.Remove(this);
        }

        public Produkt znajdzProdukt(ObjectId id)
        {
            foreach( var i in BazaDanych.listaProduktow)
            {
                if (i.Id == id)
                    return i;
            }
            return null;
        }

        public bool dodajProdukt(string nazwa, string cena, string kategorie)
        {
            this.nazwa = nazwa;
            try
            {
                this.cena = float.Parse(cena);
            }
            catch(Exception e)
            {
                const string message =
                      "Zly format ceny";
                const string caption = "Niepoprawna cena";
                var result = MessageBox.Show(message, caption,
                                             MessageBoxButtons.OK,
                                             MessageBoxIcon.Question);
                return true;
            }
            if (this.cena < 0)
            {
                const string message =
                      "Zly format ceny";
                const string caption = "Niepoprawna cena";
                var result = MessageBox.Show(message, caption,
                                             MessageBoxButtons.OK,
                                             MessageBoxIcon.Question); 
                return true;
            }

            this.uzyty = 0;

            if (kategorie != "")
            {
                foreach (var kategoria in kategorie.Split(","))
                {
                    string przyciete = kategoria.Trim();
                    if (przyciete != "")
                        this.kategorie.Add(przyciete);
                }
            }

            BazaDanych.listaProduktow.Add(this); //dodanie produktu do listy
            BazaDanych.bazaProduktow.InsertOne(this); //dodanie produktu do bazy

            return false;
        }

        public void zmienLiczbeUzyc(int zmiana, ObjectId id)
        {
            this.uzyty += zmiana;
            var filter1 = Builders<Produkt>.Filter.Eq("_id", id);
            var update1 = Builders<Listazakupow2.Produkt>.Update.Set("uzyty", this.uzyty);
            BazaDanych.bazaProduktow.UpdateOne(filter1, update1);
        }

        public bool aktualizuj(string nazwa, string cena, string kategorie)
        {
            float cenaOryginalna = this.cena;
            try
            {
                this.cena = float.Parse(cena);
            }
            catch (Exception e)
            {
                const string message =
                      "Zly format ceny";
                const string caption = "Niepoprawna cena";
                var result = MessageBox.Show(message, caption,
                                             MessageBoxButtons.OK,
                                             MessageBoxIcon.Question);
                return true;
            }
            if (this.cena < 0)
            {
                this.cena = cenaOryginalna;

                const string message =
                        "Zly format ceny";
                const string caption = "Niepoprawna cena";
                var result = MessageBox.Show(message, caption,
                                             MessageBoxButtons.OK,
                                             MessageBoxIcon.Question);
                return true;
            }
            this.nazwa = nazwa;

            this.kategorie.Clear();



            if (kategorie.EndsWith(","))
                kategorie = kategorie.Remove(kategorie.Length - 1, 1);

            if (kategorie != "")
            {
                foreach (var kategoria in kategorie.Split(","))
                {
                    string przyciete = kategoria.Trim();
                    if (przyciete != "")
                        this.kategorie.Add(przyciete);
                }
            }

            var filter = Builders<Listazakupow2.Produkt>.Filter.Eq("_id", this.Id);
            var update = Builders<Listazakupow2.Produkt>.Update.Set("nazwa", this.nazwa);
            BazaDanych.bazaProduktow.UpdateOne(filter, update);
            update = Builders<Listazakupow2.Produkt>.Update.Set("cena", this.cena);
            BazaDanych.bazaProduktow.UpdateOne(filter, update);
            update = Builders<Listazakupow2.Produkt>.Update.Set("kategorie", this.kategorie);
            BazaDanych.bazaProduktow.UpdateOne(filter, update);

            return false;
        }
    }
}
