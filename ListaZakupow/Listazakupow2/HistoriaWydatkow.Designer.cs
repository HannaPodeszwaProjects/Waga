namespace Listazakupow2
{
    partial class HistoriaWydatkow
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
            this.miesiac = new System.Windows.Forms.ComboBox();
            this.rok = new System.Windows.Forms.ComboBox();
            this.wynik = new System.Windows.Forms.Label();
            this.miesiacNazwa = new System.Windows.Forms.Label();
            this.rokNazwa = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // miesiac
            // 
            this.miesiac.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.miesiac.FormattingEnabled = true;
            this.miesiac.Location = new System.Drawing.Point(14, 33);
            this.miesiac.Name = "miesiac";
            this.miesiac.Size = new System.Drawing.Size(150, 28);
            this.miesiac.TabIndex = 0;
            this.miesiac.SelectedIndexChanged += new System.EventHandler(this.miesiac_SelectedIndexChanged);
            // 
            // rok
            // 
            this.rok.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.rok.FormattingEnabled = true;
            this.rok.Location = new System.Drawing.Point(179, 33);
            this.rok.Name = "rok";
            this.rok.Size = new System.Drawing.Size(150, 28);
            this.rok.TabIndex = 1;
            this.rok.SelectedIndexChanged += new System.EventHandler(this.rok_SelectedIndexChanged);
            // 
            // wynik
            // 
            this.wynik.AutoSize = true;
            this.wynik.Location = new System.Drawing.Point(14, 78);
            this.wynik.Name = "wynik";
            this.wynik.Size = new System.Drawing.Size(46, 20);
            this.wynik.TabIndex = 2;
            this.wynik.Text = "wynik";
            // 
            // miesiacNazwa
            // 
            this.miesiacNazwa.AutoSize = true;
            this.miesiacNazwa.Location = new System.Drawing.Point(14, 10);
            this.miesiacNazwa.Name = "miesiacNazwa";
            this.miesiacNazwa.Size = new System.Drawing.Size(59, 20);
            this.miesiacNazwa.TabIndex = 13;
            this.miesiacNazwa.Text = "Miesiąc";
            // 
            // rokNazwa
            // 
            this.rokNazwa.AutoSize = true;
            this.rokNazwa.Location = new System.Drawing.Point(179, 10);
            this.rokNazwa.Name = "rokNazwa";
            this.rokNazwa.Size = new System.Drawing.Size(34, 20);
            this.rokNazwa.TabIndex = 14;
            this.rokNazwa.Text = "Rok";
            // 
            // Form7
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(344, 113);
            this.Controls.Add(this.rokNazwa);
            this.Controls.Add(this.miesiacNazwa);
            this.Controls.Add(this.wynik);
            this.Controls.Add(this.rok);
            this.Controls.Add(this.miesiac);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "Form7";
            this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
            this.Text = "Historia zakupów";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ComboBox miesiac;
        private System.Windows.Forms.ComboBox rok;
        private System.Windows.Forms.Label wynik;
        private System.Windows.Forms.Label miesiacNazwa;
        private System.Windows.Forms.Label rokNazwa;
        private System.Windows.Forms.Label nazwaNazwa;
    }
}