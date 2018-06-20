package Main;

import Entidades.Article;
import Entidades.Comment;
import Entidades.Tag;
import Entidades.User;
import Servicios.*;
import freemarker.template.Configuration;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import spark.ModelAndView;
import spark.Session;
import spark.template.freemarker.FreeMarkerEngine;
import static spark.Spark.*;

import java.util.*;
import java.util.stream.Collectors;

import static spark.Spark.staticFileLocation;

public class Main {
    public static void main(String[] args) {

        staticFileLocation("public");

        /*User user = new User("admin", "jefito", "admin123", true);
        User user2 = new User("tugolfa123", "Marielys", "yoshi", false);

        Tag tag1 = new Tag("que trabajo");
        Tag tag2 = new Tag("una vida");
        Set<Tag> tags = new HashSet<>();
        tags.add(tag1);
        tags.add(tag2);

        Article article = new Article("Titulo de prueba", "Nada en especial", user, new Date(), tags);
        Article article2 = new Article("El segundo", "Nada en especial", user, new Date(), tags);

        Comment comment1 = new Comment("Primero en comentar!", user, article);
        Comment comment2 = new Comment("Segundo en comentar!", user2, article);
        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);

        article.setCommentList(comments);

        UserServices.getInstance().create(user);
        for (Tag tag : tags) {
            TagServices.getInstance().create(tag);
        }
        ArticleServices.getInstance().create(article);
        for (Comment comment : comments) {
            CommentServices.getInstance().create(comment);
        }

        user.setName("Pedro");
        UserServices.getInstance().edit(user);

        article.setTitle("Nuevo titulo de prueba");
        ArticleServices.getInstance().edit(article);

        List<Tag> AllTags = TagServices.getInstance().findAll();
        System.out.println(AllTags);*/

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_26);
        configuration.setClassForTemplateLoading(Main.class, "/templates");
        FreeMarkerEngine freemarkerEngine = new FreeMarkerEngine(configuration);

        BootStrapServices.getInstance().init();
        BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();

        if(UserServices.getInstance().findAll().isEmpty()){
            User user = new User("admin","Administrador",encryptor.encryptPassword("admin123"),true, true);
            UserServices.getInstance().create(user);
        }

        get("/",(request,response) ->{
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("title", "Banana Blog");
            attributes.put("articles", ArticleServices.getInstance().findAll());

            if(request.session().attribute("userValue") == null){
                attributes.put("userValue", "vacio");
            }else {
                attributes.put("userValue", request.session().attribute("userValue"));
            }

            return new ModelAndView(attributes,"index.ftl");
        },freemarkerEngine);

        get("/login",(request, response) ->{
            Map<String, Object> atributes = new HashMap<>();
            atributes.put("title", "Login");
            atributes.put("userValue",request.queryParams("username"));
            return new ModelAndView(atributes,"login.ftl");
        },freemarkerEngine);

        post("/confirmLogin", (request,response) ->{
            String username = request.queryParams("username");
            String password = request.queryParams("password");
            BasicPasswordEncryptor encryptorPass = new BasicPasswordEncryptor();
            Map<String, String> cookies = request.cookies();

            for(String key : cookies.keySet()){
                if(key.equalsIgnoreCase("cookie")){
                    String encryptedText = request.cookie("cookie");
                    BasicTextEncryptor encriptador = new BasicTextEncryptor();
                    encriptador.setPassword("secretPasswd");
                    String usern = encriptador.decrypt(encryptedText);
                    if( username.equalsIgnoreCase(usern)){
                        request.session().attribute("userValue", UserServices.getInstance().find(username));
                        response.redirect("/");
                    }else {
                        break;
                    }
                }
            }
            if(request.queryParams("remember") == null) {

                boolean verify = encryptorPass.checkPassword(password, UserServices.getInstance().find(request.queryParams("username")).getPassword());
                if(verify){
                    request.session(true).attribute("userValue",UserServices.getInstance().find(request.queryParams("username")));
                    response.redirect("/");
                }else {
                    halt(401,"Credenciales invalidas...");
                }

            }else if(request.queryParams("remember").equals("on")){

                boolean verify = encryptorPass.checkPassword(password, UserServices.getInstance().find(request.queryParams("username")).getPassword());
                if(verify){
                    BasicTextEncryptor encriptadorTexto = new BasicTextEncryptor();
                    encriptadorTexto.setPassword("secretPasswd");
                    response.cookie("/","cookie", encriptadorTexto.encrypt(request.queryParams("username")), 604800, false);
                    request.session(true).attribute("userValue",UserServices.getInstance().find(request.queryParams("username")));
                }else{
                    halt(401,"Credenciales invalidas...");
                }
                response.redirect("/");
            }
            return null;
        },freemarkerEngine);

        get("/logout",(request,response) ->{
            Session activeSession = request.session();
            activeSession.invalidate();
            response.redirect("/");

            return null;
        },freemarkerEngine);

        get("/show/:id",(request, response) ->{
            Map<String, Object> atributes = new HashMap<>();
            atributes.put("userValue", request.session().attribute("userValue"));
            //List<Articulo> articulos = Dao.getInstance().getArticulos();
            Article article = ArticleServices.getInstance().find(Long.parseLong(request.params("id")));
            atributes.put("article",article);
            atributes.put("title", article.getTitle());
            atributes.put("body", article.getBody());
            atributes.put("comments", article.getCommentList());
            atributes.put("tags", article.getTagList());
            /*for(Articulo art : articulos){
                if(Long.parseLong(request.params("id")) == art.getId()){
                    atributos.put("titulo",art.getTitulo());
                    atributos.put("articulo",art);

                    atributos.put("comentarios",Dao.getInstance().getComentarios(art.getId()));

                    atributos.put("etiquetas",Dao.getInstance().getEtiquetas(art.getId()));

                }
            }*/

            return new ModelAndView(atributes,"showArticle.ftl");
        },freemarkerEngine);

       post("show/createComment/:id",(request,response)->{

            if(request.session().attribute("userValue") == null){
                response.redirect("/login");
            }else {
                Comment comment = new Comment(request.queryParams("comment"),
                        request.session().attribute("userValue"),
                        ArticleServices.getInstance().find(Long.parseLong(request.params(("id")))));
                CommentServices.getInstance().create(comment);
                /*comentario.setComentario(request.queryParams("comentario"));
                comentario.setAutor(((Usuario)request.session().attribute("usuarioValue")).getUsername());
                comentario.setArticulo(Long.parseLong(request.params("id")));
                Dao.getInstance().insertarComentario(comentario);*/
                response.redirect("/show/" + request.params("id"));

            }
            return null;
        }, freemarkerEngine);

       get("/createUser", (request,response) ->{
            Map<String, Object> atributes = new HashMap<>();
            atributes.put("title","Crear Usuario");
            atributes.put("userValue", request.session().attribute("userValue"));
            return new ModelAndView(atributes,"createUser.ftl");
        },freemarkerEngine);

        post("/createUser", (request, response) ->{
            //Usuario user = new Usuario();
            User user = new User();
            BasicPasswordEncryptor encriptor = new BasicPasswordEncryptor();
            String passEncripted = encriptor.encryptPassword(request.queryParams("password"));
            user.setUsername(request.queryParams("username"));
            //user.setContrasena(passEncripted);
            user.setPassword(passEncripted);
            user.setName(request.queryParams("name"));
            if(request.queryParams("role").equals("administrator")){
                user.setAdministrator(true);
                user.setAuthor(true);
            }else if(request.queryParams("role").equals("author")){
                user.setAuthor(true);
                user.setAdministrator(false);
            }else {
                user.setAdministrator(false);
                user.setAuthor(false);
            }
            UserServices.getInstance().create(user);
            //Dao.getInstance().insertarUsuario(user);
            response.redirect("/");
            return null;

        }, freemarkerEngine);

       get("/createArticle", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("title", "Crear Articulo");
            model.put("userValue", request.session().attribute("userValue"));
            if(request.session().attribute("userValue") == null){
                response.redirect("/login");
            }
            return new ModelAndView(model, "createArticle.ftl");
        }, freemarkerEngine);

        post("/createArticle", (request, response) -> {

            //Articulo articulo = new Articulo();
            Article article = new Article();

            article.setTitle(request.queryParams("title"));
            article.setBody(request.queryParams("artBody"));
            article.setAuthor(request.session().attribute("userValue"));
            article.setDate(new Date());
            List<Tag> tags = new ArrayList<>();
            for(String tag : request.queryParams("tag").split(",")){
                tags.add(new Tag(tag));
            }
            for(Tag tag :tags){
                TagServices.getInstance().create(tag);
            }
            article.setTagList(tags);
            /*articulo.setTitulo(
                    request.queryParams("titulo"));

            articulo.setCuerpo(
                    request.queryParams("cuerpo"));

            articulo.setAutor((( Usuario ) request.session().attribute("usuarioValue")).getUsername());

            articulo.setFecha(new Date());

            articulo.setListaEtiquetas(
                    request.queryParams("etiquetas").split(","));*/

            ArticleServices.getInstance().create(article);


            //Dao.getInstance().insertarArticulo(articulo);

            response.redirect("/");
            return null;
        });

       get("/editArticle/:article_id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            /*Articulo articulo =
                    Dao.getInstance()
                            .getArticulosPorId(Long.parseLong(request.params("article_id")));
*/
            Article article = ArticleServices.getInstance().find(Long.parseLong(request.params("article_id")));

            model.put("title", "Editar Artículo");
            model.put("userValue", request.session().attribute("userValue"));

            model.put("article", article);
            model.put("tagsChain",article.getTagList().stream().map(Tag::getTagName).collect(Collectors.joining(", ")));
                    /*articulo.getListaEtiquetas().stream()
                            .map(Etiqueta::getEtiqueta)
                            .collect(Collectors.joining(", ")));
*/
            return new ModelAndView(model, "editArticle.ftl");
        }, freemarkerEngine);

        post("/editArticle/:articulo_id", (request, response) -> {
            /*Articulo articulo =
                    Dao.getInstance()
                            .getArticulosPorId(Long.parseLong(request.params("articulo_id")));*/
            Article article = ArticleServices.getInstance().find(Long.parseLong(request.params("articulo_id")));
            article.setTitle(request.queryParams("title"));
            article.setBody(request.queryParams("artBody"));
            article.setDate(new Date());

            List<Tag> tags = new ArrayList<>();
            for(String tag : request.queryParams("tag").split(",")){
                tags.add(new Tag(tag));
            }
            article.setTagList(tags);

            ArticleServices.getInstance().edit(article);
            /*
            articulo.setTitulo(request.queryParams("titulo"));
            articulo.setCuerpo(request.queryParams("cuerpo"));
            articulo.setFecha(new Date());
            //TODO - Arreglar el asunto de las etiquetas

            Dao.getInstance().actualizarArticulo(articulo);*/

            response.redirect("/show/" + article.getId());
            return null;
        });
    }
}
