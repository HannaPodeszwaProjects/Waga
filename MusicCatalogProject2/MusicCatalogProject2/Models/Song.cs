using Microsoft.Data.SqlClient;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MusicCatalogProject2.Models
{
    internal class Song
    {
        public int Id { get; set; }
        public string ArtistName { get; set; }
        public string Title { get; set; }
        public int Year { get; set; }
        public int Duration { get; set; }
        public int AlbumId { get; set; }

        public ObservableCollection<Song> FindAllSongs(int albumId)
        {
            ObservableCollection<Song> songList = new ObservableCollection<Song>();
            var con = new SqlConnection("Data Source=(localdb)\\MSSQLLocalDB; Initial Catalog=MusicCatalog");

            SqlCommand cmd = new SqlCommand("Select s.Id, s.ArtistName, s.Title, s.Year, s.Duration, s.AlbumId From Album a, Song s Where a.Id = s.AlbumId AND a.Id=@ID;", con);

            cmd.Parameters.AddWithValue("@ID", albumId);
            DataSet dt = new DataSet();
            con.Open();
            SqlDataAdapter sdr = new SqlDataAdapter(cmd);
            sdr.Fill(dt);


            foreach (DataRow dr in dt.Tables[0].Rows)
            {
                songList.Add(new Song
                {
                    Id = Convert.ToInt32(dr["Id"]),
                    ArtistName = Convert.ToString(dr["ArtistName"]),
                    Title = Convert.ToString(dr["Title"]),
                    Year = Convert.ToInt32(dr["Year"]),
                    Duration = Convert.ToInt32(dr["Duration"]),
                    AlbumId = Convert.ToInt32(dr["AlbumId"])
                });
            }
            con.Close();
            return songList;
        }
    }
}
