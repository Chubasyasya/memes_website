<#include "base.ftl"/>

<#macro title>
    Profile settings
</#macro>

<#macro content>
    <form method="post" action="${contextPath}/profileSettings">
        <label for="name">Имя пользователя</label><br>
        <input type="text" name="name" id="name" placeholder="${account.name()}"><br>

        <label for="email">Почта</label><br>
        <input type="email" name="email" id="email" placeholder="${account.email()}"><br>

        <label for="phoneNumber">Номер телефона</label><br>
        <input type="text" name="phoneNumber" id="phoneNumber" placeholder="${account.phoneNumber()}"><br>

        <label for="password">Пароль</label><br>
        <input type="password" name="password" id="password" placeholder="${account.password()}"><br>

        <label for="status">Статус</label><br>
        <input type="text" name="status" id="status" placeholder="${account.status()}"><br>

        <label for="birthday">День рождения</label><br>
        <input type="date" name="birthday" id="birthday" placeholder="${account.birthday()}"><br>
        <input type="submit" value="Сохранить">
    </form>

    <form method="post" action="${contextPath}/exit">
        <input type="submit" value="Выйти">
    </form>

    <#if errors??>
        <#list errors as error>
            <span>${error.message}</span>
            <br>
        </#list>
    </#if>
</#macro>

<@page/>