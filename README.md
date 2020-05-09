# Vuoronvaihtosovellus

### Ohjelmistotekniikka kevät 2020, harjoitustyö

Sovelluksen avulla käyttäjät voivat ehdottaa vaihtoja työvuorolistaan sekä tarkistaa työvuoronsa. Sovellus huolehtii siitä, että sekä kuvitteellisen TESin että työaikalainsäädännön mukaisia lepoaikasäännöksiä noudatetaan. Työpaikalla vuoronvaihto on perinteisesti tehty paperiseen vuorolistaan, mutta useiden vuoronvaihtojen jälkeen listan luvussa on välillä ilmennyt epäselvyyksiä ja joskus on käynyt niin, että vuorolistasta ei enää saa selvää kenen pitäisi olla töissä.

## Release

[Release](https://github.com/pontushed/ot-harjoitustyo/releases/tag/final)

## Dokumentaatio

[Käyttöohje](dokumentointi/kayttoohje.md)

[Vaatimusmäärittely](dokumentointi/vaatimusmaarittely.md)

[Työaikakirjanpito](dokumentointi/tyoaikakirjanpito.md)

[Arkkitehtuuri](dokumentointi/arkkitehtuuri.md)

[Testaus](dokumentointi/testaus.md)

## Komentorivikomennot

### Aja ohjelma

```
mvn -q spring-boot:run
```

Ensimmäistä kertaa ajettaessa tietokanta alustetaan esimerkkidatalla. Kirjaudu sisään käyttäjänimellä 'kayttaja1','kayttaja2','kayttaja3,'kayttaja4' tai 'kayttaja5'

### Suorita testit ja luo JaCoCo-testikattavuusraportti

```
mvn test
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

Suoritettava .jar-tiedosto löytyy hakemistosta

> /target/Vuoronvaihto-1.0-SNAPSHOT.jar

Suoritus:

```
java -jar target/Vuoronvaihto-1.0-SNAPSHOT.jar
```

Ohjelma olettaa, että alustusdata löytyy alihakemistosta `./data`.
