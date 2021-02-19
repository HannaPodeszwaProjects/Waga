namespace Listazakupow2
{
    partial class OknoList
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
            this.nazwaListy = new System.Windows.Forms.Label();
            this.listaNiezatwierdzonych = new System.Windows.Forms.ListBox();
            this.modyfikuj = new System.Windows.Forms.Button();
            this.zrealizuj = new System.Windows.Forms.Button();
            this.zapiszSzablon = new System.Windows.Forms.Button();
            this.usun = new System.Windows.Forms.Button();
            this.anuluj = new System.Windows.Forms.Button();
            this.drukuj = new System.Windows.Forms.Button();
            this.tabelaProduktowNaLiscie = new System.Windows.Forms.DataGridView();
            this.nazwa = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.ilosc = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.cena = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.dlaKogos = new System.Windows.Forms.DataGridViewCheckBoxColumn();
            this.suma = new System.Windows.Forms.Label();
            this.sumaDlaKogos = new System.Windows.Forms.Label();
            this.cenaNazwa = new System.Windows.Forms.Label();
            this.cenaDlaNazwa = new System.Windows.Forms.Label();
            this.listyNazwa = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.tabelaProduktowNaLiscie)).BeginInit();
            this.SuspendLayout();
            // 
            // nazwaListy
            // 
            this.nazwaListy.AutoSize = true;
            this.nazwaListy.Font = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.nazwaListy.Location = new System.Drawing.Point(14, 13);
            this.nazwaListy.Name = "nazwaListy";
            this.nazwaListy.Size = new System.Drawing.Size(110, 28);
            this.nazwaListy.TabIndex = 1;
            this.nazwaListy.Text = "Nazwa listy";
            // 
            // listaNiezatwierdzonych
            // 
            this.listaNiezatwierdzonych.FormattingEnabled = true;
            this.listaNiezatwierdzonych.ItemHeight = 20;
            this.listaNiezatwierdzonych.Location = new System.Drawing.Point(451, 44);
            this.listaNiezatwierdzonych.Name = "listaNiezatwierdzonych";
            this.listaNiezatwierdzonych.Size = new System.Drawing.Size(200, 244);
            this.listaNiezatwierdzonych.TabIndex = 3;
            this.listaNiezatwierdzonych.SelectedIndexChanged += new System.EventHandler(this.listaNiezatwierdzonych_SelectedIndexChanged);
            // 
            // modyfikuj
            // 
            this.modyfikuj.Location = new System.Drawing.Point(451, 298);
            this.modyfikuj.Name = "modyfikuj";
            this.modyfikuj.Size = new System.Drawing.Size(200, 29);
            this.modyfikuj.TabIndex = 4;
            this.modyfikuj.Text = "Modyfikuj";
            this.modyfikuj.UseVisualStyleBackColor = true;
            this.modyfikuj.Click += new System.EventHandler(this.modyfikuj_Click);
            // 
            // zrealizuj
            // 
            this.zrealizuj.Location = new System.Drawing.Point(451, 336);
            this.zrealizuj.Name = "zrealizuj";
            this.zrealizuj.Size = new System.Drawing.Size(200, 29);
            this.zrealizuj.TabIndex = 5;
            this.zrealizuj.Text = "Zrealizuj";
            this.zrealizuj.UseVisualStyleBackColor = true;
            this.zrealizuj.Click += new System.EventHandler(this.zrealizuj_Click);
            // 
            // zapiszSzablon
            // 
            this.zapiszSzablon.Location = new System.Drawing.Point(451, 374);
            this.zapiszSzablon.Name = "zapiszSzablon";
            this.zapiszSzablon.Size = new System.Drawing.Size(200, 29);
            this.zapiszSzablon.TabIndex = 6;
            this.zapiszSzablon.Text = "Zapisz jako szablon";
            this.zapiszSzablon.UseVisualStyleBackColor = true;
            this.zapiszSzablon.Click += new System.EventHandler(this.zapiszSzablon_Click);
            // 
            // usun
            // 
            this.usun.Location = new System.Drawing.Point(451, 450);
            this.usun.Name = "usun";
            this.usun.Size = new System.Drawing.Size(200, 29);
            this.usun.TabIndex = 7;
            this.usun.Text = "Usuń";
            this.usun.UseVisualStyleBackColor = true;
            this.usun.Click += new System.EventHandler(this.usun_Click);
            // 
            // anuluj
            // 
            this.anuluj.Location = new System.Drawing.Point(451, 412);
            this.anuluj.Name = "anuluj";
            this.anuluj.Size = new System.Drawing.Size(200, 29);
            this.anuluj.TabIndex = 9;
            this.anuluj.Text = "Anuluj listę";
            this.anuluj.UseVisualStyleBackColor = true;
            this.anuluj.Click += new System.EventHandler(this.anuluj_Click);
            // 
            // drukuj
            // 
            this.drukuj.Location = new System.Drawing.Point(451, 488);
            this.drukuj.Name = "drukuj";
            this.drukuj.Size = new System.Drawing.Size(200, 29);
            this.drukuj.TabIndex = 10;
            this.drukuj.Text = "Drukuj";
            this.drukuj.UseVisualStyleBackColor = true;
            this.drukuj.Click += new System.EventHandler(this.drukuj_Click);
            // 
            // tabelaProduktowNaLiscie
            // 
            this.tabelaProduktowNaLiscie.AllowUserToAddRows = false;
            this.tabelaProduktowNaLiscie.AllowUserToDeleteRows = false;
            this.tabelaProduktowNaLiscie.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.tabelaProduktowNaLiscie.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.nazwa,
            this.ilosc,
            this.cena,
            this.dlaKogos});
            this.tabelaProduktowNaLiscie.Location = new System.Drawing.Point(14, 44);
            this.tabelaProduktowNaLiscie.Name = "tabelaProduktowNaLiscie";
            this.tabelaProduktowNaLiscie.ReadOnly = true;
            this.tabelaProduktowNaLiscie.RowHeadersWidth = 51;
            this.tabelaProduktowNaLiscie.Size = new System.Drawing.Size(417, 473);
            this.tabelaProduktowNaLiscie.TabIndex = 8;
            this.tabelaProduktowNaLiscie.Text = "tabelaProduktowNaLiscie";
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
            this.dlaKogos.FillWeight = 125F;
            this.dlaKogos.HeaderText = "Dla kogoś";
            this.dlaKogos.MinimumWidth = 6;
            this.dlaKogos.Name = "dlaKogos";
            this.dlaKogos.ReadOnly = true;
            this.dlaKogos.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.dlaKogos.SortMode = System.Windows.Forms.DataGridViewColumnSortMode.Automatic;
            this.dlaKogos.Width = 103;
            // 
            // suma
            // 
            this.suma.AutoSize = true;
            this.suma.Location = new System.Drawing.Point(55, 527);
            this.suma.Name = "suma";
            this.suma.Size = new System.Drawing.Size(44, 20);
            this.suma.TabIndex = 11;
            this.suma.Text = "suma";
            // 
            // sumaDlaKogos
            // 
            this.sumaDlaKogos.AutoSize = true;
            this.sumaDlaKogos.Location = new System.Drawing.Point(357, 527);
            this.sumaDlaKogos.Name = "sumaDlaKogos";
            this.sumaDlaKogos.Size = new System.Drawing.Size(40, 20);
            this.sumaDlaKogos.TabIndex = 12;
            this.sumaDlaKogos.Text = "cena";
            // 
            // cenaNazwa
            // 
            this.cenaNazwa.AutoSize = true;
            this.cenaNazwa.Location = new System.Drawing.Point(14, 527);
            this.cenaNazwa.Name = "cenaNazwa";
            this.cenaNazwa.Size = new System.Drawing.Size(45, 20);
            this.cenaNazwa.TabIndex = 13;
            this.cenaNazwa.Text = "Cena:";
            // 
            // cenaDlaNazwa
            // 
            this.cenaDlaNazwa.AutoSize = true;
            this.cenaDlaNazwa.Location = new System.Drawing.Point(171, 527);
            this.cenaDlaNazwa.Name = "cenaDlaNazwa";
            this.cenaDlaNazwa.Size = new System.Drawing.Size(190, 20);
            this.cenaDlaNazwa.TabIndex = 14;
            this.cenaDlaNazwa.Text = "Cena produktów dla kogoś:";
            // 
            // listyNazwa
            // 
            this.listyNazwa.AutoSize = true;
            this.listyNazwa.Location = new System.Drawing.Point(451, 20);
            this.listyNazwa.Name = "listyNazwa";
            this.listyNazwa.Size = new System.Drawing.Size(97, 20);
            this.listyNazwa.TabIndex = 15;
            this.listyNazwa.Text = "Aktualne listy";
            // 
            // Form0
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(665, 558);
            this.Controls.Add(this.listyNazwa);
            this.Controls.Add(this.cenaDlaNazwa);
            this.Controls.Add(this.cenaNazwa);
            this.Controls.Add(this.sumaDlaKogos);
            this.Controls.Add(this.suma);
            this.Controls.Add(this.drukuj);
            this.Controls.Add(this.anuluj);
            this.Controls.Add(this.tabelaProduktowNaLiscie);
            this.Controls.Add(this.usun);
            this.Controls.Add(this.zapiszSzablon);
            this.Controls.Add(this.zrealizuj);
            this.Controls.Add(this.modyfikuj);
            this.Controls.Add(this.listaNiezatwierdzonych);
            this.Controls.Add(this.nazwaListy);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "Form0";
            this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
            this.Text = "Listy";
            ((System.ComponentModel.ISupportInitialize)(this.tabelaProduktowNaLiscie)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label nazwaListy;
        private System.Windows.Forms.ListBox listaNiezatwierdzonych;
        private System.Windows.Forms.Button modyfikuj;
        private System.Windows.Forms.Button zrealizuj;
        private System.Windows.Forms.Button zapiszSzablon;
        private System.Windows.Forms.Button usun;
        private System.Windows.Forms.DataGridView tabelaProduktowNaLiscie;
        private System.Windows.Forms.Button anuluj;
        private System.Windows.Forms.Button drukuj;
        private System.Windows.Forms.Label suma;
        private System.Windows.Forms.Label sumaDlaKogos;
        private System.Windows.Forms.Label cenaNazwa;
        private System.Windows.Forms.DataGridViewTextBoxColumn nazwa;
        private System.Windows.Forms.DataGridViewTextBoxColumn ilosc;
        private System.Windows.Forms.DataGridViewTextBoxColumn cena;
        private System.Windows.Forms.DataGridViewCheckBoxColumn dlaKogos;
        private System.Windows.Forms.Label cenaDlaNazwa;
        private System.Windows.Forms.Label listyNazwa;
    }
}