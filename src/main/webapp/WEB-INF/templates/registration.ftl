<#include "base.ftl">

<#macro title>
    Регистрация
</#macro>

<#macro content>
    <form method="post">
        <label for="name">Введите имя</label>
        <input type="text" name="name" id="name">
        <label for="login">Введите логин:</label>
        <input type="email" name="login" id="login">
        <label for="password">Введите пароль:</label>
        <input type="password" name="password" id="password">
        <input type="submit" value="Зарегистрироваться">
    </form>

    <#if errors??>
        <#list errors as error>
            <span>${error.message}</span>
            <br>
        </#list>
    </#if>
</#macro>

<@page/>