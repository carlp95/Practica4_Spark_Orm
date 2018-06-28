<#import "general.ftl" as gen>

<@gen.base user = userValue>
<div class="container" >
    <div class="card">
        <div class="card-header rounded-0">
            <h3>${article.title}</h3>
            <#if userValue != "vacio">
                <#if userValue.username == article.author.username>
                    <a href="/editArticle/${ article.id }">
                        <button class="btn btn-sm btn-primary"><i class="fa fa-edit"></i> Editar</button>
                    </a>

                    <a href="/deleteArticle/${ article.id }">
                        <button class="btn btn-sm btn-danger"><i class="fa fa-trash"></i> Eliminar</button>
                    </a>
                </#if>
            </#if>
        </div>

        <div class="card-body">
            <p class="card-text" style="text-align: justify">${ article.body }</p>

            <hr>

            <#-- Etiquetas -->
            <div class="likes-panel"> Etiquetas:
                <#if tags?size == 0>
                    <p class="text-danger">No hay etiquetas relacionada a este Artículo</p>
                <#else >
                    <#list tags as e>
                        <a class="badge badge-primary" href="/tag/${ e.tagName }/1">${ e.tagName }</a>
                    </#list>
                </#if>
            </div>

            <#-- Valoraciones articulo-->
            <#if userValue != "vacio">
            <div class="likes-panel">
                <form action="/vote/article/${article.id}" method="post">
                    <#--<button class="btn btn-outline-info" data-toggle="tooltip" data-placement="bottom" title="Me gusta" data-original-title="Me gusta" type="submit"-->
                    <button class="btn btn-sm btn-outline-success" name="vote" value="like">
                        <i class="fa fa-thumbs-up"></i>&nbsp;Me gusta&nbsp;<span class="badge badge-dark">${ likes }</span>
                    </button>
                </form>

                <form action="/vote/article/${article.id}" method="post">
                    <#--<button class="btn btn-outline-danger" data-toggle="tooltip" data-placement="bottom" title="No me gusta" data-original-title="No me gusta" type="submit"-->
                    <button class="btn btn-sm btn-outline-danger" name="vote" value="dislike">
                        <i class="fa fa-thumbs-down"></i>&nbsp;No me gusta&nbsp;<span class="badge badge-dark">${ dislikes }</span>
                    </button>
                </form>
            </div>
            </#if>

        </div>

        <div class="card-footer rounded-0 text-muted" style="text-align: center">
            Autor: ${article.author.name} - ${article.date}
        </div>
    </div>

    <#-- Formulario -->
    <div class="card rounded-0 my-1">
        <div class="card-body rounded-0">
            <h4 class="card-title">Comentarios</h4>
            <h6 class="card-subtitle mb-2 text-muted">Comenta este Post</h6>
            <form action="createComment/${article.id}" method="post">
                <div class="form-group">
                    <textarea class="form-control" name="comment" rows="6" placeholder="Escribe tu comentario aquí"></textarea>
                </div>
                <button type="submit" class="btn btn-success"><i class="fa fa-commenting"> <strong>Comentar</strong></i></button>
            </form>
        </div>

        <#-- Comentarios -->
        <#if comments?size == 0>
            <div class="card-body rounded-0">
                <div class="col-xl-5">
                    <h4 class="card-title">Comentarios sobre este Post</h4>
                    <p class="text-danger">No hay comentarios para este Artículo :(</p>
                </div>
            </div>
        <#else >
            <#--<div class="card rounded-0">-->
            <div class="card-body rounded-0">
                <h4 class="card-title">Comentarios sobre este Post</h4>
                <#list comments?reverse as com>
                    <div class="card border-secondary px-2 py-2 my-2">
                        <p><strong>${com.author.username}:</strong>&nbsp;${ com.body }</p>

                        <#if userValue != "vacio">
                            <div class="likes-panel">
                                <form method="post" action="/vote/comment/${ com.id }">
                                    <button class="btn btn-sm btn-outline-success" value="like" name="vote" data-toggle="tooltip" data-placement="bottom" title="Me gusta" data-original-title="Me gusta">
                                        <i class="fa fa-thumbs-up"></i>&nbsp;Me gusta&nbsp;<span class="badge badge-dark">${ com.countLike() }</span>
                                    </button>
                                </form>

                                <form method="post" action="/vote/comment/${ com.id }">
                                    <button class="btn btn-sm btn-outline-danger" value="dislike" name="vote" data-toggle="tooltip" data-placement="bottom" title="No me gusta" data-original-title="No me gusta">
                                        <i class="fa fa-thumbs-down"></i>&nbsp;No me gusta&nbsp;<span class="badge badge-dark">${ com.countDislike() }</span>
                                    </button>
                                </form>
                            </div>
                        </#if>
                    </div>
                </#list>
            </div>
        </#if>
    </div>

</div>
</@gen.base>