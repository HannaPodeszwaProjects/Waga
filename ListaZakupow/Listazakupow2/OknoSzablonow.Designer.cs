namespace Listazakupow2
{
    partial class OknoSzablonow
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
            this.nazwaSzablonu = new System.Windows.Forms.Label();
            this.wszystkieSzablony = new System.Windows.Forms.ListBox();
            this.modyfikuj = new System.Windows.Forms.Button();
            this.uzyj = new System.Windows.Forms.Button();
            this.usun = new System.Windows.Forms.Button();
            this.tabelaProduktow = new System.Windows.Forms.DataGridView();
            this.nazwa = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.ilosc = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.cena = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.dlaKogos = new System.Windows.Forms.DataGridViewCheckBoxColumn();
            this.suma = new System.Windows.Forms.Label();
            this.sumaDlaKogos = new System.Windows.Forms.Label();
            this.sklepy = new System.Windows.Forms.ComboBox();
            this.ceny = new System.Windows.Forms.Label();
            this.sklepNazwa = new System.Windows.Forms.Label();
            this.szablonyNazwa = new System.Windows.Forms.Label();
            this.cenaNazwa = new System.Windows.Forms.Label();
            this.cenaDlaNazwa = new System.Windows.Forms.Label();
            this.nazwaCeny = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.tabelaProduktow)).BeginInit();
            this.SuspendLayout();
            // 
            // nazwaSzablonu
            // 
            this.nazwaSzablonu.AutoSize = true;
            this.nazwaSzablonu.Font = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.nazwaSzablonu.Location = new System.Drawing.Point(14, 13);
            this.nazwaSzablonu.Name = "nazwaSzablonu";
            this.nazwaSzablonu.Size = new System.Drawing.Size(153, 28);
            this.nazwaSzablonu.TabIndex = 0;
            this.nazwaSzablonu.Text = "Nazwa szablonu";
            // 
            // wszystkieSzablony
            // 
            this.wszystkieSzablony.FormattingEnabled = true;
            this.wszystkieSzablony.ItemHeight = 20;
            this.wszystkieSzablony.Location = new System.Drawing.Point(451, 44);
            this.wszystkieSzablony.Name = "wszystkieSzablony";
            this.wszystkieSzablony.Size = new System.Drawing.Size(200, 284);
            this.wszystkieSzablony.TabIndex = 2;
            this.wszystkieSzablony.SelectedIndexChanged += new System.EventHandler(this.wszystkieSzablony_SelectedIndexChanged);
            // 
            // modyfikuj
            // 
            this.modyfikuj.Location = new System.Drawing.Point(451, 412);
            this.modyfikuj.Name = "modyfikuj";
            this.modyfikuj.Size = new System.Drawing.Size(200, 29);
            this.modyfikuj.TabIndex = 3;
            this.modyfikuj.Text = "Modyfikuj";
            this.modyfikuj.UseVisualStyleBackColor = true;
            this.modyfikuj.Click += new System.EventHandler(this.modyfikuj_Click);
            // 
            // uzyj
            // 
            this.uzyj.Location = new System.Drawing.Point(451, 450);
            this.uzyj.Name = "uzyj";
            this.uzyj.Size = new System.Drawing.Size(200, 29);
            this.uzyj.TabIndex = 4;
            this.uzyj.Text = "Użyj";
            this.uzyj.UseVisualStyleBackColor = true;
            this.uzyj.Click += new System.EventHandler(this.uzyj_Click);
            // 
            // usun
            // 
            this.usun.Location = new System.Drawing.Point(451, 488);
            this.usun.Name = "usun";
            this.usun.Size = new System.Drawing.Size(200, 29);
            this.usun.TabIndex = 5;
            this.usun.Text = "Usuń";
            this.usun.UseVisualStyleBackColor = true;
            this.usun.Click += new System.EventHandler(this.usun_Click);
            // 
            // tabelaProduktow
            // 
            this.tabelaProduktow.AllowUserToAddRows = false;
            this.tabelaProduktow.AllowUserToDeleteRows = false;
            this.tabelaProduktow.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.tabelaProduktow.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.nazwa,
            this.ilosc,
            this.cena,
            this.dlaKogos});
            this.tabelaProduktow.Location = new System.Drawing.Point(14, 44);
            this.tabelaProduktow.Name = "tabelaProduktow";
            this.tabelaProduktow.ReadOnly = true;
            this.tabelaProduktow.RowHeadersWidth = 51;
            this.tabelaProduktow.Size = new System.Drawing.Size(417, 473);
            this.tabelaProduktow.TabIndex = 6;
            this.tabelaProduktow.Text = "dataGridView1";
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
            this.ilosc.ReadOnly = true;
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
            this.dlaKogos.ReadOnly = true;
            this.dlaKogos.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.dlaKogos.Width = 103;
            // 
            // suma
            // 
            this.suma.AutoSize = true;
            this.suma.Location = new System.Drawing.Point(55, 527);
            this.suma.Name = "suma";
            this.suma.Size = new System.Drawing.Size(17, 20);
            this.suma.TabIndex = 7;
            this.suma.Text = "0";
            // 
            // sumaDlaKogos
            // 
            this.sumaDlaKogos.AutoSize = true;
            this.sumaDlaKogos.Location = new System.Drawing.Point(357, 527);
            this.sumaDlaKogos.Name = "sumaDlaKogos";
            this.sumaDlaKogos.Size = new System.Drawing.Size(17, 20);
            this.sumaDlaKogos.TabIndex = 8;
            this.sumaDlaKogos.Text = "0";
            // 
            // sklepy
            // 
            this.sklepy.FormattingEnabled = true;
            this.sklepy.Location = new System.Drawing.Point(451, 353);
            this.sklepy.Name = "sklepy";
            this.sklepy.Size = new System.Drawing.Size(200, 28);
            this.sklepy.TabIndex = 9;
            this.sklepy.SelectedIndexChanged += new System.EventHandler(this.sklepy_SelectedIndexChanged);
            // 
            // ceny
            // 
            this.ceny.AutoSize = true;
            this.ceny.Location = new System.Drawing.Point(557, 384);
            this.ceny.Name = "ceny";
            this.ceny.Size = new System.Drawing.Size(40, 20);
            this.ceny.TabIndex = 10;
            this.ceny.Text = "cena";
            // 
            // sklepNazwa
            // 
            this.sklepNazwa.AutoSize = true;
            this.sklepNazwa.Location = new System.Drawing.Point(451, 330);
            this.sklepNazwa.Name = "sklepNazwa";
            this.sklepNazwa.Size = new System.Drawing.Size(45, 20);
            this.sklepNazwa.TabIndex = 11;
            this.sklepNazwa.Text = "Sklep";
            // 
            // szablonyNazwa
            // 
            this.szablonyNazwa.AutoSize = true;
            this.szablonyNazwa.Location = new System.Drawing.Point(451, 20);
            this.szablonyNazwa.Name = "szablonyNazwa";
            this.szablonyNazwa.Size = new System.Drawing.Size(69, 20);
            this.szablonyNazwa.TabIndex = 12;
            this.szablonyNazwa.Text = "Szablony";
            // 
            // cenaNazwa
            // 
            this.cenaNazwa.AutoSize = true;
            this.cenaNazwa.Location = new System.Drawing.Point(14, 527);
            this.cenaNazwa.Name = "cenaNazwa";
            this.cenaNazwa.Size = new System.Drawing.Size(45, 20);
            this.cenaNazwa.TabIndex = 14;
            this.cenaNazwa.Text = "Cena:";
            // 
            // cenaDlaNazwa
            // 
            this.cenaDlaNazwa.AutoSize = true;
            this.cenaDlaNazwa.Location = new System.Drawing.Point(171, 527);
            this.cenaDlaNazwa.Name = "cenaDlaNazwa";
            this.cenaDlaNazwa.Size = new System.Drawing.Size(190, 20);
            this.cenaDlaNazwa.TabIndex = 15;
            this.cenaDlaNazwa.Text = "Cena produktów dla kogoś:";
            // 
            // nazwaCeny
            // 
            this.nazwaCeny.AutoSize = true;
            this.nazwaCeny.Location = new System.Drawing.Point(451, 384);
            this.nazwaCeny.Name = "nazwaCeny";
            this.nazwaCeny.Size = new System.Drawing.Size(110, 20);
            this.nazwaCeny.TabIndex = 16;
            this.nazwaCeny.Text = "Cena w sklepie:";
            // 
            // Form3
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(665, 558);
            this.Controls.Add(this.nazwaCeny);
            this.Controls.Add(this.cenaDlaNazwa);
            this.Controls.Add(this.cenaNazwa);
            this.Controls.Add(this.szablonyNazwa);
            this.Controls.Add(this.sklepNazwa);
            this.Controls.Add(this.ceny);
            this.Controls.Add(this.sklepy);
            this.Controls.Add(this.sumaDlaKogos);
            this.Controls.Add(this.suma);
            this.Controls.Add(this.tabelaProduktow);
            this.Controls.Add(this.usun);
            this.Controls.Add(this.uzyj);
            this.Controls.Add(this.modyfikuj);
            this.Controls.Add(this.wszystkieSzablony);
            this.Controls.Add(this.nazwaSzablonu);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "Form3";
            this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
            this.Text = "Szablony";
            ((System.ComponentModel.ISupportInitialize)(this.tabelaProduktow)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label nazwaSzablonu;
        private System.Windows.Forms.ListBox wszystkieSzablony;
        private System.Windows.Forms.Button modyfikuj;
        private System.Windows.Forms.Button uzyj;
        private System.Windows.Forms.Button usun;
        private System.Windows.Forms.DataGridView tabelaProduktow;
        private System.Windows.Forms.Label suma;
        private System.Windows.Forms.Label sumaDlaKogos;
        private System.Windows.Forms.ComboBox sklepy;
        private System.Windows.Forms.Label ceny;
        private System.Windows.Forms.DataGridViewTextBoxColumn nazwa;
        private System.Windows.Forms.DataGridViewTextBoxColumn ilosc;
        private System.Windows.Forms.DataGridViewTextBoxColumn cena;
        private System.Windows.Forms.DataGridViewCheckBoxColumn dlaKogos;
        private System.Windows.Forms.Label sklepNazwa;
        private System.Windows.Forms.Label szablonyNazwa;
        private System.Windows.Forms.Label cenaNazwa;
        private System.Windows.Forms.Label cenaDlaNazwa;
        private System.Windows.Forms.Label nazwaCeny;
    }
}