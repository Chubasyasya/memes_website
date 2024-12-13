<#include "base.ftl"/>

<#macro title>
    Account login
</#macro>

<#macro content>
    <a href="${contextPath}/registration" class="btn">Регистрация</a>
    <a href="${contextPath}/home" class="btn">Главная страница</a>

    <form method="post">
        <label for="login">Введите логин:</label><br>
        <input type = "text" name="login" id="login"><br>
        <label for="password">Введите пароль:</label><br>
        <input type="password" name="password" id="password"><br>
        <label for="remember_me">Запомнить меня: </label>
        <input type="checkbox" name="remember_me" id="remember_me"><br>
        <input type="submit" value="Отправить">
    </form>

</#macro>

<@page/>

