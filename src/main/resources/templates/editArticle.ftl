<#import "general.ftl" as gen>

<@gen.base user = userValue>

    <div class="card mx-auto" style="width: 750px">
        <div class="card-header">
            <h2>Editar Artículo</h2>
        </div>
        <div class="card-body">
            <form action="/editArticle/${ article.id }" method="post">
                <div class="form-group">
                    <label for="title">Título:</label>
                    <input id="title" class="form-control" type="text" name="title" placeholder="Título"
                    value="${ article.title }">
                </div>

                <div class="form-group">
                    <label for="artBody">Cuerpo:</label>
                    <textarea id="artBody" class="form-control" rows="10" name="artBody"
                              placeholder="Escribe el cuerpo de tu articulo aqui">${ article.body }</textarea>
                </div>

                <div class="form-group">
                    <label class="sr-only" for="tag">Etiquetas</label>
                    <input id="tag" class="form-control" type="text" name="tags" placeholder="Etiquetas"
                    value="${ tagsChain }">
                </div>

                <a href="/show/${ article.id }" class="btn btn-danger">Cancel</a>
                <button style="float:right" type="submit" class="btn btn-primary">Aceptar</button>
            </form>
        </div>
    </div>

</@gen.base>