package info.sqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cynthia.Khalil on 7/6/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    //name of the database
    public static final String DATABASE_NAME = "employee.db";

    //name of the tables
    public static final String TB_USER = "user";
    public static final String TB_EMPLOYEE = "employee";
    public static final String TB_SUPERVISOR = "supervisor";
    public static final String TB_MANAGER = "manager";

    //common column names
    public static final String E_ID = "e_id";


    //USER table column names
    public static final String USER_ID = "user_id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";



    //EMPLOYEE table column names
    public static final String E_NAME = "e_name";
    public static final String E_PHONENUMBER = "e_phonenumber";
    public static final String E_ADDRESS = "e_address";
    public static final String E_TYPE = "e_type";
    //new format
    public static final String E_NUMBEROFEMPLOYEE = "e_numberofemployee";
    public static final String E_PERMISSIONS = "e_permissions";
    public static final String ORGANIZATION_NAME = "organization_name";
    public static final String BRANCH_NAME = "branch_name";

    //USER table create statement
    private static final String CREATE_TB_USER = "CREATE TABLE "
            + TB_USER + "(" + USER_ID + " INTEGER PRIMARY KEY," + USERNAME
            + " NVARCHAR," + PASSWORD + " NVARCHAR" + ")";

    //EMPLOYEE table create statement
    private static final String CREATE_TB_EMPLOYEE = "CREATE TABLE "
            + TB_EMPLOYEE + "(" + E_ID + " INTEGER PRIMARY KEY," + E_NAME + " NVARCHAR,"
            + E_PHONENUMBER + " NVARCHAR" + E_PHONENUMBER + " NVARCHAR," + E_ADDRESS + " NVARCHAR,"
            + E_TYPE + " INTEGER,"+ E_NUMBEROFEMPLOYEE + " NAVCHAR,"+ E_PERMISSIONS + " NAVHCHAR,"+ ORGANIZATION_NAME + " NAVCHAR," + BRANCH_NAME+ " NAVCHAR" + ")";

    //SUPERVISOR table create statement
    private static final String CREATE_TB_SUPERVISOR = "CREATE TABLE "
            + TB_SUPERVISOR + "(" + E_ID + " INTEGER PRIMARY KEY," + E_NUMBEROFEMPLOYEE
            + " INTEGER," + E_PERMISSIONS + " NVARCHAR" + ")";

    //MANAGER table create statement
    private static final String CREATE_TB_MANAGER = "CREATE TABLE "
            + TB_MANAGER + "(" + E_ID + " INTEGER PRIMARY KEY," + ORGANIZATION_NAME
            + " NVARCHAR," + BRANCH_NAME + " NVARCHAR" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TB_USER);
        db.execSQL(CREATE_TB_EMPLOYEE);
        db.execSQL(CREATE_TB_SUPERVISOR);
        db.execSQL(CREATE_TB_MANAGER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TB_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TB_EMPLOYEE);
        db.execSQL("DROP TABLE IF EXISTS " + TB_SUPERVISOR);
        db.execSQL("DROP TABLE IF EXISTS " + TB_MANAGER);

        onCreate(db);
    }

    //CRUD ops

    /*
     * Creating tables
     */
    public long createUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_ID, Integer.toString(user.getId()));
        values.put(USERNAME, user.getUsername());
        values.put(PASSWORD, user.getPassword());

        // insert row
        long user_id = db.insert(TB_USER, null, values);

        return user_id;
    }

    public long createEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(E_ID, employee.getId());
        values.put(E_NAME, employee.getName());
        values.put(E_PHONENUMBER, employee.getPhoneNumber());
        values.put(E_ADDRESS, employee.getAddress());
        values.put(E_TYPE, employee.getType());
        values.put(E_NUMBEROFEMPLOYEE, employee.geteNbr());
        values.put(E_PERMISSIONS, employee.getPermissions());
        values.put(ORGANIZATION_NAME, employee.getOrgName());
        values.put(BRANCH_NAME, employee.getBranchName());

        // insert row
        long e_id = db.insert(TB_EMPLOYEE, null, values);

        return e_id;
    }

    public long createSupervisor(Supervisor supervisor) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(E_ID, supervisor.getId());
        values.put(E_PHONENUMBER, supervisor.getNbrEmployee());
        values.put(E_PERMISSIONS, supervisor.getPermissions());

        // insert row
        long e_id = db.insert(TB_SUPERVISOR, null, values);

        return e_id;
    }

    public long createManager(Manager manager) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(E_ID, manager.getId());
        values.put(ORGANIZATION_NAME, manager.getOrganizationName());
        values.put(BRANCH_NAME, manager.getBranchName());

        // insert row
        long e_id = db.insert(TB_MANAGER, null, values);

        return e_id;
    }

    /*
     * fetching one instance from a table
     */

    public User getUser(long user_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TB_USER + " WHERE "
                + USER_ID + " = " + user_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        User user = new User();
        user.setId(c.getInt(c.getColumnIndex(USER_ID)));
        user.setUsername((c.getString(c.getColumnIndex(USERNAME))));
        user.setPassword(c.getString(c.getColumnIndex(PASSWORD)));

        return user;
    }

    public Employee getEmployee(long e_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TB_EMPLOYEE + " WHERE "
                + E_ID + " = " + e_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Employee e = new Employee();
        e.setId(c.getInt(c.getColumnIndex(E_ID)));
        e.setName((c.getString(c.getColumnIndex(E_NAME))));
        e.setPhoneNumber(c.getString(c.getColumnIndex(E_PHONENUMBER)));
        e.setAddress(c.getString(c.getColumnIndex(E_ADDRESS)));
        e.setType(c.getString(c.getColumnIndex(E_TYPE)));
        e.seteNbr(c.getString(c.getColumnIndex(E_NUMBEROFEMPLOYEE)));
        e.setPermissions(c.getString(c.getColumnIndex(E_PERMISSIONS)));
        e.setOrgName(c.getString(c.getColumnIndex(ORGANIZATION_NAME)));
        e.setBranchName(c.getString(c.getColumnIndex(BRANCH_NAME)));

        return e;
    }

    public Supervisor getSupervisor(long e_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TB_SUPERVISOR + " WHERE "
                + E_ID + " = " + e_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Supervisor sup = new Supervisor();
        sup.setId(c.getInt(c.getColumnIndex(E_ID)));
        sup.setNbrEmployee((c.getInt(c.getColumnIndex(E_NUMBEROFEMPLOYEE))));
        sup.setPermissions(c.getString(c.getColumnIndex(E_PERMISSIONS)));

        return sup;
    }

    public Manager getManager(long e_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TB_MANAGER + " WHERE "
                + E_ID + " = " + e_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Manager man = new Manager();
        man.setId(c.getInt(c.getColumnIndex(E_ID)));
        man.setOrganizationName((c.getString(c.getColumnIndex(ORGANIZATION_NAME))));
        man.setBranchName(c.getString(c.getColumnIndex(BRANCH_NAME)));

        return man;
    }


    /*
     * fetching all instances from a table
     */

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        String selectQuery = "SELECT  * FROM " + TB_USER;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                User user = new User();
                user.setId(c.getInt(c.getColumnIndex(USER_ID)));
                user.setUsername((c.getString(c.getColumnIndex(USERNAME))));
                user.setPassword(c.getString(c.getColumnIndex(PASSWORD)));

                // adding to todo list
                users.add(user);
            } while (c.moveToNext());
        }

        return users;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> emps = new ArrayList<Employee>();
        String selectQuery = "SELECT  * FROM " + TB_EMPLOYEE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Employee e = new Employee();
                e.setId(c.getInt(c.getColumnIndex(E_ID)));
                e.setName((c.getString(c.getColumnIndex(E_NAME))));
                e.setPhoneNumber(c.getString(c.getColumnIndex(E_PHONENUMBER)));
                e.setAddress(c.getString(c.getColumnIndex(E_ADDRESS)));
                e.setType(c.getString(c.getColumnIndex(E_TYPE)));
                e.seteNbr(c.getString(c.getColumnIndex(E_NUMBEROFEMPLOYEE)));
                e.setPermissions(c.getString(c.getColumnIndex(E_PERMISSIONS)));
                e.setOrgName(c.getString(c.getColumnIndex(ORGANIZATION_NAME)));
                e.setBranchName(c.getString(c.getColumnIndex(BRANCH_NAME)));

                // adding to todo list
                emps.add(e);
            } while (c.moveToNext());
        }

        return emps;
    }

    public List<Supervisor> getAllSupervisors() {
        List<Supervisor> sups = new ArrayList<Supervisor>();
        String selectQuery = "SELECT  * FROM " + TB_SUPERVISOR;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Supervisor sup = new Supervisor();
                sup.setId(c.getInt(c.getColumnIndex(E_ID)));
                sup.setNbrEmployee((c.getInt(c.getColumnIndex(E_NUMBEROFEMPLOYEE))));
                sup.setPermissions(c.getString(c.getColumnIndex(E_PERMISSIONS)));

                // adding to todo list
                sups.add(sup);
            } while (c.moveToNext());
        }

        return sups;
    }

    public List<Manager> getAllManagers() {
        List<Manager> mans = new ArrayList<Manager>();
        String selectQuery = "SELECT  * FROM " + TB_MANAGER;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Manager man = new Manager();
                man.setId(c.getInt(c.getColumnIndex(E_ID)));
                man.setOrganizationName((c.getString(c.getColumnIndex(ORGANIZATION_NAME))));
                man.setBranchName(c.getString(c.getColumnIndex(BRANCH_NAME)));

                // adding to todo list
                mans.add(man);
            } while (c.moveToNext());
        }

        return mans;
    }


    /*
     * Updating tables
     */

    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_ID, user.getId());
        values.put(USERNAME, user.getUsername());
        values.put(PASSWORD, user.getPassword());


        // updating row
        return db.update(TB_USER, values, USER_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
    }

    public int updateEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(E_ID, employee.getId());
        values.put(E_NAME, employee.getName());
        values.put(E_PHONENUMBER, employee.getPhoneNumber());
        values.put(E_ADDRESS, employee.getAddress());
        values.put(E_TYPE, employee.getType());
        values.put(E_NUMBEROFEMPLOYEE, employee.geteNbr());
        values.put(E_PERMISSIONS, employee.getPermissions());
        values.put(ORGANIZATION_NAME, employee.getOrgName());
        values.put(BRANCH_NAME, employee.getBranchName());


        // updating row
        return db.update(TB_EMPLOYEE, values, E_ID + " = ?",
                new String[] { String.valueOf(employee.getId()) });
    }

    public int updateSupervisor(Supervisor supervisor) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(E_ID, supervisor.getId());
        values.put(E_PHONENUMBER, supervisor.getNbrEmployee());
        values.put(E_PERMISSIONS, supervisor.getPermissions());


        // updating row
        return db.update(TB_SUPERVISOR, values, E_ID + " = ?",
                new String[] { String.valueOf(supervisor.getId()) });
    }

    public int updateManager(Manager manager) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(E_ID, manager.getId());
        values.put(ORGANIZATION_NAME, manager.getOrganizationName());
        values.put(BRANCH_NAME, manager.getBranchName());


        // updating row
        return db.update(TB_MANAGER, values, E_ID + " = ?",
                new String[] { String.valueOf(manager.getId()) });
    }


    /*
     * Deleting from tables
     */
    public void deleteUser(long user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TB_USER, USER_ID + " = ?",
                new String[] { String.valueOf(user_id) });
    }

    public void deleteEmployee(long emp_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TB_EMPLOYEE, E_ID + " = ?",
                new String[] { String.valueOf(emp_id) });
    }

    public void deleteSupervisor(long e_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TB_SUPERVISOR, E_ID + " = ?",
                new String[] { String.valueOf(e_id) });
    }

    public void deleteManager(long e_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TB_MANAGER, E_ID + " = ?",
                new String[] { String.valueOf(e_id) });
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
