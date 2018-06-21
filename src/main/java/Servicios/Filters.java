package Servicios;

import Entidades.User;

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

        after("/show/createComment/:id", (request, response) -> {
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
            if(UserServices.getInstance().find(request.session().attribute("userValue")) == null ||
                    !user.isAdministrator() || !user.isAuthor() || request.session().attribute("userValue") == null
                    || request.session().attribute("userValue").equals("vacio")){
                response.redirect("/login");
            }
        });
    }

}
