using System;
using System.Data;
using System.Windows.Forms;
using MongoDB.Driver;
using MongoDB.Driver.Linq;
using System.Linq;

namespace Listazakupow2
{
    public partial class OknoSzablonow : Form
    {
        private Szablon wybSzablon = new Szablon();

        private Form rodzic = null;

        public OknoSzablonow(Form rodzicArg)
        {
            InitializeComponent();
            wypiszSzablony();
            rodzic = rodzicArg;
            zmienWidocznosc(false);
        }

        public OknoSzablonow(Szablon szablon, Form rodzicArg) //inicjalizacja okna po modyfikacji szablonu
        {
            InitializeComponent();
            wypiszSzablony();

            wybSzablon = szablon;
            nazwaSzablonu.Text = wybSzablon.nazwaSzablonu;
            wypiszProdukty();

            //szuka indeksu zmodyfikowanego szablonu
            int numer = 0;
            foreach (var item in BazaDanych.listaSzablonow)
            {
                if (item.Id != wybSzablon.Id)
                {
                    numer++;
                }
                else
                    break;
            }
            wszystkieSzablony.SelectedIndex = numer;
        }

        private void wypiszSzablony()
        {
            this.tabelaProduktow.Rows.Clear();
            wszystkieSzablony.Items.Clear();
            nazwaSzablonu.Text = "";
            suma.Text = "0";
            sumaDlaKogos.Text = "0";
            sklepy.Items.Clear();
            sklepy.ResetText();
            foreach (var item in BazaDanych.listaSzablonow)
            {
                wszystkieSzablony.Items.Add($"" + item.nazwaSzablonu);
            }
        }

        //wypisuje produkty z wybranego szablonu
        private void wypiszProdukty()
        {
            foreach (var item in wybSzablon.listaProduktowWSzablonie)
            {
                Produkt p = (BazaDanych.listaProduktow.Where(x => x.Id.Equals(item.Item1))).ToList().First();
                this.tabelaProduktow.Rows.Add(p.nazwa, item.Item2, (p.cena).ToString("0.##"), item.Item3);
            }
        }

        //wypisuje sklepy wybranego szablonu
        private void wypiszSklepy()
        {
            sklepy.Items.Clear();
            sklepy.ResetText();
            foreach (var i in wybSzablon.listaSklepow)
            {
                Sklep s = new Sklep();
                s = s.znajdzSklep(i.Item1);
                if (s != null)
                {
                    sklepy.Items.Add(s.nazwaSklepu);
                }
            }
            if (wybSzablon.listaSklepow.Count != 0)
            {

                sklepy.SelectedIndex = 0;
                zmienWidocznosc(true);
            }
            else {
                zmienWidocznosc(false);
            }
        }

        //sumuje ceny wszystkich produktow z wybranej listy oraz produktow dla kogos
        private void policzCeny()
        {
            Produkt p = new Produkt();
            wybSzablon.cenaSzablonu = 0;
            wybSzablon.cenaDlaKogos = 0;
            foreach (var i in wybSzablon.listaProduktowWSzablonie) //sugerowana cena za caly koszyk
            {
                p = p.znajdzProdukt(i.Item1);
                wybSzablon.cenaSzablonu += (p.cena * float.Parse(i.Item2));
                if (i.Item3 == true)
                {
                    wybSzablon.cenaDlaKogos += (p.cena * float.Parse(i.Item2));
                }
            }
        }

        //Otwieranie nowego okna, w którym nastąpi modyfikacja szablonu
        private void modyfikuj_Click(object sender, EventArgs e)
        {
            if (wszystkieSzablony.SelectedIndex != -1)
            {
                var myForm = new Form2(wybSzablon, (int)trybPracy.eModyfikacjaSzablonu, rodzic);
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
                nieWybranoSzablonu();
            }
        }

        //uzycie wybranego szablonu jako listy
        private void uzyj_Click(object sender, EventArgs e)
        {
            if (wszystkieSzablony.SelectedIndex != -1)
            {
                var myForm = new Form2(wybSzablon, (int)trybPracy.eZapiszSzablonJakoLista,rodzic);
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
                nieWybranoSzablonu();
            }
        }

        private void usun_Click(object sender, EventArgs e)
        {
            if (wszystkieSzablony.SelectedIndex != -1)
            {
                wybSzablon.usunSzablon();
                wybSzablon = null;
                wypiszSzablony();
            }
            else
            {
                nieWybranoSzablonu();
            }
        }

        private void wszystkieSzablony_SelectedIndexChanged(object sender, EventArgs e)
        {
            this.tabelaProduktow.Rows.Clear();
            if (wszystkieSzablony.SelectedIndex != -1)
            {
                wybSzablon = (BazaDanych.listaSzablonow)[wszystkieSzablony.SelectedIndex];
                wypiszSklepy();
                policzCeny();
                suma.Text = wybSzablon.cenaSzablonu.ToString("0.##");
                sumaDlaKogos.Text = wybSzablon.cenaDlaKogos.ToString("0.##");

                nazwaSzablonu.Text = wybSzablon.nazwaSzablonu;
                wypiszProdukty();
            }


        }

        private void nieWybranoSzablonu()
        {
            const string message = "Wybierz szablon";
            const string caption = "Nie wybrano szablonu";
            var result = MessageBox.Show(message, caption,
                                         MessageBoxButtons.OK,
                                         MessageBoxIcon.Question);
        }

        private void sklepy_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (sklepy.SelectedIndex != -1)
                ceny.Text = wybSzablon.listaSklepow.ElementAt(sklepy.SelectedIndex).Item2.ToString("0.##");
        }

        private void zmienWidocznosc(bool jak)
        {
            nazwaCeny.Visible = jak;
            ceny.Visible = jak;
            sklepy.Visible = jak;
            sklepNazwa.Visible = jak;
        }

    }
}
