namespace Listazakupow2
{
    partial class OknoGlowne
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
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
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.nowaListaSzablon = new System.Windows.Forms.Button();
            this.zobaczSzablony = new System.Windows.Forms.Button();
            this.zobaczAktualneListy = new System.Windows.Forms.Button();
            this.produkty = new System.Windows.Forms.Button();
            this.historia = new System.Windows.Forms.Button();
            this.tytul = new System.Windows.Forms.Label();
            this.wydatki = new System.Windows.Forms.Button();
            this.zarzadzaj = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // nowaListaSzablon
            // 
            this.nowaListaSzablon.Location = new System.Drawing.Point(20, 119);
            this.nowaListaSzablon.Name = "nowaListaSzablon";
            this.nowaListaSzablon.Size = new System.Drawing.Size(214, 29);
            this.nowaListaSzablon.TabIndex = 7;
            this.nowaListaSzablon.Text = "Utwórz listę / szablon";
            this.nowaListaSzablon.UseVisualStyleBackColor = true;
            this.nowaListaSzablon.Click += new System.EventHandler(this.nowaListaSzablon_Click);
            // 
            // zobaczSzablony
            // 
            this.zobaczSzablony.Location = new System.Drawing.Point(20, 81);
            this.zobaczSzablony.Name = "zobaczSzablony";
            this.zobaczSzablony.Size = new System.Drawing.Size(214, 29);
            this.zobaczSzablony.TabIndex = 10;
            this.zobaczSzablony.Text = "Zobacz szablony";
            this.zobaczSzablony.UseVisualStyleBackColor = true;
            this.zobaczSzablony.Click += new System.EventHandler(this.zobaczSzablony_Click);
            // 
            // zobaczAktualneListy
            // 
            this.zobaczAktualneListy.Location = new System.Drawing.Point(20, 43);
            this.zobaczAktualneListy.Name = "zobaczAktualneListy";
            this.zobaczAktualneListy.Size = new System.Drawing.Size(214, 29);
            this.zobaczAktualneListy.TabIndex = 10;
            this.zobaczAktualneListy.Text = "Zobacz aktualne listy";
            this.zobaczAktualneListy.UseVisualStyleBackColor = true;
            this.zobaczAktualneListy.Click += new System.EventHandler(this.zobaczAktualneListy_Click);
            // 
            // produkty
            // 
            this.produkty.Location = new System.Drawing.Point(20, 157);
            this.produkty.Name = "produkty";
            this.produkty.Size = new System.Drawing.Size(214, 29);
            this.produkty.TabIndex = 11;
            this.produkty.Text = "Zarządzaj produktami";
            this.produkty.UseVisualStyleBackColor = true;
            this.produkty.Click += new System.EventHandler(this.produkty_Click);
            // 
            // historia
            // 
            this.historia.Location = new System.Drawing.Point(20, 271);
            this.historia.Name = "historia";
            this.historia.Size = new System.Drawing.Size(214, 29);
            this.historia.TabIndex = 30;
            this.historia.Text = "Historia zrealizowanych list";
            this.historia.UseVisualStyleBackColor = true;
            this.historia.Click += new System.EventHandler(this.historia_Click);
            // 
            // tytul
            // 
            this.tytul.AutoSize = true;
            this.tytul.Font = new System.Drawing.Font("Segoe UI", 13.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.tytul.Location = new System.Drawing.Point(37, 9);
            this.tytul.Name = "tytul";
            this.tytul.Size = new System.Drawing.Size(181, 31);
            this.tytul.TabIndex = 13;
            this.tytul.Text = "LISTA ZAKUPÓW";
            // 
            // wydatki
            // 
            this.wydatki.Location = new System.Drawing.Point(20, 233);
            this.wydatki.Name = "wydatki";
            this.wydatki.Size = new System.Drawing.Size(214, 29);
            this.wydatki.TabIndex = 14;
            this.wydatki.Text = "Historia wydatków";
            this.wydatki.UseVisualStyleBackColor = true;
            this.wydatki.Click += new System.EventHandler(this.wydatki_Click);
            // 
            // zarzadzaj
            // 
            this.zarzadzaj.Location = new System.Drawing.Point(20, 195);
            this.zarzadzaj.Name = "zarzadzaj";
            this.zarzadzaj.Size = new System.Drawing.Size(214, 29);
            this.zarzadzaj.TabIndex = 15;
            this.zarzadzaj.Text = "Zarządzaj sklepami";
            this.zarzadzaj.UseVisualStyleBackColor = true;
            this.zarzadzaj.Click += new System.EventHandler(this.zarzadzaj_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(254, 318);
            this.Controls.Add(this.zarzadzaj);
            this.Controls.Add(this.wydatki);
            this.Controls.Add(this.tytul);
            this.Controls.Add(this.historia);
            this.Controls.Add(this.produkty);
            this.Controls.Add(this.zobaczAktualneListy);
            this.Controls.Add(this.zobaczSzablony);
            this.Controls.Add(this.nowaListaSzablon);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "Form1";
            this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
            this.Text = "Lista zakupów";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.Button nowaListaSzablon;
        private System.Windows.Forms.Button zobaczSzablony;
        private System.Windows.Forms.Button zobaczAktualneListy;
        private System.Windows.Forms.Button produkty;
        private System.Windows.Forms.Button historia;
        private System.Windows.Forms.Label tytul;
        private System.Windows.Forms.Button wydatki;
        private System.Windows.Forms.Button zarzadzaj;
    }
}

