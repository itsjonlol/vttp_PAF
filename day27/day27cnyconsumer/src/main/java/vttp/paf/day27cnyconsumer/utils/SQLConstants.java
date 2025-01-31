package vttp.paf.day27cnyconsumer.utils;

public class SQLConstants {
    
    public static final String SQL_INSERT_PO = """
        insert into purchase_orders(po_id,name,address,delivery_date)
        values (?,?,?,?);
            """;;
    public static final String SQL_INSERT_LI = """
        insert into line_items(name,quantity,unit_price,po_id)
        values (?,?,?,?);

            """;
}