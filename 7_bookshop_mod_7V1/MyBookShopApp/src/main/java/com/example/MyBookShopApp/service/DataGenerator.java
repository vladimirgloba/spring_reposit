package com.example.MyBookShopApp.service;


import com.example.MyBookShopApp.data.*;
import com.example.MyBookShopApp.repository.*;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class DataGenerator {
    @Autowired
    GenreRepository genreRepository;
    @Autowired
    BookService bookService;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManagerRepository em;

    @Autowired
    Book2GenreRepository book2GenreRepository;

    @Autowired
    Book2TagRepository book2TagRepository;
    @Autowired
    BookReviewRepository bookReviewRepository;
    @Autowired
    BookReviewLikeRepository bookReviewLikeRepository;
    @Autowired
    RatingEntityRepository ratingEntityRepository;
    @Autowired
    BookFileRepository bookFileRepository;

    public void insertInBookFile() {
        List<Book> bookList = bookService.getBooksData();
        Faker faker = new Faker();
        Integer integer = 1;
        for (Book book : bookList) {
            int i = 1;

            while (i <= 3) {
                BookFile bookFile = new BookFile();
                bookFile.setBook(book);
                bookFile.setHash(book.getSlug());
                bookFile.setId(integer);
                bookFile.setTypeId(i);
                bookFile.setPath(faker.app().name());
                bookFileRepository.save(bookFile);
                i++;
                integer++;
            }
        }
    }

    private Book2UserEntity generatorForOneEntity(Faker faker) {
        java.util.Date dateNow = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        java.util.Date datePrevious = Date.from(LocalDateTime.now().minusMonths(10).atZone(ZoneId.systemDefault()).toInstant());
        Book2UserEntity book2UserEntity = new Book2UserEntity();
        book2UserEntity.setId(Integer.parseInt(faker.number().digits(5)));
        book2UserEntity.setBookId(faker.number().numberBetween(1, 1001));
        book2UserEntity.setUserId(faker.number().numberBetween(1, 50));
        book2UserEntity.setTime(faker.date().between(datePrevious, dateNow)
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        book2UserEntity.setTypeId(faker.number().numberBetween(1, 5));
        return book2UserEntity;
    }

    public List<UserEntity> generateUsers() {
        int i = 1;
        List<UserEntity> buffer = new ArrayList<>();
        Faker faker = new Faker();
        List<String> include = new ArrayList<>();
        while (i < 50) {
            UserEntity user = new UserEntity();
            String name = faker.name().fullName();
            while (include.contains(name)) {
                name = faker.name().fullName();
            }

            int time = (int) (Math.random() * 1000);
            user.setId(i);
            user.setName(name);
            user.setHash(faker.code().gtin8());
            user.setBalance(0);
            user.setRegTime(LocalDateTime.now().minusDays(time));
            i++;
            buffer.add(user);
        }
        UserEntity user = new UserEntity();
        String name = faker.name().fullName();
        while (include.contains(name)) {
            name = faker.name().fullName();
        }

        int time = (int) (Math.random() * 1000);
        user.setId(1111);
        user.setName("unregistered user");
        user.setHash(faker.code().gtin8());
        user.setBalance(0);
        user.setRegTime(LocalDateTime.now());
        buffer.add(user);
        return buffer;
    }

    public void forRating() {
        int i = 1;
        Faker faker = new Faker();
        Set<String> unique = new HashSet<>();
        List<Book> bookList = bookService.getBooksData();
        List<UserEntity> users = (List<UserEntity>) userRepository.findAll();
        for (UserEntity user : users) {
            for (Book book : bookList) {
                if (book.getId() % 2 != 0 && unique.add(user.getId() + " " + book.getId()) && user.getId() != 50) {
                    RatingEntity ratingEntity = new RatingEntity();
                    ratingEntity.setId(i);
                    ratingEntity.setUserId(user.getId());
                    ratingEntity.setBookId(book.getId());
                    ratingEntity.setStars(faker.number().numberBetween(1, 6));
                    ratingEntityRepository.save(ratingEntity);
                    i++;
                    if (i == 10_000) break;
                }
            }
        }
    }

    public void reviewAndLike() {
        int i = 1;
        java.util.Date dateNow = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        java.util.Date datePrevious = Date.from(LocalDateTime.now().minusMonths(10).atZone(ZoneId.systemDefault()).toInstant());
        Faker faker = new Faker();
        while (i <= 10_000) {
            BookReviewLikeEntity likeEntity = new BookReviewLikeEntity();
            BookReviewEntity reviewEntity = new BookReviewEntity();

            int bookId = faker.number().numberBetween(1, 1001);
            if (bookId % 2 != 0) {
                bookId++;
            }
            reviewEntity.setId(i);
            likeEntity.setId(i);
            likeEntity.setReviewId(i);

            reviewEntity.setBookId(bookId);
            reviewEntity.setTime(faker.date().between(datePrevious, dateNow)
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            reviewEntity.setText(faker.lorem().sentence(getRandomNumber(100, 250)));
            Integer userId = faker.number().numberBetween(1, 50);
            reviewEntity.setUserId(userId);
            likeEntity.setUserId(userId);
            likeEntity.setTime(reviewEntity.getTime());
            likeEntity.setReviewId(i);
            int value = faker.number().numberBetween(0, 2);
            if (value == 0) {
                value = -1;
            }
            likeEntity.setValue(value);
            likeEntity.setUserId(reviewEntity.getUserId());
            bookReviewLikeRepository.save(likeEntity);
            bookReviewRepository.save(reviewEntity);
            i++;
        }
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public List<Book2UserEntity> generatorBook2User() {
        Faker faker = new Faker();
        List<Book2UserEntity> book2UserEntityList = new ArrayList<>();
        int i = 10_000;
        while (i != 0) {
            book2UserEntityList.add(generatorForOneEntity(faker));
            i--;
        }

        return book2UserEntityList;
    }

    private List<TagEntity> tagGenerator() {
        Map<Integer, String> genres = new HashMap<>();
        genres.put(49, "современная литература");
        genres.put(50, "классическая литература");
        genres.put(51, "художественная литература");
        genres.put(52, "научная литература");
        genres.put(1, "зарубежная литература");
        genres.put(2, "фэнтези");
        genres.put(3, "английская литература");
        genres.put(4, "русская литература");
        genres.put(5, "американская литература");
        genres.put(6, "фантастика");
        genres.put(7, "детская литература");
        genres.put(8, "детектив");
        genres.put(9, "любовь");
        genres.put(10, "мистика");
        genres.put(11, "юмор");
        genres.put(12, "приключения");
        genres.put(13, "сказка");
        genres.put(14, "фантастика");
        genres.put(15, "французская литература");
        genres.put(16, "англия");
        genres.put(17, "философия");
        genres.put(18, "экранизировано");
        genres.put(19, "любимое");
        genres.put(48, "антиутопия");
        genres.put(20, "books you must read before you die");
        genres.put(21, "роман");
        genres.put(22, "франция");
        genres.put(23, "дайте две");
        genres.put(24, "young adult");
        genres.put(25, "война");
        genres.put(26, "история");
        genres.put(27, "флэшмоб 2016");
        genres.put(28, "детство");
        genres.put(29, "жизнь");
        genres.put(30, "драма");
        genres.put(31, "россия");
        genres.put(32, "книжное");
        genres.put(33, "путешествие");
        genres.put(34, "советская литература");
        genres.put(35, "триллер");
        genres.put(36, "америка");
        genres.put(37, "немецкая");
        genres.put(38, "литература");
        genres.put(39, "вампиры");
        genres.put(40, "школьная программа");
        genres.put(41, "рассказы");
        genres.put(42, "магия");
        genres.put(43, "дети");
        genres.put(44, "русская классика");
        genres.put(45, "флэшмоб 2015");
        genres.put(46, "биография");
        genres.put(47, "english");
        List<TagEntity> entities = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : genres.entrySet()) {
            TagEntity tagEntity = new TagEntity();
            tagEntity.setId(entry.getKey());
            tagEntity.setName(entry.getValue());
            entities.add(tagEntity);
        }
        return entities;
    }

    public void insertDataInTag() {

        tagRepository.saveAll(tagGenerator());
    }

    private Map<String, String> genreGeneratorMap() {
        Map<String, String> mapDependencies = new HashMap<>();
        mapDependencies.put("Лёгкое чтение", "Лёгкое чтение");
        mapDependencies.put("Серьёзное чтение", "Серьёзное чтение");
        mapDependencies.put("Деловая литература", "Деловая литература");
        mapDependencies.put("Фантастика", "Лёгкое чтение");
        mapDependencies.put("Боевики", "Лёгкое чтение");
        mapDependencies.put("Детективы", "Лёгкое чтение");
        mapDependencies.put("Фэнтези", "Лёгкое чтение");
        mapDependencies.put("Романы", "Лёгкое чтение");
        mapDependencies.put("Ужасы", "Лёгкое чтение");
        mapDependencies.put("Приключения", "Лёгкое чтение");
        mapDependencies.put("Триллер", "Детективы");
        mapDependencies.put("Крутой детектив", "Детективы");
        mapDependencies.put("Иронический детектив", "Детективы");
        mapDependencies.put("Про маньяков", "Детективы");
        mapDependencies.put("Шпионский детектив", "Детективы");
        mapDependencies.put("Криминальный детектив", "Детективы");
        mapDependencies.put("Классический детектив", "Детективы");
        mapDependencies.put("Политический детектив", "Детективы");
        mapDependencies.put("Биографии", "Серьёзное чтение");
        mapDependencies.put("Управление экономикой", "Деловая литература");
        mapDependencies.put("Карьера", "Деловая литература");
        mapDependencies.put("Финансы", "Деловая литература");
        mapDependencies.put("Бизнес-справочники", "Деловая литература");
        mapDependencies.put("Личные финансы", "Деловая литература");
        mapDependencies.put("Менеджмент", "Деловая литература");
        mapDependencies.put("Зарубежная деловая литература", "Деловая литература");
        mapDependencies.put("Личная эффективность", "Деловая литература");
        mapDependencies.put("Малый бизнес", "Деловая литература");
        mapDependencies.put("Тайм-менеджмент", "Деловая литература");
        mapDependencies.put("Продажи", "Деловая литература");
        mapDependencies.put("Стартапы и создание бизнеса", "Деловая литература");
        mapDependencies.put("Корпоративная культура", "Деловая литература");
        mapDependencies.put("Банковское дело", "Деловая литература");
        mapDependencies.put("Интернет-бизнес", "Деловая литература");
        mapDependencies.put("Ораторское искусство / риторика", "Деловая литература");
        mapDependencies.put("Привлечение клиентов и лояльность", "Деловая литература");
        mapDependencies.put("Недвижимость", "Деловая литература");
        mapDependencies.put("Делопроизводство", "Деловая литература");
        mapDependencies.put("Переговоры", "Деловая литература");
        mapDependencies.put("Государственное и муниципальное управление, политическое управление", "Деловая литература");
        mapDependencies.put("О бизнесе популярно", "Деловая литература");
        mapDependencies.put("Ценные бумаги / инвестиции", "Деловая литература");
        mapDependencies.put("Бухучет / налогообложение / аудит", "Деловая литература");
        mapDependencies.put("Российская практика", "Деловая литература");
        mapDependencies.put("Истории успеха", "Деловая литература");
        mapDependencies.put("Интернет-маркетинг", "Деловая литература");
        mapDependencies.put("Лидерство", "Деловая литература");
        mapDependencies.put("Проектный менеджмент", "Деловая литература");
        mapDependencies.put("Управление качество", "Деловая литература");
        mapDependencies.put("Маркетинг, PR, реклама", "Деловая литература");
        mapDependencies.put("Финансовый менеджмент", "Деловая литература");
        mapDependencies.put("Управление персоналом", "Деловая литература");
        mapDependencies.put("Бизнес-процессы", "Деловая литература");
        mapDependencies.put("Управление", "Деловая литература");
        mapDependencies.put("Драматургия", "Драматургия");
        mapDependencies.put("Античная драма", "Драматургия");
        mapDependencies.put("Комедия", "Драматургия");
        mapDependencies.put("Сценарий", "Драматургия");
        mapDependencies.put("Драма, пьеса", "Драматургия");
        return mapDependencies;
    }


    private Book2GenreEntity generatorForOneBook2GenreEntity(Faker faker) {
        Book2GenreEntity entity = new Book2GenreEntity();
        entity.setId(Integer.parseInt(faker.number().digits(5)));
        entity.setBookId(faker.number().numberBetween(1, 1001));
        entity.setGenreId(faker.number().numberBetween(1, 60));
        return entity;
    }

    public void genreGenerator() {
        addGenreInBD();
        Faker faker = new Faker();

        List<Integer> idParent = genreRepository.idParentThirdLevel();
        idParent.addAll(genreRepository.idParentFirstLevel());
        Set<Integer> idSet = new HashSet<>();
        while (idSet.size() < 10_000) {
            Book2GenreEntity book2GenreEntity = generatorForOneBook2GenreEntity(faker);
            if (containsInteger(idParent, book2GenreEntity.getGenreId())) {
                book2GenreRepository.save(book2GenreEntity);
                idSet.add(book2GenreEntity.getId());
            }
        }

    }

    private boolean containsInteger(List<Integer> integers, Integer integer) {

        for (Integer value : integers) {
            if (value == integer) return false;
        }
        return true;
    }

    public List<GenreEntity> allGenres() {
        Faker faker = new Faker();

        List<GenreEntity> entities = new ArrayList<>();
        Map<String, Integer> idMap = new HashMap<>();
        int i = 1;
        for (Map.Entry<String, String> entry : genreGeneratorMap().entrySet()) {
            idMap.put(entry.getKey(), i);
            i++;
        }
        for (Map.Entry<String, String> entry : genreGeneratorMap().entrySet()) {
            entities.add(generateOneEntity(entry, idMap, faker));
        }

        return entities;
    }

    private void addGenreInBD() {
        List<GenreEntity> entities = allGenres();
        genreRepository.saveAll(entities);
    }

    private GenreEntity generateOneEntity(Map.Entry<String, String> entry, Map<String, Integer> idMap, Faker faker) {
        GenreEntity entity = new GenreEntity();
        entity.setId(idMap.get(entry.getKey()));
        entity.setSlug(faker.pokemon().name());
        entity.setName(entry.getKey());
        entity.setParentId(idMap.get(entry.getValue()));
        return entity;
    }


    private List<Book2TagEntity> book2ЕTagEntities() {
        Faker faker = new Faker();
        List<Book2TagEntity> entities = new ArrayList<>();
        int i = 10_000;
        while (i != 0) {
            Book2TagEntity entity = new Book2TagEntity();
            entity.setId(Integer.parseInt(faker.number().digits(5)));
            entity.setTagId(faker.number().numberBetween(1, 53));
            entity.setBookId(faker.number().numberBetween(1, 1001));
            entities.add(entity);
            i--;
        }
        return entities;
    }

    public void insertDataInBook2Tag() {
        book2TagRepository.saveAll(book2ЕTagEntities());

    }

    public void setActualData() {
        List<Book> bookList = bookService.getBooksData();
        LocalDate now = LocalDate.now();
        LocalDate before = LocalDate.now().minusMonths(3);
        for (Book book : bookList) {
            String sqlString = "update books  set pub_date = '" + between(before, now) + "'  where id = " + book.getId();
            em.updateDB(sqlString);
        }
    }

    public Date between(LocalDate startInclusive, LocalDate endExclusive) {
        long startEpochDay = startInclusive.toEpochDay();
        long endEpochDay = endExclusive.toEpochDay();
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);
        return Date.valueOf(LocalDate.ofEpochDay(randomDay));
    }
}

