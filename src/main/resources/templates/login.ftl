<#--noinspection HtmlUnknownTarget-->
<#import "general.ftl" as gen>

<@gen.base user="vacio" >
<div class="container" align="center">
    <div class="col-lg-5">
    <div class="card">
        <div class="card-body">
            <h4 class="card-title">Logeate!</h4>
            <h6 class="card-subtitle mb-2 text-muted">Bienvenido a Banana Blog, debes logearte para poder publicar articulos.</h6>
            <form action="confirmLogin" method="post">
                <div class="form-group row">
                    <div class="col-md-6" style="margin: 0 auto;">
                        <label for="username">Usuario</label>
                        <input class="form-control" name="username" placeholder="usuario123" type="text" required>
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-md-6" style="margin: 0 auto;">
                        <label for="password">Contraseña</label>
                        <input class="form-control"  name="password" type="password" required>
                    </div>
                </div>
                <div class="form-group row">
                    <div class="form-check col-md-4" style="margin: 0 auto;">
                        <label class="form-check-label">
                            <input class="form-check-input" name="remember" type="checkbox">
                            Recordarme
                        </label>
                    </div>
                </div>
                <button type="submit" class="btn btn-success"><i class="fa fa-sign-in"> <strong>Entrar</strong></i></button>
            </form>
        </div>
    </div>
    </div>
</div>
</@gen.base>