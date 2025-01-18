package vttp.batch5.paf.day23.repo;

public class Queries {
    
    

    public static final String SQL_ADD_PURCHASEORDER = """
            
            INSERT INTO purchaseorders(username,address,delivery_date) 
            VALUES (?,?,?);
            """;

    public static final String SQL_ADD_LINEITEMS = """
    
                insert into lineitems(name,quantity,unit_price,purchase_id)
                values(?,?,?,?);
    """;

    public static final String SQL_GET_ALL_ORDERS ="""
            select * from purchaseorders;
            """;
}

