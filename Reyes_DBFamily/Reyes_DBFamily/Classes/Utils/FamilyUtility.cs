using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Reyes_DBFamily.Classes.Utils
{
    class FamilyUtility
    {
        protected internal static LinkedList<FamilyMember> familyMembers = new LinkedList<FamilyMember>();
        protected internal static LinkedList<FamilyMember> updateFamilyMembers = new LinkedList<FamilyMember>(); 
    }
}
