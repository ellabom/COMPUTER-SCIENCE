package Package;//NAME: EMMANUELLA EYO
//NSID: eee917
//STUDENT ID: 11291003
//COURSE NUMBER: 270

public class Person{

        private String Pname;
        private String PLname;
        private int SIN;

        public Person(String pname, String p_lname, int sin){
                this.Pname = pname;
                this.PLname = p_lname;
                this.SIN = sin;
        }

        public String getName(){
                return Pname;
        }
        public String get_Lname(){
                return PLname;
        }
        public void set_Pname(String pname){
                this.Pname = pname;
        }
        public void setPLname(String pLname){
                this.PLname = pLname;
        }
        public int getSIN(){
                return SIN;
        }
        public String toString(){
                return Pname + " " + PLname;
        }
        public static void main(String[] args) {
                Person b = new Person("Andrew", "Barber", 123456789 );

                if (b.getName() != "Andrew"){
                        System.out.println("Test Failed for get_lname()");
                }
                if(b.get_Lname() != "Barber"){
                        System.out.println("Test failed for get_Lname");
                }
                b.set_Pname("Grace");
                b.setPLname("Eyo");
                String c = b.toString();
                if (!c.equals("Grace" + " " + "Eyo")){
                        System.out.println("Test failed for setPLName and set_Pname");
                }
                int sin = 123456789;
                int get_sin = b.getSIN();
                if(get_sin != sin){
                        System.out.println("Test failed for getSIN");
                }
                System.out.println("Test completed");
        }
}
