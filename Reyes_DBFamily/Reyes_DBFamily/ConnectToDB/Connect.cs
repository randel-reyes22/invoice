using System.Configuration;

namespace Reyes_DBFamily.ConnectToDB
{
    class Connect
    {
        public static string Link { get; } = ConfigurationManager.ConnectionStrings["MyConnection"].ConnectionString;
    }
}
