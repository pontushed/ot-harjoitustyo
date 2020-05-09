# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen avulla käyttäjät voivat ehdottaa vaihtoja työvuorolistaan sekä tarkistaa työvuoronsa. Sovellus huolehtii siitä, että sekä kuvitteellisen TESin että työaikalainsäädännön mukaisia lepoaikasäännöksiä noudatetaan. Työpaikalla vuoronvaihto on perinteisesti tehty paperiseen vuorolistaan, mutta useiden vuoronvaihtojen jälkeen listan luvussa on välillä ilmennyt epäselvyyksiä ja joskus on käynyt niin, että vuorolistasta ei enää saa selvää kenen pitäisi olla töissä.

Sovellus tarkastaa vuoronvaihtoa suunnitellessa seuraavan pykälän:

**§1 Lepoaika vuorojen välissä (Vuorokausilepo)**
Vuorokausilevon on oltava vähintään 7 tuntia työvuorojen välillä.

Sovelluksen
päämääränä on tarjota käyttäjille helppo tapa yhdistää kysyntä ja tarjonta vuoronvaihtojen toteutumiseksi.

## Käyttäjät ja vuorolistat

Käyttäjät ovat määritelty valmiiksi sovellukseen CSV-tiedostona. Tietokanta alustetaan CSV-tiedostojen
tiedoilla ensimmäisellä käyttökerralla.

## Käyttöliittymäluonnos

**(Tehty)** Sovellus aukeaa kirjautumisnäkymään, josta siirrytään onnistuneen kirjautumisen yhteydessä kirjaantuneen käyttäjän työvuorolistaan. Sovellus ei vaadi salasanaa.

**(Tehty)** Työvuorolistassa voi ottaa yhden työvuoron muutostoiveen kohteeksi. Sovellus listaa vuorot, jotka sopivat vaihdettaviksi.
Käyttäjä voi tehdä muutostoiveen tässä näkymässä.

## Perusversion tarjoama toiminnallisuus

### Ennen kirjautumista

**(Tehty)**

- käyttäjä voi kirjautua järjestelmään
  - kirjautuminen onnistuu syötettäessä olemassaoleva käyttäjätunnus kirjautumislomakkeelle
  - jos käyttäjää ei olemassa, ilmoittaa järjestelmä tästä

### Kirjautumisen jälkeen

- **(Tehty)** käyttäjä näkee omat työvuoronsa

- **(Tehty)** käyttäjä voi luoda uuden vuoronvaihtotoiveen

  - **(Tehty)** vuoronvaihtotoive näkyy potentiaalisille vaihtajille

- **(Tehty)** käyttäjä näkee, ketkä ovat potentiaalisia vaihtajia

- **(Tehty)** käyttäjä näkee, onko hänelle tullut vuoronvaihtoehdotuksia. Sovellus huolehtii, että lepoaikamääräykset toteutuvat kaikissa tapauksissa.

- **(Tehty)** hyväksyessään vuoronvaihtoehdotuksen, muuttuvat vuorot työvuorolistassa.

- **(Tehty)** käyttäjä voi kirjautua ulos järjestelmästä

## Jatkokehitysideoita

Perusversion jälkeen järjestelmää täydennetään ajan salliessa esim. seuraavilla toiminnallisuuksilla:

- käyttäjien yhteyteen salasana, joka vaaditaan kirjautuessa
- viikkolevon tarkistus
- sairastumisen merkintä
- ilmoitukset käyttäjille
- käyttäjien hallinta
- työvuorolistojen tuonti
