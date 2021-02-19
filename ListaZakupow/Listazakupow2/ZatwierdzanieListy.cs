using System;
using System.Windows.Forms;
using MongoDB.Bson;

namespace Listazakupow2
{
    public partial class ZatwierdzanieListy : Form
    {
        public Form poprzednieOkno;
        public Lista lista = null;
        public ZatwierdzanieListy()
        {
            InitializeComponent();
        }


        public ZatwierdzanieListy(Lista lista1, Form poprzednieOkno1)
        {
            lista = lista1;
            InitializeComponent();
            this.poprzednieOkno = poprzednieOkno1;
            cena.Text = lista1.cenaKoszyka.ToString();
            wypiszSklepy();
        }

        // funkcja zatwierdzająca listę
        private void zatwierdz_Click(object sender, EventArgs e)
        {
            if (cena.Text != "") // sprawdzamy czy cena koszyka została wprowadzona
            {
                float cenaKoszyka;

                try
                {
                    cenaKoszyka = float.Parse(cena.Text);  // sprawdzamy czy cena jest poprawie wporowadzona
                }
                catch (Exception ex) // jeśli zostawła źle wprowadzona pokazuje się komunikat i kończymy działanie funkcji
                {
                    const string message =
                          "Zły format ceny";
                    const string caption = "Niepoprawna cena";
                    var result = MessageBox.Show(message, caption,
                                                 MessageBoxButtons.OK,
                                                 MessageBoxIcon.Question);
                    return;
                }

                if (cenaKoszyka >= 0) // jeśli cena jest poprwna zatwierdzamy liste
                {
                    lista.zatwierdzListe(cenaKoszyka);
                    poprzednieOkno.Close();
                    this.Close();
                }
                else // jeśli cena jest mniejsza od 0 pojawia się komunikat o niepoprawnej cenie
                {
                    const string message =
                                            "Niepoprawna cena.";
                    const string caption = "Niepoprawna cena";
                    var result = MessageBox.Show(message, caption,
                                                 MessageBoxButtons.OK,
                                                 MessageBoxIcon.Question);
                }
            }
            else // jeśli cena koszyka nie została wpisana pokazuje się komunikat
            {
                const string message =
                        "Nie podano ceny koszyka.";
                const string caption = "Niepoprawna cena";
                var result = MessageBox.Show(message, caption,
                                             MessageBoxButtons.OK,
                                             MessageBoxIcon.Question);
            }
        }

        // funkcja anulująca zatwierdzanie listy
        private void anuluj_Click(object sender, EventArgs e)
        {
            this.Close();
        }


        private void sklepy_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (sklepy.SelectedIndex == 0) //jesli uzytkownik nie poda sklepu
            {
                lista.sklep = ObjectId.Empty;
            }
            else
                lista.sklep = BazaDanych.listaSklepow[sklepy.SelectedIndex - 1].Id;
        }
        
        //wypisanie listy dostepnych sklepow
        private void wypiszSklepy()
        {
            sklepy.Items.Clear();
            sklepy.Items.Add("");
            foreach (var i in BazaDanych.listaSklepow)
            {
                sklepy.Items.Add(i.nazwaSklepu);
            }
            if (BazaDanych.listaSklepow.Count != 0)
                sklepy.SelectedIndex = 0;
        }
    }
}
