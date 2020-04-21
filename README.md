# Ohjelmistotekniikka kevät 2020, harjoitustyö

## Projekti, Vuoronvaihtosovellus

[Käyttöohje](dokumentointi/kayttoohje.md)

[Vaatimusmäärittely](dokumentointi/vaatimusmaarittely.md)

[Työaikakirjanpito](dokumentointi/tyoaikakirjanpito.md)

[Arkkitehtuuri](dokumentointi/arkkitehtuuri.md)

**_30.3.2020_**

Sovelluksessa on vasta ohjelmoituna muutama luokka ja kaksi testiä. Seuraavaksi teen DAO:n ja tietokantayhteyden, jonka jälkeen alan työstämään GUI:ta.

**_6.4.2020_**

Sovellus on nyt muokattu Spring Boot-sovellukseksi. Käytän JPA:ta tietokantatoimintoihin.
CheckStyle otettu käyttöön.

## Komentorivikomennot

### Aja ohjelma

```
mvn -q spring-boot:run
```

### Suorita testit

```
mvn test
```

Luo Jacocon testikattavuusraportti

```
mvn test jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto target/site/jacoco/index.html

### Suorita Checkstyle-testi

```
mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto target/site/checkstyle.html

### Luo javadoc

```
mvn javadoc:javadoc
```

JavaDocia voi tarkastella avaamalla selaimella tiedosto target/site/apidocs/index.html

### Luo suorituskelpoinen .jar-tiedosto

```
mvn package
```
