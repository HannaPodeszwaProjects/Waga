namespace Listazakupow2
{
    partial class Form2
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.wyszukiwarka = new System.Windows.Forms.TextBox();
            this.OkienkoWyszukanychZakupow = new System.Windows.Forms.ListBox();
            this.przyciskZapiszListe = new System.Windows.Forms.Button();
            this.przyciskZapiszSzablon = new System.Windows.Forms.Button();
            this.WyszukajNazwa = new System.Windows.Forms.Label();
            this.nazwaListy = new System.Windows.Forms.TextBox();
            this.nazwaNazwa = new System.Windows.Forms.Label();
            this.tablicaWybranychProduktow = new System.Windows.Forms.DataGridView();
            this.nazwa = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.ilosc = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.cena = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.dlaKogos = new System.Windows.Forms.DataGridViewCheckBoxColumn();
            this.usun = new System.Windows.Forms.DataGridViewButtonColumn();
            this.uwagaNazwa = new System.Windows.Forms.Label();
            this.kategorie = new System.Windows.Forms.CheckedListBox();
            this.sklepy = new System.Windows.Forms.ComboBox();
            this.ceny = new System.Windows.Forms.TextBox();
            this.zapisz = new System.Windows.Forms.Button();
            this.usunsklep = new System.Windows.Forms.Button();
            this.najtaniej = new System.Windows.Forms.Label();
            this.kategorieNazwa = new System.Windows.Forms.Label();
            this.sklepNazwa = new System.Windows.Forms.Label();
            this.CenaNazwa = new System.Windows.Forms.Label();
            this.wyszukanieListaNazwa = new System.Windows.Forms.Label();
            this.nazwaZakupy = new System.Windows.Forms.Label();
            this.najtanszyNazwa = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.tablicaWybranychProduktow)).BeginInit();
            this.SuspendLayout();
            // 
            // wyszukiwarka
            // 
            this.wyszukiwarka.Location = new System.Drawing.Point(194, 32);
            this.wyszukiwarka.Name = "wyszukiwarka";
            this.wyszukiwarka.Size = new System.Drawing.Size(170, 27);
            this.wyszukiwarka.TabIndex = 1;
            this.wyszukiwarka.TextChanged += new System.EventHandler(this.Wyszukiwarka_TextChanged);
            // 
            // OkienkoWyszukanychZakupow
            // 
            this.OkienkoWyszukanychZakupow.FormattingEnabled = true;
            this.OkienkoWyszukanychZakupow.ItemHeight = 20;
            this.OkienkoWyszukanychZakupow.Location = new System.Drawing.Point(194, 87);
            this.OkienkoWyszukanychZakupow.Name = "OkienkoWyszukanychZakupow";
            this.OkienkoWyszukanychZakupow.Size = new System.Drawing.Size(170, 444);
            this.OkienkoWyszukanychZakupow.TabIndex = 2;
            this.OkienkoWyszukanychZakupow.SelectedIndexChanged += new System.EventHandler(this.OkienkoWyszukanychZakupow_SelectedIndexChanged);
            // 
            // przyciskZapiszListe
            // 
            this.przyciskZapiszListe.Location = new System.Drawing.Point(384, 464);
            this.przyciskZapiszListe.Name = "przyciskZapiszListe";
            this.przyciskZapiszListe.Size = new System.Drawing.Size(150, 29);
            this.przyciskZapiszListe.TabIndex = 3;
            this.przyciskZapiszListe.Text = "Zapisz jako listę";
            this.przyciskZapiszListe.UseVisualStyleBackColor = true;
            this.przyciskZapiszListe.Click += new System.EventHandler(this.przyciskZapiszListe_Klik);
            // 
            // przyciskZapiszSzablon
            // 
            this.przyciskZapiszSzablon.Location = new System.Drawing.Point(384, 502);
            this.przyciskZapiszSzablon.Name = "przyciskZapiszSzablon";
            this.przyciskZapiszSzablon.Size = new System.Drawing.Size(150, 29);
            this.przyciskZapiszSzablon.TabIndex = 5;
            this.przyciskZapiszSzablon.Text = "Zapisz jako szablon";
            this.przyciskZapiszSzablon.UseVisualStyleBackColor = true;
            this.przyciskZapiszSzablon.Click += new System.EventHandler(this.przyciskZapiszSzablon_Click);
            // 
            // WyszukajNazwa
            // 
            this.WyszukajNazwa.AutoSize = true;
            this.WyszukajNazwa.Location = new System.Drawing.Point(194, 9);
            this.WyszukajNazwa.Name = "WyszukajNazwa";
            this.WyszukajNazwa.Size = new System.Drawing.Size(70, 20);
            this.WyszukajNazwa.TabIndex = 6;
            this.WyszukajNazwa.Text = "Wyszukaj";
            // 
            // nazwaListy
            // 
            this.nazwaListy.Location = new System.Drawing.Point(554, 32);
            this.nazwaListy.Name = "nazwaListy";
            this.nazwaListy.Size = new System.Drawing.Size(218, 27);
            this.nazwaListy.TabIndex = 7;
            // 
            // nazwaNazwa
            // 
            this.nazwaNazwa.AutoSize = true;
            this.nazwaNazwa.Location = new System.Drawing.Point(554, 9);
            this.nazwaNazwa.Name = "nazwaNazwa";
            this.nazwaNazwa.Size = new System.Drawing.Size(54, 20);
            this.nazwaNazwa.TabIndex = 8;
            this.nazwaNazwa.Text = "Nazwa";
            // 
            // tablicaWybranychProduktow
            // 
            this.tablicaWybranychProduktow.AllowUserToAddRows = false;
            this.tablicaWybranychProduktow.AllowUserToDeleteRows = false;
            this.tablicaWybranychProduktow.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.tablicaWybranychProduktow.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.nazwa,
            this.ilosc,
            this.cena,
            this.dlaKogos,
            this.usun});
            this.tablicaWybranychProduktow.EditMode = System.Windows.Forms.DataGridViewEditMode.EditOnEnter;
            this.tablicaWybranychProduktow.Location = new System.Drawing.Point(554, 87);
            this.tablicaWybranychProduktow.Name = "tablicaWybranychProduktow";
            this.tablicaWybranychProduktow.RowHeadersWidth = 51;
            this.tablicaWybranychProduktow.Size = new System.Drawing.Size(467, 444);
            this.tablicaWybranychProduktow.TabIndex = 11;
            this.tablicaWybranychProduktow.Text = "dataGridView1";
            this.tablicaWybranychProduktow.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.tablicaWybranychProduktow_CellContentClick);
            this.tablicaWybranychProduktow.CellValueChanged += new System.Windows.Forms.DataGridViewCellEventHandler(this.tablicaWybranychProduktow_CellValueChanged);
            // 
            // nazwa
            // 
            this.nazwa.HeaderText = "Nazwa";
            this.nazwa.MinimumWidth = 6;
            this.nazwa.Name = "nazwa";
            this.nazwa.ReadOnly = true;
            this.nazwa.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.nazwa.Width = 125;
            // 
            // ilosc
            // 
            this.ilosc.HeaderText = "Ilość";
            this.ilosc.MinimumWidth = 6;
            this.ilosc.Name = "ilosc";
            this.ilosc.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.ilosc.Width = 50;
            // 
            // cena
            // 
            this.cena.HeaderText = "Cena";
            this.cena.MinimumWidth = 6;
            this.cena.Name = "cena";
            this.cena.ReadOnly = true;
            this.cena.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.cena.Width = 65;
            // 
            // dlaKogos
            // 
            this.dlaKogos.HeaderText = "Dla kogoś";
            this.dlaKogos.MinimumWidth = 6;
            this.dlaKogos.Name = "dlaKogos";
            this.dlaKogos.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.dlaKogos.SortMode = System.Windows.Forms.DataGridViewColumnSortMode.Automatic;
            this.dlaKogos.Width = 103;
            // 
            // usun
            // 
            this.usun.HeaderText = "Usuń";
            this.usun.MinimumWidth = 6;
            this.usun.Name = "usun";
            this.usun.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.usun.Width = 50;
            // 
            // uwagaNazwa
            // 
            this.uwagaNazwa.AutoSize = true;
            this.uwagaNazwa.ForeColor = System.Drawing.Color.Red;
            this.uwagaNazwa.Location = new System.Drawing.Point(554, 534);
            this.uwagaNazwa.Name = "uwagaNazwa";
            this.uwagaNazwa.Size = new System.Drawing.Size(397, 20);
            this.uwagaNazwa.TabIndex = 12;
            this.uwagaNazwa.Text = "*Uwaga! Należy wpisywać cenę za kilogram, litr lub sztukę!";
            // 
            // kategorie
            // 
            this.kategorie.CheckOnClick = true;
            this.kategorie.FormattingEnabled = true;
            this.kategorie.Location = new System.Drawing.Point(14, 87);
            this.kategorie.Name = "kategorie";
            this.kategorie.Size = new System.Drawing.Size(160, 444);
            this.kategorie.TabIndex = 13;
            this.kategorie.ItemCheck += new System.Windows.Forms.ItemCheckEventHandler(this.kategorie_ZmienZaznaczenie);
            // 
            // sklepy
            // 
            this.sklepy.FormattingEnabled = true;
            this.sklepy.Location = new System.Drawing.Point(384, 87);
            this.sklepy.Name = "sklepy";
            this.sklepy.Size = new System.Drawing.Size(150, 28);
            this.sklepy.TabIndex = 14;
            this.sklepy.SelectedIndexChanged += new System.EventHandler(this.sklepy_SelectedIndexChanged);
            // 
            // ceny
            // 
            this.ceny.Location = new System.Drawing.Point(384, 142);
            this.ceny.Name = "ceny";
            this.ceny.Size = new System.Drawing.Size(150, 27);
            this.ceny.TabIndex = 15;
            // 
            // zapisz
            // 
            this.zapisz.Location = new System.Drawing.Point(384, 182);
            this.zapisz.Name = "zapisz";
            this.zapisz.Size = new System.Drawing.Size(150, 27);
            this.zapisz.TabIndex = 16;
            this.zapisz.Text = "Zapisz";
            this.zapisz.UseVisualStyleBackColor = true;
            this.zapisz.Click += new System.EventHandler(this.zapisz_Click);
            // 
            // usunsklep
            // 
            this.usunsklep.Location = new System.Drawing.Point(384, 222);
            this.usunsklep.Name = "usunsklep";
            this.usunsklep.Size = new System.Drawing.Size(150, 27);
            this.usunsklep.TabIndex = 17;
            this.usunsklep.Text = "Usuń";
            this.usunsklep.UseVisualStyleBackColor = true;
            this.usunsklep.Click += new System.EventHandler(this.usunsklep_Click);
            // 
            // najtaniej
            // 
            this.najtaniej.AutoSize = true;
            this.najtaniej.Location = new System.Drawing.Point(384, 306);
            this.najtaniej.Name = "najtaniej";
            this.najtaniej.Size = new System.Drawing.Size(66, 20);
            this.najtaniej.TabIndex = 18;
            this.najtaniej.Text = "najtaniej";
            // 
            // kategorieNazwa
            // 
            this.kategorieNazwa.AutoSize = true;
            this.kategorieNazwa.Location = new System.Drawing.Point(14, 64);
            this.kategorieNazwa.Name = "kategorieNazwa";
            this.kategorieNazwa.Size = new System.Drawing.Size(74, 20);
            this.kategorieNazwa.TabIndex = 19;
            this.kategorieNazwa.Text = "Kategorie";
            // 
            // sklepNazwa
            // 
            this.sklepNazwa.AutoSize = true;
            this.sklepNazwa.Location = new System.Drawing.Point(384, 64);
            this.sklepNazwa.Name = "sklepNazwa";
            this.sklepNazwa.Size = new System.Drawing.Size(45, 20);
            this.sklepNazwa.TabIndex = 20;
            this.sklepNazwa.Text = "Sklep";
            // 
            // CenaNazwa
            // 
            this.CenaNazwa.AutoSize = true;
            this.CenaNazwa.Location = new System.Drawing.Point(384, 119);
            this.CenaNazwa.Name = "CenaNazwa";
            this.CenaNazwa.Size = new System.Drawing.Size(42, 20);
            this.CenaNazwa.TabIndex = 21;
            this.CenaNazwa.Text = "Cena";
            // 
            // wyszukanieListaNazwa
            // 
            this.wyszukanieListaNazwa.AutoSize = true;
            this.wyszukanieListaNazwa.Location = new System.Drawing.Point(194, 64);
            this.wyszukanieListaNazwa.Name = "wyszukanieListaNazwa";
            this.wyszukanieListaNazwa.Size = new System.Drawing.Size(145, 20);
            this.wyszukanieListaNazwa.TabIndex = 22;
            this.wyszukanieListaNazwa.Text = "Wyszukane produkty";
            // 
            // nazwaZakupy
            // 
            this.nazwaZakupy.AutoSize = true;
            this.nazwaZakupy.Location = new System.Drawing.Point(554, 64);
            this.nazwaZakupy.Name = "nazwaZakupy";
            this.nazwaZakupy.Size = new System.Drawing.Size(57, 20);
            this.nazwaZakupy.TabIndex = 23;
            this.nazwaZakupy.Text = "Zakupy";
            // 
            // najtanszyNazwa
            // 
            this.najtanszyNazwa.AutoSize = true;
            this.najtanszyNazwa.Location = new System.Drawing.Point(384, 285);
            this.najtanszyNazwa.Name = "najtanszyNazwa";
            this.najtanszyNazwa.Size = new System.Drawing.Size(114, 20);
            this.najtanszyNazwa.TabIndex = 24;
            this.najtanszyNazwa.Text = "Najtanszy sklep:";
            // 
            // Form2
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1035, 558);
            this.Controls.Add(this.najtanszyNazwa);
            this.Controls.Add(this.nazwaZakupy);
            this.Controls.Add(this.wyszukanieListaNazwa);
            this.Controls.Add(this.CenaNazwa);
            this.Controls.Add(this.sklepNazwa);
            this.Controls.Add(this.kategorieNazwa);
            this.Controls.Add(this.kategorie);
            this.Controls.Add(this.najtaniej);
            this.Controls.Add(this.usunsklep);
            this.Controls.Add(this.zapisz);
            this.Controls.Add(this.ceny);
            this.Controls.Add(this.sklepy);
            this.Controls.Add(this.uwagaNazwa);
            this.Controls.Add(this.tablicaWybranychProduktow);
            this.Controls.Add(this.nazwaNazwa);
            this.Controls.Add(this.nazwaListy);
            this.Controls.Add(this.WyszukajNazwa);
            this.Controls.Add(this.przyciskZapiszSzablon);
            this.Controls.Add(this.przyciskZapiszListe);
            this.Controls.Add(this.OkienkoWyszukanychZakupow);
            this.Controls.Add(this.wyszukiwarka);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "Form2";
            this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
            this.Text = "Tworzenie listy / szablonu";
            this.Load += new System.EventHandler(this.Form2_Load);
            ((System.ComponentModel.ISupportInitialize)(this.tablicaWybranychProduktow)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.TextBox wyszukiwarka;
        private System.Windows.Forms.ListBox OkienkoWyszukanychZakupow;
        private System.Windows.Forms.Button przyciskZapiszListe;
        private System.Windows.Forms.Button przyciskZapiszSzablon;
        private System.Windows.Forms.Label WyszukajNazwa;
        private System.Windows.Forms.TextBox nazwaListy;
        private System.Windows.Forms.Label nazwaNazwa;
        private System.Windows.Forms.DataGridView tablicaWybranychProduktow;
        private System.Windows.Forms.Label uwagaNazwa;
        private System.Windows.Forms.CheckedListBox kategorie;
        private System.Windows.Forms.ComboBox sklepy;
        private System.Windows.Forms.TextBox ceny;
        private System.Windows.Forms.Button zapisz;
        private System.Windows.Forms.Button usunsklep;
        private System.Windows.Forms.Label najtaniej;
        private System.Windows.Forms.Label kategorieNazwa;
        private System.Windows.Forms.Label sklepNazwa;
        private System.Windows.Forms.Label CenaNazwa;
        private System.Windows.Forms.Label wyszukanieListaNazwa;
        private System.Windows.Forms.Label nazwaZakupy;
        private System.Windows.Forms.DataGridViewTextBoxColumn nazwa;
        private System.Windows.Forms.DataGridViewTextBoxColumn ilosc;
        private System.Windows.Forms.DataGridViewTextBoxColumn cena;
        private System.Windows.Forms.DataGridViewCheckBoxColumn dlaKogos;
        private System.Windows.Forms.DataGridViewButtonColumn usun;
        private System.Windows.Forms.Label najtanszyNazwa;
    }
}