<#include "base.ftl">

<#macro head>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/login.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/registration.css">
</#macro>

<#macro title>
    Регистрация
</#macro>

<#macro content>
    <div class="container">
        <div class="form-container">
            <form method="post" class="input-data-form">
                <h2>Регистрация</h2>
                <div class="form-group">
                    <label for="name">Введите имя:</label>
                    <input type="text" name="name" id="name" placeholder="Ваше имя" required>
                </div>
                <div class="form-group">
                    <label for="login">Введите логин:</label>
                    <input type="email" name="login" id="login" placeholder="Ваш логин (Email)" required>
                </div>
                <div class="form-group">
                    <label for="password">Введите пароль:</label>
                    <input type="password" name="password" id="password" placeholder="Ваш пароль" required>
                </div>

                <input type="submit" value="Зарегистрироваться" class="btn-submit">

                <#if errors??>
                    <div class="error-messages">
                        <#list errors as error>
                            <span>${error.message}</span>
                        </#list>
                    </div>
                </#if>
            </form>
        </div>
    </div>

<#--    <script src="${contextPath}/static/js/loginValidation.js"></script>-->
</#macro>

<@page/>