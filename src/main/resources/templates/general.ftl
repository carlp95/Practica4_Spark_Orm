<#--noinspection ALL,HtmlUnknownTarget,ALL,HtmlUnknownTarget-->
<#macro base user>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${ title }</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link  href="https://bootswatch.com/4/darkly/bootstrap.css" rel="stylesheet"/>
    <link rel="stylesheet" href="/css/main.css">
    <link rel="icon"
          type="image/x-icon"
          href="/images/favicon.ico">
</head>
<body>

    <header class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="/">Banana Blog</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor02" aria-controls="navbarColor02" aria-expanded="false" aria-label="Toggle navigation" style="">
            <span class="navbar-toggler-icon"></span>
        </button>

        <#if user != "vacio">
            <div class="collapse navbar-collapse" id="navbarColor02">
                <ul class="navbar-nav mr-auto">
                    <#if user.administrator>
                        <li class="nav-item">
                            <a class="nav-link" href="/createUser">Crear Usuario <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/createArticle">Crear Artículo</a>
                        </li>
                    <#elseif user.author>
                        <li class="nav-item">
                            <a class="nav-link" href="/createArticle">Crear Artículo</a>
                        </li>
                    </#if>

                </ul>
            </div>
            <div>
                <div class="nav-link" style="float:left; font-style: italic">¡Hey, ${ user.username?cap_first }! What's up?</div>
                <a href="/logout"><button class="btn btn-secundary">Cerrar Sesión</button></a>
            </div>
        <#else >
            <div class="collapse navbar-collapse" id="navbarColor02">

            </div>
            <a href="/login"><button class="btn btn-secundary">Iniciar Sesión</button></a>
        </#if>

    </header>

    <#--contenido-->
    <div class="content">
        <#nested>
    </div>

    <#--footer-->
    <footer class="footer">
        <#include "footer.ftl">
    </footer>

    <#--<script-->
            <#--src="https://code.jquery.com/jquery-3.3.1.min.js"-->
            <#--integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="-->
            <#--crossorigin="anonymous"></script>-->

    <#--<script src="/js/like-dislike.js"></script>-->

</body>
</html>

</#macro>
