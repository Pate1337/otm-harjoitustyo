# Arkkitehtuurikuvaus

## Päätoiminnallisuudet
Sovelluksen toimintalogiikkaa kuvattuna sekvenssikaavioilla.
### Pelin aloittaminen
Pelin main menusta sovelluksen toiminta etenee seuraavasti:
<img src="https://raw.githubusercontent.com/Pate1337/otm-harjoitustyo/master/harjoitustyo/documentation/kuvat/PelinAloitus.png" width="750">
Kun käyttäjä painaa menussa painiketta "Play", luodaan menun AnimationTimerissa uusi Game()-olio ja tallennetaan se muuttujaan. Tämän jälkeen kutsutaan käyttöliittymän metodia drawGame() ja pysäytetään menun AnimationTimer.
Metodissa drawGame() luodaan uusi grouppi mainGroup ja asetetaan mainStageen uusi scene gameScene, joka käyttää äsken luotua mainGrouppia.
