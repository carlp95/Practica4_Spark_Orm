package Servicios;

import Entidades.User;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;

import java.util.Map;

import static spark.Spark.*;

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

        before("/createArticle",(request, response) -> {
            User user = UserServices.getInstance().find(request.session().attribute("userValue"));
            if(UserServices.getInstance().find(request.session().attribute("userValue")) == null || request.session().attribute("userValue") == null
                    || request.session().attribute("userValue").equals("vacio")){
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
