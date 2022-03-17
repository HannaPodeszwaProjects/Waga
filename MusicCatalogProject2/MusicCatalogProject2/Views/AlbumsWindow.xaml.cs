using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Windows;
using System.Windows.Controls;

namespace MusicCatalogProject2
{
    /// <summary>
    /// Interaction logic for AlbumsWindow.xaml
    /// </summary>
    public partial class AlbumsWindow : Window
    {
        private int providerId;
        private string artist = "";
        private string title = "";
        private int year;
        private ObservableCollection<Models.Album> albumList = new ObservableCollection<Models.Album>();

        public AlbumsWindow(int passedId)
        {
            providerId = passedId;

            InitializeComponent();
            FillTable();
        }

        private void FillTable()
        {
            SortedSet<int> yearsSet = new SortedSet<int>();
            albumList = new Models.Album().FindAllAlbums(yearsSet, title, artist, providerId);
            dataGrid.ItemsSource = albumList;

            comboBox.Items.Clear();
            comboBox.Items.Add("All");
            IEnumerable<int> ie = yearsSet.Reverse();
            foreach (int year in ie)
            {
                comboBox.Items.Add(Convert.ToString(year));
            }
        }

        private void FilterTable()
        {
            albumList = new Models.Album().FindFilteredAlbums(title, artist, providerId, year);
            dataGrid.ItemsSource = albumList;
        }

        private void DataGrid_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {

            DataGrid dataGrid = sender as DataGrid;
            var selectedaAlbum = albumList[dataGrid.SelectedIndex];

            SongsWindow newWindow = new SongsWindow(providerId, selectedaAlbum);
            this.Close();
            newWindow.ShowDialog();

        }

        private void artistTextBoxChanged(object sender, TextChangedEventArgs e)
        {
            artist = (sender as TextBox).Text;
            FilterTable();
        }

        private void titleTextBoxChanged(object sender, TextChangedEventArgs e)
        {
            title = (sender as TextBox).Text;
            FilterTable();
        }

        private void yearComboBoxChanged(object sender, SelectionChangedEventArgs e)
        {
            string selectedYear = (sender as ComboBox).SelectedValue.ToString();
            if (selectedYear.Equals("All"))
            {
                year = 0;
            }
            else
            {
                year = Int32.Parse(selectedYear);
            }
            FilterTable();
        }

        private void LogOutButtonClick(object sender, RoutedEventArgs e)
        {
            LogInWindow newWindow = new LogInWindow();
            this.Close();
            newWindow.ShowDialog();
        }
    }
}
