using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace Listazakupow2
{
    public partial class ZarzadzajSklepami : Form
    {
        private Sklep wybranySklep = null;
        public ZarzadzajSklepami()
        {
            InitializeComponent();

            wypiszSklepy();
        }


        private void dodaj_Click(object sender, EventArgs e)
        {
            if (sprawdzNazwe(nazwa.Text))
            {
                Sklep s = new Sklep();
                s.dodaj(nazwa.Text);
                wypiszSklepy();

                nazwa.Text = "";
                sklepy.SelectedIndex = sklepy.Items.Count-1;
            }
        }

        private void usun_Click(object sender, EventArgs e)
        {
            if (wybranySklep != null)
            {
                if (wybranySklep.uzyty == 0)
                {
                    wybranySklep.usun();
                    wypiszSklepy();
                }
                else
                {
                    const string message =
                            "Nie można usunąć sklepu, które jest użyty.";
                    const string caption = "Niepoprawany sklep";
                    var result = MessageBox.Show(message, caption,
                                                 MessageBoxButtons.OK,
                                                 MessageBoxIcon.Question);
                }
            }
            else
            {
                const string message =
                        "Wybierz sklep";
                const string caption = "Niepoprawany sklep";
                var result = MessageBox.Show(message, caption,
                                             MessageBoxButtons.OK,
                                             MessageBoxIcon.Question);
            }
        }

        private void sklepy_SelectedIndexChanged(object sender, EventArgs e)
        {
            wybranySklep = BazaDanych.listaSklepow[sklepy.SelectedIndex];
        }

        private void wypiszSklepy()
        {
            sklepy.Items.Clear();
            foreach (var i in BazaDanych.listaSklepow)
            {
                sklepy.Items.Add(i.nazwaSklepu);
            }
            if (BazaDanych.listaSklepow.Count != 0)
                sklepy.SelectedIndex = 0;
        }

        //sprawdza czy sklep o podanej nazwe jest juz w bazie dancyh
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
            
            foreach (var i in BazaDanych.listaSklepow)
            {
                if ( i.nazwaSklepu == nazwa)
                {
                    const string message =
                  "Taki sklep już istnieje";
                    const string caption = "Niepoprawna nazwa";
                    var result = MessageBox.Show(message, caption,
                                                 MessageBoxButtons.OK,
                                                 MessageBoxIcon.Question);
                    return false;
                }
            }


            return true;
        }
    }

}
