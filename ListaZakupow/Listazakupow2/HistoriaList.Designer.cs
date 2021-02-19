namespace Listazakupow2
{
    partial class HistoriaList
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
            this.listyNazwa = new System.Windows.Forms.Label();
            this.nazwaListy = new System.Windows.Forms.Label();
            this.wszystkieZrealizowane = new System.Windows.Forms.ListBox();
            this.wyczysc = new System.Windows.Forms.Button();
            this.usun = new System.Windows.Forms.Button();
            this.cenaKoszyka = new System.Windows.Forms.Label();
            this.tablicaZrealizowanych = new System.Windows.Forms.DataGridView();
            this.nazwa = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.ilosc = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.cena = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.dlaKogos = new System.Windows.Forms.DataGridViewCheckBoxColumn();
            this.utworzSzablon = new System.Windows.Forms.Button();
            this.sumaDlaKogos = new System.Windows.Forms.Label();
            this.cenaNazwa = new System.Windows.Forms.Label();
            this.cenaDlaNazwa = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.tablicaZrealizowanych)).BeginInit();
            this.SuspendLayout();
            // 
            // listyNazwa
            // 
            this.listyNazwa.AutoSize = true;
            this.listyNazwa.Location = new System.Drawing.Point(451, 20);
            this.listyNazwa.Name = "listyNazwa";
            this.listyNazwa.Size = new System.Drawing.Size(128, 20);
            this.listyNazwa.TabIndex = 0;
            this.listyNazwa.Text = "Zrealizowane listy";
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
            // wszystkieZrealizowane
            // 
            this.wszystkieZrealizowane.FormattingEnabled = true;
            this.wszystkieZrealizowane.ItemHeight = 20;
            this.wszystkieZrealizowane.Location = new System.Drawing.Point(451, 44);
            this.wszystkieZrealizowane.Name = "wszystkieZrealizowane";
            this.wszystkieZrealizowane.Size = new System.Drawing.Size(200, 344);
            this.wszystkieZrealizowane.TabIndex = 3;
            this.wszystkieZrealizowane.SelectedIndexChanged += new System.EventHandler(this.wszystkieZrealizowane_SelectedIndexChanged);
            // 
            // wyczysc
            // 
            this.wyczysc.Location = new System.Drawing.Point(451, 488);
            this.wyczysc.Name = "wyczysc";
            this.wyczysc.Size = new System.Drawing.Size(200, 29);
            this.wyczysc.TabIndex = 4;
            this.wyczysc.Text = "Wyczyść historię";
            this.wyczysc.UseVisualStyleBackColor = true;
            this.wyczysc.Click += new System.EventHandler(this.wyczysc_Click);
            // 
            // usun
            // 
            this.usun.Location = new System.Drawing.Point(451, 450);
            this.usun.Name = "usun";
            this.usun.Size = new System.Drawing.Size(200, 29);
            this.usun.TabIndex = 5;
            this.usun.Text = "Usuń wybraną listę z historii";
            this.usun.UseVisualStyleBackColor = true;
            this.usun.Click += new System.EventHandler(this.usun_Click);
            // 
            // cenaKoszyka
            // 
            this.cenaKoszyka.AutoSize = true;
            this.cenaKoszyka.Location = new System.Drawing.Point(55, 527);
            this.cenaKoszyka.Name = "cenaKoszyka";
            this.cenaKoszyka.Size = new System.Drawing.Size(17, 20);
            this.cenaKoszyka.TabIndex = 7;
            this.cenaKoszyka.Text = "0";
            // 
            // tablicaZrealizowanych
            // 
            this.tablicaZrealizowanych.AllowUserToAddRows = false;
            this.tablicaZrealizowanych.AllowUserToDeleteRows = false;
            this.tablicaZrealizowanych.AllowUserToResizeColumns = false;
            this.tablicaZrealizowanych.AllowUserToResizeRows = false;
            this.tablicaZrealizowanych.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.tablicaZrealizowanych.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.nazwa,
            this.ilosc,
            this.cena,
            this.dlaKogos});
            this.tablicaZrealizowanych.Location = new System.Drawing.Point(14, 44);
            this.tablicaZrealizowanych.Name = "tablicaZrealizowanych";
            this.tablicaZrealizowanych.ReadOnly = true;
            this.tablicaZrealizowanych.RowHeadersWidth = 51;
            this.tablicaZrealizowanych.Size = new System.Drawing.Size(417, 473);
            this.tablicaZrealizowanych.TabIndex = 6;
            this.tablicaZrealizowanych.Text = "dataGridView1";
            // 
            // nazwa
            // 
            this.nazwa.HeaderText = "Nazwa";
            this.nazwa.MinimumWidth = 6;
            this.nazwa.Name = "nazwa";
            this.nazwa.ReadOnly = true;
            this.nazwa.Resizable = System.Windows.Forms.DataGridViewTriState.False;
            this.nazwa.Width = 125;
            // 
            // ilosc
            // 
            this.ilosc.HeaderText = "Ilość";
            this.ilosc.MinimumWidth = 6;
            this.ilosc.Name = "ilosc";
            this.ilosc.ReadOnly = true;
            this.ilosc.Resizable = System.Windows.Forms.DataGridViewTriState.False;
            this.ilosc.Width = 50;
            // 
            // cena
            // 
            this.cena.HeaderText = "Cena";
            this.cena.MinimumWidth = 6;
            this.cena.Name = "cena";
            this.cena.ReadOnly = true;
            this.cena.Resizable = System.Windows.Forms.DataGridViewTriState.False;
            this.cena.Width = 65;
            // 
            // dlaKogos
            // 
            this.dlaKogos.HeaderText = "Dla kogoś";
            this.dlaKogos.MinimumWidth = 6;
            this.dlaKogos.Name = "dlaKogos";
            this.dlaKogos.ReadOnly = true;
            this.dlaKogos.Width = 103;
            // 
            // utworzSzablon
            // 
            this.utworzSzablon.Location = new System.Drawing.Point(451, 412);
            this.utworzSzablon.Name = "utworzSzablon";
            this.utworzSzablon.Size = new System.Drawing.Size(200, 29);
            this.utworzSzablon.TabIndex = 9;
            this.utworzSzablon.Text = "Utwórz szablon";
            this.utworzSzablon.UseVisualStyleBackColor = true;
            this.utworzSzablon.Click += new System.EventHandler(this.utworzSzablon_Click);
            // 
            // sumaDlaKogos
            // 
            this.sumaDlaKogos.AutoSize = true;
            this.sumaDlaKogos.Location = new System.Drawing.Point(357, 527);
            this.sumaDlaKogos.Name = "sumaDlaKogos";
            this.sumaDlaKogos.Size = new System.Drawing.Size(17, 20);
            this.sumaDlaKogos.TabIndex = 10;
            this.sumaDlaKogos.Text = "0";
            // 
            // cenaNazwa
            // 
            this.cenaNazwa.AutoSize = true;
            this.cenaNazwa.Location = new System.Drawing.Point(14, 527);
            this.cenaNazwa.Name = "cenaNazwa";
            this.cenaNazwa.Size = new System.Drawing.Size(45, 20);
            this.cenaNazwa.TabIndex = 11;
            this.cenaNazwa.Text = "Cena:";
            // 
            // cenaDlaNazwa
            // 
            this.cenaDlaNazwa.AutoSize = true;
            this.cenaDlaNazwa.Location = new System.Drawing.Point(171, 527);
            this.cenaDlaNazwa.Name = "cenaDlaNazwa";
            this.cenaDlaNazwa.Size = new System.Drawing.Size(190, 20);
            this.cenaDlaNazwa.TabIndex = 12;
            this.cenaDlaNazwa.Text = "Cena produktów dla kogoś:";
            // 
            // HistoriaList
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(665, 558);
            this.Controls.Add(this.cenaDlaNazwa);
            this.Controls.Add(this.cenaNazwa);
            this.Controls.Add(this.sumaDlaKogos);
            this.Controls.Add(this.utworzSzablon);
            this.Controls.Add(this.cenaKoszyka);
            this.Controls.Add(this.tablicaZrealizowanych);
            this.Controls.Add(this.usun);
            this.Controls.Add(this.wyczysc);
            this.Controls.Add(this.wszystkieZrealizowane);
            this.Controls.Add(this.nazwaListy);
            this.Controls.Add(this.listyNazwa);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "HistoriaList";
            this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
            this.Text = "Zrealizowane listy";
            ((System.ComponentModel.ISupportInitialize)(this.tablicaZrealizowanych)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label listyNazwa;
        private System.Windows.Forms.Label nazwaListy;
        private System.Windows.Forms.ListBox wszystkieZrealizowane;
        private System.Windows.Forms.Button wyczysc;
        private System.Windows.Forms.Button usun;
        private System.Windows.Forms.DataGridView tablicaZrealizowanych;
        private System.Windows.Forms.Label cenaKoszyka;
        private System.Windows.Forms.Button utworzSzablon;
        private System.Windows.Forms.Label sumaDlaKogos;
        private System.Windows.Forms.DataGridViewTextBoxColumn nazwa;
        private System.Windows.Forms.DataGridViewTextBoxColumn ilosc;
        private System.Windows.Forms.DataGridViewTextBoxColumn cena;
        private System.Windows.Forms.DataGridViewCheckBoxColumn dlaKogos;
        private System.Windows.Forms.Label cenaNazwa;
        private System.Windows.Forms.Label cenaDlaNazwa;
    }
}