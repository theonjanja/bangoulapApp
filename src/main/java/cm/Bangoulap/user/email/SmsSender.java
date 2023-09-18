package cm.Bangoulap.user.email;

import cm.Bangoulap.Todo;
import org.springframework.web.client.RestTemplate;

public class SmsSender {

    public static final String BASE_URL = "https://api.orange.com";

    public static void getToken() {
        RestTemplate restTemplate = new RestTemplate();

        /* // GET Syntaxe
        Todo firstTodo = restTemplate.getForObject("https://jsonplaceholder.typicode.com/todos", Todo.class);
        System.out.println(firstTodo.toString());
         */




    }

    public static void sendSMS() {

    }

}
