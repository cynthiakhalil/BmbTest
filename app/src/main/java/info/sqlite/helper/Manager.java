package info.sqlite.helper;

/**
 * Created by Cynthia.Khalil on 7/7/2017.
 */

public class Manager extends Employee {

    protected String organizationName;
    protected String branchName;

    public Manager()
    {

    }

    public Manager(int id, String organizationName, String branchName) {
        super(id);
        this.organizationName = organizationName;
        this.branchName = branchName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getBranchName() {
        return branchName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Manager manager = (Manager) o;

        if (!organizationName.equals(manager.organizationName)) return false;
        return branchName.equals(manager.branchName);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + organizationName.hashCode();
        result = 31 * result + branchName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "organizationName='" + organizationName + '\'' +
                ", branchName='" + branchName + '\'' +
                '}';
    }
}
