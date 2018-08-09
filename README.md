Sharing app server-side application
============================

## Maven
### Running locally

`mvn appengine:run`

To use vist: http://localhost:8080/

### Deploying

`mvn appengine:deploy`

To use vist:  https://YOUR-PROJECT-ID.appspot.com

## Browsing local datastore
http://localhost:8080/_ah/admin

## Adding an item via curl
### Localhost
```
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"ownerId":"tgantenbein","title":"Super Teil","category":"Haushalt","description":"Ein wirklich super Teil","city":"Winterthur","zipCode":"8408","telephoneNumber":"111"}' \
  --verbose \
  http://localhost:8080/items/add
```

### Server
```
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"ownerId":"tgantenbein","title":"Super Teil","category":"Haushalt","description":"Ein wirklich super Teil","city":"Winterthur","zipCode":"8408","telephoneNumber":"111"}' \
  --verbose \
  https://fabled-coder-210208.appspot.com/items/add
```

```
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"ownerId":"mherzog","title":"Super Teil","category":"Haushalt","description":"Ein wirklich superduper Teil","city":"Frauenfeld","zipCode":"8500","telephoneNumber":"113"}' \
  --verbose \
  https://fabled-coder-210208.appspot.com/items/add
```
  
## Deleting an item via curl
```
curl --header "Content-Type: application/json" \
  --data '' \
  --request POST \
  --verbose \
  http://localhost:8080/items/delete/3081ef07-e549-4c70-9f1d-98bd30a5b354
```
  
## Search
* https://fabled-coder-210208.appspot.com/items?userId=tgantenbein&description=super
* https://fabled-coder-210208.appspot.com/items?description=superduper
* https://fabled-coder-210208.appspot.com/items/searchByOwner/mherzog


## Testing

`mvn verify`

