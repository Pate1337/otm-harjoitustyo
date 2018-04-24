# Arkkitehtuurikuvaus

## Päätoiminnallisuudet
Sovelluksen toimintalogiikkaa kuvattuna sekvenssikaavioilla.
### Pelin aloittaminen
Pelin main menusta sovelluksen toiminta etenee seuraavasti:
<img src="https://raw.githubusercontent.com/Pate1337/otm-harjoitustyo/master/harjoitustyo/documentation/kuvat/PelinAloitus.png" width="750">

Kun käyttäjä painaa menussa painiketta "Play", luodaan menun AnimationTimerissa uusi Game()-olio ja tallennetaan se muuttujaan. Tämän jälkeen kutsutaan käyttöliittymän metodia drawGame() ja pysäytetään menun AnimationTimer.
Metodissa drawGame() luodaan uusi grouppi mainGroup ja asetetaan mainStageen uusi scene gameScene, joka käyttää äsken luotua mainGrouppia.

### Pelin loppuminen
Peli päättyy, kun pelaajan elämät loppuvat. Toiminta kuvattuna:
<img src="https://raw.githubusercontent.com/Pate1337/otm-harjoitustyo/master/harjoitustyo/documentation/kuvat/PelinLoppu.png" width="750">

drawGame()-metodin AnimationTimerissa jokaisella suorituskerralla, kutsutaan Gamen metodia getLifes(). Jos metodi palauttaa 0, kutsutaan metodia drawEndScreen() ja AnimationTimerin suoritus lopetetaan. Game Over-ikkuna piirretään mainStageen gameScenen päälle, siten että peli näkyy taustalla tummennettuna.
