# Käyttöohje
Lataa tiedosto [spaceinvaders.jar](https://github.com/Pate1337/otm-harjoitustyo/releases/tag/v1.0) ja [config.properties](https://github.com/Pate1337/otm-harjoitustyo/releases/tag/v1.0).
Lisäksi sinun on ladattava tiedostot: [keys.txt](https://github.com/Pate1337/otm-harjoitustyo/releases/tag/v1.0), [space1.jpg](https://github.com/Pate1337/otm-harjoitustyo/releases/tag/v1.0),
[whitemute.png](https://github.com/Pate1337/otm-harjoitustyo/releases/tag/v1.0), [whitesoundon.png](https://github.com/Pate1337/otm-harjoitustyo/releases/tag/v1.0),
[menusound.wav](https://github.com/Pate1337/otm-harjoitustyo/releases/tag/v1.0).

## Konfigurointi
Tiedoston _config.properties_ muoto on seuraava:
```
backGround=utilities/images/space1.jpg
keyFile=utilities/files/keys.txt
menuSound=utilities/sounds/menusound.wav
whiteMuteIcon=utilities/images/whitemute.png
soundIconWhite=utilities/images/whitesoundon.png
```
Sinun tulee siis luoda uusi hakemisto utilities samaan kansioon, jossa tiedosto _spaceinvaders.jar_ sijaitsee ja lisätä sinne alihakemistot _images_, _sounds_ ja _files_.
Alihakemistoon images lisää tiedostot _space1.jpg_, _whitemute.png_ ja _whitesound.png_.
Alihakemistoon sounds lisää tiedosto _menusound.wav_.
Alihakemistoon files lisää tiedosto _keys.txt_.

## Ohjelman käynnistäminen
Ohjelma käynnistetään _spaceinvaders.jar_:n sisältämässä hakemistossa komennolla
```
java -jar spaceinvaders.jar
```
