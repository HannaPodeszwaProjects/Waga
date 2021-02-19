using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Diagnostics;
using System.IO;

namespace Listazakupow2
{
    static class Program
    {
        /// <summary>
        ///  The \ entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Process.Start(@"..\..\..\..\Baza danych\Wlaczanie_bazy_danych_odp_z_prog.bat");
            Application.SetHighDpiMode(HighDpiMode.SystemAware);
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new OknoGlowne());
        }
    }
}