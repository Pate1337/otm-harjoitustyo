# Space Invaders

Projekti kurssille Ohjelmistotekniikan menetelmät. Sovellus on peli, joka pohjautuu vanhaan arcadepeliin Space Invaders.

## Dokumentaatio

[Käyttöohje](https://github.com/Pate1337/otm-harjoitustyo/blob/master/harjoitustyo/documentation/kayttoohje.md)

[Vaatimusmäärittely](https://github.com/Pate1337/otm-harjoitustyo/blob/master/harjoitustyo/documentation/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/Pate1337/otm-harjoitustyo/blob/master/harjoitustyo/documentation/tuntikirjanpito.md)

[Arkkitehtuuri](https://github.com/Pate1337/otm-harjoitustyo/blob/master/harjoitustyo/documentation/arkkitehtuuri.md)

## Releaset

[v1.3](https://github.com/Pate1337/otm-harjoitustyo/releases/tag/v1.3)

## Komentorivitoiminnot

### Testaus
Testit suoritetaan komennolla
```
mvn test
```
Testikattavuusraportin voi luoda komennolla
```
mvn test jacoco:report
```
Kattavuusraporttia voi tarkastella selaimella osoitteesta  /target/site/jacoco/index.html

### Suoritettavan jarin generointi
Komento
```
mvn package
```
generoi hakemistoon target suoritettavan jar-tiedoston SpaceInvaders-1.0-SNAPSHOT.jar

### Checkstyle
Tiedostoon [checkstyle.xml](https://github.com/Pate1337/otm-harjoitustyo/blob/master/SpaceInvaders/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla
```
mvn jxr:jxr checkstyle:checkstyle
```
Mahdollisia virheilmoituksia pääsee tarkastelemaan selaimella osoitteessa target/site/checkstyle.html

### JavaDoc
JavaDoc generoidaan komennolla
```
mvn javadoc:javadoc
```
JavaDocia voi tarkastella selaimella osoitteessa /target/site/apidocs
