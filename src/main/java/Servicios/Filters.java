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

        after("/createUser/",(request, response) -> {
            if(!((User)request.session().attribute("userValue")).isAdministrator() || request.session().attribute("userValue") == null
                    || request.session().attribute("userValue").equals("vacio")){
                response.redirect("/error",404);
            }
        });
    }

}
