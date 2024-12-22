<#include "base.ftl"/>

<#macro title>
    Profile settings
</#macro>

<#macro head>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/profile-settings-style.css">
</#macro>

<#macro content>
    <div class="settings-form">
        <form method="post" action="${contextPath}/profile/settings" id="profileForm">
            <label for="name">Имя пользователя</label>
            <input type="text" name="name" id="name" placeholder="${account.name}">

            <label for="email">Почта</label>
            <input type="email" name="email" id="email" placeholder="${account.email}">

            <label for="phoneNumber">Номер телефона</label>
            <input type="text" name="phoneNumber" id="phoneNumber" placeholder="${account.phoneNumber!"Не указан"}">

            <label for="password">Пароль</label>
            <input type="password" name="password" id="password" placeholder="*********">

            <label for="status">Статус</label>
            <input type="text" name="status" id="status" placeholder="${account.status!"Не указан"}">

            <label for="birthday">День рождения</label>
            <input type="date" name="birthday" id="birthday" placeholder="${account.birthday!"Не указан"}">

            <input type="submit" value="Сохранить">
        </form>

        <form method="post" action="${contextPath}/exit">
            <input type="submit" value="Выйти">
        </form>
        <div id="alertMessage"></div>

        <#if errors??>
            <#list errors as error>
                <span>${error.message}</span>
            </#list>
        </#if>
    </div>

    <script src="${contextPath}/static/js/profileSettingsValidation.js"></script>
</#macro>


<@page/>