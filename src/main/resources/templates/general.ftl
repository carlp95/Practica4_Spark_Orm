<#macro base user>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${ title }</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link  href="https://bootswatch.com/4/darkly/bootstrap.css" rel="stylesheet"/>
    <link rel="stylesheet" href="/public/css/main.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-5">
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
                        <#else >
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

    </nav>

    <#--contenido-->

    <#nested>

    <#--footer-->
    <div class="footer">
        <#include "footer.ftl">
    </div>

</body>
</html>

</#macro>
