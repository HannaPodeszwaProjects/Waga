using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;

namespace Listazakupow2
{
    public partial class OknoProduktow : Form
    {
        public OknoProduktow()
        {
            InitializeComponent();
            zaladujListeProduktow();
        }

        private void zaladujListeProduktow()
        {
            if (BazaDanych.listaProduktow.Count > 0)
            {
                List<string> kategorie = new List<string>();

                foreach (var item in BazaDanych.listaProduktow)
                {
                    listaProduktow.Items.Add($"{item.nazwa}");


                    foreach (string kategoria in item.kategorie)
                    {
                        if (!kategorie.Contains(kategoria))
                            kategorie.Add(kategoria);
                    }
                }
                foreach (string kategoria in kategorie)
                {
                    listaKategorii.Items.Add(kategoria);
                }
            }
        }

        // funkcja dodająca produkt do bazy danych
        private void dodaj_Click(object sender, EventArgs e)
        {
            if (nazwa.Text.Length != 0)
            {
                Produkt nowyProdukt = new Produkt();
                if (sprawdzNazwe(nazwa.Text, null))
                {
                    if (nowyProdukt.dodajProdukt(nazwa.Text, cena.Text, kategorie.Text)) // dodajemy produkt do bazy danych
                    {
                        return;
                    }
                    listaProduktow.Items.Add($"{nazwa.Text}");

                    foreach (string nowa in nowyProdukt.kategorie)
                    {
                        bool appeared = false;
                        foreach (string istniejacaKategoria in listaKategorii.Items)
                        {
                            if (nowa.Trim() == istniejacaKategoria)
                                appeared = true;
                        }
                        if (appeared == false)
                            this.BeginInvoke((MethodInvoker)delegate
                            {
                                listaKategorii.Items.Add(nowa, true);
                        });
                    }
                }
            }
        }


        //funkcja usuwająca produkt z bazy danych
        private void usun_Click(object sender, EventArgs e)
        {
            if (listaProduktow.SelectedIndex != -1) // sprawdzamy czy produkt został wybrany
            {
                Produkt p = BazaDanych.listaProduktow.ElementAt(listaProduktow.SelectedIndex);
                if (p.uzyty == 0) // sprawdzamy czy produkt jest użyty w liście bądź szablonie
                {
                    foreach (string usuwana in p.kategorie)  // usuwamy produkt z kategorii
                    {
                        int appeared = 0;
                        foreach (Produkt item in BazaDanych.listaProduktow)
                        {
                            foreach (string istniejaca in item.kategorie)
                            {
                                if (istniejaca == usuwana)
                                    appeared++;
                            }
                        }

                        if (appeared == 1)
                        {
                            for (int i = listaKategorii.Items.Count - 1; i >= 0; i--)
                            {
                                if ((String)listaKategorii.Items[i] == usuwana)
                                    listaKategorii.Items.RemoveAt(i);
                            }
                        }
                    }
                    p.usunProdukt(); // usuwamy produkt z bazy danych
                    listaProduktow.Items.RemoveAt(listaProduktow.SelectedIndex);

                    nazwa.Text = "";
                    cena.Text = "";
                    kategorie.Text = "";
                }
                else
                {

                    const string message =
                            "Nie można usunąć produktu, które jest użyty w liście lub szablonie.";
                    const string caption = "Niepoprawany produkt";
                    var result = MessageBox.Show(message, caption,
                                                 MessageBoxButtons.OK,
                                                 MessageBoxIcon.Question);
                }
            }
            else
            {
                const string message =
                        "Wybierz produkt";
                const string caption = "Niepoprawany produkt";
                var result = MessageBox.Show(message, caption,
                                             MessageBoxButtons.OK,
                                             MessageBoxIcon.Question);
            }
        }

        private void listaProduktow_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (listaProduktow.SelectedIndex != -1)
            {
                nazwa.Text = BazaDanych.listaProduktow.ElementAt(listaProduktow.SelectedIndex).nazwa;
                cena.Text = BazaDanych.listaProduktow.ElementAt(listaProduktow.SelectedIndex).cena.ToString("0.##");
                kategorie.Text = ""; //String.Join(",", BazaDanych.listaProduktow.ElementAt(listaProduktow.SelectedIndex).kategorie);
                for (int i = listaKategorii.Items.Count - 1; i >= 0; i--)
                {
                    listaKategorii.SetItemChecked(i, false);
                }
                foreach (string kategoria in BazaDanych.listaProduktow.ElementAt(listaProduktow.SelectedIndex).kategorie)
                {
                    for (int i = listaKategorii.Items.Count - 1; i >= 0; i--)
                    {
                        if ((String)listaKategorii.Items[i] == kategoria)
                            listaKategorii.SetItemChecked(i, true);
                    }
                }
            }
        }

        //funkcja edytująca wybrany produkt
        private void edytuj_Click(object sender, EventArgs e)
        {
            if (listaProduktow.SelectedIndex != -1) // sprawdzamy czy produkt został wybrany
            {
                const string message =
                   "Czy na pewno chcesz edytowac ten produkt?";
                const string caption = "Edytuj";
                var result = MessageBox.Show(message, caption,
                                             MessageBoxButtons.YesNo,
                                             MessageBoxIcon.Question);
                if (result == DialogResult.Yes)
                {
                    if (sprawdzNazwe(nazwa.Text, BazaDanych.listaProduktow.ElementAt(listaProduktow.SelectedIndex)))  // sprawdzamy czy nowa nazwa produktu już istnieje
                    {
                        if (BazaDanych.listaProduktow.ElementAt(listaProduktow.SelectedIndex).aktualizuj(nazwa.Text, cena.Text, kategorie.Text))
                        {
                            return;
                        }

                        foreach (string nowa in BazaDanych.listaProduktow.ElementAt(listaProduktow.SelectedIndex).kategorie)
                        {
                            bool appeared = false;
                            foreach (string istniejacaKategoria in listaKategorii.Items)
                            {
                                if (nowa.Trim() == istniejacaKategoria)
                                    appeared = true;
                            }
                            if (appeared == false)
                                this.BeginInvoke((MethodInvoker)delegate
                                {
                                    listaKategorii.Items.Add(nowa, true);
                                });
                        }

                        int index = listaProduktow.SelectedIndex;
                        listaProduktow.Items.RemoveAt(index);

                        listaProduktow.Items.Insert(index, BazaDanych.listaProduktow.ElementAt(index).nazwa);
                    }
                }
            }
            else
            {
                niepoprawnyProdukt();
            }
        }

        private void niepoprawnyProdukt()
        {
            const string message =
                      "Wybierz produkt";
            const string caption = "Niepoprawany produkt";
            var result = MessageBox.Show(message, caption,
                                         MessageBoxButtons.OK,
                                         MessageBoxIcon.Question);
        }

        private bool sprawdzNazwe(string nazwa, Produkt p)
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

            foreach (var i in BazaDanych.listaProduktow)
            {
                if ((p != null && i.Id != p.Id && i.nazwa == nazwa) || (p == null && i.nazwa == nazwa))
                {
                    const string message =
                  "Taki produkt już istnieje";
                    const string caption = "Niepoprawna nazwa";
                    var result = MessageBox.Show(message, caption,
                                                 MessageBoxButtons.OK,
                                                 MessageBoxIcon.Question);
                    return false;
                }
            }


            return true;
        }

        private void listaKategorii_ItemCheck(object sender, ItemCheckEventArgs e)
        {
            if (e.NewValue == CheckState.Checked)
            {
                if (!kategorie.Text.Contains(listaKategorii.Items[e.Index].ToString()))
                {
                    if (kategorie.Text.EndsWith(",") || kategorie.Text == "")
                        kategorie.Text += listaKategorii.Items[e.Index].ToString();
                    else
                        kategorie.Text += "," + listaKategorii.Items[e.Index].ToString();
                }
            }
            else
            {
                if (kategorie.Text.EndsWith(","))
                    kategorie.Text = kategorie.Text.Remove(kategorie.Text.Length - 1, 1);
                if (kategorie.Text != "")
                {
                    kategorie.Text = kategorie.Text.Replace(listaKategorii.Items[e.Index].ToString(), "");
                    kategorie.Text = kategorie.Text.Replace(",,", ",");
                }
            }
            if (kategorie.Text.StartsWith(","))
                kategorie.Text = kategorie.Text.Remove(0, 1);
        }
    }
}
