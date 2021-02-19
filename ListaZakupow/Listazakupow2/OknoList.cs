using System;
using System.Data;
using System.Windows.Forms;
using MongoDB.Driver;
using MongoDB.Driver.Linq;
using System.Linq;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Printing;

namespace Listazakupow2
{
    public partial class OknoList : Form
    {
        private Lista wybranaLista = new Lista();
        private List<Lista> niezatwierdzone = new List<Lista>();
        private Font printFont;

        private Form rodzic = null;

        public OknoList(Form rodzicArg)
        {
            rodzic = rodzicArg;
            niezatwierdzone.Clear();
            InitializeComponent();
            wypiszNiezatwierdzone();
            nazwaListy.Text = "";
            suma.Text = "0";
            sumaDlaKogos.Text = "0";
        }

        public OknoList(Lista poprzednia, Form rodzicArg) //inicjalizacja okna po modyfikacji listy
        {
            rodzic = rodzicArg;
            niezatwierdzone.Clear();
            InitializeComponent();
            wybranaLista = poprzednia;
            listaNiezatwierdzonych.Items.Clear();

            foreach (var item in wybranaLista.produktyWLiscie)
            {
                Produkt p = (BazaDanych.listaProduktow.Where(x => x.Id.Equals(item.Item1))).ToList().First();

                nazwaListy.Text = wybranaLista.nazwaListy;
                this.tabelaProduktowNaLiscie.Rows.Add(p.nazwa, item.Item2, (p.cena).ToString(), item.Item3);
            }
            int indeks = 0;
            foreach (var item in BazaDanych.listaList)
            {
                if (item.zatwierdzona == false && item.anulowana == false)     //wypisz tylko listy niezatwierdzone i nieanulowana   
                {
                    listaNiezatwierdzonych.Items.Add($" {item.nazwaListy}");
                    niezatwierdzone.Add(item);
                    if (item.Id == wybranaLista.Id) //znajdowanie indeksu modyfikowanej listy
                    {
                        listaNiezatwierdzonych.SelectedIndex = indeks;
                    }
                    indeks++;
                }
            }
        }

        private void wypiszNiezatwierdzone()
        {
            listaNiezatwierdzonych.Items.Clear();
            foreach (var item in BazaDanych.listaList)
            {
                nazwaListy.Text = "";
                suma.Text = "0";
                sumaDlaKogos.Text = "0";
                if (item.zatwierdzona == false && item.anulowana == false)     //wypisz tylko listy niezatwierdzone i nieanulowane      
                {
                    listaNiezatwierdzonych.Items.Add($"{item.nazwaListy}");
                    niezatwierdzone.Add(item);
                }
            }
        }

        private void listaNiezatwierdzonych_SelectedIndexChanged(object sender, EventArgs e)
        {
            this.tabelaProduktowNaLiscie.Rows.Clear();
            if (listaNiezatwierdzonych.SelectedIndex != -1)
            {
                wybranaLista = niezatwierdzone.ElementAt(listaNiezatwierdzonych.SelectedIndex);
                policzCeny();
                suma.Text = wybranaLista.cenaKoszyka.ToString();
                sumaDlaKogos.Text = wybranaLista.cenaDlaKogos.ToString();
                nazwaListy.Text = wybranaLista.nazwaListy;

                foreach (var item in wybranaLista.produktyWLiscie) //wypisanie produktow z wybranej listy
                {
                    Produkt p = (BazaDanych.listaProduktow.Where(x => x.Id.Equals(item.Item1))).ToList().First();
                    this.tabelaProduktowNaLiscie.Rows.Add(p.nazwa, item.Item2, (p.cena * float.Parse(item.Item2)).ToString(), item.Item3);
                }
            }
        }

        //modyfikacja wybranej listy
        private void modyfikuj_Click(object sender, EventArgs e)
        {
            if (listaNiezatwierdzonych.SelectedIndex != -1)
            {
                var myForm = new Form2(wybranaLista, rodzic);
                this.Close();
                if (rodzic != null)
                {
                    rodzic.Hide();
                    myForm.Closed += (s, args) => { rodzic.Show(); rodzic.Select(); };
                }
                myForm.Show();
            }
            else
            {
                niepoprawnaLista();
            }
        }

        private void policzCeny()
        {
            Produkt p = new Produkt();
            wybranaLista.cenaDlaKogos = 0;
            wybranaLista.cenaKoszyka = 0;
            foreach (var i in wybranaLista.produktyWLiscie) //sugerowana cena za caly koszyk
            {
                p = p.znajdzProdukt(i.Item1);
                wybranaLista.cenaKoszyka += (p.cena * float.Parse(i.Item2));
                if (i.Item3 == true)
                {
                    wybranaLista.cenaDlaKogos += (p.cena * float.Parse(i.Item2));
                }
            }
        }

        //realizuje wybrana liste
        private void zrealizuj_Click(object sender, EventArgs e)
        {
            if (listaNiezatwierdzonych.SelectedIndex != -1)
            {
                var myForm = new ZatwierdzanieListy(wybranaLista, this);
                myForm.ShowDialog(this);
            }
            else
            {
                const string message =
                        "Wybierz listę";
                const string caption = "Niepoprawna lista";
                var result = MessageBox.Show(message, caption,
                                             MessageBoxButtons.OK,
                                             MessageBoxIcon.Question);
            }
        }

        private void zapiszSzablon_Click(object sender, EventArgs e)
        {
            if (listaNiezatwierdzonych.SelectedIndex != -1)
            {
                if (sprawdzNazwe(wybranaLista.nazwaListy))
                {
                    Szablon szablon = new Szablon(wybranaLista.nazwaListy);
                    szablon.zapiszSzablonZListy(wybranaLista);
                    const string message =
                      "Utworzono szablon.";
                    const string caption = "Szablon";
                    var result = MessageBox.Show(message, caption,
                                                 MessageBoxButtons.OK,
                                                 MessageBoxIcon.Question);
                }
            }
            else
            {
                niepoprawnaLista();
            }
        }

        //sprawdza, czy podana nazwa jest poprawna
        private bool sprawdzNazwe(string nazwa)
        {
            if (nazwa == "")
            {
                const string message =
                     "Podaj nazwę";
                const string caption = "Niepoprawna nazwa";
                var result = MessageBox.Show(message, caption,
                                             MessageBoxButtons.OK,
                                             MessageBoxIcon.Question);
                return false;
            }

            foreach (var i in BazaDanych.listaSzablonow)
            {
                if (i.nazwaSzablonu == nazwa)
                {
                    const string message =
                  "Taki szablon już istnieje";
                    const string caption = "Niepoprawna nazwa";
                    var result = MessageBox.Show(message, caption,
                                                 MessageBoxButtons.OK,
                                                 MessageBoxIcon.Question);
                    return false;
                }
            }


            return true;
        }

        //usuwa wybrana liste
        private void usun_Click(object sender, EventArgs e)
        {
            if (listaNiezatwierdzonych.SelectedIndex != -1)
            {
                const string message =
                   "Czy na pewno chcesz usunac ta liste?";
                const string caption = "Usun";
                var result = MessageBox.Show(message, caption,
                                             MessageBoxButtons.YesNo,
                                             MessageBoxIcon.Question);
                if (result == DialogResult.Yes)
                {
                    wybranaLista.usun_liste(); //usuniecie listy z bazy danych
                    niezatwierdzone.Remove(wybranaLista); //usuniecie listy z listy niezatwierdzonych

                    this.tabelaProduktowNaLiscie.Rows.Clear();
                    wypiszNiezatwierdzone();
                }
            }
            else
            {
                niepoprawnaLista();
            }
        }

        private void anuluj_Click(object sender, EventArgs e)
        {
            if (listaNiezatwierdzonych.SelectedIndex != -1)
            {
                const string message =
                   "Czy na pewno chcesz anulowac ta liste?";
                const string caption = "Anuluj";
                var result = MessageBox.Show(message, caption,
                                             MessageBoxButtons.YesNo,
                                             MessageBoxIcon.Question);
                if (result == DialogResult.Yes)
                {
                    wybranaLista.anuluj();

                    niezatwierdzone.Remove(wybranaLista);

                    this.tabelaProduktowNaLiscie.Rows.Clear();
                    wypiszNiezatwierdzone();
                }
            }
            else
            {
                niepoprawnaLista();
            }
        }

        private void drukuj_Click(object sender, EventArgs e)
        {
            if (listaNiezatwierdzonych.SelectedIndex != -1)
            {
                try
                {
                    PrintDialog PrintDialog1 = new PrintDialog();
                    PrintDialog1.AllowSomePages = true;

                    PrintDialog1.ShowHelp = true;


                    printFont = new Font("Consolas", 8);
                    PrintDocument pd = new PrintDocument();
                    pd.PrintPage += new PrintPageEventHandler
                        (this.drukujStrone);

                    PrintDialog1.Document = pd;
                    PrintDialog1.PrinterSettings.FromPage = 1;
                    PrintDialog1.PrinterSettings.ToPage = 1;

                    DialogResult result = PrintDialog1.ShowDialog();

                    if (result == DialogResult.OK)
                        pd.Print();
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }
            }
            else
            {
                niepoprawnaLista();
            }
        }


        private void drukujStrone(object sender, PrintPageEventArgs ev)
        {
            float linesPerPage = 0;
            float topMargin = ev.MarginBounds.Top;
            int count = 0;
            float yPos = topMargin + (count *
                   printFont.GetHeight(ev.Graphics));
            float leftMargin = ev.MarginBounds.Left;

            linesPerPage = ev.MarginBounds.Height /
               printFont.GetHeight(ev.Graphics);

            ev.Graphics.DrawString(wybranaLista.nazwaListy, printFont, Brushes.Black,
               leftMargin, yPos, new StringFormat());
            count++;

            ev.Graphics.DrawString("", printFont, Brushes.Black,
               leftMargin, yPos, new StringFormat());
            count++;

            nazwaListy.Text = "Wybrana lista: " + wybranaLista.nazwaListy;

            foreach (var item in wybranaLista.produktyWLiscie)
            {
                yPos = topMargin + (count *
                   printFont.GetHeight(ev.Graphics));


                Produkt p = (BazaDanych.listaProduktow.Where(x => x.Id.Equals(item.Item1))).ToList().First();
                string linia = $"{p.nazwa,-30}{"Ilość:" + item.Item2,-20}{(item.Item3 == true ? "Dla kogoś" : ""),-20}";


                ev.Graphics.DrawString(linia, printFont, Brushes.Black,
                   leftMargin, yPos, new StringFormat());
                count++;
                if (count > linesPerPage)
                    break;
            }
        }

        private void niepoprawnaLista()
        {
            const string message =
                      "Wybierz listę";
            const string caption = "Niepoprawna lista";
            var result = MessageBox.Show(message, caption,
                                         MessageBoxButtons.OK,
                                         MessageBoxIcon.Question);
        }


    }
}
