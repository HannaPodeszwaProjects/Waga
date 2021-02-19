using System;
using System.Collections.Generic;
using System.Text;
using MongoDB.Bson;
using MongoDB.Driver;
using System.Text.RegularExpressions;

using MongoDB.Driver.Linq;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Windows.Forms;


namespace Listazakupow2
{
    public class Sklep
    {
        public ObjectId Id { get; set; }
        public string nazwaSklepu;
        public int uzyty;

        public Sklep znajdzSklep(ObjectId id)
        {
            foreach (var i in BazaDanych.listaSklepow)
            {
                if (i.Id == id)
                    return i;
            }
            return null;
        }

        public void dodaj(string nazwa)
        {
            if (czyIstnieje(nazwa))
            {
                nazwaSklepu = nazwa;
                uzyty = 0;

                BazaDanych.listaSklepow.Add(this); //dodanie sklepu do listy
                BazaDanych.bazaSklepow.InsertOne(this); //dodanie sklepu do bazy
            }
            else
            {
                const string message =
                     "Ten sklep jest już w bazie.";
                const string caption = "Niepoprawna nazwa sklepu";
                var result = MessageBox.Show(message, caption,
                                             MessageBoxButtons.OK,
                                             MessageBoxIcon.Question);
            }

        }

        public void usun()
        {
                var filter = Builders<Listazakupow2.Sklep>.Filter.Eq("_id", this.Id);
                BazaDanych.bazaSklepow.DeleteOne(filter);
                BazaDanych.listaSklepow.Remove(this);
        }

        //Sprawdza, czy sklep o podanej nazwe jest juz w bazie danych
        private bool czyIstnieje(string nazwa)
        {
            foreach ( var i in BazaDanych.listaSklepow)
            {
                if(i.nazwaSklepu.Equals(nazwa))
                {
                    return false;
                }
            }
            return true;
        }

        //Inkrementuje lub dekrementuje liczbe uzyc danego sklepu
        public void zmienLiczbeUzyc(int zmiana, ObjectId id)
        {
            this.uzyty += zmiana;
            var filter1 = Builders<Sklep>.Filter.Eq("_id", id);
            var update1 = Builders<Listazakupow2.Sklep>.Update.Set("uzyty", this.uzyty);
            BazaDanych.bazaSklepow.UpdateOne(filter1, update1);
        }
    }
}
