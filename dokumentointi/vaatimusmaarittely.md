# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen avulla käyttäjät voivat ehdottaa vaihtoja työvuorolistaan sekä tarkistaa työvuoronsa. Sovellus huolehtii siitä, että sekä TESin että työaikalainsäädännön mukaisia lepoaikasäännöksiä noudatetaan. Sovelluksen
päämääränä on tarjota käyttäjille helppo tapa yhdistää kysyntä ja tarjonta vuoronvaihtojen toteutumiseksi.

## Käyttäjät

Alkuvaiheessa sovelluksella on ainoastaan yksi käyttäjärooli eli _normaali käyttäjä_. Myöhemmin sovellukseen saatetaan lisätä suuremmilla oikeuksilla varustettu _pääkäyttäjä_.

## Käyttöliittymäluonnos

Sovellus aukeaa kirjautumisnäkymään, josta siirrytään onnistuneen kirjautumisen yhteydessä kirjaantuneen käyttäjän työvuorolistaan.

Työvuorolistassa voi ottaa yhden työvuoron muutostoiveen kohteeksi. Sovellus avaa näkymän, jossa haetaan vaihtokandidaatit. Käyttäjä voi tehdä muutostoiveen tässä näkymässä.

## Perusversion tarjoama toiminnallisuus

### Ennen kirjautumista

- käyttäjä voi kirjautua järjestelmään
  - kirjautuminen onnistuu syötettäessä olemassaoleva käyttäjätunnus kirjautumislomakkeelle
  - käyttäjätunnus on uniikki ja se vastaa työpaikan Office365-käyttäjätunnusta.
  - jos käyttäjää ei olemassa, ilmoittaa järjestelmä tästä

### Kirjautumisen jälkeen

- käyttäjä näkee omat työvuoronsa

- käyttäjä voi luoda uuden vuoronvaihtotoiveen

  - vuoronvaihtotoive näkyy potentiaalisille vaihtajille

- käyttäjä näkee, ketkä ovat potentiaalisia vaihtajia

- käyttäjä näkee, onko hänelle tullut vuoronvaihtoehdotuksia. Sovellus huolehtii, että lepoaikamääräykset toteutuvat kaikissa tapauksissa.

- hyväksyessään vuoronvaihtoehdotuksen, muuttuvat vuorot työvuorolistassa.

- käyttäjä voi kirjautua ulos järjestelmästä

## Jatkokehitysideoita

Perusversion jälkeen järjestelmää täydennetään ajan salliessa esim. seuraavilla toiminnallisuuksilla:

- käyttäjien yhteyteen salasana, joka vaaditaan kirjautuessa
- sairastumisen merkintä
- ilmoitukset käyttäjille
