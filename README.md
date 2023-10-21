# Projekt Aplikacji Medycznej


## Opis Projektu

Aplikacja medyczna to projekt oparty na Java, wykorzystujący dwa główne wzorce architektoniczne: Spring Web MVC i REST API. 

## Funkcje Aplikacji

Aplikacja oferuje wiele funkcji, które ułatwiają zarówno pacjentom, jak i lekarzom korzystanie z systemu. Oto główne funkcje dostępne dla obu grup użytkowników:

### Lekarze

- **Dostępność**: Lekarze mogą określić swoją dostępność na potencjalne wizyty, co pozwala pacjentom umawiać się na wizyty w wybranych terminach.
- **Rejestracja**: Nowi lekarze muszą zarejestrować się w aplikacji, aby zacząć korzystać z jej usług.
- **Notatki Medyczne**: Po odbyciu wizyty z pacjentem, lekarz może wprowadzić notatkę medyczną, która zostaje przechowana w systemie.
- **Historia Chorób**: Lekarze mają dostęp do historii chorób pacjenta, co ułatwia diagnozowanie i leczenie. 
- **Recepty**: Lekarze mogą wystawiać recepty i przypisywać leki dla danej wizyty. Wpisują ilość leków, ich łączną cenę oraz aktualizują dane pacjentów. Doktor może również zaktualizować swoje konto.

### Pacjenci

- **Umawianie Wizyt**: Pacjenci mogą umawiać się na wizyty do wybranych lekarzy w dostępnych terminach. Aplikacja obsługuje przypadki kolizji, gdy dwa pacjenci chcą się umówić na ten sam slot u tego samego lekarza.
- **Rejestracja**: Nowi pacjenci również muszą zarejestrować się, aby korzystać z aplikacji.
- **Historia Wizyt**: Pacjenci mogą sprawdzić historię odbytych wizyt oraz terminy nadchodzących wizyt.
- **Odwoływanie Wizyt**: Pacjenci mają możliwość odwołania wcześniej umówionych wizyt.
- **Notatki Medyczne**: Pacjenci mogą sprawdzić notatki medyczne napisane przez lekarzy do odbytych wizyt.
- **Recepty**: Lekarze mogą wystawiać recepty i przypisywać leki dla danej wizyty. Wpisują ilość leków i ich łączną cenę. Pacjent może również zaktualizować swoje konto.
## Domyślni Użytkownicy

W celu ułatwienia testowania aplikacji, udostępniam domyślnych użytkowników:

### Lekarze

1. Email: tom.shelby@medi.com, Hasło: test
2. Email: grace.shelby@medi.com, Hasło: test
3. Email: krzysztof.coolmedi.com, Hasło: test

### Pacjenci

1. Email: w.white@gmail.com, Hasło: test
2. Email: s.white@gmail.com, Hasło: test

## Techniczne Aspekty
Testy uruchamiane są w **testContainers**. Potrzebujemy zainstalowanego dockera na naszym systemie operacyjnym .Można również uruchomić aplikację na dockerze domyślnie pod portem 8081
### Testy
W ramach projektu przeprowadziliśmy różne rodzaje testów, w tym:
- **Unit Test**: Testy skupiające się na izolowaniu i testowaniu pojedynczych komponentów systemu, takich jak metody serwisów i kontrolerów.
- **Parametrized Tests**: Testy z różnymi zestawami parametrów, aby sprawdzić, czy aplikacja zachowuje się poprawnie w różnych scenariuszach.
- **WebMvcTest**: Testy warstwy kontrolerów Spring MVC.
- **RestAssured Tests**: Testy API REST za pomocą biblioteki RestAssured.
- **Data JPA Tests**: Testy integracji z bazą danych przy użyciu Spring Data JPA.
- **Spring Boot Application Test**: Testy całej aplikacji Spring Boot.

### Migracje Bazy Danych

Aby zainicjować tabele i dane w bazie danych, używamy migracji Flyway. Migracje pozwalają na zarządzanie schematem bazy danych i ewolucję struktury.

## Instrukcja Instalacji

1. Sklonuj repozytorium na swój lokalny komputer:

   ```bash
   git clone https://github.com/centGeek/HealthMeetProject
   ```

2. Przejdź do katalogu projektu:

   ```bash
   cd SciezkaDoTegoPliku
   ```

3. Skonfiguruj dostęp do bazy danych i inne ustawienia w pliku `application.properties`.


4. Uruchom aplikację, klikając na plik `HealthyMeetApplication.java`, a następnie odwiedź stronę internetową, domyślnie `http://localhost:8080/HealthMeet`.

## Kontakt

Jeżeli cię zaciekawił mój projekt, napisz pod adres email [centkowski.lukasz03@gmail.com].

Dziękuję za przeczytanie tych wypocin ;)!

