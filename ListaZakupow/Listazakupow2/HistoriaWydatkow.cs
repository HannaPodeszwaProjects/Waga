using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;


enum tryb { wszytkierok, wszytkie, danymiesmiesiac }


namespace Listazakupow2
{
    public partial class HistoriaWydatkow : Form
    {

        private List<Lista> zatwierdzone = new List<Lista>();
        private List<string> roczniki = new List<string>();
        private List<string> miesiace = new List<string>();


        public HistoriaWydatkow()
        {
            InitializeComponent();
            uzupelnijRoczniki();
        }


        private void miesiac_SelectedIndexChanged(object sender, EventArgs e)
        {
            string wybranyRok = roczniki[rok.SelectedIndex];
            string wybranyMiesiac = miesiace[miesiac.SelectedIndex];
            if (wybranyMiesiac == "Wszystkie")
            {
                obliczsume((int)tryb.wszytkierok);
            }
            else
            {
                obliczsume((int)tryb.danymiesmiesiac);
            }
        }

        private void rok_SelectedIndexChanged(object sender, EventArgs e)
        {
            String ostatniaData = "";
            miesiac.Items.Clear();
            miesiace.Clear();
            string wybranyRok = roczniki[rok.SelectedIndex];
            if (wybranyRok == "Wszystkie")
            {
                miesiac.Enabled = false;
                obliczsume((int)tryb.wszytkie);
            }
            else
            {
                miesiac.Items.Add("Wszystkie");
                miesiace.Add("Wszystkie");
                foreach (var item in zatwierdzone)
                {
                    if (item.dajRok() == wybranyRok && ostatniaData != item.dajMiesiac())
                    {
                        miesiac.Items.Add(item.dajMiesiacS());
                        ostatniaData = item.dajMiesiac();
                        miesiace.Add(item.dajMiesiac());
                    }
                }
                miesiac.Enabled = true;
                miesiac.SelectedIndex = 0;
                obliczsume((int)tryb.wszytkierok);
            }
        }
        
        private void uzupelnijRoczniki()
        {
            String ostatniaData = "";
            rok.Items.Add("Wszystkie");
            roczniki.Add("Wszystkie");
            if (BazaDanych.listaList.Count > 0)
            {
                foreach (var item in BazaDanych.listaList)
                {
                    if (item.zatwierdzona == true)
                    {
                        zatwierdzone.Add(item);
                        if (item.dajRok() != ostatniaData)
                        {
                            rok.Items.Add(item.dajRok());
                            roczniki.Add(item.dajRok());
                        }
                        ostatniaData = item.dajRok();
                    }
                }
            }
            rok.SelectedIndex = 0;
            miesiac.Enabled = false;
        }


        private void obliczsume(int colicz)
        {
            float wydatek = 0;

            switch (colicz)
            {
                case (int)tryb.wszytkierok:
                    string wybranyRok = roczniki[rok.SelectedIndex];
                    foreach (var item in zatwierdzone)
                    {
                        if (item.dajRok() == wybranyRok)
                        {
                            wydatek += item.cenaKoszyka;
                        }
                    }
                    wynik.Text = "Wydatki z roku " + wybranyRok + ": " + wydatek.ToString();

                    break;
                case (int)tryb.wszytkie:
                    foreach (var item in zatwierdzone)
                    {
                        wydatek += item.cenaKoszyka;
                    }
                    wynik.Text = "Wszystkie wydatki: " + wydatek.ToString();
                    break;
                    
                case (int)tryb.danymiesmiesiac:
                    string wybranyRokm = roczniki[rok.SelectedIndex];
                    string wybranyMiesiac = miesiace[miesiac.SelectedIndex];
                    foreach (var item in zatwierdzone)
                    {
                        if (item.dajRok() == wybranyRokm && item.dajMiesiac() == wybranyMiesiac)
                        {
                            wydatek += item.cenaKoszyka;
                        }
                    }
                    wynik.Text = "Wydatki z miesiąca " + miesiac.SelectedItem.ToString() + " " + wybranyRokm + ": " + wydatek.ToString();
                    break;
            }
        }

    }
}

