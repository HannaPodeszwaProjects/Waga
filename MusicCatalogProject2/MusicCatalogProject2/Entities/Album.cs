using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MusicCatalogProject2.Entities
{
    internal class Album
    {
        [Key]
        public int Id { get; set; }
        public string ArtistName { get; set; }
        public string Title { get; set; }
        public int Version { get; set; }
        public int Year { get; set; }
        public int ProviderId { get; set; }
    }
}
