package com.example.demoShop;

import com.example.demoShop.entity.Product;
import com.example.demoShop.repository.ProductRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    private final ProductRepository productRepository;

    public DataInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (productRepository.count() > 0) return;

        // БУКЕТИ
        productRepository.save(product("Букет червоних троянд",    "Класичний букет з 11 оксамитових червоних троянд — вічна класика кохання",           950,  "https://images.unsplash.com/photo-1519378058457-4c29a0a2efac?w=600",  "bouquets",     "Хіт",         15));
        productRepository.save(product("Піоновий букет",           "Розкішний букет з 9 білих піонів із ніжним ароматом",                               1100,  "images/lobach.jpg",  "bouquets",     "Новинка",      8));
        productRepository.save(product("Польовий мікс",            "Ніжний букет із ромашок, лаванди та польових квітів",                                620,  "https://images.unsplash.com/photo-1490750967868-88aa4486c946?w=600",  "bouquets",     null,          10));
        productRepository.save(product("Романтичний букет",        "Пастельний мікс із троянд, евкаліпту та маттіоли",                                   980,  "https://images.unsplash.com/photo-1487530811015-780780169993?w=600",  "bouquets",     null,          12));
        productRepository.save(product("Сонячний букет",           "Яскравий букет із соняшників та жовтих троянд — заряд гарного настрою",              820,  "https://images.unsplash.com/photo-1543466835-00a7907e9de1?w=600",     "bouquets",     null,          14));
        productRepository.save(product("Святковий мікс",           "Розкішна збірка з троянд, лізіантусу та гіперикуму",                                1300,  "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=600",  "bouquets",     "Топ продажів",  9));
        productRepository.save(product("Монобукет лізіантус",      "Ніжний монобукет із фіолетового лізіантусу — витончено і стильно",                   750,  "https://images.unsplash.com/photo-1525310072745-f49212b5ac6d?w=600",  "bouquets",     null,          11));
        productRepository.save(product("Білі троянди",             "Мінімалістичний букет із 7 білих троянд — елегантність у кожній пелюстці",           870,  "https://images.unsplash.com/photo-1548460093-58dca9a303e4?w=600",    "bouquets",     null,          13));

        // ТЮЛЬПАНИ
        productRepository.save(product("Весняні тюльпани",         "Свіжий букет із 15 яскраво-рожевих тюльпанів",                                       750,  "https://images.unsplash.com/photo-1508610048659-a06b669e3321?w=600",  "tulips",       null,          20));
        productRepository.save(product("Ніжні тюльпани",           "Пастельний букет із білих та кремових тюльпанів",                                    700,  "https://images.unsplash.com/photo-1518895949257-7621c3c786d7?w=600",  "tulips",       null,          18));
        productRepository.save(product("Бордові тюльпани",         "Насичений букет із 11 бордових тюльпанів — пристрасть і глибина",                    780,  "https://images.unsplash.com/photo-1462275646964-a0e3386b89fa?w=600",  "tulips",       "Новинка",     15));
        productRepository.save(product("Мікс тюльпанів",           "Різнобарвний букет із 20 тюльпанів — весняний настрій у кожному кольорі",            850,  "https://images.unsplash.com/photo-1520763185298-1b434c919102?w=600",  "tulips",       "Хіт",         25));

        // РОСЛИНИ
        productRepository.save(product("Орхідея біла",             "Горщикова орхідея фаленопсис — вишуканий подарунок, що цвіте місяцями",               450,  "https://images.unsplash.com/photo-1566907225472-514215c51b00?w=600",  "plants",       null,          20));
        productRepository.save(product("Монстера",                 "Декоративна монстера у стильному горщику — тренд сучасного інтер'єру",                380,  "https://images.unsplash.com/photo-1614594975525-e45190c55d0b?w=600",  "plants",       "Хіт",         18));
        productRepository.save(product("Сукулент набір",           "Набір із 3 різних сукулентів у керамічних горщиках",                                  290,  "https://images.unsplash.com/photo-1459156212016-c812468e2115?w=600",  "plants",       null,          25));
        productRepository.save(product("Фікус Бенджаміна",         "Елегантна кімнатна рослина заввишки 60 см у плетеному кашпо",                         550,  "https://images.unsplash.com/photo-1416879595882-3373a0480b5b?w=600",  "plants",       null,          10));
        productRepository.save(product("Пахіра",                   "«Грошове дерево» у горщику — символ достатку та гарний декор",                        420,  "https://images.unsplash.com/photo-1485955900006-10f4d324d411?w=600",  "plants",       "Новинка",     12));
        productRepository.save(product("Замiокулькас",             "Невибаглива рослина з глянцевим листям — ідеально для офісу чи дому",                 350,  "https://images.unsplash.com/photo-1501004318641-b39e6451bec6?w=600",  "plants",       null,          15));

        // ЕКЗОТИКА
        productRepository.save(product("Екзотичний букет",         "Тропічний мікс із протеями, стреліціями та пальмовим листям",                        1450,  "https://i.pinimg.com/736x/8e/71/0e/8e710ec5931235915edfc0fa274fbfaa.jpg", "exotic", "Преміум",      6));
        productRepository.save(product("Антуріум червоний",        "Яскравий антуріум у горщику — розкіш тропіків у вашому домі",                         490,  "https://images.unsplash.com/photo-1612540139150-4b1b7a809d4e?w=600",  "exotic",       null,          14));
        productRepository.save(product("Стреліція",                "«Квітка-птах» — неймовірна екзотична рослина із Південної Африки",                    680,  "https://images.unsplash.com/photo-1598880940080-ff9a29891b85?w=600",  "exotic",       "Новинка",      8));
        productRepository.save(product("Протея букет",             "Преміум-букет із протей та сухоцвітів — стильно, довговічно, незабутньо",             1200,  "https://images.unsplash.com/photo-1559181567-c3190ca9d5db?w=600",     "exotic",       "Преміум",      5));

        // КОМПОЗИЦІЇ
        productRepository.save(product("Весільна композиція",      "Елегантна настільна композиція для урочистостей із троянд та евкаліпту",             1500,  "https://images.unsplash.com/photo-1525310072745-f49212b5ac6d?w=600",  "compositions", null,           5));
        productRepository.save(product("Кошик квітів",             "Плетений кошик із сезонними квітами — теплий і затишний подарунок",                  1100,  "https://images.unsplash.com/photo-1444021465936-c6ca81d39b84?w=600",  "compositions", "Хіт",          7));
        productRepository.save(product("Квіткова коробка",         "Стильна капелюшна коробка з трояндами та піонами — wow-ефект гарантований",          1350,  "https://images.unsplash.com/photo-1582794543139-8ac9cb0f7b11?w=600",  "compositions", "Топ продажів", 6));
        productRepository.save(product("Міні-сад у кашпо",         "Акуратна композиція із сукулентів та мохового декору — вічна краса без догляду",      480,  "https://images.unsplash.com/photo-1459156212016-c812468e2115?w=600",  "compositions", "Новинка",     10));
    }

    private Product product(String name, String desc, int price, String img, String category, String badge, int stock) {
        Product p = new Product();
        p.setName(name);
        p.setDescription(desc);
        p.setPrice(price);
        p.setImageUrl(img);
        p.setCategory(category);
        p.setBadge(badge);
        p.setStock(stock);
        return p;
    }
}