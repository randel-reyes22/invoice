using System;
using System.Windows.Forms;
using Reyes_DBFamily.Classes;
using Reyes_DBFamily.Classes.Utils;

namespace Reyes_DBFamily
{
    public partial class AddFamilyMember : Form
    {
        private Family family = new Family();

        public AddFamilyMember()
        {
            InitializeComponent();
        }

        private void btnAdd_Click(object sender, EventArgs e)
        {
            if (!CheckEmptyFields(this))
            {
                FamilyUtility.familyMembers.AddFirst(new FamilyMember(tbFname.Text, tblast.Text, tbRelatiosnhip.Text,
                    tbHometown.Text, Convert.ToInt32(tbAge.Text)));

                switch (family.AddFamilyMember())
                {
                    case 0:
                        MessageBox.Show(@"Family member added", @"Success", MessageBoxButtons.OK, MessageBoxIcon.Information);
                        break;
                    case 1:
                        MessageBox.Show(@"Cant connect to DB", @"Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        break;
                }

                CleanForm(this);

            }
            else
            {
                MessageBox.Show(@"Supply all fields", @"Empty fields", MessageBoxButtons.OK,
                    MessageBoxIcon.Exclamation);
            }
        }

        private bool CheckEmptyFields(Control ctrl)
        {
            foreach (Control c in ctrl.Controls)
            {
                if (c is TextBox)
                {
                    if (string.IsNullOrEmpty(c.Text))
                        return true;
                    CheckEmptyFields(c);
                }
            }

            return false;
        }

        private void CleanForm(Control ctrl)
        {
            foreach (Control c in ctrl.Controls)
            {
                if (c is TextBox) ((TextBox)c).Text = string.Empty;
                CleanForm(c);
            }
        }
    }
}
