namespace Listazakupow2
{
    partial class ZarzadzajSklepami
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
            this.sklepy = new System.Windows.Forms.ComboBox();
            this.dodaj = new System.Windows.Forms.Button();
            this.usun = new System.Windows.Forms.Button();
            this.nazwa = new System.Windows.Forms.TextBox();
            this.sklepyNazwa = new System.Windows.Forms.Label();
            this.nazwaNazwa = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // sklepy
            // 
            this.sklepy.FormattingEnabled = true;
            this.sklepy.Location = new System.Drawing.Point(179, 33);
            this.sklepy.Name = "sklepy";
            this.sklepy.Size = new System.Drawing.Size(150, 28);
            this.sklepy.TabIndex = 0;
            this.sklepy.SelectedIndexChanged += new System.EventHandler(this.sklepy_SelectedIndexChanged);
            // 
            // dodaj
            // 
            this.dodaj.Location = new System.Drawing.Point(14, 71);
            this.dodaj.Name = "dodaj";
            this.dodaj.Size = new System.Drawing.Size(150, 29);
            this.dodaj.TabIndex = 1;
            this.dodaj.Text = "Dodaj";
            this.dodaj.UseVisualStyleBackColor = true;
            this.dodaj.Click += new System.EventHandler(this.dodaj_Click);
            // 
            // usun
            // 
            this.usun.Location = new System.Drawing.Point(179, 71);
            this.usun.Name = "usun";
            this.usun.Size = new System.Drawing.Size(150, 29);
            this.usun.TabIndex = 2;
            this.usun.Text = "Usuń";
            this.usun.UseVisualStyleBackColor = true;
            this.usun.Click += new System.EventHandler(this.usun_Click);
            // 
            // nazwa
            // 
            this.nazwa.Location = new System.Drawing.Point(14, 33);
            this.nazwa.Name = "nazwa";
            this.nazwa.Size = new System.Drawing.Size(150, 27);
            this.nazwa.TabIndex = 3;
            // 
            // sklepyNazwa
            // 
            this.sklepyNazwa.AutoSize = true;
            this.sklepyNazwa.Location = new System.Drawing.Point(179, 10);
            this.sklepyNazwa.Name = "sklepyNazwa";
            this.sklepyNazwa.Size = new System.Drawing.Size(45, 20);
            this.sklepyNazwa.TabIndex = 14;
            this.sklepyNazwa.Text = "Sklep";
            // 
            // nazwaNazwa
            // 
            this.nazwaNazwa.AutoSize = true;
            this.nazwaNazwa.Location = new System.Drawing.Point(14, 10);
            this.nazwaNazwa.Name = "nazwaNazwa";
            this.nazwaNazwa.Size = new System.Drawing.Size(54, 20);
            this.nazwaNazwa.TabIndex = 15;
            this.nazwaNazwa.Text = "Nazwa";
            // 
            // ZarzadzajSklepami
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(342, 112);
            this.Controls.Add(this.nazwaNazwa);
            this.Controls.Add(this.sklepyNazwa);
            this.Controls.Add(this.nazwa);
            this.Controls.Add(this.usun);
            this.Controls.Add(this.dodaj);
            this.Controls.Add(this.sklepy);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "ZarzadzajSklepami";
            this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
            this.Text = "Zarządzanie sklepami";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ComboBox sklepy;
        private System.Windows.Forms.Button dodaj;
        private System.Windows.Forms.Button usun;
        private System.Windows.Forms.TextBox nazwa;
        private System.Windows.Forms.Label sklepyNazwa;
        private System.Windows.Forms.Label nazwaNazwa;
    }
}