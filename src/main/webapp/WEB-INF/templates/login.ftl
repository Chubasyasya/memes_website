<#include "base.ftl"/>

<#macro head>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/login.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/registration.css">
</#macro>

<#macro content>
    <div class="container">
        <div class="form-container">

            <form method="post" id="input-data-form">
                <h2>Вход</h2>
                <div class="form-group">
                    <label for="login">Введите логин:</label>
                    <input type="text" name="login" id="login" required>
                </div>

                <div class="form-group">
                    <label for="password">Введите пароль:</label>
                    <input type="password" name="password" id="password" required>
                </div>

                <div class="form-group remember-group">
                    <label for="remember_me">Запомнить меня:</label>
                    <input type="checkbox" name="remember_me" id="remember_me">
                </div>

                <div class="form-group">
                    <input type="submit" value="Отправить" class="btn-submit">
                </div>

                <div class="link-container">
                    <p>Нет аккаунта? <a href="${contextPath}/registration">Регистрация</a></p>
                </div>
            </form>

            <div id="alertMessage"></div>
        </div>
    </div>

    <script src="${contextPath}/static/js/loginValidation.js"></script>
</#macro>

<@page/>

