# Securities database
Простое api-приложение, для работы с базой данных, хранящей информацию о ценных бумагах и истории их торгов. 
Поддерживаются операции добавления, изменения, удаления информации, а также добавление информации из .xml файлов. Для обработки HTTP-запросов к серверу создан REST-контроллер средствами Spring-boot. Для взаимодействия с базой данных Postgresql используется Spring-data-JPA.
Присутствует веб-интерфейс для работы с приложением (HTML+Javascript).


## Запуск приложения
1. Необходим Postgresql сервер. Для создания базы данных выполните скрипт myApp\DBCreateScript.sql на сервере.
2. По умолчанию приложение обратится к Postgresql серверу с параметрами username=postgres и password=postgres.
Если вы используете другие учетные данные, измените в файле sec-db-1.0.jar\BOOT-INF\classes\application.properties строки:    
spring.datasource.username=    
spring.datasource.password=    
3. Запустите myApp\sec-db-1.0.jar.
4. API будет доступно по адресу localhost:8080/api.    

По адресу localhost:8080/ будет доступен вёб-интерфейс для работы с приложением.

## Описание API 
### Ценные бумаги(Securities)

```http
GET api/securities
```
Возвращает массив всех записей ценных бумаг из базы данных:
```json
[
{
    "id":85909,
    "secid":"acru",
    "regnumber":"1-03-01692-A",
    "name":"Открытое акционерное общество \"Авиационная корпорация \"Рубин\"",
    "emitentTitle":"публичное акционерное общество \"Авиационная корпорация \"Рубин\""
},
...
]
```
404 NOT FOUND - записи не найдены.
____
```http
PUT api/securities
Content-Type: 'application/x-www-form-urlencoded'
body: 'id=4444&secid=AAAA&regnumber=reg4444&name=nameAAAA&emitentTitle=EmitentName'
```
id - тип int,  
secid - уникальное значение, при повторном использовании запись обновится,    
Создаёт или обновляет запись:    
200 OK - запись создана или обновлена,    
400 BAD REQUEST - неверные данные.

____
```http
POST api/securities
Content-Type: 'multipart/form-data'
``` 
Загружает записи из .xml файлов:    
200 OK – записи из файлов загружены в базу данных,    
415 UNSUPPORTED MEDIA FILE – неверный формат данных в файле.
____
```http
DELETE api/securities
Content-Type: 'application/x-www-form-urlencoded'
body: 'secid=AFKS'
``` 
Удаляет запись, при этом удаляются все связанные записи истории торгов:    
200 OK – запись удалена,    
204 NO CONTENT – запись не найдена.
### Истории торгов (Histories)
```http
GET api/histories
```
Возвращает массив всех записей торгов из базы данных:
```json
[
{
  "tradedate":"2020-04-15",
  "numtrades":58827,
  "open":75.04,
  "close":72.1,
  "secid":"AFLT"
},
...
]
```
404 NOT FOUND - записи не найдены.
____
```http
PUT api/histories
Content-Type: 'application/x-www-form-urlencoded'
body: 'secid=AFLT&tradedate=2020-04-15&numtrades=58827&open=75.04&close=72.1'
```
tradedate – тип String,    
numtrades - тип int,     
необходимо, чтобы существовала запись ценной бумаги с таким secid,    
для каждого secid значение tradedate не может дублироваться, при повторном использовании запись обновится,    
Создаёт или обновляет запись:    
200 OK - запись создана или обновлена,    
400 BAD REQUEST - неверные данные,    
406 NOT ACCEPTABLE – не найдена ценная бумага с таким secid.
____
```http
POST api/histories
Content-Type: 'multipart/form-data'
``` 
Загружает записи из .xml файлов:    
200 OK – записи из файлов загружены в базу данных,    
415 UNSUPPORTED MEDIA FILE – неверный формат данных в файле.    
При отсутствии ценной бумаги для конкретной истории, запись в базу данных этой истории не производится.
____
```http
DELETE api/histories
Content-Type: 'application/x-www-form-urlencoded'
body: 'id=AFKS2020-04-15'
``` 
Удаляет запись:    
200 OK – запись удалена,    
204 NO CONTENT – запись не найдена.

### Поиск по фильтру (Info)
```http
GET api/info?secid=BISV&date=2020-04-15
```
Параметры secid и date обязательны в запросе. Поиск производится по точному соответствию с параметрами.    
Если параметр = ”” – фильтрация по нему не производится.    
Возвращает массив найденных записей:
```json
[
{
  "secid":"BISV",
  "regnumber":"1-01-00011-A",
  "name":"Башинформсвязь(ПАО) ао",
  "emitentTitle":"Публичное акционерное общество \"Башинформсвязь\"",
  "tradedate":"2020-04-15",
  "numtrades":0,
  "open":0.0,
  "close":0.0
},
...
]
```
404 NOT FOUND - записи не найдены.
### Ожидаемые исключения
При запросах могут возникнуть исключения. Ожидаемые исключения возвращают:
```json
{
    "error": "[ERROR_DESCRIPTION]"
}
```
