# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus on peli, joka poikkeaa vanhasta klassikosta Space Invadersistä siinä määrin, että vihollisalukset lähenevät pelaajaa ruudun yläreunasta yksitellen. Pelaajan tavoitteena on ampua mahdollisimman monta vihollisalusta, ennen kuin tämän "elämät" loppuvat.

## Käyttäjät

Kaikki käyttäjät ovat pelin pelaajia.

## Perusversion tarjoama toiminnallisuus

### Ennen pelin alkua
* Käyttäjä näkee pelin alkuruudun, jossa vaihtoehdot "Play", "Highscores", "Settings" ja "Quit".
* Menun oikeassa alakulmassa painike, jota painamalla pelin äänet saa pois päältä.
* Jos käyttäjä painaa "Highscores"
  * Näytetään tietokannassa olevat Top10 tulokset
  * Näytetään painike "Back to menu"
* Jos käyttäjä painaa "Settings"
  * Näytetään painikkeet "Keyboard" ja "Back to menu".
    * Painamalla "Keyboard" käyttäjä voi asettaa haluamansa näppäimet peliin.
### Pelin alkaessa
* Ruudun vasemmassa yläkulmassa näytetään pelaajan jäljellä olevat elämät (3 elämää)
* Ruudun oikeassa yläkulmassa näytetään pelaajan pisteet
* Pelaaja liikku horisontaalisesti ruudun alalaidassa A ja D näppäimillä
* Vihollisalukset tulevat yksi kerrallaan ruudun ylälaidasta
  * Liikkuvat suoraan alaspäin
  * Vihollisalusten "spawnaus"-tiheys kasvaa, aina kun 10 normaalia vihollisalusta on tuhottu.
* "Bonus"-viholliset spawnaavat 10 normaalin vihollisaluksen välein.
  * Lähtevät oikealta tai vasemmalta ja liikkuvat horisontaalisesti.
* Kun pelaaja ampuu normaalin vihollisaluksen, tämän pistemäärä kasvaa 50 pisteellä
* Kun pelaaja ampuu bonusaluksen, tämän pistemäärä kasvaa 300 pisteellä.
* Jos vihollisalus saavuttaa ruudun alalaidan, pelaaja menettää elämän
* Kun pelaaja on menettänyt kaikki elämänsä, peli päättyy
### Pelin loppuessa
* Käyttäjä näkee loppuruudun, jossa lukee "Game Over" ja vaihtoehto "Play again"
* Jos pelaajan pisteet riittivät top10 tulokseen, näytetään ruutu, jossa lukee "You made it to top10"
  * Tekstin alla on syötekenttä, johon pelaaja antaa haluamansa nimimerkin
  * Syötekentän vieressä painike "Submit"
  * Alla painikkeet "Play again" ja "Back to menu", joita painamalla pelaaja voi jatkaa lisäämättä tulostaan highscores-listaan.
* Kun pelaaja painaa "submit", hänen tuloksensa tallennetaan tietokantaan
  * Tämän jälkeen pelaaja ohjataan highscores-listaukseen, josta painamalla "Back to menu" pääsee takaisin main menuun.

## Jatkokehitysideoita
Mikäli aika riittää
* Kaksinpeli
* Eri pelimoodeja
  * Pelimoodi 1:
    * Pelissä on erikokoisia vihollisaluksia (pieni ja iso)
    * Pelaajalla on pelin alkaessa tietty määrä ammuksia (esim. 10)
    * Pelaajalla ei ole elämiä, vain ammukset.
    * Vihollisalukset spawnaavat samaantapaan kuin originaalissa, mutta pienemmällä tahdilla.
    * Kun pelaaja osuu pieneen vihollisalukseen, tämä saa 2 ammusta lisää ja 100 pistettä.
    * Kun pelaaja osuu isoon vihollisalukseen, tämä saa 1 ammuksen lisää ja 50 pistettä.
    * Aina kun pelaaja ampuu, ammukset vähenevät yhdellä.
    * Jos vihollisalus pääsee ruudun alareunaan, ammukset vähenevät yhdellä.
    * Peli päättyy, kun pelaajan ammukset loppuvat.
    * Pelimoodin pointti on siis olla ampumatta ohi.
* Asetuksissa mahdollista muuttaa pelaajan liikkumisnopeutta.
* Pelin äänenvoimakkuuden säätö ja peliin taustamusiikki.
