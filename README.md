Java Pacman Game

Prosta implementacja klasycznej gry Pacman stworzona w celach edukacyjnych, skupiająca się na czystej architekturze i mechanice gier 2D.

## Cechy projektu
* **Architektura MVC:** Wyraźny podział na Model (logika gry), View (renderowanie Swing) oraz Controller (obsługa klawiatury).
* **Globalny skrót klawiszowy:** Kombinacja klawiszy CTRL + SHIFT + Q w dowolnym momencie wraca do menu głównego.
* **Zapisywanie wyników:** Gra automatycznie tworzy plik 'Highscores.dat' jeśli nie istnieje i zapisuje rekordy między sesjami.
* **Losowy generator plansz:** Każda rozgrywka jest inna, bo plansze są za każdym razem generowane od zera. Użytkownik ustala wymiary planszy, a generato się dostosowywuje.
* **Nieskończona rozgrywka:** Grać można w nieskończoność, bo po zebraniu wszystkich punktów gra nagradza gracza punktami i generuje poziom od nowa.

## 🛠️ Technologie
* **Język:** Java (major release: 67)
* **Biblioteki:** Swing, AWT
* **Architektura:** Model-View-Controller (MVC)
---
*Projekt zrealizowany w ramach nauki programowania obiektowego w Javie.*
