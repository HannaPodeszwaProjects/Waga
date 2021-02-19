using System;
using System.Collections.Generic;
using System.Windows.Forms;
using System.Linq;


namespace Listazakupow2
{

    public partial class HistoriaList : Form
    {
        private List<Lista> zatwierdzone = new List<Lista>();
        private Lista wybranaLista = null;

        public HistoriaList()
        {
            InitializeComponent();

            nazwaListy.Text = "";
            zapelnijTabeleZrealizowanych();
            if (wszystkieZrealizowane.Items.Count > 0)
            {
                ustawWidocznosc(true);
            }
            else
            {
                ustawWidocznosc(false);
            }

        }

        // funkcja wyświeltająca wybraną zrealizowaną lub anulowaną listę
        private void wszystkieZrealizowane_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (wszystkieZrealizowane.SelectedIndex != -1) // sprawdzamy czy lista została wybrana
            {
                wybranaLista = zatwierdzone.ElementAt(wszystkieZrealizowane.SelectedIndex);
                if(wybranaLista.anulowana == true)
                {
                    nazwaListy.Text = wybranaLista.nazwaListy + " (anulowana)"; // wypisujemy nazwę listy
                }
                else
                nazwaListy.Text = wybranaLista.nazwaListy; // wypisujemy nazwę listy

                tablicaZrealizowanych.Rows.Clear();   // czyścimy tablicę po poprzedniej liście

                foreach (var item in zatwierdzone.ElementAt(wszystkieZrealizowane.SelectedIndex).wyswietl_liste()) // wypisujemy produkty z listy
                {
                    this.tablicaZrealizowanych.Rows.Add(item.Item1.nazwa, item.Item2, (item.Item1.cena * float.Parse(item.Item2)).ToString(), item.Item3);                  
                }

                if (wybranaLista.anulowana == false)
                {
                    cenaKoszyka.Text = wybranaLista.cenaKoszyka.ToString("0.##"); // wypisujemy cenę koszyka
                    sumaDlaKogos.Text = wybranaLista.cenaDlaKogos.ToString("0.##"); // wypisujey cenę produktów dla kogoś
                    ustawWidocznosc(true);
                }
                else
                {
                    ustawWidocznosc(false);
                }
            }
            else // jeśli nie wybraliśmy listy czyścimy nazwę listy
            {
                nazwaListy.Text = ""; 
            }
        }

        // czyścimy historię ze wszystkich zrealizowanych oraz anulowanych list
        private void wyczysc_Click(object sender, EventArgs e)
        {
            int liczbaList = BazaDanych.listaList.Count;
            if (liczbaList > 0) // sprawdzamy czy są jakieś listy
            {
                for(int i =0; i < liczbaList; i++) // iterujemy po wszystkich listach
                {
                    if (BazaDanych.listaList.ElementAt(i).zatwierdzona == true || BazaDanych.listaList.ElementAt(i).anulowana == true) // jeśli są zrealizowana lub anulowane to je usuwamy 
                    {
                        BazaDanych.listaList.ElementAt(i).usun_liste();
                        i--;
                        liczbaList--;
                    }
                }
                wszystkieZrealizowane.Items.Clear(); // czyścimy okienko wszystkich zrealizowanych list 
                tablicaZrealizowanych.Rows.Clear(); // czyścimy okienko tablicy zrealizowanej listy
                nazwaListy.Text = "0";
                cenaKoszyka.Text = "0";
            }
        }

        // funkcja usuwająca wybraną listę z historii
        private void usun_Click(object sender, EventArgs e)
        {
            if (wszystkieZrealizowane.SelectedIndex != -1) // sprawdzamy czy lista jest wybrana
            {
                BazaDanych.listaList.ElementAt(wszystkieZrealizowane.SelectedIndex).usun_liste(); // usuwamy wybarną listę
                zatwierdzone.RemoveAt(wszystkieZrealizowane.SelectedIndex); 
                wszystkieZrealizowane.Items.RemoveAt(wszystkieZrealizowane.SelectedIndex);
                tablicaZrealizowanych.Rows.Clear();
                nazwaListy.Text = "0";
                cenaKoszyka.Text = "0";
            }
            else // jeśli lista nie jest wyrana pojawa się komunikat
            {
                const string message =
                        "Wybierz listę";
                const string caption = "Niepoprawna lista";
                var result = MessageBox.Show(message, caption,
                                             MessageBoxButtons.OK,
                                             MessageBoxIcon.Question);
            }
        }

        private void zapelnijTabeleZrealizowanych()
        {
            if (BazaDanych.listaList.Count > 0)
            {
                foreach (var item in BazaDanych.listaList)
                {
                    if (item.zatwierdzona == true || item.anulowana == true)
                    {
                        wszystkieZrealizowane.Items.Add($"{item.nazwaListy}");
                        zatwierdzone.Add(item);
                    }
                }
            }
        }



        private void utworzSzablon_Click(object sender, EventArgs e)
        {
            if (wszystkieZrealizowane.SelectedIndex != -1)
            {
                const string message =
                   "Czy na pewno chcesz utworzyć szablon?";
                const string caption = "Utwórz";
                var result = MessageBox.Show(message, caption,
                                             MessageBoxButtons.YesNo,
                                             MessageBoxIcon.Question);

                if (result == DialogResult.Yes)
                {
                    if (sprawdzNazwe(wybranaLista.nazwaListy))
                    {
                        Szablon szablon = new Szablon(wybranaLista.nazwaListy);
                        szablon.zapiszSzablonZListy(wybranaLista);
                        const string message1 =
                 "Utworzono szablon.";
                        const string caption1 = "Szablon";
                        var result1 = MessageBox.Show(message1, caption1,
                                                     MessageBoxButtons.OK,
                                                     MessageBoxIcon.Question);
                    }
                }
            }
            else
            {
                niepoprawnaLista();
            }
        }

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

        private void niepoprawnaLista()
        {
            const string message =
                      "Wybierz listę";
            const string caption = "Niepoprawna lista";
            var result = MessageBox.Show(message, caption,
                                         MessageBoxButtons.OK,
                                         MessageBoxIcon.Question);
        }


        private void ustawWidocznosc(bool jak)
        {
            cenaKoszyka.Visible = jak;
            sumaDlaKogos.Visible = jak;
            cenaNazwa.Visible = jak;
            cenaDlaNazwa.Visible = jak;
        }

    }
}
