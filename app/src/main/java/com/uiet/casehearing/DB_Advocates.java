package com.uiet.casehearing;

public class DB_Advocates {

    public String advocate,startDate,endDate;

    public DB_Advocates(){}

    public DB_Advocates(String advocate,String startDate,String endDate){
        this.advocate = advocate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getAdvocate(){
        return advocate;
    }

    public String getStartDate() { return startDate;}

    public String getEndDate() { return endDate; }

}


