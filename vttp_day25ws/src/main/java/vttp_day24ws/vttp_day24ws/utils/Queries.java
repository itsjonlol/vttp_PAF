package vttp_day24ws.vttp_day24ws.utils;

public class Queries {
    
    public static final String SQL_CREATE_ORDER = """
        insert into orders(order_date,customer_name,ship_address,notes,tax)
        values(?,?,?,?,?)
            """;
    public static final String SQL_CREATE_ORDERDETAILS = """
        insert into orderdetails(product,unit_price,discount,quantity,order_id)
        values(?,?,?,?,?);
        """;
}
