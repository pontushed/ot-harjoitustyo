# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen avulla käyttäjät voivat ehdottaa vaihtoja työvuorolistaan sekä tarkistaa työvuoronsa. Sovellus huolehtii siitä, että sekä TESin että työaikalainsäädännön mukaisia lepoaikasäännöksiä noudatetaan. Ohjelman
päämääränä on tarjota käyttäjille helppo tapa yhdistää kysyntä ja tarjonta vuoronvaihtojen toteutumiseksi. Sovellus

## Käyttäjät

Alkuvaiheessa sovelluksella on ainoastaan yksi käyttäjärooli eli _normaali käyttäjä_. Myöhemmin sovellukseen saatetaan lisätä suuremmilla oikeuksilla varustettu _pääkäyttäjä_.

## Käyttöliittymäluonnos

Sovellus koostuu kolmesta eri näkymästä

Sovellus aukeaa kirjautumisnäkymään, josta on mahdollista siirtyä uuden käyttäjän luomisnäkymään tai onnistuneen kirjautumisen yhteydessä kirjaantuneen käyttäjän työvuorolistaan.

## Perusversion tarjoama toiminnallisuus

### Ennen kirjautumista

- käyttäjä voi luoda järjestelmään käyttäjätunnuksen

  - käyttäjätunnuksen täytyy olla uniikki ja sen pitää vastata työpaikan Office365-käyttäjätunnusta.

- käyttäjä voi kirjautua järjestelmään
  - kirjautuminen onnistuu syötettäessä olemassaoleva käyttäjätunnus kirjautumislomakkeelle
  - jos käyttäjää ei olemassa, ilmoittaa järjestelmä tästä

### Kirjautumisen jälkeen

- käyttäjä näkee omat työvuoronsa

- käyttäjä voi luoda uuden vuoronvaihtotoiveen

  - vuoronvaihtotoive näkyy potentiaalisille vaihtajille

- käyttäjä näkee, ketkä ovat potentiaalisia vaihtajia

- käyttäjä näkee, onko hänelle tullut vuoronvaihtoehdotuksia. Sovellus huolehtii, että lepoaikamääräykset toteutuvat kaikissa tapauksissa.

- hyväksyessään vuoronvaihtoehdotuksen, muuttuuvat vuorot työvuorolistassa.

- käyttäjä voi kirjautua ulos järjestelmästä

## Jatkokehitysideoita

Perusversion jälkeen järjestelmää täydennetään ajan salliessa esim. seuraavilla toiminnallisuuksilla:

- käyttäjien yhteyteen salasana, joka vaaditaan kirjautuessa
- sairastumisen merkintä
- ilmoitukset käyttäjille
