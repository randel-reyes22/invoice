using System;
using System.Data.SqlClient;
using Reyes_DBFamily.Classes.Utils;
using Reyes_DBFamily.ConnectToDB;
using Reyes_DBFamily.Interface;

namespace Reyes_DBFamily.Classes
{
    internal class Family : FamilyUtility, IFamily
    {
        private SqlConnection connection = new SqlConnection(Connect.Link);
        private SqlCommand cmd;
        private SqlDataReader reader;

        public int AddFamilyMember()
        {
            try
            {
                cmd = new SqlCommand();
                connection.Open();
                cmd.Connection = connection;

                cmd.CommandText =
                    "INSERT INTO SmallFam (firstname, lastname, relationship, hometown, age)" +
                    "VALUES (@fname, @lname, @relation, @hometown, @age)";

                if (familyMembers.Count > 0)
                {
                    var member = familyMembers.Last.Value; //get the last added value in the link-list
                    cmd.Parameters.AddWithValue("@fname", member.Fistname);
                    cmd.Parameters.AddWithValue("@lname", member.Lastname);
                    cmd.Parameters.AddWithValue("@relation", member.Relationship);
                    cmd.Parameters.AddWithValue("@hometown", member.Hometown);
                    cmd.Parameters.AddWithValue("@age", member.Age);
                }

                reader = cmd.ExecuteReader();
                reader.Close();
                return 0;
            }

            catch (SqlException e)
            {
                Console.WriteLine(e.ToString());
                return 1;
            }

            finally
            {
                connection.Close();
            }
        }

        public int UpdateFamilyMember()
        {
            try
            {
                cmd = new SqlCommand();
                connection.Open();
                cmd.Connection = connection;

                cmd.CommandText =
                    "UPDATE SmallFam SET firstname = @fname, lastname = @lname, relationship = @relation" +
                    " WHERE family_id = @prime";

                if (updateFamilyMembers.Count > 0)
                {
                    var member = updateFamilyMembers.Last.Value; //get the last added value in the link-list
                    cmd.Parameters.AddWithValue("@prime", Convert.ToInt32(member.ID));
                    cmd.Parameters.AddWithValue("@fname", member.Fistname);
                    cmd.Parameters.AddWithValue("@lname", member.Lastname);
                    cmd.Parameters.AddWithValue("@relation", member.Relationship);
                }

                cmd.ExecuteNonQuery();
                return 0;
            }

            catch (SqlException e)
            {
                Console.WriteLine(e.ToString());
                return 1;
            }

            finally
            {
                connection.Close();
            }
        }

        public void RemoveFamilyMember()
        {
            
        }
    }
}
