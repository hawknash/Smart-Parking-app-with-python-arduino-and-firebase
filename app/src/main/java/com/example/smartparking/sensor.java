package com.example.smartparking;

public class sensor {
    String D0;
    String D1;
    String D2;
    String D3;
    String D4;
    String D5;

    public sensor (){};
    public sensor(String d0,String d1,String d2,String d3,String d4,String d5){
        this.D0=d0;
        this.D1=d1;
        this.D2=d2;
        this.D3=d3;
        this.D4=d4;
        this.D5=d5;
    }


    public String getD0() {
        return D0;
    }

    public String getD1() {
        return D1;
    }

    public String getD2() {
        return D2;
    }

    public String getD3() {
        return D3;
    }

    public String getD4() {
        return D4;
    }

    public String getD5() {
        return D5;
    }
}


