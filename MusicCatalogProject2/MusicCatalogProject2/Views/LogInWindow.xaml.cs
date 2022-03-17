using Microsoft.Data.SqlClient;
using System;
using System.Data;
using System.Windows;
using System.Windows.Controls;

namespace MusicCatalogProject2
{
    /// <summary>
    /// Interaction logic for LogInWindow.xaml
    /// </summary>
    public partial class LogInWindow : Window
    {
        private string login, password;
        public LogInWindow()
        {
            InitializeComponent();
        }

        private void LogInButtonClick(object sender, RoutedEventArgs e)
        {
            if (login != null && password != null)
            {
                DataSet result = new Models.Provider().findProvider(login);

                if (result.Tables[0].Rows.Count != 0)
                {
                    string hash = Convert.ToString(result.Tables[0].Rows[0]["Password"]);
                    if (BCrypt.Net.BCrypt.Verify(password, hash))
                    {
                        DataRow dr = result.Tables[0].Rows[0];

                        AlbumsWindow newWindow = new AlbumsWindow(Convert.ToInt32(dr["Id"]));
                        this.Close();
                        newWindow.ShowDialog();
                    }
                    else
                    {
                        PrepareAlert("Incorrect login or password", "Incorrect data");
                    }
                }
                else
                {
                    PrepareAlert("Incorrect login or password", "Incorrect data");
                }
            }
            else
            {
                PrepareAlert("Enter login and password", "Incorrect data");
            }
        }

        private void PasswordChanged(object sender, RoutedEventArgs e)
        {
            password = (sender as PasswordBox).Password;
        }

        private void LoginChanged(object sender, TextChangedEventArgs e)
        {
            login = (sender as TextBox).Text;
        }

        private void PrepareAlert(string text, string title)
        {
            MessageBoxButton button = MessageBoxButton.OK;
            MessageBoxImage icon = MessageBoxImage.Error;
            MessageBox.Show(text, title, button, icon, MessageBoxResult.OK);
        }
    }
}
