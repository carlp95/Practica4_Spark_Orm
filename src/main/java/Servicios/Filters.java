package Servicios;

import Entidades.User;
import org.jasypt.util.text.BasicTextEncryptor;

import javax.jws.soap.SOAPBinding;
import java.util.Map;

import static spark.Spark.after;
import static spark.Spark.before;

public class Filters {
    public Filters() {
    }

    public void aplicarFiltros(){
        before((request, response) -> {
            if(request.session().attribute("userValue") == null){
                request.session().attribute("userValue","vacio");
            }
        });

        before("/",(request,response) ->{
            Map<String, String> cookies = request.cookies();

            for(String key : cookies.keySet()){
                if(key.equalsIgnoreCase("cookie")){
                    String encryptedText = request.cookie("cookie");
                    BasicTextEncryptor encriptador = new BasicTextEncryptor();
                    encriptador.setPassword("secretPasswd");
                    String usern = encriptador.decrypt(encryptedText);
                    request.session().attribute("userValue", UserServices.getInstance().find(usern));
                }
            }
        });

        after("/show/createComment/*", (request, response) -> {
            if(request.session().attribute("userValue").equals("vacio") || request.session().attribute("userValue") == null) {
                response.redirect("/login");
            }
        });

        after("/show/*", (request, response) -> {
            if(request.session().attribute("userValue") == null) {
                request.session().attribute("userValue", "vacio");
            }
        });

        before("/createUser",(request, response) -> {
            User user = UserServices.getInstance().find(request.session().attribute("userValue"));
            if(UserServices.getInstance().find(request.session().attribute("userValue")) == null ||
                    !user.isAdministrator() || request.session().attribute("userValue") == null
                    || request.session().attribute("userValue").equals("vacio")){
                response.redirect("/error");
            }
        });

        before("/like/*", (request,response) ->{
            if(UserServices.getInstance().find(((User)request.session().attribute("userValue")).getUsername()) == null
                    || request.session().attribute("userValue") == null
                    || request.session().attribute("userValue").equals("vacio")){
                response.redirect("/login");
            }
        });

        before("/dislike/*", (request,response) ->{
            if(UserServices.getInstance().find(((User)request.session().attribute("userValue")).getUsername()) == null
                    || request.session().attribute("userValue") == null
                    || request.session().attribute("userValue").equals("vacio")){
                response.redirect("/login");
            }
        });

        before("/likeComment/*", (request,response) ->{
            //User user = UserServices.getInstance().find(((User)request.session().attribute("userValue")).getUsername());
            //String username = ;
            if(UserServices.getInstance().find(((User)request.session().attribute("userValue")).getUsername()) == null
                    || request.session().attribute("userValue") == null
                    || request.session().attribute("userValue").equals("vacio")){
                response.redirect("/login");
            }
        });

        before("/dislikeComment/*", (request,response) ->{
            if(UserServices.getInstance().find(((User)request.session().attribute("userValue")).getUsername()) == null
                    || request.session().attribute("userValue") == null
                    || request.session().attribute("userValue").equals("vacio")){
                response.redirect("/login");
            }
        });

        before("/createArticle",(request, response) -> {
            String username = ((User) request.session().attribute("userValue")).getUsername();
            User user = UserServices.getInstance().find(username);
            if(UserServices.getInstance().find(username) == null || username == null
                    || username.equals("vacio")){
                response.redirect("/login");
            }
            if(!user.isAdministrator() && !user.isAuthor()){
                response.redirect("/error");
            }
        });

        before("/editArticle/*",(request, response) -> {
            User user = UserServices.getInstance().find(request.session().attribute("userValue"));
            if(UserServices.getInstance().find(request.session().attribute("userValue")) == null || request.session().attribute("userValue") == null
                    || request.session().attribute("userValue").equals("vacio")){
                response.redirect("/login");
            }
            if(!user.isAdministrator() && !user.isAuthor()){
                response.redirect("/error");
            }
        });
    }

}
