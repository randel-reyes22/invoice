using System;
using System.Data;
using System.Data.SqlClient;
using System.Windows.Forms;
using Reyes_DBFamily.Classes;
using Reyes_DBFamily.Classes.Utils;
using Reyes_DBFamily.ConnectToDB;

namespace Reyes_DBFamily
{
    public partial class Form1 : Form
    {
        private Family family = new Family();

        public Form1()
        {
            InitializeComponent();
        }

        private void LoadEverything()
        {
            var binder = new BindingSource();
            var connection = new SqlConnection(Connect.Link);
            var cmd = new SqlCommand();

            try
            {
                connection.Open();
                cmd.Connection = connection;

                cmd.CommandText = "SELECT * FROM SmallFam";

                var adapter = new SqlDataAdapter(cmd);
                var dataTable = new DataTable();
                adapter.Fill(dataTable);

                dataGridView1.DataSource = binder;
                binder.DataSource = dataTable;
            }
            catch (SqlException a)
            {
                Console.WriteLine(a.ToString());
                MessageBox.Show(@"Cant connect to DB", @"Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }

            catch (FormatException a)
            {
                Console.WriteLine(a.ToString());
            }
            finally
            {
                connection.Close();
            }
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            LoadEverything();
        }

        private void dataGridView1_RowHeaderMouseClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            var selectRow = dataGridView1.Rows[e.RowIndex];

            tbID.Text = selectRow.Cells[0].Value.ToString();
            tbFname.Text = selectRow.Cells[1].Value.ToString();
            tbLastName.Text = selectRow.Cells[2].Value.ToString();
            tbRelatiosnhip.Text = selectRow.Cells[3].Value.ToString();
        }

        private void btnUpdate_Click(object sender, EventArgs e)
        {
            if (!CheckEmptyFields(this))
            {
                FamilyUtility.updateFamilyMembers.AddFirst(new FamilyMember(tbID.Text, tbFname.Text, tbLastName.Text,
                    tbRelatiosnhip.Text));

                switch (family.UpdateFamilyMember())
                {
                    case 0:
                        LoadEverything();
                        MessageBox.Show(@"Family member updated", @"Success", MessageBoxButtons.OK,
                            MessageBoxIcon.Information);
                        break;
                    case 1:
                        MessageBox.Show(@"Cant connect to DB", @"Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        break;
                }

                CleanForm(this);
            }
            else
            {
                MessageBox.Show(@"Invalid Update, select a family member.", @"Failed", MessageBoxButtons.OK,
                    MessageBoxIcon.Information);
            }
        }

        private void btnAdd_Click(object sender, EventArgs e)
        {
            var add = new AddFamilyMember();
            add.ShowDialog();
        }

        private bool CheckEmptyFields(Control ctrl)
        {
            foreach (Control c in ctrl.Controls)
                if (c is TextBox)
                {
                    if (string.IsNullOrEmpty(c.Text))
                    {
                        if (c.Name.Equals("tbSearch"))
                            continue;
                        return true;
                    }

                    CheckEmptyFields(c);
                }

            return false;
        }

        private void CleanForm(Control ctrl)
        {
            foreach (Control c in ctrl.Controls)
            {
                if (c is TextBox) ((TextBox) c).Text = string.Empty;
                CleanForm(c);
            }
        }

        private void Reload_MouseHover(object sender, EventArgs e)
        {
            var tt = new ToolTip();
            tt.SetToolTip(Reload, "Refresh table");
        }

        private void tbSearch_MouseHover(object sender, EventArgs e)
        {
            var tt = new ToolTip();
            tt.SetToolTip(tbSearch, "Search by Firstname or Lastname");
        }

        private void Reload_Click(object sender, EventArgs e)
        {
            LoadEverything();
        }

        private void tbSearch_TextChanged(object sender, EventArgs e)
        {
            var binder = new BindingSource();
            var connection = new SqlConnection(Connect.Link);
            var cmd = new SqlCommand();

            if (string.IsNullOrEmpty(tbSearch.Text))
                Reload_Click(sender, e);
            else
                try
                {
                    connection.Open();
                    cmd.Connection = connection;

                    cmd.CommandText = "SELECT * FROM SmallFam WHERE firstname LIKE @name OR lastname LIKE @name";
                    cmd.Parameters.AddWithValue("@name", @"%" + tbSearch.Text.Trim() + @"%");

                    var adapter = new SqlDataAdapter(cmd);
                    var dataTable = new DataTable();
                    adapter.Fill(dataTable);

                    dataGridView1.DataSource = binder;
                    binder.DataSource = dataTable;
                }
                catch (SqlException a)
                {
                    Console.WriteLine(a.ToString());
                    MessageBox.Show(@"Cant connect to DB", @"Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }

                catch (FormatException a)
                {
                    Console.WriteLine(a.ToString());
                }
                finally
                {
                    connection.Close();
                }
        }
    }
}