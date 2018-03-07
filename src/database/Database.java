package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	
	 public static void main(String[] args)
     {
         Connection connection = null;
         try
         {
             // create a database connection
             connection = DriverManager.getConnection("jdbc:sqlite:src/database/database.db");
             Statement statement = connection.createStatement();
             statement.setQueryTimeout(30);  // set timeout to 30 sec.

             statement.executeUpdate("drop table if exists okt");
             statement.executeUpdate("CREATE TABLE okt (\n" + 
             		"    okt_id INT UNSIGNED NOT NULL,\n" + 
             		"    dato DATE NOT NULL,\n" + 
             		"    tidspunkt DATETIME NOT NULL,\n" + 
             		"    varighet INT UNSIGNED NOT NULL,\n" + 
             		"    personlig_form INT UNSIGNED,\n" + 
             		"    prestasjon INT UNSIGNED,\n" + 
             		"    notat VARCHAR(250),\n" + 
             		"    CONSTRAINT ID PRIMARY KEY (okt_id)\n" + 
             		");");
             
             statement.executeUpdate("drop table if exists ovelse");
             statement.executeUpdate("CREATE TABLE ovelse (\n" + 
             		"    navn VARCHAR(50) NOT NULL,\n" + 
             		"    CONSTRAINT ID PRIMARY KEY (navn)\n" + 
             		");");
             
             statement.executeUpdate("drop table if exists ovelseIOkt");
             statement.executeUpdate("CREATE TABLE ovelseIOkt (\n" + 
             		"    ovelse_navn VARCHAR(50) NOT NULL,\n" + 
             		"    okt_id INT UNSIGNED NOT NULL,\n" + 
             		"    CONSTRAINT ovelseIOkt_FK1 FOREIGN KEY (okt_id)\n" + 
             		"        REFERENCES okt (okt_id),\n" + 
             		"    CONSTRAINT ovelseIOkt_FK2 FOREIGN KEY (ovelse_navn)\n" + 
             		"        REFERENCES ovelse (navn)\n" + 
             		");");
             
             statement.executeUpdate("drop table if exists ovelsesgruppe");
             statement.executeUpdate("CREATE TABLE ovelsesgruppe (\n" + 
             		"    ovelsesgruppe_navn VARCHAR(50) NOT NULL,\n" + 
             		"    CONSTRAINT ID PRIMARY KEY (ovelsesgruppe_navn)\n" + 
             		");");
             
             statement.executeUpdate("drop table if exists ovelseIOvelsesgruppe");
             statement.executeUpdate("CREATE TABLE ovelseIOvelsesgruppe (\n" + 
             		"    ovelse_navn VARCHAR(50) NOT NULL,\n" + 
             		"    ovelsesgruppe_navn VARCHAR(50) NOT NULL,\n" + 
             		"    CONSTRAINT ovelseIOvelsesgruppe_FK1 FOREIGN KEY (ovelse_navn)\n" + 
             		"        REFERENCES ovelse (navn),\n" + 
             		"    CONSTRAINT ovelseIOvelsesgruppe_FK2 FOREIGN KEY (ovelsesgruppe_navn)\n" + 
             		"        REFERENCES ovelsesgruppe (ovelsesgruppe_navn)\n" + 
             		");");
             
             statement.executeUpdate("drop table if exists apparat");
             statement.executeUpdate("CREATE TABLE apparat (\n" + 
             		"    apparat_navn VARCHAR(50) NOT NULL,\n" + 
             		"    CONSTRAINT ID PRIMARY KEY (apparat_navn)\n" + 
             		");");
             
             statement.executeUpdate("drop table if exists ovelsePaApparat");
             statement.executeUpdate("CREATE TABLE ovelsePaApparat (\n" + 
             		"    ovelse_navn VARCHAR(50) NOT NULL,\n" + 
             		"    antall_kilo INT UNSIGNED NOT NULL,\n" + 
             		"    antall_set INT UNSIGNED NOT NULL,\n" + 
             		"    apparat_navn VARCHAR(50) NOT NULL,\n" + 
             		"    CONSTRAINT ovelsePaApparat_FK1 FOREIGN KEY (apparat_navn)\n" + 
             		"        REFERENCES apparat (apparat_navn),\n" + 
             		"    CONSTRAINT ovelsePaApparat_FK2 FOREIGN KEY (ovelse_navn)\n" + 
             		"        REFERENCES ovelse (ovelse_navn)\n" + 
             		");");
             
             statement.executeUpdate("drop table if exists ovelseUtenApparat");
             statement.executeUpdate("CREATE TABLE ovelseUtenApparat (\n" + 
             		"    ovelse_navn VARCHAR(50) NOT NULL,\n" + 
             		"    beskrivelse VARCHAR(300),\n" + 
             		"    CONSTRAINT ovelseUtenApparat_FK1 FOREIGN KEY (ovelse_navn)\n" + 
             		"        REFERENCES ovelse (navn)\n" + 
             		");");

             /*
             ResultSet rs = statement.executeQuery("select * from okt");
             while(rs.next())
             {
                 // read the result set
                 System.out.println("name = " + rs.getString("varighet"));
                 System.out.println("id = " + rs.getInt("okt_id"));
             }
             */
         }
         catch(SQLException e)
         {
             // if the error message is "out of memory",
             // it probably means no database file is found
             System.err.println(e.getMessage());
         }
         finally
         {
             try
             {
                 if(connection != null)
                     connection.close();
             }
             catch(SQLException e)
             {
                 // connection close failed.
                 System.err.println(e);
             }
         }
     }
}
