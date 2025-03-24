package com.example.security.spring_security.controller;

import com.example.security.spring_security.model.Service;
import com.example.security.spring_security.model.ServiceCategory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
public class PriceController {

    @GetMapping("/price")
    @ResponseBody
    public String showPriceList() {
        List<ServiceCategory> categories = createPriceList();
        return generateHtml(categories);
    }

    private String generateHtml(List<ServiceCategory> categories) {
        StringBuilder html = new StringBuilder();

        // Начало HTML-документа с подключением стилей из шаблона Lora
        html.append("<!DOCTYPE html>\n")
                .append("<html lang=\"ru\">\n")
                .append("<head>\n")
                .append("    <meta charset=\"UTF-8\">\n")
                .append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n")
                .append("    <title>Цены | Лора Гобова</title>\n")
                .append("    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH\" crossorigin=\"anonymous\">\n")
                .append("    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css\">\n")
                .append("    <link rel=\"stylesheet\" href=\"/styles.css\">\n")
                .append("    <link href=\"https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;700&family=Open+Sans:wght@400;500&display=swap\" rel=\"stylesheet\">\n")
                .append("</head>\n")
                .append("<body>\n");

        // Хедер с классом fixed-top
        html.append("<header class=\"header fixed-top\">\n")
                .append("    <nav class=\"navbar navbar-expand-lg navbar-light\">\n")
                .append("        <div class=\"container\">\n")
                .append("            <a class=\"navbar-brand logo\" href=\"/\">Лора Гобова</a>\n")
                .append("            <button class=\"navbar-toggler\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#navbarNav\" aria-controls=\"navbarNav\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n")
                .append("                <span class=\"navbar-toggler-icon\"></span>\n")
                .append("            </button>\n")
                .append("            <div class=\"collapse navbar-collapse\" id=\"navbarNav\">\n")
                .append("                <ul class=\"navbar-nav ms-auto\">\n")
                .append("                    <li class=\"nav-item\"><a class=\"nav-link\" href=\"/\">Главная</a></li>\n")
                .append("                    <li class=\"nav-item\"><a class=\"nav-link\" href=\"/#services\">Услуги</a></li>\n")
                .append("                    <li class=\"nav-item\"><a class=\"nav-link\" href=\"/#about\">Обо мне</a></li>\n")
                .append("                    <li class=\"nav-item\"><a class=\"nav-link\" href=\"/price\">Цены</a></li>\n")
                .append("                    <li class=\"nav-item\"><a class=\"nav-link\" href=\"/#contacts\">Контакты</a></li>\n")
                .append("                </ul>\n")
                .append("            </div>\n")
                .append("        </div>\n")
                .append("    </nav>\n")
                .append("</header>\n");

        // Секция Цены
        html.append("<section id=\"price\" class=\"price py-5\">\n")
                .append("    <div class=\"container\">\n")
                .append("        <h1 class=\"text-center mb-5\">Цены</h1>\n")
                .append("        <div class=\"row justify-content-center\">\n")
                .append("            <div class=\"col-lg-10 col-md-12\">\n");

        // Генерация списка категорий и услуг
        for (ServiceCategory category : categories) {
            html.append("                <table class=\"table table-striped table-bordered mb-5\">\n")
                    .append("                    <thead>\n")
                    .append("                        <tr>\n")
                    .append("                            <th scope=\"col\" style=\"width: 70%;\">").append(category.getName()).append("</th>\n")
                    .append("                            <th scope=\"col\" style=\"width: 30%;\">Стоимость услуги</th>\n")
                    .append("                        </tr>\n")
                    .append("                    </thead>\n")
                    .append("                    <tbody>\n");

            for (Service service : category.getServices()) {
                String displayName = service.getName() != null ? service.getName() : service.getDescription() != null ? service.getDescription() : "";
                String description = (service.getName() != null && service.getDescription() != null) ? service.getDescription() : "";

                html.append("                        <tr>\n")
                        .append("                            <td>\n")
                        .append("                                <div class=\"service-name\">").append(displayName).append("</div>\n");

                if (!description.isEmpty()) {
                    html.append("                                <div class=\"service-description mt-2\">").append(description).append("</div>\n");
                }

                html.append("                            </td>\n")
                        .append("                            <td class=\"align-middle text-end\">\n")
                        .append("                                <span class=\"price-value\">").append(service.getPrice()).append(" ₽</span>\n")
                        .append("                            </td>\n")
                        .append("                        </tr>\n");
            }

            html.append("                    </tbody>\n")
                    .append("                </table>\n");
        }

        html.append("            </div>\n")
                .append("        </div>\n")
                .append("    </div>\n")
                .append("</section>\n");

        // Футер
        html.append("<footer class=\"footer py-4\">\n")
                .append("    <div class=\"container text-center\">\n")
                .append("        <div class=\"footer-content\">\n")
                .append("            <div class=\"logo\">Лора Гобова</div>\n")
                .append("            <div class=\"socials mt-3\">\n")
                .append("                <a href=\"https://instagram.com/lora_gobova\" target=\"_blank\"><img src=\"/instagram-icon.png\" alt=\"Instagram\" class=\"social-icon\"></a>\n")
                .append("                <a href=\"https://t.me/+79122488565\" target=\"_blank\"><img src=\"/telegram-icon.png\" alt=\"Telegram\" class=\"social-icon\"></a>\n")
                .append("                <a href=\"https://wa.me/79122488565\" target=\"_blank\"><img src=\"/whatsapp-icon.png\" alt=\"WhatsApp\" class=\"social-icon\"></a>\n")
                .append("            </div>\n")
                .append("            <p class=\"mt-3\">© 2025 Все права защищены</p>\n")
                .append("        </div>\n")
                .append("    </div>\n")
                .append("</footer>\n");

        // Чат-виджет
        html.append("<div class=\"chat-widget\">\n")
                .append("    <div class=\"chat-options\" style=\"display: none;\">\n")
                .append("        <a href=\"https://wa.me/79122488565\" target=\"_blank\" aria-label=\"Чат в WhatsApp\">\n")
                .append("            <i class=\"fab fa-whatsapp\"></i> WhatsApp\n")
                .append("        </a>\n")
                .append("        <a href=\"https://t.me/+79122488565\" target=\"_blank\" aria-label=\"Чат в Telegram\">\n")
                .append("            <i class=\"fab fa-telegram\"></i> Telegram\n")
                .append("        </a>\n")
                .append("    </div>\n")
                .append("    <button class=\"chat-button\" aria-label=\"Открыть варианты чата\">Чат</button>\n")
                .append("</div>\n");

        // JavaScript для чат-виджета и хедера
        html.append("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz\"Begins\" crossorigin=\"anonymous\"></script>\n")
                .append("<script>\n")
                .append("    document.addEventListener('DOMContentLoaded', function() {\n")
                .append("        document.querySelector('.chat-button').addEventListener('click', function() {\n")
                .append("            var options = document.querySelector('.chat-options');\n")
                .append("            options.style.display = options.style.display === 'none' ? 'block' : 'none';\n")
                .append("        });\n")
                .append("    });\n")
                .append("</script>\n");

        // JavaScript для исчезающего хедера
        html.append("<script>\n")
                .append("    let lastScroll = 0;\n")
                .append("    window.addEventListener('scroll', () => {\n")
                .append("        const currentScroll = window.pageYOffset;\n")
                .append("        if (currentScroll > lastScroll && currentScroll > 90) {\n")
                .append("            document.querySelector('.header').style.top = '-90px';\n")
                .append("        } else {\n")
                .append("            document.querySelector('.header').style.top = '0';\n")
                .append("        }\n")
                .append("        lastScroll = currentScroll;\n")
                .append("    });\n")
                .append("</script>\n");

        // Завершение HTML
        html.append("</body>\n")
                .append("</html>");

        return html.toString();
    }

    private List<ServiceCategory> createPriceList() {
        // Категория 1: SMAS-Лифтинг
        Service smasLiftingFace = new Service();
        smasLiftingFace.setDescription("Лицо полностью");
        smasLiftingFace.setPrice(7000);

        Service smasLiftingEyes = new Service();
        smasLiftingEyes.setDescription("Область глаз");
        smasLiftingEyes.setPrice(2500);

        Service smasLiftingMid = new Service();
        smasLiftingMid.setDescription("Брыли, щеки, область второго подбородка");
        smasLiftingMid.setPrice(5000);

        Service smasLiftingFull = new Service();
        smasLiftingFull.setDescription("Лицо, глаза, шея");
        smasLiftingFull.setPrice(8000);

        Service smasLiftingOverFull = new Service();
        smasLiftingOverFull.setDescription("Глаза, лицо, шея, декольте");
        smasLiftingOverFull.setPrice(10000);

        ServiceCategory smasLifting = new ServiceCategory("SMAS-Лифтинг", Arrays.asList(
                smasLiftingFace, smasLiftingEyes, smasLiftingMid, smasLiftingFull, smasLiftingOverFull
        ));

        // Категория 2: Микроигольчатый RF-Лифтинг
        Service rfMicroneedlingFace = new Service();
        rfMicroneedlingFace.setDescription("Лицо полностью");
        rfMicroneedlingFace.setPrice(8000);

        Service rfMicroneedlingEyes = new Service();
        rfMicroneedlingEyes.setDescription("Глаза");
        rfMicroneedlingEyes.setPrice(5000);

        Service rfMicroneedlingMid = new Service();
        rfMicroneedlingMid.setDescription("Глаза, лицо, шея");
        rfMicroneedlingMid.setPrice(10000);

        Service rfMicroneedlingFull = new Service();
        rfMicroneedlingFull.setDescription("Лицо, шея, декольте");
        rfMicroneedlingFull.setPrice(13000);

        Service rfMicroneedlingHands = new Service();
        rfMicroneedlingHands.setDescription("Кисти рук");
        rfMicroneedlingHands.setPrice(6000);

        Service rfMicroneedlingStomach = new Service();
        rfMicroneedlingStomach.setDescription("Живот");
        rfMicroneedlingStomach.setPrice(8000);

        ServiceCategory rfMicroneedling = new ServiceCategory("Микроигольчатый RF-Лифтинг", Arrays.asList(
                rfMicroneedlingFace, rfMicroneedlingEyes, rfMicroneedlingMid,
                rfMicroneedlingFull, rfMicroneedlingHands, rfMicroneedlingStomach
        ));

        // Категория 3: Пилинги SESDERMA (MEDI + DERMA) Испания
        Service mindalPilling = new Service();
        mindalPilling.setName("МИНДАЛЬНЫЙ ПИЛИНГ - MANDELAC 40%");
        mindalPilling.setPrice(3500);

        Service bioRevitPilling = new Service();
        bioRevitPilling.setName("ПИЛЛИНГ БИОРЕВИТАЛИЗАНТ - C-PEEL");
        bioRevitPilling.setDescription("Это всесезонный пилинг для любого типа кожи и возраста с активными ингредиентами, " +
                "инкапсулированными в наносомы. По эффекту сопоставим с биоревитализацией гиалуроновой кислотой.");
        bioRevitPilling.setPrice(4000);

        Service azelainPilling = new Service();
        azelainPilling.setName("АЗЕЛАИНОВЫЙ ПИЛИНГ");
        azelainPilling.setPrice(3500);

        Service milkPilling = new Service();
        milkPilling.setName("МОЛОЧНЫЙ ПИЛИНГ - LACTIPEEL 80% C ДОБАВКОЙ RESVERADERM POWDER");
        milkPilling.setDescription("С целью достижения наилучшего лечебного эффекта путем внедрения чистых активных компонентов, " +
                "усиливающих эффект от пилинга");
        milkPilling.setPrice(3000);

        Service retinolYellowPilling = new Service();
        retinolYellowPilling.setName("РЕТИНОЛОВЫЙ ЖЁЛТЫЙ ПИЛИНГ С СИСТЕМОЙ 3-РЕТИНОЛ");
        retinolYellowPilling.setDescription("3-RetisestCT - это наиболее эффективное средство для борьбы с основными " +
                "признаками старения на основании липосомированного ретинола");
        retinolYellowPilling.setPrice(4500);

        Service retisesCTPilling = new Service();
        retisesCTPilling.setName("РЕТИНОЛОВЫЙ RETISES CT ПИЛИНГ");
        retisesCTPilling.setPrice(4000);

        Service salicilPilling = new Service();
        salicilPilling.setName("САЛИЦИЛОВЫЙ ПИЛИНГ + SALIPEPEEL PLUS С АЗЕЛАИНОВОЙ КИСЛОТОЙ И ЭКСТРАКТАМИ КАМЕЛИИ");
        salicilPilling.setDescription("Обладает противовоспалительным, противозудным, антисептическим, антимикробным, " +
                "уменьшает P.Acne, вяжущим, омолаживающим и фотозащитными свойствами");
        salicilPilling.setPrice(3500);

        Service salicilNanoPilling = new Service();
        salicilNanoPilling.setName("САЛИЦИЛОВЫЙ НАНО ПИЛИНГ SALIPEEL LIC");
        salicilNanoPilling.setDescription("Система наносомальных пилингов на основе салициловой, лимонной " +
                "и линолевой кислот. Предназначен для борьбы с акне средней тяжести, кератоза, фолликулита, розацеи, " +
                "псориаза, себореи кожи и волосистой части головы");
        salicilNanoPilling.setPrice(3500);

        Service jesnerPilling = new Service();
        jesnerPilling.setName("ПИЛИНГ ДЖЕССНЕРА");
        jesnerPilling.setDescription("Смесь гидроксикислот молочной, лимонной, салициловой и резорцина");
        jesnerPilling.setPrice(4000);

        Service coffeePilling = new Service();
        coffeePilling.setName("КОФЕЙНЫЙ ПИЛИНГ + FORTE-NOMEKAN CAFEICO FORTE");
        coffeePilling.setDescription("Процедура прогрессивного омоложения кожи");
        coffeePilling.setPrice(4600);

        Service dnaPilling = new Service();
        dnaPilling.setName("ПИЛИНГ ДЛЯ ВОССТАНОВЛЕНИЯ ДНК КОЖИ DNA RECOVERY PEEL SYSTEM");
        dnaPilling.setPrice(3500);

        Service tcaPilling = new Service();
        tcaPilling.setName("ПИЛИНГ ТСА 25%");
        tcaPilling.setPrice(3000);

        ServiceCategory pilings = new ServiceCategory("Пилинги SESDERMA (MEDI + DERMA) Испания", Arrays.asList(
                mindalPilling, bioRevitPilling, azelainPilling, milkPilling, retinolYellowPilling,
                retisesCTPilling, salicilPilling, salicilNanoPilling, jesnerPilling, coffeePilling, dnaPilling, tcaPilling
        ));

        // Категория 4: Восстановительные программы
        Service museCristina = new Service();
        museCristina.setName("УКРЕПЛЕНИЕ ЗАЩИТНЫХ СИСТЕМ КОЖИ И ПОВЫШЕНИЕ ЕЁ СОПРОТИВЛЯЕМОСТИ MUSE CRISTINA");
        museCristina.setDescription("Действие косметических препаратов направлено на восстановление гидролипидной мантии, " +
                "повышение иммунитета кожи и её антиоксидантного потенциала, нормализацию работы клеточных систем " +
                "и предотвращение преждевременного старения");
        museCristina.setPrice(3000);

        Service wishCristina = new Service();
        wishCristina.setName("КОРРЕКЦИЯ ВОЗРАСТНЫХ ИЗМЕНЕНИЙ WISH CRISTINA");
        wishCristina.setDescription("Это инновационная серия препаратов, направленная на замедление процесса старения кожи " +
                "путём повышения эффективности работы её естественного противовозрастного механизма.");
        wishCristina.setPrice(4000);

        Service bioFitoCristina = new Service();
        bioFitoCristina.setName("УХОД ЗА ЧУВСТВИТЕЛЬНОЙ, РАЗДРАЖЕННОЙ КОЖЕЙ И КОЖЕЙ С ПРИЗНАКАМИ КУПЕРОЗА BIO PHYTO CRISTINA");
        bioFitoCristina.setDescription("В основе линии лежит уникальный союз науки и природы, помогающий коже бороться с " +
                "неблагоприятным воздействием окружающей среды изнутри и защищающий от её негативных факторов");
        bioFitoCristina.setPrice(3300);

        Service glow = new Service();
        glow.setName("ФЛЕШ ПРОЦЕДУРА НА ВЫХОД В СВЕТ - МОДЕЛИРУЮЩИЙ СПА КОМПЛЕКС \"СИЯНИЕ\" С ХИРОМАССАЖЕМ ЛИЦА");
        glow.setPrice(3500);

        Service atravmaticClean = new Service();
        atravmaticClean.setName("АТРАВМАТИЧНАЯ ЧИСТКА ЛИЦА HOLY LAND (Израиль)");
        atravmaticClean.setDescription("Это комплексная процедура, позволяющая очистить, выровнять и освежить кожу " +
                "без механической травмы. Полезна людям с различным состоянием кожи. Способствует выравниванию цвета " +
                "и текстуры, уменьшению покраснения и воспаления, активирует обмен веществ, сопровождается эффектом лифтинга кожи");
        atravmaticClean.setPrice(3300);

        Service massage = new Service();
        massage.setName("ХИРОМАССАЖ ЛИЦА, ВОРОТНИКОВОЙ ЗОНЫ И ЗОНЫ ДЕКОЛЬТЕ");
        massage.setDescription("Это уникальная методика, при которой естественным образом происходит изменение " +
                "течения биохимических процессов в тканях. Благодаря перераспределению жидкости в тканях, активации " +
                "микроциркуляции, мощному релаксирующему эффекту. Хиромассаж может заменить миостимуляцию и некоторые " +
                "современные методы пластической хирургии");
        massage.setPrice(2000);

        Service blefaroLift = new Service();
        blefaroLift.setName("БЛЕФАРОЛИФТ-МАССАЖ");
        blefaroLift.setDescription("Или как сделать глаза больше, лицо моложе, а жизнь счастливее");
        blefaroLift.setPrice(2000);

        ServiceCategory recovery = new ServiceCategory("Восстановительные программы", Arrays.asList(
                museCristina, wishCristina, bioFitoCristina, glow, atravmaticClean, massage, blefaroLift
        ));

        return Arrays.asList(smasLifting, rfMicroneedling, pilings, recovery);
    }
}