using System.Drawing;

namespace Listazakupow2
{
    partial class OknoProduktow
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
            this.listaProduktow = new System.Windows.Forms.ListBox();
            this.nazwa = new System.Windows.Forms.TextBox();
            this.cena = new System.Windows.Forms.TextBox();
            this.kategorie = new System.Windows.Forms.TextBox();
            this.dodaj = new System.Windows.Forms.Button();
            this.usun = new System.Windows.Forms.Button();
            this.nazwaNazwa = new System.Windows.Forms.Label();
            this.cenaNazwa = new System.Windows.Forms.Label();
            this.kategorieNazwa = new System.Windows.Forms.Label();
            this.listaKategorii = new System.Windows.Forms.CheckedListBox();
            this.edytuj = new System.Windows.Forms.Button();
            this.uwagaNazwa = new System.Windows.Forms.Label();
            this.listaProduktowNazwa = new System.Windows.Forms.Label();
            this.listaKategoriiNazwa = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // listaProduktow
            // 
            this.listaProduktow.FormattingEnabled = true;
            this.listaProduktow.ItemHeight = 20;
            this.listaProduktow.Location = new System.Drawing.Point(14, 33);
            this.listaProduktow.Name = "listaProduktow";
            this.listaProduktow.Size = new System.Drawing.Size(170, 444);
            this.listaProduktow.TabIndex = 0;
            this.listaProduktow.SelectedIndexChanged += new System.EventHandler(this.listaProduktow_SelectedIndexChanged);
            // 
            // nazwa
            // 
            this.nazwa.Location = new System.Drawing.Point(384, 33);
            this.nazwa.Name = "nazwa";
            this.nazwa.Size = new System.Drawing.Size(150, 27);
            this.nazwa.TabIndex = 1;
            // 
            // cena
            // 
            this.cena.Location = new System.Drawing.Point(384, 88);
            this.cena.Name = "cena";
            this.cena.Size = new System.Drawing.Size(150, 27);
            this.cena.TabIndex = 2;
            // 
            // kategorie
            // 
            this.kategorie.Location = new System.Drawing.Point(384, 143);
            this.kategorie.Name = "kategorie";
            this.kategorie.Size = new System.Drawing.Size(150, 27);
            this.kategorie.TabIndex = 3;
            // 
            // dodaj
            // 
            this.dodaj.Location = new System.Drawing.Point(384, 183);
            this.dodaj.Name = "dodaj";
            this.dodaj.Size = new System.Drawing.Size(150, 29);
            this.dodaj.TabIndex = 4;
            this.dodaj.Text = "Dodaj";
            this.dodaj.UseVisualStyleBackColor = true;
            this.dodaj.Click += new System.EventHandler(this.dodaj_Click);
            // 
            // usun
            // 
            this.usun.Location = new System.Drawing.Point(384, 259);
            this.usun.Name = "usun";
            this.usun.Size = new System.Drawing.Size(150, 29);
            this.usun.TabIndex = 5;
            this.usun.Text = "Usuń";
            this.usun.UseVisualStyleBackColor = true;
            this.usun.Click += new System.EventHandler(this.usun_Click);
            // 
            // nazwaNazwa
            // 
            this.nazwaNazwa.AutoSize = true;
            this.nazwaNazwa.Location = new System.Drawing.Point(384, 10);
            this.nazwaNazwa.Name = "nazwaNazwa";
            this.nazwaNazwa.Size = new System.Drawing.Size(54, 20);
            this.nazwaNazwa.TabIndex = 6;
            this.nazwaNazwa.Text = "Nazwa";
            // 
            // cenaNazwa
            // 
            this.cenaNazwa.AutoSize = true;
            this.cenaNazwa.Location = new System.Drawing.Point(384, 65);
            this.cenaNazwa.Name = "cenaNazwa";
            this.cenaNazwa.Size = new System.Drawing.Size(42, 20);
            this.cenaNazwa.TabIndex = 7;
            this.cenaNazwa.Text = "Cena";
            // 
            // kategorieNazwa
            // 
            this.kategorieNazwa.AutoSize = true;
            this.kategorieNazwa.Location = new System.Drawing.Point(384, 120);
            this.kategorieNazwa.Name = "kategorieNazwa";
            this.kategorieNazwa.Size = new System.Drawing.Size(74, 20);
            this.kategorieNazwa.TabIndex = 8;
            this.kategorieNazwa.Text = "Kategorie";
            // 
            // listaKategorii
            // 
            this.listaKategorii.FormattingEnabled = true;
            this.listaKategorii.Location = new System.Drawing.Point(204, 33);
            this.listaKategorii.Name = "listaKategorii";
            this.listaKategorii.Size = new System.Drawing.Size(160, 444);
            this.listaKategorii.TabIndex = 9;
            this.listaKategorii.ItemCheck += new System.Windows.Forms.ItemCheckEventHandler(this.listaKategorii_ItemCheck);
            // 
            // edytuj
            // 
            this.edytuj.Location = new System.Drawing.Point(384, 221);
            this.edytuj.Name = "edytuj";
            this.edytuj.Size = new System.Drawing.Size(150, 29);
            this.edytuj.TabIndex = 10;
            this.edytuj.Text = "Edytuj";
            this.edytuj.UseVisualStyleBackColor = true;
            this.edytuj.Click += new System.EventHandler(this.edytuj_Click);
            // 
            // uwagaNazwa
            // 
            this.uwagaNazwa.AutoSize = true;
            this.uwagaNazwa.ForeColor = System.Drawing.Color.Red;
            this.uwagaNazwa.Location = new System.Drawing.Point(14, 480);
            this.uwagaNazwa.Name = "uwagaNazwa";
            this.uwagaNazwa.Size = new System.Drawing.Size(397, 20);
            this.uwagaNazwa.TabIndex = 11;
            this.uwagaNazwa.Text = "*Uwaga! Należy wpisywać cenę za kilogram, litr lub sztukę!";
            // 
            // listaProduktowNazwa
            // 
            this.listaProduktowNazwa.AutoSize = true;
            this.listaProduktowNazwa.Location = new System.Drawing.Point(14, 10);
            this.listaProduktowNazwa.Name = "listaProduktowNazwa";
            this.listaProduktowNazwa.Size = new System.Drawing.Size(115, 20);
            this.listaProduktowNazwa.TabIndex = 12;
            this.listaProduktowNazwa.Text = "Lista produktów";
            // 
            // listaKategoriiNazwa
            // 
            this.listaKategoriiNazwa.AutoSize = true;
            this.listaKategoriiNazwa.Location = new System.Drawing.Point(204, 10);
            this.listaKategoriiNazwa.Name = "listaKategoriiNazwa";
            this.listaKategoriiNazwa.Size = new System.Drawing.Size(102, 20);
            this.listaKategoriiNazwa.TabIndex = 13;
            this.listaKategoriiNazwa.Text = "Lista kategorii";
            // 
            // Form5
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(546, 508);
            this.Controls.Add(this.listaKategoriiNazwa);
            this.Controls.Add(this.listaProduktowNazwa);
            this.Controls.Add(this.uwagaNazwa);
            this.Controls.Add(this.edytuj);
            this.Controls.Add(this.listaKategorii);
            this.Controls.Add(this.kategorieNazwa);
            this.Controls.Add(this.cenaNazwa);
            this.Controls.Add(this.nazwaNazwa);
            this.Controls.Add(this.usun);
            this.Controls.Add(this.dodaj);
            this.Controls.Add(this.kategorie);
            this.Controls.Add(this.cena);
            this.Controls.Add(this.nazwa);
            this.Controls.Add(this.listaProduktow);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "Form5";
            this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
            this.Text = "Produkty";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ListBox listaProduktow;
        private System.Windows.Forms.TextBox nazwa;
        private System.Windows.Forms.TextBox cena;
        private System.Windows.Forms.TextBox kategorie;
        private System.Windows.Forms.Button dodaj;
        private System.Windows.Forms.Button usun;
        private System.Windows.Forms.Label nazwaNazwa;
        private System.Windows.Forms.Label cenaNazwa;
        private System.Windows.Forms.Label kategorieNazwa;
        private System.Windows.Forms.CheckedListBox listaKategorii;
        private System.Windows.Forms.Button edytuj;
        private System.Windows.Forms.Label uwagaNazwa;
        private System.Windows.Forms.Label listaProduktowNazwa;
        private System.Windows.Forms.Label listaKategoriiNazwa;
    }
}