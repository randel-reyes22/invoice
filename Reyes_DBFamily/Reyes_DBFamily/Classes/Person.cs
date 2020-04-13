namespace Reyes_DBFamily.Classes
{
    class Person
    {
        protected Person() { }

        protected Person(string firstname, string lastname, int age)
        {
            Fistname = firstname;
            Lastname = lastname;
            Age = age;
        }

        protected Person(string firstname, string lastname)
        {
            Fistname = firstname;
            Lastname = lastname;
        }

        public string Fistname { get; }
        public string Lastname { get; }
        public int Age { get; }

    }
}
