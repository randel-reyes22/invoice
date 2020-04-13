namespace Reyes_DBFamily.Classes
{
    internal class FamilyMember : Person
    {
        public FamilyMember() { }

        public FamilyMember(string firstname, string lastname, string relationship, string hometown, int age) : base(firstname, lastname, age)
        {
            Relationship = relationship;
            Hometown = hometown;
        }

        public FamilyMember(string id, string firstname, string lastname, string relationship) : base(firstname, lastname)
        {
            ID = id;
            Relationship = relationship;
        }

        public string Relationship { get; }
        public string Hometown { get; }
        public string ID { get; }

    }
}