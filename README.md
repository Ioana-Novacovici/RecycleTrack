# Recycle Track - Aplicație web pentru gestionarea colectării deșeurilor reciclabile

Pași necesari pentru configurarea mediului de lucru și rularea aplicației

- Crearea unui director propriu în care se clonează repository-ul

Frontend

1. Instalare Visual Studio Code (https://code.visualstudio.com/download)
2. Instalare Noje.js și npm (https://nodejs.org/en/download/prebuilt-installer); Este necesară setarea și configurarea variabilei de mediu pentru Node, după instalare
3. Deschiderea folderuui gcs-frontend în Visual Studio Code
4. Rularea comenzii `npm install` pentru instalarea tuturor modulelor node de dependențe
5. Navigarea în primul subdirector și rularea comenzii `npm run dev`, prin care se pornește aplicația
6. Accesarea interfeței în browser la adresa http://localhost:5173/

Baza de date

1. Instalarea MySQL Workbench (https://dev.mysql.com/downloads/workbench/); Este necesară setarea și configurarea variabilei de mediu pentru sql, după instalare
2. Crearea unei noi conexiuni din MySQL Workbench (numele conexiunii nu este important) cu username-ul `root`, iar parola `root` pentru compatibilitate cu setările din fișierul `application.properties` al aplicației de backend
3. Se testează și deschide conexiunea după setarea userului și parolei
4. Se creează o nouă bază de date din meniul `query` al MySQL Workbench: `CREATE NEW DATABASE gcs;`

Observații: În cazul în care se setează diferit numele bazei de date, userul si parola pentru stabilirea conexiunii la baza de date, va fi necesară modificarea proprietăților din aplicația backend, din fișierul `application.properties`
Configurațiile de interes sunt următoarele

```
spring.datasource.url=jdbc:mysql://localhost:3306/gcs
spring.datasource.username=root
spring.datasource.password=root
```

Backend

1. Descărcare și instalare Java 17(https://www.oracle.com/java/technologies/downloads/#jdk17-windows); Este necesară setarea și configurarea variabilei de mediu pentru Java, după instalarea JDK
2. Instalare IntelliJ IDEA (https://www.jetbrains.com/idea/download/?fromIDE=&section=windows)
3. Deschiderea folderului gcs-backend ca proiect în IntelliJ IDEA, după clonarea reposiory-ului în prealabil
4. Pornirea aplicației din meniul superior al interfeței IntelliJ(butonul sub formă de săgeată verde 'Run')
5. Dacă setările pentru conexiunea la baza de date au fost făcute corect, la pornire se vor genera tabelele în baa de date
6. Swagger UI se va putea accesa în browser la adresa http://localhost:8080/swagger-ui/index.html
