package info.sqlite.helper;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Cynthia.Khalil on 7/7/2017.
 */

public class Employee implements IEmployee{

    protected int id;
    protected String name;
    protected String phoneNumber;
    protected String address;
    protected String type;
    protected String eNbr="";
    protected String permissions="";
    protected String branchName="";
    protected String orgName="";

    public void seteNbr(String eNbr) {
        this.eNbr = eNbr;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String geteNbr() {

        return eNbr;
    }

    public String getPermissions() {
        return permissions;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getOrgName() {
        return orgName;
    }



    public Employee()
    {

    }

    public Employee(int id) {
        this.id = id;
    }

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Employee(int id, String name, String phoneNumber, String address, String type) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getEmployeeInfo() {
        return "Employee Info:\n ID: " + id + "\nName: " + name + "\nPhoneNumber: "
                + phoneNumber + "\nAddress: " + address + "\ntype: " + type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (id != employee.id) return false;
        if (type != employee.type) return false;
        if (!name.equals(employee.name)) return false;
        if (!phoneNumber.equals(employee.phoneNumber)) return false;
        return address.equals(employee.address);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + phoneNumber.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    public Employee deserializeData(String str)
    {
        Gson gson = new Gson();
        try {
            HashMap hashMapResponse = new Gson().fromJson(str, HashMap.class);
            Employee emp = new Employee();
            emp.setId(Integer.parseInt((String) hashMapResponse.get("E_ID")));
            emp.setName((String) hashMapResponse.get("E_name"));
            emp.setPhoneNumber((String) hashMapResponse.get("E_PhoneNumber"));
            emp.setAddress((String) hashMapResponse.get("E_Address"));
            emp.setType((String) hashMapResponse.get("E_Type"));
            emp.seteNbr((String) hashMapResponse.get("E_NumberOfEmployee"));
            emp.setPermissions((String) hashMapResponse.get("E_Permissions"));
            emp.setBranchName((String) hashMapResponse.get("Branch_Name"));
            emp.setOrgName((String) hashMapResponse.get("Organization_Name"));
            return emp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
