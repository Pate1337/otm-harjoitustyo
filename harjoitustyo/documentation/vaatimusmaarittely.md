# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus on peli, joka poikkeaa vanhasta klassikosta Space Invadersistä siinä määrin, että vihollisalukset lähenevät pelaajaa ruudun yläreunasta yksitellen. Pelaajan tavoitteena on ampua mahdollisimman monta vihollisalusta, ennen kuin tämän "elämät" loppuvat.

## Käyttäjät

Kaikki käyttäjät ovat pelin pelaajia.

## Perusversion tarjoama toiminnallisuus

### Ennen pelin alkua
* Käyttäjä näkee pelin alkuruudun, jossa vaihtoehdot "Play now" ja "High Scores"
* Jos käyttäjä painaa "High Scores"
  * Näytetään tietokannassa olevat Top10 tulokset
  * Näytetään painike "Back to menu"
### Pelin alkaessa
* Ruudun vasemmassa yläkulmassa näytetään pelaajan jäljellä olevat elämät (3 elämää)
* Ruudun oikeassa yläkulmassa näytetään pelaajan pisteet
* Pelaaja liikku horisontaalisesti ruudun alalaidassa A ja D näppäimillä
* Vihollisalkuset tulevat yksi kerrallaan ruudun ylälaidasta
  * Liikkuvat suoraan alaspäin
  * Tietyn ajan kuluessa vihollisalusten vauhti kasvaa
* Kun pelaaja ampuu vihollisaluksen, tämän pistemäärä päivittyy
* Kun vihollisalus saavuttaa ruudun alalaidan, pelaaja menettää elämän
* Kun pelaaja on menettänyt kaikki elämänsä, peli päättyy
### Pelin loppuessa
* Käyttäjä näkee loppuruudun, jossa lukee "Game Over" ja vaihtoehto "Play again"
* Jos pelaajan pisteet riittivät top10 tulokseen, näytetään ruutu, jossa lukee "You made it to top10"
  * Tekstin alla on syötekenttä, johon pelaaja antaa haluamansa nimimerkin
  * Syötekentän alla painike "Submit"
* Kun pelaaja painaa "submit", hänen tuloksensa tallennetaan tietokantaan
* Käyttäjä näkee alkuruudun

## Jatkokehitysideoita
Mikäli aika riittää
* Alkuruutuun vaihtoehto settings, jossa voi ainakin vaihtaa näppäimiä
* Peliin äänet ja mahdollisuus niiden pois päältä kytkemiseen
* Kaksinpeli
* Eri pelimoodeja
