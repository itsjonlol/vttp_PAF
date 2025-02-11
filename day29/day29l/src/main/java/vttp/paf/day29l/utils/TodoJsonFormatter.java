package vttp.paf.day29l.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.paf.day29l.model.Todo;

@Component
public class TodoJsonFormatter {
    
    public static JsonObject pojoToJson(Todo todo) {
        Date toDoDate = todo.getDueDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String toDoDateString = sdf.format(toDoDate);

        JsonObject jsonObject = Json.createObjectBuilder()
            .add("id",todo.getId())
            .add("taskName",todo.getTaskName())
            .add("dueDate",toDoDateString)
            .build();
        return jsonObject;
    }
    public static Todo JsonToPojo(JsonObject jsonObject) {
        Todo todo = new Todo();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String toDoDateString = jsonObject.getString("dueDate");

        todo.setId(jsonObject.getInt("id"));
        todo.setTaskName(jsonObject.getString("taskName"));
        Date dueDate;
        try {
            dueDate = sdf.parse(toDoDateString);
            
        } catch (ParseException e) {
            dueDate = new Date();
        }
        todo.setDueDate(dueDate);
        

        return todo;
    }
}
