<#import "general.ftl" as gen>

<@gen.base user = userValue>

    <#list articles>
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
                            <span class="badge badge-primary">${ e.tagName }</span>
                        <#else>
                            <span class="badge badge-secondary">Este Articulo no contiene etiquetas</span>
                        </#list>
                    </div>
                </div>
            </#items>
        </ul>
    </#list>

</@gen.base>

