using Microsoft.Data.SqlClient;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data;

namespace MusicCatalogProject2.Models
{
    public class Album
    {
        public int id { get; set; }
        public string artistName { get; set; }
        public string title { get; set; }
        public int version { get; set; }
        public int year { get; set; }
        public string provider { get; set; }

        public ObservableCollection<Album> FindFilteredAlbums(string title, string artist, int providerId, int year)
        {
            ObservableCollection<Album> albumList = new ObservableCollection<Album>();

            var con = new SqlConnection("Data Source=(localdb)\\MSSQLLocalDB; Initial Catalog=MusicCatalog");
            SqlCommand cmd;
            if (year != 0)
            {
                cmd = new SqlCommand("Select * From Album a, Provider p Where a.ProviderId = p.Id AND p.ID = @ID AND a.artistName LIKE @ARTIST AND a.title LIKE @TITLE AND a.year = @YEAR;", con);
                cmd.Parameters.AddWithValue("@YEAR", year);
            }
            else
            {
                cmd = new SqlCommand("Select * From Album a, Provider p Where a.ProviderId = p.Id AND p.ID = @ID AND a.artistName LIKE @ARTIST AND a.title LIKE @TITLE;", con);
            }
            cmd.Parameters.AddWithValue("@ID", providerId);
            cmd.Parameters.AddWithValue("@ARTIST", artist + "%");
            cmd.Parameters.AddWithValue("@TITLE", title + "%");
            DataSet dt = new DataSet();
            con.Open();
            SqlDataAdapter sdr = new SqlDataAdapter(cmd);
            sdr.Fill(dt);

            albumList.Clear();
            foreach (DataRow dr in dt.Tables[0].Rows)
            {
                albumList.Add(new Album
                {
                    id = Convert.ToInt32(dr["Id"]),
                    artistName = Convert.ToString(dr["ArtistName"]),
                    title = Convert.ToString(dr["Title"]),
                    version = Convert.ToInt32(dr["Version"]),
                    year = Convert.ToInt32(dr["Year"]),
                    provider = Convert.ToString(dr["Name"])
                });
            }
            con.Close();
            return albumList;
        }
        public ObservableCollection<Album> FindAllAlbums(SortedSet<int> yearsSet, string title, string artist, int providerId)
        {
            ObservableCollection<Album> albumList = new ObservableCollection<Album>();

            var con = new SqlConnection("Data Source=(localdb)\\MSSQLLocalDB; Initial Catalog=MusicCatalog");
            SqlCommand cmd = new SqlCommand("Select * From Album a, Provider p Where a.ProviderId = p.Id AND p.ID = @ID AND a.artistName LIKE @ARTIST AND a.title LIKE @TITLE;", con);
            cmd.Parameters.AddWithValue("@ID", providerId);
            cmd.Parameters.AddWithValue("@ARTIST", artist + "%");
            cmd.Parameters.AddWithValue("@TITLE", title + "%");
            DataSet dt = new DataSet();
            con.Open();
            SqlDataAdapter sdr = new SqlDataAdapter(cmd);
            sdr.Fill(dt);

            foreach (DataRow dr in dt.Tables[0].Rows)
            {
                albumList.Add(new Album
                {
                    id = Convert.ToInt32(dr["Id"]),
                    artistName = Convert.ToString(dr["ArtistName"]),
                    title = Convert.ToString(dr["Title"]),
                    version = Convert.ToInt32(dr["Version"]),
                    year = Convert.ToInt32(dr["Year"]),
                    provider = Convert.ToString(dr["Name"])
                });
                yearsSet.Add(Convert.ToInt32(dr["Year"]));
            }
            con.Close();
            return albumList;
        }

    }
}
