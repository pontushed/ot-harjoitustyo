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

**_21.4.2020_**

GUI:n tekeminen on edistynyt. Käytän siinä FxWeaver-kirjastoa, jolla saa JavaFX:n sovitettua yhteen Spring Bootin kanssa, jotta pääsen hyödyntämään Spring Bootin JPA-tietokantaominaisuuksia.

## Komentorivikomennot

### Aja ohjelma

```
mvn -q spring-boot:run
```

Ensimmäistä kertaa ajettaessa tietokanta alustetaan esimerkkidatalla. Kirjaudu sisään käyttäjänimellä 'kayttaja1','kayttaja2','kayttaja3,'kayttaja4' tai 'kayttaja5'

### Suorita testit

```
mvn test
```

Luo Jacocon testikattavuusraportti

```
mvn test jacoco:report
```

**Huom! Törmäsin ongelmaan, Jacoco Report näyttää kattavuudeksi 0%, vaikka sen pitäisi olla 90-100%**

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

Suoritettava .jar-tiedosto löytyy hakemistosta

> /target/Vuoronvaihto-1.0-SNAPSHOT.jar
