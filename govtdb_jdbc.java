//STEP 1. Import required packages

import java.sql.*;
import java.util.*;

public class govtdb_jdbc
{

    //STEP 2a: Set JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/govtdb?useSSL=false";

    //  Database Credentials
    static final String USER = "samakshd";
    static final String PASS = "Sam@1234";

    public static void main(String[] args)
    {
        Statement stmt = null;
        Connection conn = null;

        try
        {
            //STEP 2b: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.print("Establishing Connection to the Database....\n");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.print("Creating Query....\n\n");
            stmt = conn.createStatement();

            //String sql;
            Scanner sc = new Scanner(System.in);
            System.out.print("\033[H\033[2J");
            while (true) {

                System.out.print("GOVERNMENT DATABASE SYSTEM\n\n");

                System.out.print("1. ADD A CITIZEN\n");
                System.out.print("2. REMOVE A CITIZEN\n");
                System.out.print("3. ADD PHONE NUMBER\n");
                System.out.print("4. UPDATE QUOTA\n");
                System.out.print("5. SEARCH CITIZEN BY CITIZEN ID\n");
                System.out.print("0. EXIT\n\n");

                System.out.print("SELECT OPERATION ID: ");

                int choice = Integer.valueOf(sc.nextLine());

                if (choice == 0)
                    break;
                else if (choice == 1) {
                    System.out.print("Enter Citizen ID: "); int citizen_id = Integer.valueOf(sc.nextLine());
                    System.out.print("Enter Citizen Name: "); String citizen_name = sc.nextLine();
                    System.out.print("Enter Citizen Aadhar Number: "); int aadhar_no = Integer.valueOf(sc.nextLine());
                    System.out.print("Enter Citizen Phone Number: "); String phone_no = sc.nextLine();
                    System.out.print("Enter Citizen Address: "); String addr = sc.nextLine();
                    System.out.print("Enter Citizen Date Of Birth (YYYY-MM-DD): "); String dob = sc.nextLine();
                    System.out.print("Enter Citizen quota(if any, else enter 0): "); int quota = Integer.valueOf(sc.nextLine());

                    String query1 = "select * from CITIZEN_DETAILS where citizen_id = " + citizen_id + ";";
                    ResultSet rs1 = stmt.executeQuery(query1);
                    if(rs1.next()) {
                        System.out.print("\033[H\033[2J");
                        System.out.print("\nUnable to Add Record. Citizen ID Entered Already Exists in Database\n\n");
                        continue;
                    }
                    rs1.close();
                    
                    String query2 = "select * from CITIZEN_DETAILS where aadhar_no = " + aadhar_no + ";";
                    ResultSet rs2 = stmt.executeQuery(query2);
                    if(rs2.next()) {
                        System.out.print("\033[H\033[2J");
                        System.out.print("\nUnable to Add Record. Citizen Aadhar Number Entered Already Exists in Database\n\n");
                        continue;
                    }
                    rs2.close();

                    if(quota!=0) {
                        String query3 = "select * from QUOTA_DETAILS where quota_id = " + quota + ";";
                        ResultSet rs3 = stmt.executeQuery(query3);
                        if(!rs3.next()) {
                            System.out.print("\033[H\033[2J");
                            System.out.print("\nUnable to Add Record. No such Quota ID Exist\n\n");
                            continue;
                        }
                        rs3.close();
                    }
                    String citizen_details_query = "insert into CITIZEN_DETAILS values ( " + citizen_id + ", '" + citizen_name + "' ," + aadhar_no + ", '" + addr + "', '" + dob + "', ";
                    if(quota !=0) citizen_details_query = citizen_details_query +  + quota + ");";
                    else citizen_details_query = citizen_details_query + "NULL );";
                    stmt.executeUpdate(citizen_details_query);
                    
                    int phone_id = 0;

                    String query4 = "select * from PHONE_RECORDS;";
                    ResultSet rs4 = stmt.executeQuery(query4);

                    while(rs4.next()) {
                        phone_id = Integer.valueOf(rs4.getString("phone_record_id")); 
                    }
                    rs4.close();

                    phone_id++;

                    String phone_records_query = "insert into PHONE_RECORDS values ( " + phone_id + "," + citizen_id + ", '" + phone_no + "');";
                    stmt.executeUpdate(phone_records_query);

                    System.out.print("\033[H\033[2J");
                    System.out.print("Successfully Added New Citizen Record\n\n");
                }
                else if(choice == 2) {
                    System.out.print("Enter Citizen ID: "); int citizen_id = Integer.valueOf(sc.nextLine());

                    String query1 = "select * from CITIZEN_DETAILS where citizen_id = " + citizen_id + ";";
                    ResultSet rs1 = stmt.executeQuery(query1);
                    if(!rs1.next()) {
                        System.out.print("\033[H\033[2J");
                        System.out.print("Unable to Delete Record. Citizen ID entered does not exist in Database\n\n");
                        continue;
                    }
                    rs1.close();

                    String phone_record_query = "delete from PHONE_RECORDS where citi_id = " + citizen_id + ";";
                    stmt.executeUpdate(phone_record_query);

                    String citizen_details_query = "delete from CITIZEN_DETAILS where citizen_id = " + citizen_id + ";";
                    stmt.executeUpdate(citizen_details_query);

                    System.out.print("\033[H\033[2J");
                    System.out.print("Successfully Deleted Citizen Record\n\n");
                }
                else if(choice == 3) {

                    System.out.print("Enter Citizen ID: "); int citizen_id = Integer.valueOf(sc.nextLine());

                    String query1 = "select * from CITIZEN_DETAILS where citizen_id = " + citizen_id + ";";
                    ResultSet rs1 = stmt.executeQuery(query1);
                    if(!rs1.next()) {
                        System.out.print("\033[H\033[2J");
                        System.out.print("Unable to Add Phone Number. Citizen ID entered does not exist in Database\n\n");
                        continue;
                    }
                    rs1.close();

                    System.out.print("Enter New Phone Number: "); String phone_no = sc.nextLine();

                    int phone_id = 0;

                    String query3 = "select * from PHONE_RECORDS;";
                    ResultSet rs3 = stmt.executeQuery(query3);

                    while(rs3.next()) {
                        phone_id = Integer.valueOf(rs3.getString("phone_record_id")); 
                    }
                    rs3.close();

                    phone_id++;

                    String phone_records_query = "insert into PHONE_RECORDS values ( " + phone_id + "," + citizen_id + ", '" + phone_no + "');";
                    stmt.executeUpdate(phone_records_query);

                    System.out.print("\033[H\033[2J");
                    System.out.print("Successfully Added Phone Number\n\n");
                }
                else if(choice == 4) {

                    System.out.print("Enter Citizen ID: "); int citizen_id = Integer.valueOf(sc.nextLine());

                    String query1 = "select * from CITIZEN_DETAILS where citizen_id = " + citizen_id + ";";
                    ResultSet rs1 = stmt.executeQuery(query1);
                    if(!rs1.next()) {
                        System.out.print("\033[H\033[2J");
                        System.out.print("Unable to Update Quota. Citizen ID entered does not exist in Database\n\n");
                        continue;
                    }
                    rs1.close();

                    System.out.print("Enter Updated Quota ID: "); int quota = Integer.valueOf(sc.nextLine());

                    String query2 = "select * from QUOTA_DETAILS where quota_id = " + quota + ";";
                    ResultSet rs2 = stmt.executeQuery(query2);
                    if(!rs2.next()) {
                        System.out.print("\033[H\033[2J");
                        System.out.print("Unable to Add Record. No such Quota ID Exist");
                        continue;
                    }
                    rs2.close();

                    String citizen_details_query = "update CITIZEN_DETAILS set quota = " + quota + " where citizen_id = " + citizen_id + ";";
                    stmt.executeUpdate(citizen_details_query);

                    System.out.print("\033[H\033[2J");
                    System.out.print("Successfully Updated Quota\n\n");
                }
                else if(choice == 5) {
                    System.out.print("Enter Citizen ID: "); int citizen_id = Integer.valueOf(sc.nextLine());
                    String query1 = "select * from CITIZEN_DETAILS where citizen_id = " + citizen_id + ";";
                    ResultSet rs1 = stmt.executeQuery(query1);
                    if(!rs1.next()) {
                        System.out.print("\033[H\033[2J");
                        System.out.print("Unable to Search Record. Citizen ID entered does not exist in Database\n\n");
                        continue;
                    }
                    rs1.close();

                    String citizen_details_query = "select * from CITIZEN_DETAILS where citizen_id = " + citizen_id + ";";
                    ResultSet rs2 = stmt.executeQuery(citizen_details_query);

                    System.out.print("\033[H\033[2J");
                    System.out.print("Searched Citizen Record:-\n\n");
                    while(rs2.next()) {
                        System.out.print("Citizen ID: " + rs2.getString("citizen_id") + "\n");
                        System.out.print("Citizen Name: " + rs2.getString("citizen_name")+ "\n");
                        System.out.print("Citizen Aadhar Number: " + rs2.getString("aadhar_no")+ "\n");
                        System.out.print("Citizen Address: " + rs2.getString("address_field")+ "\n");
                        System.out.print("Citizen Date Of Birth: " + rs2.getString("date_of_birth")+ "\n\n");
                    }
                    rs2.close();

                    String quota_details_query = "select quota_id,quota_name,quota_benefits from CITIZEN_DETAILS, QUOTA_DETAILS where citizen_id = " + citizen_id + " and quota_id = quota;";
                    ResultSet rs3 = stmt.executeQuery(quota_details_query);

                    if(rs3.next()) {

                        System.out.print("Quota ID: " + rs3.getString("quota_id") + "\n");
                        System.out.print("Quota Name: " + rs3.getString("quota_name")+ "\n");
                        System.out.print("Quota Benefits: " + rs3.getString("quota_benefits")+ "\n\n");
                    }
                    else {
                        System.out.print("No Quota alloted to citizen\n\n");
                    }
                    rs3.close();

                    String phone_records_query = "select phone_no from PHONE_RECORDS where citi_id = " + citizen_id + ";";
                    ResultSet rs4 = stmt.executeQuery(phone_records_query);

                    System.out.print("Phone Numbers:-\n");
                    while(rs4.next()) {
                        System.out.print(rs4.getString("phone_no") + "\n");
                    }
                    rs4.close();
                    
                    System.out.print("\n\n");
                }
            }


            sc.close();
            stmt.close();
            conn.close();

        } catch (Exception e)
        {   // Handle errors for JDBC
            e.printStackTrace();
        }   // Handle errors for Class.forName
        finally
        {   // finally, block used to close resources
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            } catch (SQLException se2)
            {
                // nothing we can do
            }
            try
            {
                if (conn != null)
                {
                    conn.close();
                }
            } catch (SQLException se)
            {
                se.printStackTrace();
            } // end finally try
        }   // end try
        System.out.print("THANK YOU !!\n");
    }   // end main
}   //end class