package com.example.casehearing;

public class DB_CaseHearing {

    private String advocate_name;
    private String case_title;
    private String ndh;
    private String purpose;
    private String last_updated;

    public DB_CaseHearing(){}

    public DB_CaseHearing(String advocate_name,String case_title,String ndh,String purpose,String last_updated)
    {
        this.advocate_name = advocate_name;
        this.case_title = case_title;
        this.ndh = ndh;
        this.purpose = purpose;
        this.last_updated = last_updated;
    }

    public String getAdvocate_name(){
        return advocate_name;
    }

    public String getCase_title(){
        return case_title;
    }

    public String getNdh(){
    return ndh;
    }

    public String getPurpose(){
        return purpose;
    }

    public String getLast_updated(){
        return last_updated;
    }
}
