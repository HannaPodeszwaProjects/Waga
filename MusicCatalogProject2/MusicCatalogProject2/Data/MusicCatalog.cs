using Microsoft.EntityFrameworkCore;
using MusicCatalogProject2.Entities;

namespace MusicCatalogProject2.Data
{
    internal class MusicCatalog : DbContext
    {
        public DbSet<Album> Album { get; set; }
        public DbSet<Song> Song { get; set; }
        public DbSet<Provider> Provider { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseSqlServer("Data Source=(localdb)\\MSSQLLocalDB; Initial Catalog=MusicCatalog");
        }
    }
}
