# Työaikakirjanpito

 Pvm | Tunnit | Kuvaus
 ---- | ---- | ----
 27.03.18 | 1h | Sovelluksen vaatimusmäärittely ja työaikakirjanpidon pystyttäminen.
 27.03.18 | 1,5h | Mavenin käyttöönotto. Kaikenmaailman säätöä joutui taas tekemään.
 27.03.18 | 1h | Tutustuttu javaFX:ään. Ensimmäinen ikkuna luotu.
 28.03.18 | 1,5h | Opeteltu javaFX:n käyttöä. Lisätty näppäimistönkuuntelija alkunäyttöön.
 05.04.18 | 2h | Saatu jonkinnäköinen käsitys miten rakentaa sovellus. Aloiteltu refaktoroimalla mainia. Lisätty konfiguraatiotiedosto, josta sovelluksen taustakuva.
 05.04.18 | 2h | Menunäyttö saatu rakennettua. Toiminnallisuutta ei vielä.
 05.04.18 | 1h | Menunäyttöön saatu tapahtumankuuntelijat kun tiettyä menupainiketta painetaan.
 06.04.18 | 1,5h | Toiminnallisuus nyt sillä mallilla, että itse peliä voi ruveta koodaamaan. Playtä painaessa käynnistetään AnimationTimer.
 06.04.18 | 2h | Menun toimintaa paranneltu. Pystyy hallitsemaan näppäimistöllä ja hiirellä. Painikkeet muuttavat väriään kun ne ovat valittuina.
 06.04.18 | 2h | Menuun lisätty painikkeita. Painikkeet vaihtuvat riippuen laitetaanko peli paussille escapella vai ollaanko Main menussa.
 08.04.18 | 3,5h | Menuun lisätty varmistusikkuna, kun painetaan Quit tai Exit to main Menu.
 08.04.18 | 1,5h | Lisätty näppäimille enum-luokka ja tiedosto, joka sisältää bindatut näppäimet.
 08.04.18 | 1h | Muokattu menua vielä siten, että kun painetaan kesken pelin esciä, niin peli näkyy taustalla, mutta tummana. Samoin varmistusikkunan ollessa näkyvissä.
 08.04.18 | 1h | Lisätty menuun settings painike. Toiminnallisuus mietitty, mutta ei toteutettu.
 09.04.18 | 9h | Settings painikkeen toiminnallisuus. Painikkeita voi nyt muuttaa asetuksista. Asetukset eivät talletu vielä tiedostoon.
 10.04.18 | 2h | Lisätty Keyboard settingsiin painikkeet "Apply" ja "Back to Settings".
 10.04.18 | 2h | Muutetut näppäimet tallentuvat nyt tiedostoon, kun painetaan Apply settings, muutoin eivät.
 10.04.18 | 1h | Testiriippuvuudet ja kaksi testiä lisätty. Siistitty README.
 10.04.18 | 2h | Lisätty menuun äänet.
 12.04.18 | 4h | Menuun lisätty kuvapainike, joka kertoo onko äänet päällä vai pois. Kuvaketta painamalla kuva muuttuu ja äänet menevät päälle tai pois.
 12.04.18 | 0,5h | Lisätty Shoot -näppäin.
 12.04.18 | 1h | Generoitu jar ja luotu mukana ladattavat tiedostot.
 12.04.18 | 0,5h | Lisätty käyttöohje.
 13.04.18 | 2h | Alettu koodaamaan peliä. Luotu Game-luokka, josta peliä hallitaan. Sekä GameObject luokka joka sisältää pelihahmot (pelaaja, viholliset, ammukset).
 13.04.18 | 2h | Pelaaja liikkuu nyt oikein ja pystyy ampumaan. Vihollisia ei vielä tehty.
 13.04.18 | 2h | Lisätty ääniä sekä countdown pelin alkuun.
 15.04.18 | 5h | Luotu vihollisalukset ja logiikka, jolla peli etenee. Opeteltu käyttämään Pintaa kuvien piirtämiseen ja piirretty pelaajan alus.
 15.04.18 | 2h | Pelissä nyt myös elämät ja pisteet. Peli päättyy kun elämät loppuvat. Luokan Game collisions()-metodi pitää vielä korjata jossain vaiheessa. Aiheuttaa "java.util.ConcurrentModificationException" virheen kun miljoona vihollisalusta ruudussa.
