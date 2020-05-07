# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen avulla käyttäjät voivat ehdottaa vaihtoja työvuorolistaan sekä tarkistaa työvuoronsa. Sovellus huolehtii siitä, että sekä kuvitteellisen TESin että työaikalainsäädännön mukaisia lepoaikasäännöksiä noudatetaan.

Sovellus tarkastaa vuoronvaihtoa suunnitellessa seuraavat pykälät:

**§1 Viikkolepo**
Vapaa-aika on yhtäjaksoisesti vähintään 24 tuntia kalenteriviikossa.

**§2 Lepoaika vuorojen välissä (Vuorokausilepo)**
Vuorokausilevon on oltava vähintään 7 tuntia työvuorojen välillä.

Sovelluksen
päämääränä on tarjota käyttäjille helppo tapa yhdistää kysyntä ja tarjonta vuoronvaihtojen toteutumiseksi.

## Käyttäjät

Käyttäjät ovat määritelty valmiiksi sovellukseen CSV-tiedostona.

## Käyttöliittymäluonnos

**(Tehty)** Sovellus aukeaa kirjautumisnäkymään, josta siirrytään onnistuneen kirjautumisen yhteydessä kirjaantuneen käyttäjän työvuorolistaan.

**(Tehty)** Työvuorolistassa voi ottaa yhden työvuoron muutostoiveen kohteeksi. Sovellus listaa vuorot, jotka sopivat vaihdettaviksi.
Käyttäjä voi tehdä muutostoiveen tässä näkymässä.

## Perusversion tarjoama toiminnallisuus

### Ennen kirjautumista

**(Tehty)**

- käyttäjä voi kirjautua järjestelmään
  - kirjautuminen onnistuu syötettäessä olemassaoleva käyttäjätunnus kirjautumislomakkeelle
  - jos käyttäjää ei olemassa, ilmoittaa järjestelmä tästä

### Kirjautumisen jälkeen

**(Tehty)**

- käyttäjä näkee omat työvuoronsa

- käyttäjä voi luoda uuden vuoronvaihtotoiveen

  - vuoronvaihtotoive näkyy potentiaalisille vaihtajille

- **(Tehty)** käyttäjä näkee, ketkä ovat potentiaalisia vaihtajia

- käyttäjä näkee, onko hänelle tullut vuoronvaihtoehdotuksia. Sovellus huolehtii, että lepoaikamääräykset toteutuvat kaikissa tapauksissa.

- hyväksyessään vuoronvaihtoehdotuksen, muuttuvat vuorot työvuorolistassa.

- **(Tehty)** käyttäjä voi kirjautua ulos järjestelmästä

## Jatkokehitysideoita

Perusversion jälkeen järjestelmää täydennetään ajan salliessa esim. seuraavilla toiminnallisuuksilla:

- käyttäjien yhteyteen salasana, joka vaaditaan kirjautuessa
- sairastumisen merkintä
- ilmoitukset käyttäjille
- käyttäjien hallinta
- työvuorolistojen tuonti
