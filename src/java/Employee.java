import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
@ManagedBean
@RequestScoped
public class Employee{

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String rooms) {
        this.room = rooms;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }
    int id;
    String fname;
    String lname;
    String division;
    String building;
    String title;
    String room;
    ArrayList usersList;
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();  
    Connection connection;  
    
    
   
    // Used to establish connection
    public Connection getConnection(){
        try{
//           Class.forName("com.mysql.jdbc.Driver");   
           Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection( "jdbc:oracle:thin:@localhost:1521:XE","system","root");
//            connection = DriverManager.getConnection( "jdbc:mysql://localhost:3306/User","root","root");
        }catch(Exception e){
            System.out.println(e);
        }
        return connection;
    }
    // Used to fetch all records
    public ArrayList usersList(){
        try{
            usersList = new ArrayList();
            connection = getConnection();
            Statement stmt=getConnection().createStatement();  
            ResultSet rs=stmt.executeQuery("select * from employee");  
            while(rs.next()){
                Employee employee = new Employee();
                employee.setid(rs.getInt("id"));
                employee.setFname(rs.getString("fname"));
                employee.setLname(rs.getString("lname"));
                employee.setDivision(rs.getString("division"));
                employee.setBuilding(rs.getString("building"));
                employee.setTitle(rs.getString("title"));
                employee.setRoom(rs.getString("room"));
                usersList.add(employee);
            }
            connection.close();        
        }catch(Exception e){
            System.out.println(e);
        }
        return usersList;
    }
//    // Used to search an employee
//    public void find(int oneid){
//        try{
//            connection = getConnection();
//            String query = "Select * from employee where id="+oneid;
//            Statement stmt;
//            stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery(query);
////            ResultSet rs = stmt.executeQuer
////            rs = stmt.executeQuery("Select * from employee where id="+Integer.toBinaryString(oneid));
//            while(rs.next()){
//            Employee emp = new Employee(); 
//                emp.setid(rs.getInt("id"));
//                emp.setFname(rs.getString("fname"));
//                emp.setLname(rs.getString("lname"));
//                emp.setDivision(rs.getString("division"));
//                emp.setBuilding(rs.getString("building"));
//                emp.setTitle(rs.getString("title"));
//                emp.setRoom(rs.getString("room"));
//            usersList.add(emp);
//            }
//            connection.close();
//        }
//        catch(Exception e){
//            System.out.println(e);
//        }
////        return emp;
//    }
    // Used to search an employee
    public ArrayList find(int oneid){
        ArrayList one = new ArrayList();
        try{
            Employee emp = null;
            System.out.println("Find er id: "+oneid);
            connection = getConnection();
            String query = "Select * from employee where id="+String.valueOf(oneid);
            System.out.println(query);
            Statement stmt;
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){ 
//                emp.setid(rs.getInt(1));
//                emp.setFname(rs.getString(2));
//                emp.setLname(rs.getString(3));
//                emp.setDivision(rs.getString(4));
//                emp.setBuilding(rs.getString(5));
//                emp.setTitle(rs.getString(6));
//                emp.setRoom(rs.getString(7));
//                sessionMap.put("emp", emp);

Employee employee = new Employee();
                employee.setid(rs.getInt("id"));
                employee.setFname(rs.getString("fname"));
                employee.setLname(rs.getString("lname"));
                employee.setDivision(rs.getString("division"));
                employee.setBuilding(rs.getString("building"));
                employee.setTitle(rs.getString("title"));
                employee.setRoom(rs.getString("room"));
                one.add(employee);

//            sessionMap.put("oid", rs.getInt("id"));
//            sessionMap.put("ofname", rs.getString(2));
//            sessionMap.put("olname", rs.getString(3));
//            sessionMap.put("odivision", rs.getString(4));
//            sessionMap.put("obuilding", rs.getString(5));
//            sessionMap.put("otitle", rs.getString(6));
//            sessionMap.put("oroom", rs.getString(7));
            }
            rs.close();
            connection.close();
            
        }
        catch(Exception e){
            System.out.println(e);
        }
        return one;
    }
    // Used to save employee record
    public String save(){
        int result = 0;
        try{
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("insert into employee(FNAME,LNAME,DIVISION,BUILDING,TITLE,ROOM)values(?,?,?,?,?,?)");
            stmt.setString(1, fname);
            stmt.setString(2, lname);
            stmt.setString(3, division);
            stmt.setString(4, building);
            stmt.setString(5, title);
            stmt.setString(6, room);
            result = stmt.executeUpdate();
            connection.close();
        }catch(Exception e){
            System.out.println(e);
        }
        if(result !=0)
            return "index.xhtml?faces-redirect=true";
        else return "create.xhtml?faces-redirect=true";
    }
    // Used to fetch record to update
    public String edit(int id){
        Employee emp = null;
        System.out.println(id);
        try{
            connection = getConnection();
            Statement stmt=getConnection().createStatement();  
            ResultSet rs=stmt.executeQuery("select * from employee where id = "+(id));
            rs.next();
            emp = new Employee();
            emp.setid(rs.getInt("id"));
            emp.setFname(rs.getString("fname"));
            emp.setLname(rs.getString("lname"));
            emp.setDivision(rs.getString("division"));
            emp.setBuilding(rs.getString("building"));
            emp.setTitle(rs.getString("title"));
            emp.setRoom(rs.getString("room"));
            sessionMap.put("editUser", emp);
            connection.close();
        }catch(Exception e){
            System.out.println(e);
        }       
        return "/edit.xhtml?faces-redirect=true";
    }
    // Used to update employee record
    public String update(Employee u){
        //int result = 0;
        try{
            connection = getConnection();  
            PreparedStatement stmt=connection.prepareStatement("update employee set fname=?,lname=?,division=?,building=?,title=?,room=? where id=?");  
            stmt.setString(1,u.getFname());  
            stmt.setString(2,u.getLname());  
            stmt.setString(3,u.getDivision());  
            stmt.setString(4,u.getBuilding());  
            stmt.setString(5,u.getTitle());  
            stmt.setString(6,u.getRoom());  
            stmt.setInt(7,u.getid());  
            stmt.executeUpdate();
            connection.close();
        }catch(Exception e){
            System.out.println();
        }
        return "/index.xhtml?faces-redirect=true";      
    }
    // Used to delete employee record
    public void delete(int id){
        try{
            connection = getConnection();  
            PreparedStatement stmt = connection.prepareStatement("delete from employee where id = "+id);  
            stmt.executeUpdate();  
        }catch(Exception e){
            System.out.println(e);
        }
    }

}