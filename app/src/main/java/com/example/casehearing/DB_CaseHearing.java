package com.example.casehearing;

public class DB_CaseHearing {

    private String advocate_name;
    private String case_title;
    private String ndh;
    private String purpose;
    private String last_updated;
    private String case_type;
    private String case_id;
    private String doa;

    public DB_CaseHearing(){}

    public DB_CaseHearing(String case_type,String case_id,String doa,String advocate_name,String case_title,String ndh,String purpose,String last_updated)
    {
        this.case_type = case_type;
        this.case_id = case_id;
        this.doa = doa;
        this.advocate_name = advocate_name;
        this.case_title = case_title;
        this.ndh = ndh;
        this.purpose = purpose;
        this.last_updated = last_updated;
    }

    public String getCase_type(){
        return case_type;
    }

    public String getCase_id(){
        return case_id;
    }

    public String getDoa() { return doa;}

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
