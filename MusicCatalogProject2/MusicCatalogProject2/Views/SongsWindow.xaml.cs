using System.Windows;
using System.Collections.ObjectModel;

namespace MusicCatalogProject2
{
    /// <summary>
    /// Interaction logic for SongsWindow.xaml
    /// </summary>
    public partial class SongsWindow : Window
    {
        private ObservableCollection<Models.Song> songList = new ObservableCollection<Models.Song>();
        private int providerId;
        Models.Album album;
        public SongsWindow(int passedProviderId, Models.Album passedAlbum)
        {
            providerId = passedProviderId;
            album = passedAlbum;
            InitializeComponent();
            title.Text = passedAlbum.title;
            artist.Text = passedAlbum.artistName;
            version.Text = passedAlbum.version.ToString();
            year.Text = passedAlbum.year.ToString();
            provider.Text = passedAlbum.provider;
            FillTable();
        }

        private void FillTable()
        {
            songList = new Models.Song().FindAllSongs(album.id);
            dataGrid.ItemsSource = songList;
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            AlbumsWindow newWindow = new AlbumsWindow(providerId);
            this.Close();
            newWindow.ShowDialog();
        }
    }
}
