# TeilHabe -- Serverseitige Anwendung

## Über dieses Projekt
Dieses Projekt ist eine Springboot-Anwendung. Sie stellt einen
einfachen REST-Service für die [TeilHabe-Client-Anwendung](https://github.com/thomas-gantenbein-tga/sharingapp_serverside) 
zur Verfügung. Der Service wird als Google-Appengine-Anwendung deployt.

Maven-Setup übernommen von einem [Google-Beispielprojekt](https://github.com/GoogleCloudPlatform/getting-started-java/tree/master/appengine-standard-java8/springboot-appengine-standard).

## Features
* Ressourcen können mit dem REST-Service 
  * erstellt
  * durchsucht, 
  * geholt und 
  * gelöscht werden.
* Status-Codes geben in den HTTP-Responses Auskunft über das Resultat der jeweiligen Anfrage.
* Ergänzungen des Services sind einfach: Methode hinzufügen und die richtige Annotation hinzufügen.
* "Items", die im Request-Body ankommen, werden automatisch deserialisiert und umgekehrt
  in der Response wieder als JSON serialisiert. Bilddaten werden als Base64-encoded Strings geliefert.

## Nutzung online
Die Anwendung ist bereits deployt und online. Um alle geteilten Gegenstände 
als JSON abzurufen, kann zum Beispiel die folgende URL aufgerufen werden:
http://fabled-coder-210208.appspot.com/items.


## Lokale Ausführung
Um den Rest-Service lokal (localhost:8080) laufen zu lassen:

1. Anleitung von Google befolgen: [Apache Maven und das (Cloud SDK-basierte) App Engine-Plug-in verwenden](https://cloud.google.com/appengine/docs/standard/java/tools/using-maven).
2. Kommandozeile öffnen, in das Verzeichnis mit dem Maven-Projekt wechseln und dort `mvn appengine:run`
   ausführen.
3. Lokalen Datastore durchsuchen: http://localhost:8080/_ah/admin
4. Beispiele für Get-Requests:
  * `http://localhost:8080/items` listet alle Gegenstände als JSON (ohne Bildinhalt).
  * `http://localhost:8080/items?ownerId=tgantenbein&description=gelb` listet alle
     Gegenstände, die unter tgantenbein erfasst sind und "gelb" in der Beschreibung enthalten.
  * `http://localhost:8080/items/1234` liefert den Gegenstand mit ID 1234 -- falls vorhanden.
   
## Auf Google Appengine deployen
Um den Service auf Google Appengine zu betreiben, alle Installationen gemäss dem Absatz
oben durchführen, eine Kommandozeile öffnen, in das Verzeichnis mit dem Maven-Projek wechseln
und mvn `appengine:deploy` ausführen.

## Limitierungen
* Der REST-Service kann von jedem ohne Anmeldung benutzt werden.
* Fehlerhandling ist nicht implementiert.
* Auch die Bilder werden direkt in den DataStore geschrieben statt auf ein Filesystem.

