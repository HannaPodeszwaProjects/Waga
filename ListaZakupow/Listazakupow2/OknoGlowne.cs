using MongoDB.Driver;
using System;
using System.Windows.Forms;


namespace Listazakupow2
{
    public partial class OknoGlowne : Form
    {
        public OknoGlowne()
        {
            InitializeComponent();
        }

        protected static IMongoClient _client;
        protected static IMongoDatabase _database;
   

        private void Form1_FormClosing(Object sender, FormClosingEventArgs e)
        {

            System.Text.StringBuilder messageBoxCS = new System.Text.StringBuilder();
            messageBoxCS.AppendFormat("{0} = {1}", "CloseReason", e.CloseReason);
            messageBoxCS.AppendLine();
            messageBoxCS.AppendFormat("{0} = {1}", "Cancel", e.Cancel);
            messageBoxCS.AppendLine();
            MessageBox.Show(messageBoxCS.ToString(), "FormClosing Event");
        }

        private void nowaListaSzablon_Click(object sender, EventArgs e)
        {
            this.Hide();
            var myForm = new Form2(this);
            myForm.Show();
            myForm.Closed += (s, args) => { this.Show(); this.Select(); };
        }

        private void zobaczSzablony_Click(object sender, EventArgs e)
        {
            this.Hide();
            var myForm = new OknoSzablonow(this);
            myForm.Show();
            myForm.Closed += (s, args) => { this.Show(); this.Select(); };
        }

        private void zobaczAktualneListy_Click(object sender, EventArgs e)
        {
            this.Hide();
            var myForm = new OknoList(this);
            myForm.Show();
            myForm.Closed += (s, args) => { this.Show(); this.Select(); };
        }

        private void historia_Click(object sender, EventArgs e)
        {
            this.Hide();
            var myForm = new HistoriaList();
            myForm.Show();
            myForm.Closed += (s, args) => { this.Show(); this.Select(); };
        }

        private void produkty_Click(object sender, EventArgs e)
        {
            this.Hide();
            var myForm = new OknoProduktow();
            myForm.Show();
            myForm.Closed += (s, args) => { this.Show(); this.Select(); };
        }

        private void wydatki_Click(object sender, EventArgs e)
        {
            this.Hide();
            var myForm = new HistoriaWydatkow();
            myForm.Show();
            myForm.Closed += (s, args) => { this.Show(); this.Select(); };
        }

        private void zarzadzaj_Click(object sender, EventArgs e)
        {
            this.Hide();
            var myForm = new ZarzadzajSklepami();
            myForm.Show();
            myForm.Closed += (s, args) => { this.Show(); this.Select(); };
        }
    }
}