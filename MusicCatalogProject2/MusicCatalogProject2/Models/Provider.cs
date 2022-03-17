using Microsoft.Data.SqlClient;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MusicCatalogProject2.Models
{
    internal class Provider
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Login { get; set; }
        public string Password { get; set; }

        public DataSet findProvider(string login)
        {
            var con = new SqlConnection("Data Source=(localdb)\\MSSQLLocalDB; Initial Catalog=MusicCatalog");

            SqlCommand cmd = new SqlCommand("Select * From Provider p Where p.Login=@LOGIN;", con);
            cmd.Parameters.AddWithValue("@LOGIN", login);
            DataSet dt = new DataSet();
            con.Open();
            SqlDataAdapter sdr = new SqlDataAdapter(cmd);
            sdr.Fill(dt);
            con.Close();

            return dt;
        }
    }
}
