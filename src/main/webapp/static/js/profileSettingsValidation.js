document.getElementById('profileForm').addEventListener('submit', function(event) {
    const name = document.getElementById('name').value.trim();
    const email = document.getElementById('email').value.trim();
    const phoneNumber = document.getElementById('phoneNumber').value.trim();
    const password = document.getElementById('password').value.trim();
    const status = document.getElementById('status').value.trim();
    const birthday = document.getElementById('birthday').value.trim();

    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    const phonePattern = /^\+7\(\d{3}\)\d{3}-\d{2}-\d{2}$|^\+7\d{10}$/;

    if (email.length>0 && !emailPattern.test(email)) {
        showAlert('Некорректный формат почты.');
        event.preventDefault();
        return;
    }

    if (phoneNumber>0 && !phonePattern.test(phoneNumber)) {
        showAlert('Некорректный формат номера телефона. Пример: +7(123)456-78-90 или +71234567890');
        event.preventDefault();
        return;
    }

    if (password.length > 0 && password.length < 6) {
        showAlert('Пароль должен содержать минимум 6 символов.');
        event.preventDefault();
        return;
    }

    if (!name && !email && !phoneNumber && !password && !status && !birthday) {
        event.preventDefault();
        const alertMessage = document.getElementById('alertMessage');
        alertMessage.textContent = 'Форма не может быть полностью пустой. Пожалуйста, заполните хотя бы одно поле.';
    }else{
        showAlert('Форма успешно отправлена!', 6000,);
    }

});

document.addEventListener("DOMContentLoaded", function () {
    const statusInput = document.getElementById("status");
    const statusCounter = document.getElementById("statusCounter");
    const maxLength = 100;

    statusInput.addEventListener("input", function () {
        const amount = statusInput.value.length;
        statusCounter.textContent = `${amount}/100`;
    });
})

function showAlert(message, duration = 3000) {
    const alertDiv = document.getElementById('alertMessage');
    alertDiv.textContent = message;
    alertDiv.style.display = 'block';

    setTimeout(() => {
        alertDiv.style.display = 'none';
    }, duration);
}

