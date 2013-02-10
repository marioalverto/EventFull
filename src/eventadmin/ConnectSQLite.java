/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eventadmin;

/**
 *
 * @author mariomena
 */
import java.sql.*;
//import org.sqlite.*;

public class ConnectSQLite {
    
    //String ruta = "/db/CLIENTES.sqlite";
    String nombreDB= "db/CLIENTES.sqlite";
    String[] exp;
    
    public void ConnectDB(){
        try {
            Class.forName("org.sqlite.JDBC");//especificamos el driver
            Connection conn = DriverManager.getConnection("jdbc:sqlite:"+nombreDB);//creamos la conexión, si la BD no existe la crea
            Statement stat = conn.createStatement();
            stat.close();
            conn.close();//Finalmente cerramos la conexion
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public void Consulta(){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:"+nombreDB);
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select * from EXPOSITORES");//en la variable rs se guardan todas las filas devueltas por la consulta
            System.out.println("ID\t\tSTAND\t\t\t\t\t\t\tNOMBRE");
            while(rs.next()){//recorremos todas las filas
                //System.out.println(rs.getString("NOM_STAND")); 
                System.out.println(rs.getString("ID_EXP")+"\t\t"+rs.getString("NOM_STAND")+"\t\t\t\t\t"+rs.getString("NOM_RESP"));//rs.getString("<nombreColumna>")
            }
            stat.close();
            conn.close();//Cerramos la conexion
       }catch(ClassNotFoundException ex){
            ex.printStackTrace();
       }catch (SQLException ex){
            ex.printStackTrace();
       }
    }
    
    public int numFilas (ResultSet rs) 
                                throws SQLException{
        int contador = 0;

        while (rs.next()) {
            contador++;
        }
        
        return contador;
    }
    
    public String[] getDatosExpositor(String expD){
        String[] expDat = new String[4];
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:"+nombreDB);
            Statement stat = conn.createStatement();
            if (expD != null){
                ResultSet rs = stat.executeQuery("SELECT * FROM EXPOSITORES WHERE NOM_STAND = '"+expD+"' ");//en la variable rs se guardan valores

                /*int i = 0;
                while(rs.next()){//recorremos todas las filas
                    expDat[i] = rs.getString(i);//rs.getString("<nombreColumna>")
                    System.out.println(rs.getString(1));
                    i++;
                }*/
                expDat[0] = rs.getString("GIRO");
                expDat[1] = rs.getString("NOM_RESP");
                expDat[2] = rs.getString("TEL");
                expDat[3] = rs.getString("MAIL");
            }
            stat.close();
            conn.close();//Cerramos la conexion
       }catch(ClassNotFoundException ex){
            ex.printStackTrace();
       }catch (SQLException ex){
            ex.printStackTrace();
       }
       return expDat;
       
    }
    
    
    public void ConsultaExpositoresList(){
        
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:"+nombreDB);
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select NOM_STAND from EXPOSITORES");//en la variable rs se guardan valores
            
            this.exp = new String[this.numFilas(rs)];
            rs = stat.executeQuery("select NOM_STAND from EXPOSITORES");
            
            int i = 0;
            while(rs.next()){//recorremos todas las filas
                this.exp[i] = rs.getString("NOM_STAND");//rs.getString("<nombreColumna>")
                i++;
            }
            
            stat.close();
            conn.close();//Cerramos la conexion
       }catch(ClassNotFoundException ex){
            ex.printStackTrace();
       }catch (SQLException ex){
            ex.printStackTrace();
       }   
    }
    
    public String[] getConsultaExpositores(){
        return this.exp;
    }
    
    public void insertDatosExpositores(String nombrestand, String responsable, String giro, String tel, String mail){
        
        try {
            
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:"+nombreDB);
            Statement stat = conn.createStatement();

            /*ResultSet rs = stat.executeQuery("select max(id) as id from EXPOSITORES");//Seleccionamos el maximo id
            if(rs.getString("id")==null) id=1;//Si es NULL es porque no hay ningun contacto en la tabla
            else id = Integer.parseInt(rs.getString("id"))+1;//Sino, sumanos 1 al maximo id actual en la tabla para nuestro nuevo contacto
            */
            stat.executeUpdate("INSERT INTO EXPOSITORES(NOM_STAND,NOM_RESP,GIRO,TEL,MAIL) VALUES('"+nombrestand+"','"+responsable+"','"+giro+"','"+tel+"','"+mail+"')");//Si el id es auto_increment no hay que poner el id
            stat.close();
            conn.close();
        }catch(ClassNotFoundException ex){
             ex.printStackTrace();
        }catch (SQLException ex){
             ex.printStackTrace();
        }
        
    }
    
    public void EliminarExpositor(String expD){
        
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:"+nombreDB);
            Statement stat = conn.createStatement();
            stat.executeUpdate("DELETE FROM EXPOSITORES WHERE NOM_STAND= '"+expD+"' ");//Modificar para eliminar contacto.
            stat.close();
            conn.close();
       }catch(ClassNotFoundException ex){
            ex.printStackTrace();
       }catch (SQLException ex){
            ex.printStackTrace();
       }
    }
    
}
