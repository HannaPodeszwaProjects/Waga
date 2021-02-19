namespace Listazakupow2
{
    partial class ZatwierdzanieListy
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
            this.pytanieNazwa = new System.Windows.Forms.Label();
            this.cena = new System.Windows.Forms.TextBox();
            this.zatwierdz = new System.Windows.Forms.Button();
            this.anuluj = new System.Windows.Forms.Button();
            this.cenaNazwa = new System.Windows.Forms.Label();
            this.sklepy = new System.Windows.Forms.ComboBox();
            this.sklepNazwa = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // pytanieNazwa
            // 
            this.pytanieNazwa.AutoSize = true;
            this.pytanieNazwa.Location = new System.Drawing.Point(14, 10);
            this.pytanieNazwa.Name = "pytanieNazwa";
            this.pytanieNazwa.Size = new System.Drawing.Size(196, 20);
            this.pytanieNazwa.TabIndex = 0;
            this.pytanieNazwa.Text = "Czy chcesz zatwierdzić listę?";
            // 
            // cena
            // 
            this.cena.Location = new System.Drawing.Point(129, 42);
            this.cena.Name = "cena";
            this.cena.Size = new System.Drawing.Size(100, 27);
            this.cena.TabIndex = 1;
            // 
            // zatwierdz
            // 
            this.zatwierdz.Location = new System.Drawing.Point(14, 127);
            this.zatwierdz.Name = "zatwierdz";
            this.zatwierdz.Size = new System.Drawing.Size(100, 29);
            this.zatwierdz.TabIndex = 2;
            this.zatwierdz.Text = "Zatwierdź";
            this.zatwierdz.UseVisualStyleBackColor = true;
            this.zatwierdz.Click += new System.EventHandler(this.zatwierdz_Click);
            // 
            // anuluj
            // 
            this.anuluj.Location = new System.Drawing.Point(129, 127);
            this.anuluj.Name = "anuluj";
            this.anuluj.Size = new System.Drawing.Size(100, 29);
            this.anuluj.TabIndex = 3;
            this.anuluj.Text = "Anuluj";
            this.anuluj.UseVisualStyleBackColor = true;
            this.anuluj.Click += new System.EventHandler(this.anuluj_Click);
            // 
            // cenaNazwa
            // 
            this.cenaNazwa.AutoSize = true;
            this.cenaNazwa.Location = new System.Drawing.Point(14, 45);
            this.cenaNazwa.Name = "cenaNazwa";
            this.cenaNazwa.Size = new System.Drawing.Size(97, 20);
            this.cenaNazwa.TabIndex = 4;
            this.cenaNazwa.Text = "Cena koszyka";
            // 
            // sklepy
            // 
            this.sklepy.FormattingEnabled = true;
            this.sklepy.Location = new System.Drawing.Point(129, 80);
            this.sklepy.Name = "sklepy";
            this.sklepy.Size = new System.Drawing.Size(100, 28);
            this.sklepy.TabIndex = 5;
            this.sklepy.SelectedIndexChanged += new System.EventHandler(this.sklepy_SelectedIndexChanged);
            // 
            // sklepNazwa
            // 
            this.sklepNazwa.AutoSize = true;
            this.sklepNazwa.Location = new System.Drawing.Point(14, 80);
            this.sklepNazwa.Name = "sklepNazwa";
            this.sklepNazwa.Size = new System.Drawing.Size(45, 20);
            this.sklepNazwa.TabIndex = 6;
            this.sklepNazwa.Text = "Sklep";
            // 
            // Form6
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(242, 170);
            this.Controls.Add(this.sklepNazwa);
            this.Controls.Add(this.sklepy);
            this.Controls.Add(this.cenaNazwa);
            this.Controls.Add(this.anuluj);
            this.Controls.Add(this.zatwierdz);
            this.Controls.Add(this.cena);
            this.Controls.Add(this.pytanieNazwa);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "Form6";
            this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
            this.Text = "Zatwierdzanie listy";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label pytanieNazwa;
        private System.Windows.Forms.TextBox cena;
        private System.Windows.Forms.Button zatwierdz;
        private System.Windows.Forms.Button anuluj;
        private System.Windows.Forms.Label cenaNazwa;
        private System.Windows.Forms.ComboBox sklepy;
        private System.Windows.Forms.Label sklepNazwa;
    }
}