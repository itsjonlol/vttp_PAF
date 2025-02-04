package vttp_lecture22.vttp_lecture22.utils;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import vttp_lecture22.vttp_lecture22.model.Summary;

public class ToSummary {
    
    public static Summary toSummary(SqlRowSet rs) {
        Summary summary = new Summary();

        summary.setAuthor(rs.getString("author"));
        summary.setNoBooks(rs.getInt("number_books"));
        summary.setAvgPrice(rs.getFloat("avg(price)"));
        summary.setAvgStars(rs.getFloat("bk_avg"));

        return summary;
    }
}
