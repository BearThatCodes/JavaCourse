package mysql.hu.edu;

public class MySQLAccess {
    public static void main(String[] args) throws Exception {
        PlantDAO dao = new PlantDAO();
        dao.readDataBase();
    }

} 