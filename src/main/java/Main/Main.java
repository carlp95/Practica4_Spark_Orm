package Main;

import Entidades.*;
import Servicios.*;
import freemarker.template.Configuration;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import spark.ModelAndView;
import spark.Session;
import spark.template.freemarker.FreeMarkerEngine;

import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

import static spark.Spark.*;

public class Main {
//    public static void insertArticles(int numArticles) {
//
//        Lorem lorem = LoremIpsum.getInstance();
//        Random rand = new Random();
//        List<String> posibleTags = new ArrayList<>();
//        Collections.addAll(posibleTags, "food", "");
//
//        BasicPasswordEncryptor basicPasswordEncryptor = new BasicPasswordEncryptor();
//        User author = new User("darkwiz", "Nelson Daniel", basicPasswordEncryptor.encryptPassword("123"), false, true);
//        UserServices.getInstance().create(author);
//
//
//        for (int i = 0; i < numArticles; i++) {
//            List<Tag> tags = new ArrayList<>();
//            for (int j = 0; j < rand.nextInt(5); j++) {
//                Tag tag = new Tag(lorem.getCountry());
//                tags.add(tag);
//                TagServices.getInstance().create(tag);
//            }
//
//            ArticleServices.getInstance().create(new Article(
//                    lorem.getTitle(5, 10),
//                    lorem.getParagraphs(4, 5),
//                    author,
//                    new Date(),
//                    tags
//            ));
//        }
//    }

    public static void main(String[] args) {

        staticFileLocation("public");

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_26);
        configuration.setClassForTemplateLoading(Main.class, "/templates");
        FreeMarkerEngine freemarkerEngine = new FreeMarkerEngine(configuration);

        BootStrapServices.getInstance().init();
        BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();

        // Creating default user if there are none
        if(UserServices.getInstance().findAll().isEmpty()){
            User user = new User("admin","Administrador",encryptor.encryptPassword("admin123"),true, true);
            UserServices.getInstance().create(user);
        }

        //
//        insertArticles(30);

        //Applying all the filters
        new Filters().aplicarFiltros();
        get("/",(request,response) ->{
            response.redirect("/articles/page/1");
            return "Hi~";
        });

        get("/articles/page/:page", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("title", "Banana Blog");
            attributes.put("userValue", request.session().attribute("userValue"));

            TypedQuery<Article> query = ArticleServices.getInstance()
                    .getEntityManager()
                    .createQuery("from Article a order by a.date desc", Article.class);

            int endLimit = Integer.parseInt(request.params("page")) * 5;
            int startLimit = endLimit - 5;

            query.setFirstResult(startLimit);
            query.setMaxResults(endLimit);
            attributes.put("page", Integer.parseInt(request.params("page")));
            attributes.put("articles", query.getResultList());

            return new ModelAndView(attributes,"index.ftl");
        }, freemarkerEngine);

        get("/login",(request, response) ->{
            Map<String, Object> atributes = new HashMap<>();
            atributes.put("title", "Login");
            atributes.put("userValue",request.queryParams("username"));
            return new ModelAndView(atributes,"login.ftl");
        }, freemarkerEngine);

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
            response.removeCookie("cookie");
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

            // Pass the votes
            atributes.put("likes", article.countLike());
            atributes.put("dislikes", article.countDislike());
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
           Comment comment = new Comment(request.queryParams("comment"),
                        request.session().attribute("userValue"),
                        ArticleServices.getInstance().find(Long.parseLong(request.params(("id")))));
           CommentServices.getInstance().create(comment);
                /*comentario.setComentario(request.queryParams("comentario"));
                comentario.setAutor(((Usuario)request.session().attribute("usuarioValue")).getUsername());
                comentario.setArticulo(Long.parseLong(request.params("id")));
                Dao.getInstance().insertarComentario(comentario);*/
           response.redirect("/show/" + request.params("id"));

            return null;
        }, freemarkerEngine);


       post("/vote/article/:id", (request, response) -> {
           Article article = ArticleServices.getInstance().find(Long.parseLong(request.params("id")));
           ArticleVote vote = new ArticleVote();


           if (request.queryParams("vote").equals("like")) {
               vote.setArticle(article);
               vote.setAuthor(request.session().attribute("userValue"));
               vote.setValue(Vote.LIKE);
           } else if (request.queryParams("vote").equals("dislike")) {
               vote.setArticle(article);
               vote.setAuthor(request.session().attribute("userValue"));
               vote.setValue(Vote.DISLIKE);
           }

           if (ArticleVoteServices.getInstance().find(vote.getId()) != null) {
                ArticleVoteServices.getInstance().create(vote);
           }

           response.redirect("/show/"+article.getId());
           return null;
       });

        post("/vote/comment/:id", (request, response) -> {
            Comment comment = CommentServices.getInstance().find(Long.parseLong(request.params("id")));
            CommentVote vote = new CommentVote();

            if (request.queryParams("vote").equals("like")) {
                vote.setComment(comment);
                vote.setAuthor(request.session().attribute("userValue"));
                vote.setValue(Vote.LIKE);
            } else if (request.queryParams("vote").equals("dislike")) {
                vote.setComment(comment);
                vote.setAuthor(request.session().attribute("userValue"));
                vote.setValue(Vote.DISLIKE);
            }

            CommentVoteServices.getInstance().create(vote);
            response.redirect("/show/"+comment.getArticle().getId());
            return null;
        });

//       get("/like/:id", (request,response) -> {
//
//           Article article = ArticleServices.getInstance().find(Long.parseLong(request.params("id")));
//           ArticleVote like = new ArticleVote(1, request.session().attribute("userValue"), article);
//           ArticleVoteServices.getInstance().create(like);
//
//           response.redirect("/show/" + request.params("id"));
//
//           return null;
//       });
//
//        get("/dislike/:id",(request,response) ->{
//
//            Article article = ArticleServices.getInstance().find(Long.parseLong(request.params("id")));
//            ArticleVote dislike = new ArticleVote(2, request.session().attribute("userValue"), article);
//            ArticleVoteServices.getInstance().create(dislike);
//
//            response.redirect("/show/"+request.params("id"));
//
//
//            return null;
//        });

//        get("/likeComment/:art_id/:id",(request,response) ->{
//            if(request.session().attribute("userValue") == null || request.session().attribute("userValue").equals("vacio")){
//                response.redirect("/login");
//            }else {
//                Comment comment = CommentServices.getInstance().find(Long.parseLong(request.params("id")));
//                comment.setLikenum(comment.getLikenum() + 1);
//                CommentServices.getInstance().edit(comment);
//                response.redirect("/show/"+request.params("art_id"));
//            }
//
//            return null;
//        },freemarkerEngine);
//
//        get("/dislikeComment/:art_id/:id",(request,response) ->{
//            if(request.session().attribute("userValue") == null || request.session().attribute("userValue").equals("vacio")){
//                response.redirect("/login");
//            }else{
//                Comment comment = CommentServices.getInstance().find(Long.parseLong(request.params("id")));
//                comment.setDislike(comment.getDislike() + 1);
//                CommentServices.getInstance().edit(comment);
//                response.redirect("/show/"+request.params("art_id"));
//            }
//
//            return null;
//        },freemarkerEngine);

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
            switch (request.queryParams("role")) {
                case "administrator":
                    user.setAdministrator(true);
                    user.setAuthor(true);
                    break;
                case "author":
                    user.setAuthor(true);
                    user.setAdministrator(false);
                    break;
                case "normal":
                    user.setAdministrator(false);
                    user.setAuthor(false);
                    break;
            }
            UserServices.getInstance().create(user);
            response.redirect("/");
            return null;

        }, freemarkerEngine);

       get("/createArticle", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            model.put("title", "Crear Articulo");
            model.put("userValue", request.session().attribute("userValue"));

            return new ModelAndView(model, "createArticle.ftl");
        }, freemarkerEngine);

        post("/createArticle", (request, response) -> {

            Article article = new Article();

            article.setTitle(request.queryParams("title"));
            article.setBody(request.queryParams("artBody"));
            article.setAuthor(request.session().attribute("userValue"));
            article.setDate(new Date());

            List<Tag> tags = new ArrayList<>();

            // Add the tags fetched from the view to the Article object and persist it
            String tagString = request.queryParams("tag");
            addAndPersistTags(tags, tagString);
            article.setTagList(tags);

            ArticleServices.getInstance().create(article);

            response.redirect("/");
            return null;
        });

       get("/editArticle/:article_id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            Article article = ArticleServices.getInstance().find(Long.parseLong(request.params("article_id")));

            model.put("title", "Editar Artículo");
            model.put("userValue", request.session().attribute("userValue"));

            model.put("article", article);
            model.put("tagsChain",article.getTagList()
                    .stream()
                    .map(Tag::getTagName)
                    .collect(Collectors.joining(", ")));

            return new ModelAndView(model, "editArticle.ftl");
        }, freemarkerEngine);

       post("/editArticle/:article_id", (request, response) -> {
            Article article = ArticleServices.getInstance().find(Long.parseLong(request.params("article_id")));
            article.setTitle(request.queryParams("title"));
            article.setBody(request.queryParams("artBody"));
            article.setDate(new Date());

            List<Tag> tags = new ArrayList<>();

            TagServices.getInstance().deleteAllFromArticle(article);

            // Add the tags fetched from the view to the Article object
            String tagString = request.queryParams("tag");
           addAndPersistTags(tags, tagString);
           article.setTagList(tags);

            ArticleServices.getInstance().edit(article);

            response.redirect("/show/" + article.getId());
            return null;
        });

        get("/deleteArticle/:article_id", (request, response) -> {
            ArticleServices.getInstance().delete(Long.parseLong(request.params("article_id")));

            response.redirect("/show/" + request.params("article_id"));
            return null;
        }, freemarkerEngine);

        get("/tag/:tagName", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            model.put("title", "Buscando Artículos por tag");
            model.put("userValue", request.session().attribute("userValue"));
            model.put("searchTag", request.params("tagName"));
            model.put("articlesFiltered",
                    ArticleServices.getInstance().findAllWithTag(request.params("tagName")));

            return new ModelAndView(model, "searchPerTag.ftl");
        }, freemarkerEngine);

        notFound((request, response) -> {
            //response.type("text/html");
            response.redirect("/login");
            return null;
        });
    }

    private static void addAndPersistTags(List<Tag> tags, String tagString) {
        for(String tagName : tagString.replaceAll(" ", "").toLowerCase().split(",")){
            Tag newTag = TagServices.getInstance().findByTagName(tagName);
            if (newTag != null)
                tags.add(newTag);
            else  {
                newTag = new Tag(tagName);
                tags.add(newTag);
                TagServices.getInstance().create(newTag);
            }
        }
    }
}
