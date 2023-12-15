//NAME: EMMANUELLA EYO
//NSID: eee917
//STUDENT ID: 11291003
//COURSE NUMBER


public class BasicManager{

    private String name;
    private String l_name;

    public BasicManager(String name, String l_name){

        this.name = name;
        this.l_name = l_name;
    }

    public String getname(){
        return name;
    }
    public String get_lname(){
        return l_name;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setL_name(String l_name){
        this.l_name = l_name;
    }
    public String toString(){
        return name + " " + l_name;
    }

    public static void main(String[] args) {
        BasicManager b = new BasicManager("Andrew", "Barber");

        if (b.get_lname() != "Barber"){
            System.out.println("Test Failed for get_lname()");
        }
        if(b.getname() != "Andrew"){
            System.out.println("Test failed for get_name()");
        }
        b.setName("Grace");
        String x = b.toString();
        if (!x.equals("Grace"+" "+"Barber")){
            System.out.println("Test failed for setName");
        }
        b.setL_name("Eyo");
        String c = b.toString();
        if (!c.equals("Grace" + " " + "Eyo")){
            System.out.println("Test failed for setL_Name");
        }
        System.out.println("Test completed");
    }

}


