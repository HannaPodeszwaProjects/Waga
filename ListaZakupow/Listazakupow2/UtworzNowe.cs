using MongoDB.Bson;
using MongoDB.Driver;
using MongoDB.Driver.Linq;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Windows.Forms;

enum trybPracy { eTworzenieNowej, eModyfikacjaListy, eModyfikacjaSzablonu, eZapiszSzablonJakoLista }

namespace Listazakupow2
{


    public partial class Form2 : Form
    {
        public Lista lista = null;
        public Szablon szablon = null;


        private Form rodzic = null;

        private static int tryb = (int)trybPracy.eTworzenieNowej;

        List<Produkt> listaPrzefiltrowana = BazaDanych.listaProduktow;
        List<Tuple<MongoDB.Bson.ObjectId, String, bool>> listaWybranychProduktow = new List<Tuple<MongoDB.Bson.ObjectId, String, bool>>();

        public Form2(Form rodzicArg)
        {
            rodzic = rodzicArg;
            InitializeComponent();
            zapelnijTabele(BazaDanych.listaProduktow);
            tryb = (int)trybPracy.eTworzenieNowej;
            sklepy.Visible = false;
            ceny.Visible = false;
            usunsklep.Visible = false;
            zapisz.Visible = false;
            sklepNazwa.Visible = false;
            CenaNazwa.Visible = false;
            najtanszyNazwa.Visible = false;
            najtaniej.Visible = false;
        }

        public Form2(Szablon modSzablon, int trybPr, Form rodzicArg)
        {
            rodzic = rodzicArg;
            InitializeComponent();
            tryb = trybPr;
            szablon = modSzablon;
            if (tryb == (int)trybPracy.eZapiszSzablonJakoLista)
            {
                sklepy.Visible = false;
                ceny.Visible = false;
                usunsklep.Visible = false;
                zapisz.Visible = false;
                sklepNazwa.Visible = false;
                CenaNazwa.Visible = false;
                najtanszyNazwa.Visible = false;
                najtaniej.Visible = false;
            }
            else if (tryb == (int)trybPracy.eModyfikacjaSzablonu)
            {
                przyciskZapiszSzablon.Text = "Zapisz szablon";
                zapelnijSklepy();
                if (BazaDanych.listaSklepow.Count() != 0)
                {
                    sklepy.SelectedIndex = 0;
                }
                wyszukajNajtanszySklep();
                przyciskZapiszListe.Enabled = false;
            }

            zapelnijTabele(BazaDanych.listaProduktow);

            foreach (var item in modSzablon.listaProduktowWSzablonie)
            {
                Produkt p = (BazaDanych.listaProduktow.Where(x => x.Id.Equals(item.Item1))).ToList().First();

                Tuple<MongoDB.Bson.ObjectId, String, bool> t = new Tuple<MongoDB.Bson.ObjectId, String, bool>(p.Id, item.Item2, item.Item3); //krotka wybranego produktu

                listaWybranychProduktow.Add(t);

                this.tablicaWybranychProduktow.Rows.Add(p.nazwa, t.Item2, (p.cena * float.Parse(t.Item2)).ToString("0.##"), t.Item3);
            }

            nazwaListy.Text = modSzablon.nazwaSzablonu;
        }


        public Form2(Lista modLista, Form rodzicArg)
        {
            rodzic = rodzicArg;

            InitializeComponent();
            lista = modLista;
            przyciskZapiszSzablon.Enabled = false;
            zapelnijTabele(BazaDanych.listaProduktow);
            tryb = (int)trybPracy.eModyfikacjaListy;
            sklepy.Visible = false;
            ceny.Visible = false;
            usunsklep.Visible = false;
            zapisz.Visible = false;
            sklepNazwa.Visible = false;
            CenaNazwa.Visible = false;
            najtanszyNazwa.Visible = false;
            najtaniej.Visible = false;

            foreach (var item in modLista.produktyWLiscie)
            {
                Produkt p = (BazaDanych.listaProduktow.Where(x => x.Id.Equals(item.Item1))).ToList().First();
                Tuple<MongoDB.Bson.ObjectId, String, bool> t = new Tuple<MongoDB.Bson.ObjectId, String, bool>(p.Id, item.Item2, item.Item3); //krotka wybranego produktu
                listaWybranychProduktow.Add(t);

                this.tablicaWybranychProduktow.Rows.Add(p.nazwa, t.Item2, (p.cena).ToString("0.##"), t.Item3);
            }

            nazwaListy.Text = modLista.nazwaListy;
        }

        //USUNAC
        private void checkedListBox1_SelectedIndexChanged(object sender, EventArgs e)//usuwanie z listy wybranych //DO USUNIECIA
        {
        }


        //wpisanie tekstu w polu wyszukiwania
        private void Wyszukiwarka_TextChanged(object sender, EventArgs e)//wyszukiwarka?
        {
            OkienkoWyszukanychZakupow.Items.Clear();

            listaPrzefiltrowana = BazaDanych.listaProduktow.Where(x => Regex.IsMatch(x.nazwa, wyszukiwarka.Text, RegexOptions.IgnoreCase) == true).ToList();

            foreach (string i in kategorie.CheckedItems)
                listaPrzefiltrowana = listaPrzefiltrowana.Where(x => x.kategorie.Contains(i) == true).ToList();

            zapelnijTabele(listaPrzefiltrowana);
        }


        private void OkienkoWyszukanychZakupow_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (OkienkoWyszukanychZakupow.SelectedIndex != -1)
            {
                Produkt p = listaPrzefiltrowana.ElementAt(OkienkoWyszukanychZakupow.SelectedIndex); //wybrany produkt
                Tuple<MongoDB.Bson.ObjectId, String, bool> t = new Tuple<MongoDB.Bson.ObjectId, String, bool>(p.Id, "1", false); //krotka wybranego produktu
                listaWybranychProduktow.Add(t);

                this.tablicaWybranychProduktow.Rows.Add(p.nazwa, t.Item2, (p.cena).ToString("0.##"), t.Item3);

                if(tryb== (int)trybPracy.eModyfikacjaSzablonu)
                {
                    szablon.listaSklepow.Clear();
                    ceny.Text = "";
                    sklepy.Items.Clear();
                    sklepy.ResetText();

                }
            }
        }


        //zapełnienie tabeli wyszukanych produktów
        private void zapelnijTabele(List<Produkt> listaPrzefiltrowana)
        {

            List<string> listaKategorii = new List<string>();
            List<string> listaZaznaczonychKategorii = new List<string>();

            OkienkoWyszukanychZakupow.Items.Clear();

            for (int i = kategorie.Items.Count - 1; i >= 0; i--)
            {
                if (kategorie.GetItemChecked(i) == false)
                    kategorie.Items.RemoveAt(i);
            }

            foreach (string item in kategorie.Items)
            {
                listaZaznaczonychKategorii.Add(item);
            }

            foreach (var item in listaPrzefiltrowana.AsEnumerable())
            {
                OkienkoWyszukanychZakupow.Items.Add($"{item.nazwa}");

                foreach (string kategoria in item.kategorie)
                {
                    if (!listaKategorii.Contains(kategoria) && !listaZaznaczonychKategorii.Contains(kategoria))
                        listaKategorii.Add(kategoria);
                }
            }
            foreach (string kategoria in listaKategorii)
            {
                kategorie.Items.Add(kategoria);
            }
        }

        private void przyciskZapiszListe_Klik(object sender, EventArgs e)
        {
            switch (tryb)
            {
                case (int)trybPracy.eModyfikacjaListy:
                    if (!(sprawdzNazwe(true, nazwaListy.Text)))
                    {
                        break;
                    }
                    lista.aktualizuj_liste(listaWybranychProduktow, nazwaListy.Text);


                    var myForm = new OknoList(lista, rodzic);
                    this.Close();
                    if (rodzic != null)
                    {
                        rodzic.Hide();
                        myForm.Closed += (s, args) => { rodzic.Show(); rodzic.Select(); };
                    }
                    myForm.Show();
                    break;
                case (int)trybPracy.eTworzenieNowej:
                    if (!(sprawdzNazwe(true, nazwaListy.Text)))
                    {
                        break;
                    }
                    Lista lista1 = new Lista();
                    lista1.dodaj_liste(listaWybranychProduktow, nazwaListy.Text);

                    var myForm1 = new OknoList(lista1, rodzic);
                    this.Close();
                    if (rodzic != null)
                    {
                        rodzic.Hide();
                        myForm1.Closed += (s, args) => { rodzic.Show(); rodzic.Select(); };
                    }
                    myForm1.Show();
                    break;
                case (int)trybPracy.eZapiszSzablonJakoLista:
                    if (!(sprawdzNazwe(true, nazwaListy.Text)))
                    {
                        break;
                    }
                    Lista lista2 = new Lista();
                    lista2.dodaj_liste(listaWybranychProduktow, nazwaListy.Text);
                    var myForm3 = new OknoList(lista2, rodzic);
                    this.Close();
                    if (rodzic != null)
                    {
                        rodzic.Hide();
                        myForm3.Closed += (s, args) => { rodzic.Show(); rodzic.Select(); };
                    }
                    myForm3.Show();
                    break;
                case (int)trybPracy.eModyfikacjaSzablonu:

                    break;
            }
        }

        private void Form2_Load(object sender, EventArgs e)
        {

        }

        private void przyciskZapiszSzablon_Click(object sender, EventArgs e)
        {
            switch (tryb)
            {
                case (int)trybPracy.eZapiszSzablonJakoLista:
                case (int)trybPracy.eTworzenieNowej:
                    if (sprawdzNazweSzablonu(nazwaListy.Text))
                    {
                        Szablon szablontw = new Szablon(nazwaListy.Text);
                        szablontw.zapiszSzablon(listaWybranychProduktow);
                        var myForm = new OknoSzablonow(szablontw, rodzic);
                        this.Close();
                        if (rodzic != null)
                        {
                            rodzic.Hide();
                            myForm.Closed += (s, args) => { rodzic.Show(); rodzic.Select(); };
                        }
                        myForm.Show();
                    }
                    break;
                case (int)trybPracy.eModyfikacjaSzablonu:
                    if (!(sprawdzNazwe(false, nazwaListy.Text)))
                    {
                        break;
                    }
                    szablon.aktualizujSzablon(listaWybranychProduktow, nazwaListy.Text);
                    var myForm1 = new OknoSzablonow(szablon,rodzic);
                    this.Close();
                    if (rodzic != null)
                    {
                        rodzic.Hide();
                        myForm1.Closed += (s, args) => { rodzic.Show(); rodzic.Select(); };
                    }
                    myForm1.Show();
                    break;
            }
        }

        private void tablicaWybranychProduktow_CellValueChanged(object sender, DataGridViewCellEventArgs e)
        {
            var senderGrid = (DataGridView)sender;
            DataGridViewTextBoxCell ch2 = new DataGridViewTextBoxCell();
            if (senderGrid.Columns[e.ColumnIndex] is DataGridViewTextBoxColumn &&
                         e.RowIndex >= 0)
            {
                float cena;
                ch2 = (DataGridViewTextBoxCell)tablicaWybranychProduktow.Rows[tablicaWybranychProduktow.CurrentRow.Index].Cells[1];
                try
                {
                    cena = float.Parse(ch2.Value.ToString());
                }
                catch (Exception ex)
                {
                    tablicaWybranychProduktow.Rows[e.RowIndex].Cells[1].Value = 1;

                    const string message =
                      "Zły format ceny";
                    const string caption = "Niepoprawna cena";
                    var result = MessageBox.Show(message, caption,
                                                 MessageBoxButtons.OK,
                                                 MessageBoxIcon.Question);
                    return;
                }
                Tuple<MongoDB.Bson.ObjectId, String, bool> t = new Tuple<MongoDB.Bson.ObjectId, String, bool>(listaWybranychProduktow.ElementAt(e.RowIndex).Item1, ch2.Value.ToString(), listaWybranychProduktow.ElementAt(e.RowIndex).Item3); //krotka wybranego produktu

                listaWybranychProduktow.RemoveAt(e.RowIndex);
                listaWybranychProduktow.Insert(e.RowIndex, t);

                Produkt p = new Produkt();
                p = p.znajdzProdukt(listaWybranychProduktow.ElementAt(e.RowIndex).Item1);
                tablicaWybranychProduktow.Rows[e.RowIndex].Cells[2].Value = p.cena * cena;
            }
        }

        private void tablicaWybranychProduktow_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

            var senderGrid = (DataGridView)sender;
            DataGridViewCheckBoxCell ch1 = new DataGridViewCheckBoxCell();



            if (senderGrid.Columns[e.ColumnIndex] is DataGridViewButtonColumn &&
                e.RowIndex >= 0)
            {
                this.tablicaWybranychProduktow.Rows.RemoveAt(e.RowIndex);
                listaWybranychProduktow.RemoveAt(e.RowIndex);

            }
            if (senderGrid.Columns[e.ColumnIndex] is DataGridViewCheckBoxColumn &&
               e.RowIndex >= 0)
            {
                ch1 = (DataGridViewCheckBoxCell)tablicaWybranychProduktow.Rows[tablicaWybranychProduktow.CurrentRow.Index].Cells[3];
                Tuple<MongoDB.Bson.ObjectId, String, bool> t = new Tuple<MongoDB.Bson.ObjectId, String, bool>(listaWybranychProduktow.ElementAt(e.RowIndex).Item1, listaWybranychProduktow.ElementAt(e.RowIndex).Item2, (bool)ch1.EditedFormattedValue); //krotka wybranego produktu

                listaWybranychProduktow.RemoveAt(e.RowIndex);
                listaWybranychProduktow.Insert(e.RowIndex, t);
            }

        }

        //sprawdza czy podana nazwa jest poprawna
        private bool sprawdzNazwe(bool czyLista, string nazwa)
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
            if (czyLista)
            {
                foreach (var i in BazaDanych.listaList)
                {
                    if ((lista == null && i.nazwaListy == nazwa) || (lista != null && lista.Id != i.Id && i.nazwaListy == nazwa))
                    {
                        const string message =
                      "Ta nazwa listy jest już użyta";
                        const string caption = "Niepoprawna nazwa";
                        var result = MessageBox.Show(message, caption,
                                                     MessageBoxButtons.OK,
                                                     MessageBoxIcon.Question);
                        return false;
                    }
                }
            }
            else
            {
                foreach (var i in BazaDanych.listaSzablonow)
                {
                    if ((szablon == null && i.nazwaSzablonu == nazwa) || (szablon != null && szablon.Id != i.Id && i.nazwaSzablonu == nazwa))
                    {
                        const string message =
                      "Ta nazwa szablonu jest już użyta";
                        const string caption = "Niepoprawna nazwa";
                        var result = MessageBox.Show(message, caption,
                                                     MessageBoxButtons.OK,
                                                     MessageBoxIcon.Question);
                        return false;
                    }
                }
            }
            return true;
        }

        private bool sprawdzNazweSzablonu(string nazwa)
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
                    if ((i.nazwaSzablonu == nazwa))
                    {
                        const string message =
                      "Ta nazwa szablonu jest już użyta";
                        const string caption = "Niepoprawna nazwa";
                        var result = MessageBox.Show(message, caption,
                                                     MessageBoxButtons.OK,
                                                     MessageBoxIcon.Question);
                        return false;
                    }
                }
            
            return true;
        }


        private void kategorie_ZmienZaznaczenie(object sender, ItemCheckEventArgs e)
        {

            List<string> checkedItems = new List<string>();
            foreach (var item in kategorie.CheckedItems)
                checkedItems.Add(item.ToString());

            if (e.NewValue == CheckState.Checked)
                checkedItems.Add(kategorie.Items[e.Index].ToString());
            else
                checkedItems.Remove(kategorie.Items[e.Index].ToString());


            if (kategorie.SelectedIndex != -1)
            {
                listaPrzefiltrowana = BazaDanych.listaProduktow.Where(x => Regex.IsMatch(x.nazwa, wyszukiwarka.Text, RegexOptions.IgnoreCase) == true).ToList();

                foreach (string i in checkedItems)
                    listaPrzefiltrowana = listaPrzefiltrowana.Where(x => x.kategorie.Contains(i) == true).ToList();
                this.BeginInvoke((MethodInvoker)delegate
                {
                    zapelnijTabele(listaPrzefiltrowana);
                });
            }
        }


        private void sklepy_SelectedIndexChanged(object sender, EventArgs e)
        {
            ceny.Text = "";
            if (sklepy.SelectedIndex != -1)
            {
                foreach (var item in szablon.listaSklepow)
                {
                    if (item.Item1 == BazaDanych.listaSklepow.ElementAt(sklepy.SelectedIndex).Id)
                    {
                        ceny.Text = item.Item2.ToString("0.##");
                        break;
                    }
                }
            }
        }

        private void zapelnijSklepy()
        {
            foreach (var item in BazaDanych.listaSklepow)
            {
                sklepy.Items.Add(item.nazwaSklepu);
            }
        }

        private void zapisz_Click(object sender, EventArgs e)
        {
            if (sklepy.SelectedIndex != -1)
            {
                foreach (var item in szablon.listaSklepow)
                {
                    if (item.Item1 == BazaDanych.listaSklepow.ElementAt(sklepy.SelectedIndex).Id)
                    {
                        const string message =
                        "Czy na pewno chcesz przypisać cene do sklepu?";
                        const string caption = "Przypisz";
                        var result = MessageBox.Show(message, caption,
                                                     MessageBoxButtons.YesNo,
                                                     MessageBoxIcon.Question);

                        if (result == DialogResult.Yes)
                        {
                            Tuple<MongoDB.Bson.ObjectId, float> t;
                            try
                            {
                                t = new Tuple<MongoDB.Bson.ObjectId, float>(item.Item1, float.Parse(ceny.Text));
                                if (t.Item2 < 0) { throw new Exception(); }
                            }
                            catch (Exception ex)
                            {
                                const string message1 =
                                      "Zły format ceny";
                                const string caption1 = "Niepoprawna cena";
                                var result1 = MessageBox.Show(message1, caption1,
                                                             MessageBoxButtons.OK,
                                                             MessageBoxIcon.Question);
                                return;
                            }
                            szablon.listaSklepow.Remove(item);
                            szablon.listaSklepow.Add(t);

                            BazaDanych.listaSklepow.ElementAt(sklepy.SelectedIndex).zmienLiczbeUzyc(1, BazaDanych.listaSklepow.ElementAt(sklepy.SelectedIndex).Id);
                            wyszukajNajtanszySklep();
                            return;
                        }
                        else
                        {
                            return;
                        }
                    }

                }

                const string message2 =
                        "Czy na pewno chcesz przypisac cene do sklepu?";
                const string caption2 = "Przypisz";
                var result2 = MessageBox.Show(message2, caption2,
                                             MessageBoxButtons.YesNo,
                                             MessageBoxIcon.Question);

                if (result2 == DialogResult.Yes)
                {
                    Tuple<MongoDB.Bson.ObjectId, float> t;
                    try
                    {
                        t = new Tuple<MongoDB.Bson.ObjectId, float>(BazaDanych.listaSklepow.ElementAt(sklepy.SelectedIndex).Id, float.Parse(ceny.Text));
                        if (t.Item2 < 0) { throw new Exception(); }
                    }
                    catch (Exception ex)
                    {
                        const string message3 =
                              "Zły format ceny";
                        const string caption3 = "Niepoprawna cena";
                        var result3 = MessageBox.Show(message3, caption3,
                                                     MessageBoxButtons.OK,
                                                     MessageBoxIcon.Question);
                        return;
                    }
                    szablon.listaSklepow.Add(t);
                    BazaDanych.listaSklepow.ElementAt(sklepy.SelectedIndex).zmienLiczbeUzyc(1, BazaDanych.listaSklepow.ElementAt(sklepy.SelectedIndex).Id);
                    wyszukajNajtanszySklep();
                    return;

                }
            }
        }

        private void usunsklep_Click(object sender, EventArgs e)
        {
            if (sklepy.SelectedIndex != -1)
            {
                foreach (var item in szablon.listaSklepow)
                {
                    if (item.Item1 == BazaDanych.listaSklepow.ElementAt(sklepy.SelectedIndex).Id)
                    {
                        const string message =
                        "Czy na pewno chcesz usunąć przypisaną cene do sklepu?";
                        const string caption = "Usuń";
                        var result = MessageBox.Show(message, caption,
                                                     MessageBoxButtons.YesNo,
                                                     MessageBoxIcon.Question);

                        if (result == DialogResult.Yes)
                        {
                            szablon.listaSklepow.Remove(item);
                            BazaDanych.listaSklepow.ElementAt(sklepy.SelectedIndex).zmienLiczbeUzyc(-1, BazaDanych.listaSklepow.ElementAt(sklepy.SelectedIndex).Id);
                            ceny.Text = "";
                            wyszukajNajtanszySklep();
                            return;
                        }
                    }
                }
            }
        }

        private void wyszukajNajtanszySklep()
        {
            if (szablon.listaSklepow.Count != 0)
            {
                float najtanszySklep = szablon.listaSklepow.ElementAt(0).Item2;
                ObjectId idNajtanszegoSklepu = szablon.listaSklepow.ElementAt(0).Item1;
                int index = 0;
                foreach (var item in szablon.listaSklepow)
                {
                    if (najtanszySklep > item.Item2)
                    {
                        najtanszySklep = item.Item2;
                        idNajtanszegoSklepu = item.Item1;
                    }
                }
                foreach (var item in BazaDanych.listaSklepow)
                {
                    if (item.Id == idNajtanszegoSklepu)
                    {
                        najtaniej.Text =item.nazwaSklepu;
                        sklepy.SelectedIndex = index;
                        return;
                    }
                    index++;
                }
            }
            else
            {
                najtaniej.Text = "";
            }
        }
    }
}
