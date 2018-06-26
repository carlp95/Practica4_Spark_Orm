<#import "general.ftl" as gen>

<@gen.base user = userValue>

    <div class="bubble">
        <h1>Articulos filtrados por la etiqueta: ${ searchTag }</h1>
    </div>
    <#list articlesFiltered>
        <ul>
            <#items as article>
                <div class="card mx-auto mb-3" style="width: 750px">
                    <div class="card-body">
                        <a href="/show/${ article.id }"><h2>${ article.title }</h2></a>
                        <#if article.body?length &lt; 70>
                                <p>${ article.body }</p>
                        <#else >
                                <p>${ article.body[0..70]}...</p>
                        </#if>
                    </div>

                    <div class="card-footer">
                        <#list article.tagList as e>
                            <#if searchTag == e.tagName>
                                <span class="badge badge-success">${ e.tagName }</span>
                            <#else>
                                <a href="/tag/${ e.tagName }/1" class="badge badge-primary">${ e.tagName }</a>
                            </#if>
                        <#else>
                            <p class="text-danger">No hay etiquetas relacionada a este Artículo</p>
                        </#list>
                    </div>
                </div>
            </#items>
        </ul>
    </#list>

    <nav aria-label="Navegacion entre los resultados filtrados">
        <ul class="pagination justify-content-center">
            <li class="page-item">
                <a class="page-link" href="/tag/${ searchTag }/${page - 1}" tabindex="-1"><i class="fa fa-angle-double-left"> Anterior</i></a>
            </li>
            <li class="page-item">
                <a class="page-link" href="/tag/${ searchTag }/${page + 1}">Siguiente <i class="fa fa-angle-double-right"></i></a>
            </li>
        </ul>
    </nav>

</@gen.base>

