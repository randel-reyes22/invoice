using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Reyes_DBFamily.Interface
{
    interface IFamily
    {
        int AddFamilyMember();
        int UpdateFamilyMember();
        void RemoveFamilyMember();

    }
}
