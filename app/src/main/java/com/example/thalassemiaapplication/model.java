package com.example.thalassemiaapplication;

public class model {

    String FullName;
    String BloodGroup;
    String EmailId;
    String PhoneNo;
    public model()
    {

    }

    public model(String fullName, String bloodGroup, String emailId, String phoneNo) {
        FullName = fullName;
        BloodGroup = bloodGroup;
        EmailId = emailId;
        PhoneNo = phoneNo;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }
}
