Sharing app server-side application
============================

## Maven
### Running locally

`mvn appengine:run`

To use vist: http://localhost:8080/

### Deploying

`mvn appengine:deploy`

To use vist:  https://YOUR-PROJECT-ID.appspot.com

### Browsing local datastore
http://localhost:8080/_ah/admin

### Adding an item via curl
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"ownerId":"tgantenbein","title":"Super Teil","category":"Haushalt","description":"Ein wirklich super Teil","city":"Winterthur","zipCode":"8408","telephoneNumber":"111"}' \
  --verbose \
  http://localhost:8080/items/add

## Testing

`mvn verify`

