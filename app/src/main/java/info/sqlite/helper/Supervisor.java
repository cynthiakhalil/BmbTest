package info.sqlite.helper;

/**
 * Created by Cynthia.Khalil on 7/7/2017.
 */

public class Supervisor extends Employee {

    protected int nbrEmployee;
    protected String permissions;

    public Supervisor()
    {

    }

    public Supervisor(int id, int nbrEmployee, String permissions) {
        super(id);
        this.nbrEmployee = nbrEmployee;
        this.permissions = permissions;
    }

    public void setNbrEmployee(int nbrEmployee) {
        this.nbrEmployee = nbrEmployee;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public int getNbrEmployee() {
        return nbrEmployee;
    }

    public String getPermissions() {
        return permissions;
    }

    @Override
    public String toString() {
        return "Supervisor{" +
                "nbrEmployee=" + nbrEmployee +
                ", permissions='" + permissions + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Supervisor that = (Supervisor) o;

        if (nbrEmployee != that.nbrEmployee) return false;
        return permissions.equals(that.permissions);

    }

    @Override
    public int hashCode() {
        int result = nbrEmployee;
        result = 31 * result + permissions.hashCode();
        return result;
    }
}
