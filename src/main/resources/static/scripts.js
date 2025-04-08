// Чат-виджет
document.querySelector('.chat-button').addEventListener('click', function () {
    var options = document.querySelector('.chat-options');
    options.style.display = options.style.display === 'none' ? 'block' : 'none';
});

// Плавающий хедер
let lastScroll = 0;
window.addEventListener('scroll', () => {
    const currentScroll = window.pageYOffset;
    if (currentScroll > lastScroll && currentScroll > 90) {
        document.querySelector('.header').style.top = '-90px'; // Скрыть
    } else {
        document.querySelector('.header').style.top = '0'; // Показать
    }
    lastScroll = currentScroll;
});

// Сворачивание меню и стили для .about-link
document.addEventListener('DOMContentLoaded', function () {
    const navbarToggler = document.querySelector('.navbar-toggler');
    const navbarCollapse = document.querySelector('#navbarNav');
    const navLinks = document.querySelectorAll('.nav-link');
    const aboutLink = document.querySelector('.about-link');
    const aboutLinkChildren = aboutLink ? aboutLink.querySelectorAll('h2, p, .lead') : [];

    // Сворачивание меню при клике на ссылку
    navLinks.forEach(link => {
        link.addEventListener('click', () => {
            if (navbarCollapse.classList.contains('show')) {
                const bsCollapse = new bootstrap.Collapse(navbarCollapse, {
                    toggle: false
                });
                bsCollapse.hide();
            }
        });
    });

    // Сворачивание меню при скролле на мобильных устройствах
    window.addEventListener('scroll', () => {
        if (window.innerWidth <= 992 && navbarCollapse.classList.contains('show')) {
            const bsCollapse = new bootstrap.Collapse(navbarCollapse, {
                toggle: false
            });
            bsCollapse.hide();
        }
    });

    // Принудительное применение стилей для .about-link
    if (aboutLink) {
        aboutLink.style.color = '#000';
        aboutLink.style.textDecoration = 'none';

        aboutLinkChildren.forEach(child => {
            child.style.color = '#000';
            child.style.textDecoration = 'none';
        });

        aboutLink.addEventListener('mouseover', function() {
            this.style.color = '#000';
            this.style.textDecoration = 'none';
        });
        aboutLink.addEventListener('mouseout', function() {
            this.style.color = '#000';
            this.style.textDecoration = 'none';
        });
        aboutLink.addEventListener('click', function() {
            this.style.color = '#000';
            this.style.textDecoration = 'none';
        });
    }
});